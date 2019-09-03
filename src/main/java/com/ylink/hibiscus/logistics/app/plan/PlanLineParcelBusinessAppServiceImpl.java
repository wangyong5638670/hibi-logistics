/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-08-05
 */
package com.ylink.hibiscus.logistics.app.plan;

import com.ylink.hibiscus.app.logistics.plan.PlanLineParcelBusinessAppService;
import com.ylink.hibiscus.common.base.exception.ApplicationException;
import com.ylink.hibiscus.common.base.exception.BusinessException;
import com.ylink.hibiscus.common.base.resource.communication.RestfulRequest;
import com.ylink.hibiscus.logistics.service.plan.PlanLineParcelBusinessManager;
import com.ylink.hibiscus.resource.logistics.PlanLineParcelModelResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 出入库计划线路袋业务接口
 *
 * @Author He Bingxing
 * @Date 2019-08-05
 */
@Api("出入库计划线路袋业务接口")
@RestController()
@Slf4j
@RequestMapping("/v1/logistics/plan/line/parcel/business")
public class PlanLineParcelBusinessAppServiceImpl implements PlanLineParcelBusinessAppService {

    @Autowired
    private PlanLineParcelBusinessManager planLineParcelBusinessManager;

    /**
     * 日志模板
     */
    private static final String LOGGER_TEMPLATE= String.format("服务名:[%s],方法:[{}],信息:[{}]",PlanLineParcelBusinessAppServiceImpl.class);


    @ApiOperation(value = "出入库计划线路袋--新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planLineParcelModelResource",value = "出入库计划线路袋信息",required = true)
    })
    @PostMapping("/curd")
    @Override
    public void create(@RequestBody RestfulRequest<PlanLineParcelModelResource> planLineParcelModelResource) {
        try {
            PlanLineParcelModelResource resource = planLineParcelModelResource.getResouces();
            planLineParcelBusinessManager.create(resource.getPlanLineId(), resource.getPlanLineParcels());
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error(LOGGER_TEMPLATE,"create",String.format("调用“出入库计划线路袋--新增”时，发生错误，错误信息：[%s]",e.getMessage()));
            }
            e.printStackTrace();
            throw new ApplicationException("出入库系统发生系统错误,具体请查看出入库系统日志.");
        }
    }

    @ApiOperation(value = "出入库计划线路袋--更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planLineParcelModelResource",value = "出入库计划线路袋信息",required = true)
    })
    @PutMapping("/curd")
    @Override
    public void update(@RequestBody RestfulRequest<PlanLineParcelModelResource> planLineParcelModelResource) {
        try {
            PlanLineParcelModelResource resource = planLineParcelModelResource.getResouces();
            planLineParcelBusinessManager.update(resource.getPlanLineId(), resource.getPlanLineParcels());
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error(LOGGER_TEMPLATE,"update",String.format("调用“出入库计划线路袋--更新”时，发生错误，错误信息：[%s]",e.getMessage()));
            }
            e.printStackTrace();
            throw new ApplicationException("出入库系统发生系统错误,具体请查看出入库系统日志.");
        }
    }

    @ApiOperation(value = "出入库计划线路袋组--保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planLineParcelModelResource",value = "出入库计划线路袋信息",required = true)
    })
    @PutMapping("/curd/groups")
    @Override
    public void save(@RequestBody RestfulRequest<PlanLineParcelModelResource> planLineParcelModelResource) {
        try {
            PlanLineParcelModelResource resource = planLineParcelModelResource.getResouces();
            planLineParcelBusinessManager.save(resource.getPlanLineId(), resource.getPlanLineParcels());
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error(LOGGER_TEMPLATE,"save",String.format("调用“出入库计划线路袋组--保存”时，发生错误，错误信息：[%s]",e.getMessage()));
            }
            e.printStackTrace();
            throw new ApplicationException("出入库系统发生系统错误,具体请查看出入库系统日志.");
        }
    }

    @ApiOperation(value = "出入库计划线路--删除所有袋")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planLineId",value = "出入库计划线路id",required = true)
    })
    @DeleteMapping("/curd")
    @Override
    public void emptyPlanLineParcel(@RequestParam("planLineId") String planLineId) {
        try {
            planLineParcelBusinessManager.emptyPlanLineParcel(planLineId);
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error(LOGGER_TEMPLATE,"save",String.format("调用“出入库计划线路--删除所有袋”时，发生错误，错误信息：[%s]",e.getMessage()));
            }
            e.printStackTrace();
            throw new ApplicationException("出入库系统发生系统错误,具体请查看出入库系统日志.");
        }
    }

    @ApiOperation(value = "出入库计划线路--删除所有袋组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planLineId",value = "出入库计划线路id",required = true)
    })
    @PutMapping("/curd/groups/empty")
    @Override
    public void emptyPlanLineGroup(@RequestParam("planLineId") String planLineId) {
        try {
            planLineParcelBusinessManager.emptyPlanLineGroup(planLineId);
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error(LOGGER_TEMPLATE,"save",String.format("调用“出入库计划线路--删除所有袋组”时，发生错误，错误信息：[%s]",e.getMessage()));
            }
            e.printStackTrace();
            throw new ApplicationException("出入库系统发生系统错误,具体请查看出入库系统日志.");
        }
    }
}
