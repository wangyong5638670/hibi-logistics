package com.ylink.hibiscus.logistics.service.plan;

import com.ylink.hibiscus.entity.logistics.plan.PlanLineParcel;

import java.util.List;

/**
 * 出入库计划线路袋manager
 *
 * @Author He Bingxing
 * @Date 2019-08-05
 */
public interface PlanLineParcelBusinessManager {

    /**
     * 出入库计划线路袋--新增
     *
     * @param planLineId      计划线路id
     * @param planLineParcels 计划线路袋
     * @author He Bingxing
     * @date 2019-08-05
     */
    void create(String planLineId, List<PlanLineParcel> planLineParcels);

    /**
     * 出入库计划线路袋--更新
     *
     * @param planLineId      计划线路id
     * @param planLineParcels 计划线路袋
     * @author He Bingxing
     * @date 2019-08-05
     */
    void update(String planLineId, List<PlanLineParcel> planLineParcels);

    /**
     * 出入库计划线路袋组--保存
     *
     * @param planLineId      计划线路id
     * @param planLineParcels 计划线路袋
     * @author He Bingxing
     * @date 2019-08-06
     */
    void save(String planLineId, List<PlanLineParcel> planLineParcels);

    /**
     * 出入库计划线路--删除所有袋
     *
     * @param planLineId
     * @author He Bingxing
     * @date 2019-08-07
     */
    void emptyPlanLineParcel(String planLineId);

    /**
     * 出入库计划线路--删除所有袋组
     *
     * @param planLineId
     * @author He Bingxing
     * @date 2019-08-07
     */
    void emptyPlanLineGroup(String planLineId);
}
