/*
  版权所有(C) 2019 深圳市雁联计算系统有限公司
  创建: YangHan 2019-04-16
 */
package com.ylink.hibiscus.logistics.service.handler;

/**
 * @author YangHan
 * @date 2019-04-16
 */
public interface RecordTaskManager {

    /**
    * 执行差错账处理明细
    * @author : lilinjun
    * @date : 2019-07-01
    */
    void handle(String date);


    /**
     * 完成计划
     * @author : lilinjun
     * @date : 2019-07-01
     */
    void handleFinish(String date);

}
