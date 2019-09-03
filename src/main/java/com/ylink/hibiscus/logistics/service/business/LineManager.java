/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-03-28
 */
package com.ylink.hibiscus.logistics.service.business;

import com.ylink.hibiscus.common.base.enums.Status;
import com.ylink.hibiscus.common.base.service.BaseManager;
import com.ylink.hibiscus.entity.logistics.business.Line;

import java.util.List;

/**
 * @author lilinjun
 * @date 2019-07-28
 */
public interface LineManager extends BaseManager<Line> {

    /**
     * 获取站点下[有效|无效|ALL]的线路
     *  备注：status 未null为查所有
     *
     * @param siteId 站点id
     * @param status 状态
     * @return java.util.List<com.ylink.hibiscus.entity.logistics.business.Line>
     * @author He Bingxing
     * @date 2019-08-01
     */
    List<Line> listWithSiteIdStats(String siteId, Status status);

}
