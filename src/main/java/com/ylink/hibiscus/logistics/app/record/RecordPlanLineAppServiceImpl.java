/**
* 版权所有(C) 2019 深圳市雁联计算系统有限公司
* 创建: luojinxu 2019-07-31
*/
package com.ylink.hibiscus.logistics.app.record;


import com.ylink.hibiscus.app.logistics.record.RecordPlanLineAppService;
import com.ylink.hibiscus.common.base.exception.ApplicationException;
import com.ylink.hibiscus.common.base.exception.BusinessException;
import com.ylink.hibiscus.entity.logistics.record.RecordPlanLine;
import com.ylink.hibiscus.entity.logistics.record.RecordResponse;
import com.ylink.hibiscus.logistics.service.record.RecordPlanLineManager;

import com.ylink.hibiscus.common.base.app.BaseAppServiceImpl;
import com.ylink.hibiscus.common.base.dao.SearchCondition;
import com.ylink.hibiscus.common.base.dao.Term;
import com.ylink.hibiscus.common.base.model.page.ListPage;
import com.ylink.hibiscus.common.base.resource.base.ModelResource;
import com.ylink.hibiscus.common.base.resource.base.PaginationResouce;
import com.ylink.hibiscus.common.base.resource.communication.RestfulRequest;
import com.ylink.hibiscus.common.base.resource.communication.RestfulResponse;
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
@Api("出入库记录计划线路接口")
@RestController()
@Slf4j
@RequestMapping("/v1/logistics/record/plan/line")
public class RecordPlanLineAppServiceImpl extends BaseAppServiceImpl<RecordPlanLine,RecordPlanLineManager> implements RecordPlanLineAppService {

    @Override
    @ApiOperation(value = "保存实体")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "保存实体参数",required = true)
    })
    @RequestMapping(value = "/curd",method = RequestMethod.POST)
    public void create(@Validated @NotNull(message = "请求参数不能为空")
    @RequestBody RestfulRequest<ModelResource<RecordPlanLine>> request) {
        getManager().create(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "保存实体集合")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "保存实体集合参数",required = true)
    })
    @RequestMapping(value = "/curds",method = RequestMethod.POST)
    public void createWithCollection(@Validated @NotNull(message = "请求参数不能为空")
    @RequestBody RestfulRequest<ModelResource<Collection<RecordPlanLine>>> request) {
        getManager().create(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "更新实体")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "更新实体参数",required = true)
    })
    @RequestMapping(value = "/curd",method = RequestMethod.PUT)
    public void update(@Validated @NotNull(message = "请求参数不能为空")
    @RequestBody RestfulRequest<ModelResource<RecordPlanLine>> request) {
        getManager().update(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "更新实体集合")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "更新实体集合参数",required = true)
    })
    public void updateWithCollection(
    @Validated @NotNull(message = "请求参数不能为空")
    @RequestBody  RestfulRequest<ModelResource<Collection<RecordPlanLine>>> request) {
        getManager().update(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "删除实体集合")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "删除实体集合参数",required = true)
    })
    public void removeWithCollection(
    @Validated @NotNull(message = "请求参数不能为空")
    @RequestBody RestfulRequest<ModelResource<Collection<RecordPlanLine>>> request) {
        getManager().remove(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "分页查询（实体为查询条件）")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "分页查询、查询条件参数",required = true)
    })
    @RequestMapping(value = "/list/query/criteria/entity",method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<ModelResource<ListPage<RecordPlanLine>>> list(
        @Validated @NotNull(message = "请求参数不能为空")
        @RequestBody RestfulRequest<PaginationResouce<RecordPlanLine, RecordPlanLine>> request) {
        ListPage<RecordPlanLine> page  = getManager().list(request.getResouces().getPage(),request.getResouces().getSearch());
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
    public RestfulResponse<ModelResource<ListPage<RecordPlanLine>>> listWithTerm(
        @Validated @NotNull(message = "请求参数不能为空")
        @RequestBody RestfulRequest<PaginationResouce<RecordPlanLine, Collection<Term>>> request) {
            ListPage<RecordPlanLine> page  = getManager().list(request.getResouces().getPage(),request.getResouces().getSearch());
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
    public RestfulResponse<ModelResource<ListPage<RecordPlanLine>>> listWithSearchCondition(
        @Validated @NotNull(message = "请求参数不能为空")
        @RequestBody RestfulRequest<PaginationResouce<RecordPlanLine, SearchCondition>> request) {
        ListPage<RecordPlanLine> page  = getManager().list(request.getResouces().getPage(),request.getResouces().getSearch());
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
        @RequestBody RestfulRequest<ModelResource<RecordPlanLine>> request) {
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
        @RequestBody RestfulRequest<ModelResource<RecordPlanLine>> request) {
            Boolean isUniqueProperty = getManager().isUniqueProperty(property,request.getResouces().getEntity());
            return RestfulResponse.builder()
            .success()
            .setResouces(ModelResource.builder().setEntity(isUniqueProperty).build())
            .build();
    }

    @Override
    @ApiOperation(value = "根据传入的计划编号，线路编号和日期查询出入库计划线路记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "计划编号", required = true),
            @ApiImplicitParam(name = "lineId", value = "计划线路编号", required = true),
            @ApiImplicitParam(name = "date", value = "日期", required = true)
    })
    @RequestMapping(value = "/recordplanline/query", method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<ModelResource<RecordPlanLine>> query(@RequestParam(value = "planId") String planId,
                                                                @RequestParam(value = "lineId") String lineId,
                                                                @RequestParam(value = "date") String date){
        try {
            return RestfulResponse.builder()
                    .success()
                    .setResouces(ModelResource.builder().setEntity(getManager().query(planId,lineId, date)).build())
                    .build();
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error("调用“出入库扫码+线路统计信息”时,发生错误,错误信息:[{}]",e.getMessage());
            }
            throw new ApplicationException("logistics系统发生系统错误,具体请查看logistics系统日志.");
        }
    }

    @Override
    @ApiOperation(value = "出入库扫码+线路统计信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "siteId", value = "站点编号"),
            @ApiImplicitParam(name = "recordPlanId", value = "计划编号"),
            @ApiImplicitParam(name = "recordLineId", value = "计划线路编号"),
            @ApiImplicitParam(name = "staffId", value = "派送人编号"),
            @ApiImplicitParam(name = "code", value = "扫到的编码", required = true)
    })
    @RequestMapping(value = "/recordlineparcel/record", method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<ModelResource<RecordResponse>> record(@RequestParam(value = "siteId", required = false) String siteId,
                                                                 @RequestParam(value = "recordPlanId", required = false) String recordPlanId,
                                                                 @RequestParam(value = "recordLineId", required = false) String recordLineId,
                                                                 @RequestParam(value = "staffId", required = false) String staffId,
                                                                 @RequestParam(value = "code") String code) {
        try {
            return RestfulResponse.builder()
                    .success()
                    .setResouces(ModelResource.builder().setEntity(getManager().record(siteId,recordPlanId, recordLineId, staffId, code)).build())
                    .build();
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error("调用“出入库扫码+线路统计信息”时,发生错误,错误信息:[{}]",e.getMessage());
            }
            throw new ApplicationException("logistics系统发生系统错误,具体请查看logistics系统日志.",e);
        }
    }
}