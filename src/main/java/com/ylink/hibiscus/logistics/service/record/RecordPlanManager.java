/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.record;


import com.ylink.hibiscus.common.base.service.BaseManager;
import com.ylink.hibiscus.entity.logistics.record.RecordPlan;

import java.util.List;

/**
 * @author luojinxu
 * @date 2019-07-30
 */
public interface RecordPlanManager extends BaseManager<RecordPlan> {


    /**
     * 批量初始化 出入库计划记录
     * @param planIds 计划ID集合
     * @param date
     */
    void newRecordPlans(List<String> planIds,String date);


    /**
     * 更具传入的计划编号和日期查询出入库计划记录
     * @param planId
     * @param date
     * @return
     */
    RecordPlan query(String planId,String date);

    RecordPlan queryByPlanId(String planId);

    /**
     * 更具计划编号，日期，状态查询计划更改状态
     * @param planId
     * @param date
     * @param status
     * @param paramValue
     * @return
     */
    void update(String planId,String date,String status,String paramValue);


    /**
     * 根据站点编号，时间和类型查询计划信息
     * @param siteId
     * @param date
     * @param type
     */
    RecordPlan queryRecordPlan(String siteId,String date,String type);

    /**
     * 更具日期查询出计划线路编号
     * @param date 日期
     */
    List<String> planIds(String date);
}