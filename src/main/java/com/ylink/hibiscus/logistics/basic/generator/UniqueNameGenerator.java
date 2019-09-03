package com.ylink.hibiscus.logistics.basic.generator;/*
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: YangHan 2019-03-08
 */

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * @author YangHan
 * @date 2019-03-08
 */
public class UniqueNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return definition.getBeanClassName();
    }
}
