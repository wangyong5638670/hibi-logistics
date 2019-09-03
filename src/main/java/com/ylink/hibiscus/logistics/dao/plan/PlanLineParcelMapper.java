package com.ylink.hibiscus.logistics.dao.plan;

import com.ylink.hibiscus.common.base.support.mybatis.mapper.MyBasicMapper;
import com.ylink.hibiscus.entity.logistics.plan.PlanLineParcel;

import java.util.List;

public interface PlanLineParcelMapper extends MyBasicMapper<PlanLineParcel> {

    /**
     * 获取线路的袋信息
     *
     * @param planLineId 线路id
     * @return java.util.List<com.ylink.hibiscus.entity.logistics.plan.PlanLineParcel>
     * @author He Bingxing
     * @date 2019-08-01
     */
    List<PlanLineParcel> listWithPlanLineId(String planLineId);

    /**
     * 获取线路的袋组信息
     *
     * @param planLineId 线路id
     * @return java.util.List<com.ylink.hibiscus.entity.logistics.plan.PlanLineParcel>
     * @author He Bingxing
     * @date 2019-08-06
     */
    List<PlanLineParcel> listGroupsWithPlanLineId(String planLineId);

    /**
     * 出入库计划线路--删除所有袋
     *
     * @param planLineId
     * @author He Bingxing
     * @date 2019-08-07
     */
    void deleteWithPlanLineId(String planLineId);
}