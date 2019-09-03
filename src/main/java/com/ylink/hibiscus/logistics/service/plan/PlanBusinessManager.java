package com.ylink.hibiscus.logistics.service.plan;

import com.ylink.hibiscus.entity.logistics.plan.Plan;

/**
 * 出入库计划业务manager
 *
 * @Author He Bingxing
 * @Date 2019-08-01
 */
public interface PlanBusinessManager {

    /**
     * 新增出入库计划,包含初始化线路
     *
     * @param plan 出入库计划信息
     * @return void
     * @author He Bingxing
     * @date 2019-08-01
     */
    void create(Plan plan);

    /**
     * 出入库计划详情（包含计划线路）
     *
     * @param planId 出入库计划id
     * @return Plan
     * @author He Bingxing
     * @date 2019-08-01
     */
    Plan detail(String planId);

    /**
     * 校验计划
     * 规则：同一站点下不允许：相同周期相同类别的计划
     *
     * @param siteId        站点id
     * @param cycle         周期
     * @param type          类型
     * @param customizeDate 自定义周期日期
     * @return com.ylink.hibiscus.entity.logistics.plan.Plan
     * @author He Bingxing
     * @date 2019-08-15
     */
    Plan query(String siteId, String cycle, String type, String customizeDate);
}
