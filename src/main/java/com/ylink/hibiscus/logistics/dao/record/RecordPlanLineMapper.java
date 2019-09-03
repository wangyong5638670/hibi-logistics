/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.dao.record;


import com.ylink.hibiscus.common.base.support.mybatis.mapper.MyBasicMapper;
import com.ylink.hibiscus.entity.logistics.record.RecordPlanLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luojinxu
 * @date 2019-07-30
 */
public interface RecordPlanLineMapper extends MyBasicMapper<RecordPlanLine> {

    /**
     * 批量初始化 出入库计划线路记录
     * @param planIds 计划ID集合
     * @param date
     */
    void newRecordPlanLines(@Param("planIds")List<String> planIds, @Param("date")String date);

    /**
     * 根据传入的计划编号，线路编号和日期查询出入库计划线路记录
     * @param planId
     * @param lineId
     * @param date
     * @return
     */
    RecordPlanLine query(@Param("planId")String planId,@Param("lineId")String lineId,@Param("date")String date);


    /**
     * 根据传入的计划编号，时间和日期查询出入库计划线路记录
     * @param planId
     * @param status
     * @param date
     * @return
     */
    List<RecordPlanLine> findRecordPlanLine(@Param("planId")String planId,@Param("status")String status,@Param("date")String date);


    /**
     * 更具计划编号，日期，状态查询计划线路更改状态
     * @param planId
     * @param date
     * @param status
     * @param paramValue
     * @return
     */
    void update(@Param("planId")String planId, @Param("date")String date,@Param("status")String status,@Param("paramValue") String paramValue);

}