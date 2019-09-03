/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-01-21
 */
package com.ylink.hibiscus.logistics.app.base;

import com.ylink.hibiscus.app.logistics.base.sequence.SequenceAppService;
import com.ylink.hibiscus.common.base.constant.Constant;
import com.ylink.hibiscus.common.base.resource.base.ModelResource;
import com.ylink.hibiscus.common.base.resource.communication.RestfulResponse;
import com.ylink.hibiscus.logistics.service.base.SequenceManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 主键生成接口
 *
 * @author lilinjun
 * @date 2019-07-29
 */
@Api("主键生成接口")
@RestController()
@Slf4j
@RequestMapping("/v1/logistics/base/sequence/")
public class SequenceAppServiceImpl implements SequenceAppService {

    @Autowired
    private SequenceManager sequenceManager;

    @ApiOperation(value = "获取当前序号")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "sequenceCode", value = "序号规则编码", required = true, dataType = "String")
    })
    @GetMapping("current")
    @Override
    public RestfulResponse<ModelResource<String>> current(@NotBlank(message = "规则编号不能为空") @RequestParam("sequenceCode")String sequenceCode) {
        try {
            return RestfulResponse.builder().setResouces(
                    ModelResource.builder().setEntity(sequenceManager.current(sequenceCode))
                            .build()
            ).success().build();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(Constant.LOG_ERROR_PATTERN_DEFAULT, SequenceAppServiceImpl.class.getSimpleName(), "current", "获取当前序号失败" + e.getMessage());
            }
            return RestfulResponse.builder().setResouces(
                    ModelResource.builder().setEntity(null)
                            .build()
            ).fail().build();
        }
    }


    @ApiOperation(value = "获取下一个序号")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "sequenceCode", value = "序号规则编码", required = true, dataType = "String")
    })
    @GetMapping("next")
    @Override
    public RestfulResponse<ModelResource<String>> next(@NotBlank(message = "规则编号不能为空") @RequestParam("sequenceCode")String sequenceCode) {
        try {
            return RestfulResponse.builder().setResouces(
                    ModelResource.builder().setEntity(sequenceManager.next(sequenceCode))
                            .build()
            ).success().build();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(Constant.LOG_ERROR_PATTERN_DEFAULT, SequenceAppServiceImpl.class.getSimpleName(), "next", "获取下一个序号失败" + e.getMessage());
            }
            return RestfulResponse.builder().setResouces(
                    ModelResource.builder().setEntity(null)
                            .build()
            ).fail().build();
        }
    }


    @ApiOperation(value = "获取下一个序号")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "sequenceCode", value = "序号规则编码", required = true, dataType = "String")
    })
    @GetMapping("transaction/next")
    @Override
    public RestfulResponse<ModelResource<String>> newTransactionNext(@NotBlank(message = "规则编号不能为空") @RequestParam("sequenceCode")String sequenceCode) {
        try {
            return RestfulResponse.builder().setResouces(
                    ModelResource.builder().setEntity(sequenceManager.newTransactionNext(sequenceCode))
                            .build()
            ).success().build();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(Constant.LOG_ERROR_PATTERN_DEFAULT, SequenceAppServiceImpl.class.getSimpleName(), "newTransactionNext", "获取下一个序号失败" + e.getMessage());
            }
            return RestfulResponse.builder().setResouces(
                    ModelResource.builder().setEntity(null)
                            .build()
            ).fail().build();
        }
    }
}
