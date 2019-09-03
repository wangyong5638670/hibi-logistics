/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.dao.record;

import com.ylink.hibiscus.common.base.support.mybatis.mapper.MyBasicMapper;
import com.ylink.hibiscus.entity.logistics.record.RecordParcelAlarm;
import org.apache.ibatis.annotations.Param;

/**
 * @author luojinxu
 * @date 2019-07-30
 */
public interface RecordParcelAlarmMapper extends MyBasicMapper<RecordParcelAlarm> {

    /**
     * 根据出入库记录id查询告警信息
     *
     * @param recordId 出入库记录id
     * @return com.ylink.hibiscus.entity.logistics.record.RecordParcelAlarm
     * @author He Bingxing
     * @date 2019-08-13
     */
    RecordParcelAlarm queryWithRecordId(@Param("recordId")String recordId);


    /**
     * 根据计划编号，计划线路编号，日期，袋编号查询告警信息
     * @param date 日期
     * @param planId 计划
     * @param planLineId 计划线路
     * @param recordParcelId 记录袋
     * @return com.ylink.hibiscus.entity.logistics.record.RecordParcelAlarm
     * @author lilinjun
     * @date 2019-08-15
     */
    RecordParcelAlarm queryRecordParcelAlarm(@Param("date")String date,@Param("planId")String planId,@Param("planLineId")String planLineId, @Param("recordParcelId")String recordParcelId);
}