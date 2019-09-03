/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.plan;

import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.entity.logistics.plan.PlanLineGroup;
import com.ylink.hibiscus.logistics.dao.plan.PlanLineGroupMapper;
import org.springframework.stereotype.Service;

/**
 * 出入库计划线路袋组manager
 *
 * @Author He Bingxing
 * @Date 2019-07-30
 */
@Service
public class PlanLineGroupManagerImpl extends BaseMybatisBaManagerImpl<PlanLineGroup, PlanLineGroupMapper> implements PlanLineGroupManager {
}
