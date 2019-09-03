/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.plan;


import com.ylink.hibiscus.common.base.enums.Week;
import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.common.base.utils.DateUtils;
import com.ylink.hibiscus.entity.logistics.plan.Plan;
import com.ylink.hibiscus.logistics.dao.plan.PlanMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 出入库计划manager
 *
 * @Author He Bingxing
 * @Date 2019-07-30
 */
@Service
public class PlanManagerImpl extends BaseMybatisBaManagerImpl<Plan, PlanMapper> implements PlanManager {

    @Override
    public List<String> planIds(String dateStr) {

        Week week= DateUtils.weekDay(dateStr,DateUtils.DATE_FORMAT_yyyy_MM_dd);

        List<Plan>  plans = getDao().planIds(dateStr,week.getCode());
        if(CollectionUtils.isEmpty(plans)){
            return null;
        }
        List<String> planIds = new ArrayList<>();
        List<String> planIds2 = new ArrayList<>();
        List<String> planIds3 = new ArrayList<>();
        for(Plan plan:plans){
            //是否指定日期
            if(null != plan.getCustomizeDate()){
                planIds.add(plan.getId());
                continue;
            }
            //是否默认
            if(plan.getDef()){
                planIds2.add(plan.getId());
                continue;
            }
            //当前周期
            planIds3.add(plan.getId());
        }
        //指定日期
        if(!CollectionUtils.isEmpty(planIds)){
            return planIds;
        }
        //周期
        if(!CollectionUtils.isEmpty(planIds3)){
            return planIds3;
        }

        //都不满足就用默认的计划
        return planIds2;

    }

    @Override
    public Plan query(@NotBlank String siteId, @NotBlank String cycle, @NotBlank String type, String customizeDate) {
        return getDao().queryWithSiteIdCycleType(siteId, cycle, type, customizeDate);
    }
}
