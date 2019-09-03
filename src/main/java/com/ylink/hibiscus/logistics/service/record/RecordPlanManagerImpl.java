/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.record;


import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.common.base.support.mybatis.mapper.MyBasicMapper;
import com.ylink.hibiscus.entity.logistics.plan.Plan;
import com.ylink.hibiscus.entity.logistics.record.RecordPlan;
import com.ylink.hibiscus.logistics.dao.record.RecordPlanMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * @author luojinxu
 * @date 2019-07-30
 */
@Slf4j
@Service("recordPlanManager")
public class RecordPlanManagerImpl extends BaseMybatisBaManagerImpl<RecordPlan, RecordPlanMapper> implements RecordPlanManager {

    @Override
    public void newRecordPlans(List<String> planIds, String date) {
        getDao().newRecordPlans(planIds, date);
    }

    @Override
    public RecordPlan query(String planId, String date) {
        return getDao().query(planId, date);
    }


    @Override
    public RecordPlan queryByPlanId(String planId) {
        return getDao().queryByPlanId(planId);
    }

    @Override
    public void update(String planId, String date, String status, String paramValue) {
        getDao().update(planId, date, status, paramValue);
    }

    @Override
    public RecordPlan queryRecordPlan(@NotBlank String siteId,@NotBlank  String date,@NotBlank  String type) {
        return getDao().queryRecordPlan(siteId,date, type);
    }

    @Override
    public List<String> planIds(String date) {
        return getDao().planIds(date);
    }
}