/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-03-28
 */
package com.ylink.hibiscus.logistics.service.business;

import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.entity.logistics.business.Parcel;
import com.ylink.hibiscus.logistics.dao.business.ParcelMapper;
import org.springframework.stereotype.Service;

/**
 * @author lilinjun
 * @date 2019-03-28
 */
@Service("parcelManager")
public class ParcelManagerImpl extends BaseMybatisBaManagerImpl<Parcel, ParcelMapper> implements ParcelManager{


}
