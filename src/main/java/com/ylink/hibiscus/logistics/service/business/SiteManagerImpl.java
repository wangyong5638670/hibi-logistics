/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-03-28
 */
package com.ylink.hibiscus.logistics.service.business;

import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.entity.logistics.business.Site;
import com.ylink.hibiscus.logistics.dao.business.SiteMapper;
import org.springframework.stereotype.Service;

/**
 * @author lilinjun
 * @date 2019-03-28
 */
@Service("siteManager")
public class SiteManagerImpl extends BaseMybatisBaManagerImpl<Site, SiteMapper> implements SiteManager{


}
