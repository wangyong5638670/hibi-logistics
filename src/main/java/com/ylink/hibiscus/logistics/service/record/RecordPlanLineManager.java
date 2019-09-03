/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.record;


import com.ylink.hibiscus.common.base.service.BaseManager;
import com.ylink.hibiscus.entity.logistics.plan.Plan;
import com.ylink.hibiscus.entity.logistics.record.RecordPlanLine;
import com.ylink.hibiscus.entity.logistics.record.RecordResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luojinxu
 * @date 2019-07-30
 */
public interface RecordPlanLineManager extends BaseManager<RecordPlanLine> {


    /**
     * 更具计划编号批量初始化 出入库计划线路记录
     * @param planIds 计划ID集合
     * @param date
     */
    void newRecordPlanLines(List<String> planIds,String date);


    /**
     * 完成计划线路
     * @param planId
     * @param lineId
     * @param date
     */
    void completeRecordLine(String planId, String lineId,String date);


    /**
     * 根据传入的计划编号，时间和日期查询出入库计划线路记录
     * @param planId
     * @param status
     * @param date
     * @return
     */
    List<RecordPlanLine> findRecordPlanLine(String planId,String status,String date);


    /**
     * 根据传入的计划编号，线路编号和日期查询出入库计划线路记录
     * @param planId
     * @param lineId
     * @param date
     * @return
     */
    RecordPlanLine query(String planId,String lineId, String date);

    /**
     * 出入库（出入库扫码+线路统计信息）
     * @author : lilinjun
     * @date : 2019-04-18
     * @param siteId,
     * @param recordPlanId
     * @param recordLineId
     * @param staffId
     * @param code
     * @return
     */
    RecordResponse record(String siteId,String recordPlanId, String recordLineId, String staffId, String code);

}