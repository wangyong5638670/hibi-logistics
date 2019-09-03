/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.dao.record;


import com.ylink.hibiscus.common.base.support.mybatis.mapper.MyBasicMapper;
import com.ylink.hibiscus.entity.logistics.plan.Plan;
import com.ylink.hibiscus.entity.logistics.record.RecordPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luojinxu
 * @date 2019-07-30
 */
public interface RecordPlanMapper extends MyBasicMapper<RecordPlan> {


    /**
     * 批量初始化 出入库计划记录
     * @param planIds 计划ID集合
     * @param date
     */
    void newRecordPlans(@Param("planIds")List<String> planIds,@Param("date") String date);

    /**
     * 更具传入的计划编号和日期查询出入库计划记录
     * @param planId
     * @param date
     * @return
     */
    RecordPlan query(@Param("planId")String planId, @Param("date")String date);



    RecordPlan queryByPlanId(@Param("planId") String planId);


    /**
     * 更具计划编号，日期，状态查询计划更改状态
     * @param planId
     * @param date
     * @param status
     * @param paramValue
     * @return
     */
    void update(@Param("planId")String planId, @Param("date")String date,@Param("status")String status,@Param("paramValue") String paramValue);

    /**
     * 更具站点编号，时间和类型查询计划信息
     * @param siteId
     * @param date
     * @param type
     */
    RecordPlan queryRecordPlan(@Param("siteId") String siteId,@Param("date") String date,@Param("type") String type);

    /**
     *更具日期查询出所有复合条件的计划记录
     * @param date
     * @return
     */
    List<String> planIds(@Param("date")String date);
}