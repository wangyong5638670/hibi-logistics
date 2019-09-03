package com.ylink.hibiscus.logistics.dao.plan;

import com.ylink.hibiscus.common.base.support.mybatis.mapper.MyBasicMapper;
import com.ylink.hibiscus.entity.logistics.plan.PlanLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanLineMapper extends MyBasicMapper<PlanLine> {


    /**
     * 获取出入库计划线路下所有的线路
     *
     * @param planId 计划id
     * @param lineId 线路id
     * @return com.ylink.hibiscus.entity.logistics.plan.PlanLine
     * @author He Bingxing
     * @date 2019-08-01
     */
    PlanLine listWithPlanIdLineId(@Param("planId") String planId, @Param("lineId") String lineId);

    /**
     * 获取出入库计划下所有的线路
     *
     * @param planId 计划id
     * @return java.util.List<com.ylink.hibiscus.entity.logistics.plan.PlanLine>
     * @author He Bingxing
     * @date 2019-08-01
     */
    List<PlanLine> listWithPlanId(String planId);
}