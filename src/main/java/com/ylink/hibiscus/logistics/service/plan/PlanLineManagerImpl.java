/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.plan;

import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.entity.logistics.plan.Plan;
import com.ylink.hibiscus.entity.logistics.plan.PlanLine;
import com.ylink.hibiscus.logistics.dao.plan.PlanLineMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 出入库计划线路manager
 *
 * @Author He Bingxing
 * @Date 2019-07-30
 */
@Service
public class PlanLineManagerImpl extends BaseMybatisBaManagerImpl<PlanLine, PlanLineMapper> implements PlanLineManager {
    @Override
    public PlanLine listWithPlanIdLineId(@NotBlank String planId, @NotBlank String lineId) {
        return getDao().listWithPlanIdLineId(planId, lineId);
    }

    @Override
    public List<PlanLine> listWithPlanId(String planId) {
        return  getDao().listWithPlanId(planId);
    }
}
