/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.record;

/**
 * @author luojinxu
 * @date 2019-07-30
 */

import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.entity.logistics.record.RecordLineGroup;
import com.ylink.hibiscus.logistics.dao.record.RecordLineGroupMapper;
import org.springframework.stereotype.Service;

@Service("recordLineGroupManager")
public class RecordLineGroupManagerImpl extends BaseMybatisBaManagerImpl<RecordLineGroup, RecordLineGroupMapper> implements RecordLineGroupManager {
    @Override
    public void newRecordLineGroups(String date) {
        getDao().newRecordLineGroups(date);
    }
}