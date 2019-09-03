/**
* 版权所有(C) 2019 深圳市雁联计算系统有限公司
* 创建: luojinxu 2019-07-31
*/
package com.ylink.hibiscus.logistics.app.record;


import com.ylink.hibiscus.app.logistics.record.RecordParcelAlarmAppService;
import com.ylink.hibiscus.common.base.app.BaseAppServiceImpl;
import com.ylink.hibiscus.common.base.dao.SearchCondition;
import com.ylink.hibiscus.common.base.dao.Term;
import com.ylink.hibiscus.common.base.exception.ApplicationException;
import com.ylink.hibiscus.common.base.exception.BusinessException;
import com.ylink.hibiscus.common.base.model.page.ListPage;
import com.ylink.hibiscus.common.base.resource.base.ModelResource;
import com.ylink.hibiscus.common.base.resource.base.PaginationResouce;
import com.ylink.hibiscus.common.base.resource.communication.RestfulRequest;
import com.ylink.hibiscus.common.base.resource.communication.RestfulResponse;
import com.ylink.hibiscus.entity.logistics.record.RecordParcelAlarm;
import com.ylink.hibiscus.logistics.service.record.RecordParcelAlarmManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;



/**
  * @author luojinxu
  * @date 2019-07-31
  */
@Api("出入库记录告警接口")
@RestController()
@Slf4j
@RequestMapping("/v1/logistics/record/parcel/alarm")
public class RecordParcelAlarmAppServiceImpl extends BaseAppServiceImpl<RecordParcelAlarm,RecordParcelAlarmManager> implements RecordParcelAlarmAppService {

    /**
     * 日志模板
     */
    private static final String LOGGER_TEMPLATE= String.format("服务名:[%s],方法:[{}],信息:[{}]", RecordParcelAlarmAppServiceImpl.class);



    @Override
    @ApiOperation(value = "保存实体")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "保存实体参数",required = true)
    })
    @RequestMapping(value = "/curd",method = RequestMethod.POST)
    public void create(@Validated @NotNull(message = "请求参数不能为空")
    @RequestBody RestfulRequest<ModelResource<RecordParcelAlarm>> request) {
        getManager().create(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "保存实体集合")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "保存实体集合参数",required = true)
    })
    @RequestMapping(value = "/curds",method = RequestMethod.POST)
    public void createWithCollection(@Validated @NotNull(message = "请求参数不能为空")
    @RequestBody RestfulRequest<ModelResource<Collection<RecordParcelAlarm>>> request) {
        getManager().create(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "更新实体")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "更新实体参数",required = true)
    })
    @RequestMapping(value = "/curd",method = RequestMethod.PUT)
    public void update(@Validated @NotNull(message = "请求参数不能为空")
    @RequestBody RestfulRequest<ModelResource<RecordParcelAlarm>> request) {
        getManager().update(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "更新实体集合")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "更新实体集合参数",required = true)
    })
    public void updateWithCollection(
    @Validated @NotNull(message = "请求参数不能为空")
    @RequestBody  RestfulRequest<ModelResource<Collection<RecordParcelAlarm>>> request) {
        getManager().update(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "删除实体集合")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "删除实体集合参数",required = true)
    })
    public void removeWithCollection(
    @Validated @NotNull(message = "请求参数不能为空")
    @RequestBody RestfulRequest<ModelResource<Collection<RecordParcelAlarm>>> request) {
        getManager().remove(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "分页查询（实体为查询条件）")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "分页查询、查询条件参数",required = true)
    })
    @RequestMapping(value = "/list/query/criteria/entity",method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<ModelResource<ListPage<RecordParcelAlarm>>> list(
        @Validated @NotNull(message = "请求参数不能为空")
        @RequestBody RestfulRequest<PaginationResouce<RecordParcelAlarm, RecordParcelAlarm>> request) {
        ListPage<RecordParcelAlarm> page  = getManager().list(request.getResouces().getPage(),request.getResouces().getSearch());
            return RestfulResponse.builder()
            .success()
            .setResouces(ModelResource.builder().setEntity(page).build())
            .build();
    }

    @Override
    @ApiOperation(value = "分页查询（Term为查询条件）")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "分页查询、查询条件参数",required = true)
    })
    @RequestMapping(value = "/list/query/criteria/term",method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<ModelResource<ListPage<RecordParcelAlarm>>> listWithTerm(
        @Validated @NotNull(message = "请求参数不能为空")
        @RequestBody RestfulRequest<PaginationResouce<RecordParcelAlarm, Collection<Term>>> request) {
            ListPage<RecordParcelAlarm> page  = getManager().list(request.getResouces().getPage(),request.getResouces().getSearch());
                return RestfulResponse.builder()
                .success()
                .setResouces(ModelResource.builder().setEntity(page).build())
                .build();
    }

    @Override
    @ApiOperation(value = "分页查询（SearchCondition为查询条件）")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "分页查询、查询条件参数",required = true)
    })
    @RequestMapping(value = "/list/query/criteria/condition",method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<ModelResource<ListPage<RecordParcelAlarm>>> listWithSearchCondition(
        @Validated @NotNull(message = "请求参数不能为空")
        @RequestBody RestfulRequest<PaginationResouce<RecordParcelAlarm, SearchCondition>> request) {
        ListPage<RecordParcelAlarm> page  = getManager().list(request.getResouces().getPage(),request.getResouces().getSearch());
            return RestfulResponse.builder()
            .success()
            .setResouces(ModelResource.builder().setEntity(page).build())
            .build();
    }

    @Override
    @ApiOperation(value = "判断实体参数是否存在")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "request",value = "需要判断的实体",required = true)
    })
    @RequestMapping(value = "/property/unique",method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<ModelResource<Boolean>> isUniqueProperty(
        @Validated @NotNull(message = "请求参数不能为空")
        @RequestBody RestfulRequest<ModelResource<RecordParcelAlarm>> request) {
            Boolean isUniqueProperty = getManager().isUniqueProperty(request.getResouces().getEntity());
            return RestfulResponse.builder()
            .success()
            .setResouces(ModelResource.builder().setEntity(isUniqueProperty).build())
            .build();
    }
    @Override
    @ApiOperation(value = "判断实体参数是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "property",value = "判断的参数",required = true),
            @ApiImplicitParam(name = "request",value = "需要判断的实体",required = true)
    })
    @RequestMapping(value = "/property/unique/{property}",method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<ModelResource<Boolean>> isUniqueProperty(
        @NotBlank(message = "需要判断实体参数名不能为空")
        @PathVariable("property") String property,
        @Validated @NotNull(message = "请求参数不能为空")
        @RequestBody RestfulRequest<ModelResource<RecordParcelAlarm>> request) {
            Boolean isUniqueProperty = getManager().isUniqueProperty(property,request.getResouces().getEntity());
            return RestfulResponse.builder()
            .success()
            .setResouces(ModelResource.builder().setEntity(isUniqueProperty).build())
            .build();
    }

    @ApiOperation(value = "告警处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restfulRequest",value = "告警信息",required = true)
    })
    @PutMapping("/handle")
    @Override
    public void handle(@RequestBody RestfulRequest<ModelResource<RecordParcelAlarm>> restfulRequest) {
        try {
            getManager().handle(restfulRequest.getResouces().getEntity());
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error(LOGGER_TEMPLATE,"create",String.format("调用“告警处理”时，发生错误，错误信息：[%s]",e.getMessage()));
            }
            e.printStackTrace();
            throw new ApplicationException("出入库系统发生系统错误,具体请查看出入库系统日志.");
        }
    }

    @ApiOperation(value = "根据出入库记录id查询告警信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recordId",value = "出入库记录id",required = true)
    })
    @GetMapping("/query/{recordId}")
    @Override
    public RestfulResponse<ModelResource<RecordParcelAlarm>> queryWithRecordId(@PathVariable("recordId") String recordId) {
        try {
            return RestfulResponse.builder().setResouces(ModelResource.builder().setEntity(getManager().queryWithRecordId(recordId)).build()).build();
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error(LOGGER_TEMPLATE,"queryWithRecordId",String.format("调用“根据出入库记录id查询告警信息”时，发生错误，错误信息：[%s]",e.getMessage()));
            }
            e.printStackTrace();
            throw new ApplicationException("出入库系统发生系统错误,具体请查看出入库系统日志.");
        }
    }
}