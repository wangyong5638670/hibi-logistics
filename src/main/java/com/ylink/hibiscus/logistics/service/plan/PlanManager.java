/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.plan;

import com.ylink.hibiscus.common.base.service.BaseManager;
import com.ylink.hibiscus.entity.logistics.plan.Plan;

import java.util.List;

/**
 * 出入库计划manager
 *
 * @Author He Bingxing
 * @Date 2019-07-30
 */
public interface PlanManager extends BaseManager<Plan> {


    /**
     * 更具日期查询出计划线路编号
     * @param dateStr yyyy-MM-dd 日期
     */
    List<String> planIds(String dateStr);

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
