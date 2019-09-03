/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.record;

import com.ylink.hibiscus.common.base.service.BaseManager;
import com.ylink.hibiscus.entity.logistics.business.Line;
import com.ylink.hibiscus.entity.logistics.business.Parcel;
import com.ylink.hibiscus.entity.logistics.business.Site;
import com.ylink.hibiscus.entity.logistics.plan.Plan;
import com.ylink.hibiscus.entity.logistics.record.RecordLineParcel;
import com.ylink.hibiscus.entity.logistics.record.RecordParcelAlarm;
import com.ylink.hibiscus.entity.logistics.staff.Staff;
import org.apache.ibatis.annotations.Param;

/**
 * @author luojinxu
 * @date 2019-07-30
 */
public interface RecordParcelAlarmManager extends BaseManager<RecordParcelAlarm> {

    /**
     * 生成差错告警信息
     * @param recordLineParcel
     * @param parcel
     * @param status
     */
    void creatRecordParcelAlarm(RecordLineParcel recordLineParcel,Parcel parcel,String status);


    /**
     * 生成差错告警信息
     * @param recordLineParcel
     * @param status
     */
    void creatRecordParcelAlarm(RecordLineParcel recordLineParcel,String status);

    /**
     * 告警处理
     *
     * @param recordParcelAlarm 告警信息
     * @return void
     * @author He Bingxing
     * @date 2019-08-09
     */
    void handle(RecordParcelAlarm recordParcelAlarm);


    /**
     * 根据出入库记录id查询告警信息
     *
     * @param recordId 出入库记录id
     * @return com.ylink.hibiscus.entity.logistics.record.RecordParcelAlarm
     * @author He Bingxing
     * @date 2019-08-13
     */
    RecordParcelAlarm queryWithRecordId(String recordId);

    /**
     * 根据计划编号，计划线路编号，日期，袋编号查询告警信息
     * @param date 日期
     * @param planId 计划
     * @param planLineId 计划线路
     * @param recordParcelId 记录袋
     * @return com.ylink.hibiscus.entity.logistics.record.RecordParcelAlarm
     * @author lilinjun
     * @date 2019-08-13
     */
    RecordParcelAlarm queryRecordParcelAlarm(String date, String planId,String planLineId,String recordParcelId);

}