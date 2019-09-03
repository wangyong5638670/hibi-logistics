/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.plan;

import com.ylink.hibiscus.common.base.enums.ScanType;
import com.ylink.hibiscus.common.base.service.BaseManager;
import com.ylink.hibiscus.entity.logistics.plan.PlanLineParcel;

import java.util.List;

/**
 * 出入库计划袋manager
 *
 * @Author He Bingxing
 * @Date 2019-07-30
 */
public interface PlanLineParcelManager extends BaseManager<PlanLineParcel> {

    /**
     * 获取线路的袋信息
     *
     * @param planLineId 线路id
     * @return java.util.List<com.ylink.hibiscus.entity.logistics.plan.PlanLineParcel>
     * @author He Bingxing
     * @date 2019-08-01
     */
    List<PlanLineParcel> listWithPlanLineId(String planLineId);

    /**
     * 判断条码类型
     * @param id
     * @author lilinjun
     * @return ScanType 扫描类型
     */
    ScanType scan(String id);


    /**
     * 获取线路的袋组信息
     *
     * @param planLineId 线路id
     * @return java.util.List<com.ylink.hibiscus.entity.logistics.plan.PlanLineParcel>
     * @author He Bingxing
     * @date 2019-08-06
     */
    List<PlanLineParcel> listGroupsWithPlanLineId(String planLineId);

    /**
     * 出入库计划线路--删除所有袋
     *
     * @param planLineId
     * @author He Bingxing
     * @date 2019-08-07
     */
    void deleteWithPlanLineId(String planLineId);

}
