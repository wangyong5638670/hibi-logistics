/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-01-21
 */
package com.ylink.hibiscus.logistics.dao;

/**
 * 主键生成
 *
 * @author He Bingxing
 * @date 2019-01-21
 */
public interface SequenceMapper {

    /**
     * 获取当前序号
     *
     * @param sequenceCode 序号规则编码
     * @return RestfulResponse
     * @author He Bingxing
     * @date 2019-01-21
     */
    String current(String sequenceCode);

    /**
     * 获取下一个序号
     *
     * @param sequenceCode 序号规则编码
     * @return RestfulResponse
     * @author He Bingxing
     * @date 2019-01-21
     */
    String next(String sequenceCode);
}
