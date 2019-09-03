/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-08-01
 */
package com.ylink.hibiscus.logistics.app.plan;

import com.ylink.hibiscus.app.logistics.plan.PlanBusinessAppService;
import com.ylink.hibiscus.common.base.exception.ApplicationException;
import com.ylink.hibiscus.common.base.exception.BusinessException;
import com.ylink.hibiscus.common.base.resource.base.ModelResource;
import com.ylink.hibiscus.common.base.resource.communication.RestfulRequest;
import com.ylink.hibiscus.common.base.resource.communication.RestfulResponse;
import com.ylink.hibiscus.entity.logistics.plan.Plan;
import com.ylink.hibiscus.logistics.service.plan.PlanBusinessManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 出入库计划业务接口
 *
 * @Author He Bingxing
 * @Date 2019-08-01
 */
@Api("出入库计划业务接口")
@RestController()
@Slf4j
@RequestMapping("/v1/logistics/plan/business")
public class PlanBusinessAppServiceImpl implements PlanBusinessAppService {

    /**
     * 日志模板
     */
    private static final String LOGGER_TEMPLATE= String.format("服务名:[%s],方法:[{}],信息:[{}]",PlanBusinessAppServiceImpl.class);

    @Autowired
    private PlanBusinessManager planBusinessManager;

    @ApiOperation(value = "新增出入库计划,包含初始化线路")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "plan",value = "出入库计划信息",required = true)
    })
    @PostMapping("/curd")
    @Override
    public void create(@RequestBody RestfulRequest<ModelResource<Plan>> plan) {
        try {
            planBusinessManager.create(plan.getResouces().getEntity());
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error(LOGGER_TEMPLATE,"create",String.format("调用“新增出入库计划”时，发生错误，错误信息：[%s]",e.getMessage()));
            }
            e.printStackTrace();
            throw new ApplicationException("出入库系统发生系统错误,具体请查看出入库系统日志.");
        }
    }

    @ApiOperation(value = "出入库计划详情（包含计划线路）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId",value = "计划id",required = true)
    })
    @GetMapping("/curd/{planId}")
    @Override
    public RestfulResponse<ModelResource<Plan>> detail(@PathVariable("planId") String planId) {
        try {
            return RestfulResponse.builder().setResouces(ModelResource.builder().setEntity(
                    planBusinessManager.detail(planId)
            ).build()).success().build();
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error(LOGGER_TEMPLATE,"create",String.format("调用“通过id查询出入库计划信息”时，发生错误，错误信息：[%s]",e.getMessage()));
            }
            e.printStackTrace();
            throw new ApplicationException("出入库系统发生系统错误,具体请查看出入库系统日志.");
        }
    }

    @ApiOperation(value = "校验计划（规则：同一站点下不允许：相同周期相同类别的计划）")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "siteId",value = "站点id",required = true),
        @ApiImplicitParam(name = "cycle",value = "周期",required = true),
        @ApiImplicitParam(name = "type",value = "类型",required = true),
        @ApiImplicitParam(name = "customizeDate",value = "自定义周期日期")
    })
    @GetMapping("/curd/checked")
    @Override
    public RestfulResponse<ModelResource<Plan>> query(@RequestParam("siteId") String siteId, @RequestParam("cycle") String cycle,
                                                      @RequestParam("type") String type, @RequestParam(value = "customizeDate",required = false) String customizeDate) {
        try {
            return RestfulResponse.builder().setResouces(ModelResource.builder().setEntity(
                planBusinessManager.query(siteId, cycle, type, customizeDate)
            ).build()).success().build();
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error(LOGGER_TEMPLATE,"create",String.format("调用“通过id查询出入库计划信息”时，发生错误，错误信息：[%s]",e.getMessage()));
            }
            e.printStackTrace();
            throw new ApplicationException("出入库系统发生系统错误,具体请查看出入库系统日志.");
        }
    }
}
