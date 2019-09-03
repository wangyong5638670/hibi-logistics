/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.record;

/**
 * @author luojinxu
 * @date 2019-07-30
 */

import com.ylink.hibiscus.common.base.service.BaseManager;
import com.ylink.hibiscus.entity.logistics.record.RecordLineGroup;

import java.util.List;

public interface RecordLineGroupManager extends BaseManager<RecordLineGroup> {

    /**
     * 更具计划编号批量初始化 出入库计划线路袋记录
     * @param date
     */
    void newRecordLineGroups(String date);
}