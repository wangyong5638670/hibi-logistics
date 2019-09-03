package com.ylink.hibiscus.logistics.dao.plan;

import com.ylink.hibiscus.common.base.support.mybatis.mapper.MyBasicMapper;
import com.ylink.hibiscus.entity.logistics.plan.Plan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanMapper extends MyBasicMapper<Plan> {

    /**
     * 更具日期，是否默认，周期查询出所有复合条件的计划
     *
     * @param date
     * @param cycle
     * @return
     */
    List<Plan> planIds(@Param("date") String date, @Param("cycle") String cycle);

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
    Plan queryWithSiteIdCycleType(@Param("siteId") String siteId, @Param("cycle") String cycle, @Param("type") String type, @Param("customizeDate") String customizeDate);
}