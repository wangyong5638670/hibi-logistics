/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-08-05
 */
package com.ylink.hibiscus.logistics.service.plan;

import com.ylink.hibiscus.common.base.enums.SequenceKey;
import com.ylink.hibiscus.common.base.utils.DateUtils;
import com.ylink.hibiscus.entity.logistics.plan.PlanLine;
import com.ylink.hibiscus.entity.logistics.plan.PlanLineGroup;
import com.ylink.hibiscus.entity.logistics.plan.PlanLineParcel;
import com.ylink.hibiscus.logistics.service.base.SequenceManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 出入库计划线路袋manager
 *
 * @Author He Bingxing
 * @Date 2019-08-05
 */
@Service
public class PlanLineParcelBusinessManagerImpl implements PlanLineParcelBusinessManager {

    @Autowired
    private PlanLineParcelManager planLineParcelManager;
    @Autowired
    private PlanLineManager planLineManager;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private PlanLineGroupManager planLineGroupManager;

    /**
     * 更新计划线路的袋数量
     *
     * @param planLineId   计划线路id
     * @param parcelNumber 袋数量
     * @return void
     * @author He Bingxing
     * @date 2019-08-06
     */
    private void updatePlanLine(String planLineId, int parcelNumber) {
        // t_plan_line
        PlanLine planLine = new PlanLine();
        planLine.setId(planLineId);
        planLine.setParcelNumber(parcelNumber);
        planLineManager.update(planLine);
    }

    /**
     * 创建袋组信息
     *
     * @param planLineParcels 袋组包含袋信息
     * @return void
     * @author He Bingxing
     * @date 2019-08-06
     */
    private void createPlanLineGroup(List<PlanLineParcel> planLineParcels) {
        // 新增的袋组
        PlanLineGroup planLineGroup = new PlanLineGroup();
        // t_plan_line_group
        String groupId = sequenceManager.next(SequenceKey.PLAN_LINE_GROUP.name());
        planLineGroup.setId(groupId);
        planLineGroup.setMinNum(1);
        planLineGroup.setTotal(planLineParcels.size());
        planLineGroupManager.create(planLineGroup);

        // 赋值袋组编号
        planLineParcels.forEach(planLineParcel -> {
            planLineParcel.setGroupId(groupId);
            planLineParcelManager.update(planLineParcel);
        });
    }




    @Override
    public void create(@NotBlank String planLineId, @NotNull List<PlanLineParcel> planLineParcels) {
        // t_plan_line_parcel
        planLineParcels.forEach(planLineParcel -> {
            if (StringUtils.isBlank(planLineParcel.getId())){
                planLineParcel.setId(sequenceManager.next(SequenceKey.PLAN_LINE_PARCEL.name()));
            }
            planLineParcelManager.create(planLineParcel);
        });
        // 更新计划线路的袋数量
        updatePlanLine(planLineId, planLineParcels.size());
    }

    @Override
    public void update(@NotBlank String planLineId, @NotNull List<PlanLineParcel> planLineParcels) {
        /*已有数据处理*/
        // 已有所有袋
        List<PlanLineParcel> planLineParcelsDB = planLineParcelManager.listWithPlanLineId(planLineId);
        if (CollectionUtils.isEmpty(planLineParcelsDB)) {
            create(planLineId, planLineParcels);
        } else {
            // 保留的袋
            List<PlanLineParcel> planLineParcelsKeep = planLineParcelsDB.stream().filter(planLineParcelDB -> planLineParcels.stream().map(PlanLineParcel::getParcelId).collect(Collectors.toList()).contains(planLineParcelDB.getParcelId())).collect(Collectors.toList());
            // 删掉的袋
            List<PlanLineParcel> planLineParcelsDel = planLineParcelsDB.stream().filter(planLineParcelDB -> !(planLineParcels.stream().map(PlanLineParcel::getParcelId).collect(Collectors.toList()).contains(planLineParcelDB.getParcelId()))).collect(Collectors.toList());
            // 新增的袋
            List<PlanLineParcel> planLineParcelsNew = planLineParcels.stream().filter(planLineParcelNew -> !(planLineParcelsKeep.stream().map(PlanLineParcel::getParcelId).collect(Collectors.toList()).contains(planLineParcelNew.getParcelId()))).collect(Collectors.toList());

            /*处理删除的袋*/
            // 删掉的袋归属袋组
            List<String> groupIdsDel = planLineParcelsDel.stream().map(PlanLineParcel::getGroupId).distinct().collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(planLineParcelsDel)) {
                // 需要删除的袋组
                List<String> groupIdsToDel = groupIdsDel.stream().filter(groupId -> !(planLineParcelsKeep.stream().map(PlanLineParcel::getGroupId).distinct().collect(Collectors.toList()).contains(groupId))).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(groupIdsToDel)) {
                    groupIdsToDel.forEach(groupId -> planLineGroupManager.remove(groupId));
                }
                planLineParcelManager.remove(planLineParcelsDel);
            }

            /*保留的袋，更新数据*/
            if (!CollectionUtils.isEmpty(planLineParcelsKeep)) {
                planLineParcelsKeep.forEach(planLineParcelDB -> {
                    planLineParcels.forEach(planLineParcelNew -> {
                        if (Objects.equals(planLineParcelDB.getParcelId(),planLineParcelNew.getParcelId())){
                            planLineParcelDB.setParcelId(planLineParcelNew.getParcelId());
                            planLineParcelDB.setParcelName(planLineParcelNew.getParcelName());
                            planLineParcelDB.setParcelLevel(planLineParcelNew.getParcelLevel());
                            planLineParcelDB.setStaffId(planLineParcelNew.getStaffId());
                            planLineParcelDB.setStaffName(planLineParcelNew.getStaffName());
                        }
                    });
                });
                planLineParcelManager.update(planLineParcelsKeep);
            }

            /*新增的袋*/
            if (!CollectionUtils.isEmpty(planLineParcelsNew)) {
                planLineParcelsNew.forEach(planLineParcel -> {
                    if (StringUtils.isBlank(planLineParcel.getId())){
                        planLineParcel.setId(sequenceManager.newTransactionNext(SequenceKey.PLAN_LINE_PARCEL.name()));
                        planLineParcel.setCreatedTime(DateUtils.getCurrentDate());
                    }
                });
                planLineParcelManager.create(planLineParcelsNew);
            }

            // 更新计划线路的袋数量
            updatePlanLine(planLineId, planLineParcels.size());
        }
    }

    @Override
    public void save(@NotBlank String planLineId, @NotNull List<PlanLineParcel> planLineParcels) {
        // 袋组
        Map<String, List<PlanLineParcel>> groups = planLineParcels.stream().collect(Collectors.groupingBy(PlanLineParcel::getGroupId));

        // 数据库已有袋组数据
        List<PlanLineParcel> groupsDB = planLineParcelManager.listGroupsWithPlanLineId(planLineId);
        if (CollectionUtils.isEmpty(groupsDB)) {
            groups.forEach((k, v) -> createPlanLineGroup(v));
        } else {
            // 已有袋组id
            List<String> groupIds = groupsDB.stream().map(PlanLineParcel::getGroupId).distinct().collect(Collectors.toList());
            // 保留的袋组
            List<PlanLineParcel> groupsKeep = groupsDB.stream().filter(planLineParcel -> groups.keySet().contains(planLineParcel.getGroupId())).collect(Collectors.toList());
            // 已删除的袋组
            List<PlanLineParcel> groupsDel = groupsDB.stream().filter(planLineParcel -> !(groups.keySet().contains(planLineParcel.getGroupId()))).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(groupsDel)) {
                List<String> groupIdsDel = groupsDel.stream().map(PlanLineParcel::getGroupId).distinct().collect(Collectors.toList());
                // 删除袋组
                groupIdsDel.forEach(groupId -> planLineGroupManager.remove(groupId));
                // 更新袋信息
                groupsDel.forEach(planLineParcel -> planLineParcel.setGroupId(null));
                planLineParcelManager.update(groupsDel);
            }

            // 清除保留的袋组信息
            if (!CollectionUtils.isEmpty(groupsKeep)){
                groupsKeep.forEach(planLineParcel -> planLineParcel.setGroupId(null));
                planLineParcelManager.update(groupsKeep);
            }

            // 新增/编辑
            groups.forEach((k, v) -> {
                // 保留的袋组
                if (groupIds.contains(k)) {
                    v.forEach(parcel -> {
                        parcel.setGroupId(k);
                        planLineParcelManager.update(parcel);
                    });
                } else {
                    // 新增的袋组
                    createPlanLineGroup(v);
                }
            });
        }

        // t_plan_line
        PlanLine planLine = new PlanLine();
        planLine.setId(planLineId);
        planLine.setParcelGroup(Boolean.TRUE);
        planLineManager.update(planLine);
    }

    @Override
    public void emptyPlanLineParcel(@NotBlank String planLineId) {
        // 已有数据
        List<PlanLineParcel> planLineParcels = planLineParcelManager.listGroupsWithPlanLineId(planLineId);
        if (!CollectionUtils.isEmpty(planLineParcels)){
            List<String> groupIds = planLineParcels.stream().map(PlanLineParcel::getGroupId).collect(Collectors.toList());
            groupIds.forEach(groupId -> planLineGroupManager.remove(groupId));
        }
        planLineParcelManager.deleteWithPlanLineId(planLineId);
        // 更新计划线路
        PlanLine planLine = new PlanLine();
        planLine.setId(planLineId);
        planLine.setParcelGroup(Boolean.FALSE);
        planLine.setParcelNumber(0);
        planLineManager.update(planLine);
    }

    @Override
    public void emptyPlanLineGroup(@NotBlank String planLineId) {
        List<PlanLineParcel> groupsDB = planLineParcelManager.listGroupsWithPlanLineId(planLineId);
        if (!CollectionUtils.isEmpty(groupsDB)) {
            groupsDB.forEach(parcel -> {
                planLineGroupManager.remove(parcel.getGroupId());
                parcel.setGroupId(null);
            });
            planLineParcelManager.update(groupsDB);

            // 更新计划线路
            PlanLine planLine = new PlanLine();
            planLine.setId(planLineId);
            planLine.setParcelGroup(Boolean.FALSE);
            planLineManager.update(planLine);
        }
    }
}
