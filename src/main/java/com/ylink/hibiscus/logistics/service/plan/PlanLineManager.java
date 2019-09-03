/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.plan;

import com.ylink.hibiscus.common.base.service.BaseManager;
import com.ylink.hibiscus.entity.logistics.plan.Plan;
import com.ylink.hibiscus.entity.logistics.plan.PlanLine;

import java.util.List;

/**
 * 出入库计划线路manager
 *
 * @Author He Bingxing
 * @Date 2019-07-30
 */
public interface PlanLineManager extends BaseManager<PlanLine> {

    /**
     * 获取出入库计划线路
     *
     * @param planId 计划id
     * @param lineId 线路id
     * @return com.ylink.hibiscus.entity.logistics.plan.PlanLine
     * @author He Bingxing
     * @date 2019-08-01
     */
    PlanLine listWithPlanIdLineId(String planId, String lineId);

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
