/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: zhoumingxing 2019-03-07
 */
package com.ylink.hibiscus.logistics.service.handler;



import java.util.List;

/**
 * 处理出入库计划数据
 * @author lilinjun
 * @date 2019-08-01
 */
public interface RecordHandlerManager {

    /**
     * 初始化出入库数据
     * @param processData 待处理取数据
     */
    void process(List<String> processData,String date);


    /**
     * 完成计划
     * @param processData 待处理取数据
     */
    void processFinish(List<String> processData,String date);

}
