/*
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: YangHan 2019-03-14
 */
package com.ylink.hibiscus.depot.auto;

import com.ylink.hibiscus.logistics.SpringBootLogisticsApplication;
import com.ylink.hibiscus.logistics.service.handler.RecordTaskManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author YangHan
 * @date 2019-03-14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootLogisticsApplication.class)
public class RecordHandleTest {

    @Autowired
    private RecordTaskManager recordTaskManager;

    @Test
    public void test(){
        recordTaskManager.handle("20190821");
        System.out.println("定时执行出入库记录");
    }
}
