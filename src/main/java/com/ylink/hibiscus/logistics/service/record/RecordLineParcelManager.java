/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.record;


import com.ylink.hibiscus.common.base.enums.CheckStatus;
import com.ylink.hibiscus.common.base.enums.LineParcelStatus;
import com.ylink.hibiscus.common.base.service.BaseManager;
import com.ylink.hibiscus.entity.logistics.record.CountRecord;
import com.ylink.hibiscus.entity.logistics.record.CountRecordAll;
import com.ylink.hibiscus.entity.logistics.record.RecordLineParcel;
import com.ylink.hibiscus.model.logistics.record.RecordLineParcelSearch;

import java.util.List;

/**
 * @author luojinxu
 * @date 2019-07-30
 */
public interface RecordLineParcelManager extends BaseManager<RecordLineParcel> {


    /**
     * 出入库计划下所有线路袋统计
     * @author : lilinjun
     * @date : 2019-04-18
     * @param siteId 站点编号
     * @param planId 计划编号
     * @param date 日期
     */
    List<CountRecordAll> statistics(String siteId, String planId, String date);


    /**
     * 出入库当前线路袋统计
     * @author : lilinjun
     * @date : 2019-04-18
     * @param siteId 站点编号
     * @param planId 计划编号
     * @param lineId 计划线路编号
     * @param date 日期
     */
    List<CountRecord> statisticsLine(String siteId, String planId,  String lineId,String date);


    /**
     * 出入库计划线路统计
     * @author : lilinjun
     * @date : 2019-04-18
     * @param siteId 站点编号
     * @param data 日期
     * @return : com.ylink.hibiscus.common.base.resource.communication.RestfulResponse<com.ylink.hibiscus.common.base.resource.base.ModelResource<com.ylink.hibiscus.entity.logistics.record.UserAccount>>>
     */
    List<CountRecord> statisticsPlan(String siteId, String data);



    /**
     * 更具计划编号批量初始化 出入库计划线路袋记录
     * @param planIds 计划ID集合
     * @param date
     */
    void newRecordLineParcels(List<String> planIds,String date);


    /**
     * 完成袋的出入库
     * @param siteId
     * @param planId
     * @param lineId
     * @param staffId
     * @param recordLineParcelIds
     */
    String completeRecordLineParcels(String siteId, String planId, String lineId, String staffId, List<String> recordLineParcelIds,String date);


    /**
     * 根据条件查询袋记录集合
     * @author : lilinjun
     * @date : 2019-08-16
     * @param search 条件
     */
    List<RecordLineParcel> listRecordLineParcel(RecordLineParcelSearch search);

    /**
     * 记录袋处理（出入库记录删除）
     *
     * @param recordLineParcel 记录袋信息
     * @return void
     * @author lilinjun
     * @date 2019-08-09
     */
    void handle(RecordLineParcel recordLineParcel);

    /**
     * 复核特殊袋
     * @author lilinjun
     * @param id
     *
     */
    void doCheckStatus(String id);

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
    void updateStatusToSuccess(String date, String planId, String planLineId, String parcelId, LineParcelStatus lineParcelStatus);
}