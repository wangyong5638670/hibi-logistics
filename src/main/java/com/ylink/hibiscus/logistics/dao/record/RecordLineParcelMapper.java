/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.dao.record;


import com.ylink.hibiscus.common.base.enums.CheckStatus;
import com.ylink.hibiscus.common.base.support.mybatis.mapper.MyBasicMapper;
import com.ylink.hibiscus.entity.logistics.record.CountRecord;
import com.ylink.hibiscus.entity.logistics.record.RecordLineParcel;
import com.ylink.hibiscus.model.logistics.record.RecordLineParcelSearch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luojinxu
 * @date 2019-07-30
 */
public interface RecordLineParcelMapper extends MyBasicMapper<RecordLineParcel> {

    /**
     * 出入库线路袋统计(到计划分组)
     * @author : lilinjun
     * @date : 2019-04-18
     * @param siteId 站点编号
     * @param planId 计划编号
     * @param date 日期
     * @return : com.ylink.hibiscus.common.base.resource.communication.RestfulResponse<com.ylink.hibiscus.common.base.resource.base.ModelResource<com.ylink.hibiscus.entity.logistics.record.UserAccount>>>
     */
    List<CountRecord> statistics(@Param("siteId")String siteId,@Param("planId") String planId,@Param("date") String date);

    /**
     * 出入库线路袋统计（到线路分组）
     * @author : lilinjun
     * @date : 2019-04-18
     * @param siteId 站点编号
     * @param planId 计划编号
     * @param lineId 计划线路编号
     * @param date 日期
     * @return : com.ylink.hibiscus.common.base.resource.communication.RestfulResponse<com.ylink.hibiscus.common.base.resource.base.ModelResource<com.ylink.hibiscus.entity.logistics.record.UserAccount>>>
     */
    List<CountRecord> statisticsLine(@Param("siteId")String siteId,@Param("planId") String planId,@Param("lineId") String lineId,@Param("date") String date);

    /**
     * 出入库计划线路统计
     * @author : lilinjun
     * @date : 2019-04-18
     * @param siteId 站点编号
     * @param date 日期
     * @return : com.ylink.hibiscus.common.base.resource.communication.RestfulResponse<com.ylink.hibiscus.common.base.resource.base.ModelResource<com.ylink.hibiscus.entity.logistics.record.UserAccount>>>
     */
    List<CountRecord> statisticsPlan(@Param("siteId")String siteId,@Param("date") String date);

    /**
     * 批量初始化 出入库计划线路袋记录
     * @param planIds 计划ID集合
     * @param date
     */
    void newRecordLineParcels(@Param("planIds")List<String> planIds, @Param("date")String date);


    /**
     * 更具传入的袋编号和日期查询出入库袋记录
     * @param planParcelId
     * @param date
     * @return
     */
    List<RecordLineParcel> query(@Param("planParcelId")String planParcelId, @Param("date")String date);

    /**
     * 更具传入的袋编号和日期查询出入库袋记录
     * @param planParcelId
     * @param date
     * @return
     */
    //List<RecordLineParcel> query(@Param("planParcelId")String planParcelId, @Param("date")String date);
    /**
     * 根据条件查询袋记录集合
     * @author : lilinjun
     * @date : 2019-08-16
     * @param search 条件
     */
    List<RecordLineParcel> listRecordLineParcel(RecordLineParcelSearch search);

    /**
     * 复核特殊袋状态
     * @author lilinjun
     * @param id
     * @param checkStatus
     *
     */
    void doCheckStatus(@Param("id") String id, @Param("checkStatus") String checkStatus);

    /**
     * 更新出入库记录袋状态为正常
     *
     * @param date             入出库日期
     * @param planId           计划id
     * @param planLineId       计划线路id
     * @param parcelId         袋id
     * @param lineParcelStatus 出入库记录线路袋状态
     * @return com.ylink.hibiscus.entity.logistics.record.RecordLineParcel
     * @author He Bingxing
     * @date 2019-08-09
     */
    void updateStatusToSuccess(@Param("date") String date, @Param("planId") String planId,
    @Param("planLineId") String planLineId, @Param("parcelId") String parcelId, @Param("lineParcelStatus") String lineParcelStatus);
}