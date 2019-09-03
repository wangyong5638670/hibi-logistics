/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: pujie 2019-02-25
 */
package com.ylink.hibiscus.logistics.dao.business;

import com.ylink.hibiscus.common.base.enums.Status;
import com.ylink.hibiscus.common.base.support.mybatis.mapper.MyBasicMapper;
import com.ylink.hibiscus.entity.logistics.business.Line;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 线路信息
 * @author lilinjun
 * @date 2019-07-25
 */
public interface LineMapper extends MyBasicMapper<Line> {

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
    List<Line> listWithSiteIdStats(@Param("siteId") String siteId, @Param("status") Status status);



}