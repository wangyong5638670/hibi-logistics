/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-01-22
 */
package com.ylink.hibiscus.logistics.service.base;

import com.ylink.hibiscus.logistics.dao.SequenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

/**
 * 主键生成
 *
 * @author He Bingxing
 * @date 2019-01-22
 */
@Slf4j
@Component("squesceManager")
public class SequenceManagerImpl implements SequenceManager {

    @Autowired
    private SequenceMapper mapper;

    @Override
    public String current(@NotBlank(message = "规则编号不能为空") String sequenceCode) {
        return mapper.current(sequenceCode);
    }

    @Override
    public String next(@NotBlank(message = "规则编号不能为空") String sequenceCode) {
        return mapper.next(sequenceCode);
    }

    @Override
    public String newTransactionNext(@NotBlank(message = "规则编号不能为空") String sequenceCode) {
        return mapper.next(sequenceCode);
    }
}
