/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: 赵谦 2019-05-11
 */
package com.ylink.hibiscus.logistics.app.handler;

import com.ylink.hibiscus.app.logistics.handle.RecordHandleAppService;
import com.ylink.hibiscus.common.base.exception.ApplicationException;
import com.ylink.hibiscus.logistics.service.handler.RecordTaskManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lilinjun
 * @date 2019-07-01
 */
@Api("出入库记录入库接口")
@RestController()
@Slf4j
@RequestMapping("/v1/logistics/handler/record")
public class RecordHandleAppServiceImpl implements RecordHandleAppService {

    @Autowired
    private RecordTaskManager recordTaskManager;


    @Override
    @ApiOperation(value = "出入库记录处理任务")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "date", value = "日期", required = true, dataType = "String")
    })
    @RequestMapping(value = "/handle", method = RequestMethod.GET)
    public void handle(@RequestParam("date") String date) {
        try {
            //处理初始化出入库记录
            recordTaskManager.handle(date);
        }catch(Exception e){
            if(log.isErrorEnabled()){
                log.error("出入库记录处理异常!",e);
            }
            throw new ApplicationException("出入库系统发生系统错误,具体请查看出入库系统日志.");
        }
    }



    @Override
    @ApiOperation(value = "定时任务完成计划")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "date", value = "日期", required = true, dataType = "String")
    })
    @RequestMapping(value = "/handle/finish", method = RequestMethod.GET)
    public void handleFinish(@RequestParam("date") String date) {
        try {
            //处理初始化出入库记录
            recordTaskManager.handleFinish(date);
        }catch(Exception e){
            if(log.isErrorEnabled()){
                log.error("完成计划处理异常!",e);
            }
            throw new ApplicationException("完成计划处理发生系统错误,具体请查看出入库系统日志.");
        }
    }


}
