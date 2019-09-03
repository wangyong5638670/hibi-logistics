/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-03-28
 */
package com.ylink.hibiscus.logistics.service.staff;

import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.entity.logistics.staff.Staff;
import com.ylink.hibiscus.logistics.dao.staff.StaffMapper;
import org.springframework.stereotype.Service;

/**
 * @author lilinjun
 * @date 2019-03-28
 */
@Service("staffManager")
public class StaffManagerImpl extends BaseMybatisBaManagerImpl<Staff, StaffMapper> implements StaffManager{


}
