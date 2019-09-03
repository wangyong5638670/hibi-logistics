/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-08-01
 */
package com.ylink.hibiscus.logistics.service.plan;

import com.ylink.hibiscus.common.base.enums.SequenceKey;
import com.ylink.hibiscus.common.base.enums.Status;
import com.ylink.hibiscus.common.base.utils.DateUtils;
import com.ylink.hibiscus.entity.logistics.business.Line;
import com.ylink.hibiscus.entity.logistics.plan.Plan;
import com.ylink.hibiscus.entity.logistics.plan.PlanLine;
import com.ylink.hibiscus.exception.logistics.DepotBusinessException;
import com.ylink.hibiscus.logistics.service.base.SequenceManager;
import com.ylink.hibiscus.logistics.service.business.LineManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 出入库计划业务manager
 *
 * @Author He Bingxing
 * @Date 2019-08-01
 */
@Service
public class PlanBusinessManagerImpl implements PlanBusinessManager {

    @Autowired
    private PlanManager planManager;
    @Autowired
    private PlanLineManager planLineManager;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private LineManager lineManager;

    @Override
    public void create(@NotNull Plan plan) {
        try {
            /*计划*/
            String planId;
            // 计划id为空处理
            if (StringUtils.isBlank(plan.getId())) {
                planId = sequenceManager.newTransactionNext(SequenceKey.PLAN.name());
                plan.setId(planId);
            } else {
                planId = plan.getId();
            }
            plan.setStatus(Status.NORMAL.name());
            plan.setCreatedTime(DateUtils.formatDate(DateUtils.getCurrentDate(),DateUtils.DATE_FORMAT_yyyy_MM_dd));
            plan.setDef(Boolean.FALSE);

            planManager.create(plan);

            /*计划线路*/
            // 基础数据--线路
            List<Line> lines = lineManager.listWithSiteIdStats(plan.getSiteId(), Status.NORMAL);
            if (CollectionUtils.isEmpty(lines)) {
                throw new DepotBusinessException(String.format("基础业务数据--站点[%s]线路数据未初始化", plan.getSiteId()));
            }

            // 计划线路
            PlanLine planLine;
            List<PlanLine> planLines = new ArrayList<>(lines.size());
            for (Line line : lines) {
                planLine = new PlanLine();

                planLine.setId(sequenceManager.newTransactionNext(SequenceKey.PLAN_LINE.name()));
                planLine.setPlanId(planId);
                planLine.setPlanName(plan.getName());
                planLine.setLineId(line.getId());
                planLine.setLineName(line.getName());
                planLine.setParcelNumber(0);
                planLine.setParcelGroup(Boolean.FALSE);
                planLine.setStatus(Status.NORMAL.name());
                planLine.setCreatedTime(DateUtils.getCurrentDate());

                planLines.add(planLine);
            }

            planLineManager.create(planLines);
        } catch (Exception e) {
            throw new DepotBusinessException(e.getMessage());
        }

    }

    @Override
    public Plan detail(@NotBlank String planId) {
        try {
            /*计划信息*/
            Plan plan = planManager.get(planId);
            /*计划线路*/
            List<PlanLine> planLines = planLineManager.listWithPlanId(planId);
            plan.setPlanLines(planLines);
            return plan;
        } catch (Exception e) {
            throw new DepotBusinessException(e.getMessage());
        }
    }

    @Override
    public Plan query(@NotBlank String siteId, @NotBlank String cycle, @NotBlank String type, String customizeDate) {
        return planManager.query(siteId, cycle, type, customizeDate);
    }
}
