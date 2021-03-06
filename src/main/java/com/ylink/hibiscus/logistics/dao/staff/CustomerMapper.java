/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: pujie 2019-02-25
 */
package com.ylink.hibiscus.logistics.dao.staff;

import com.ylink.hibiscus.common.base.enums.Status;
import com.ylink.hibiscus.common.base.support.mybatis.mapper.MyBasicMapper;
import com.ylink.hibiscus.entity.logistics.staff.Customer;
import org.apache.ibatis.annotations.Param;


/**
 * 员工信息
 * @author lilinjun
 * @date 2019-07-25
 */
public interface CustomerMapper extends MyBasicMapper<Customer> {

    /**
     * 改变状态(有效，无效)
     *
     * @param custId 客户编号
     * @param status 客户状态
     * @author Li LinJun
     * @date 2019-02-27
     */
    int updateStatus(@Param("id") String custId, @Param("status") Status status);

}