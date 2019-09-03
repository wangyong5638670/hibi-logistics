/**
* 版权所有(C) 2019 深圳市雁联计算系统有限公司
* 创建: luojinxu 2019-07-31
*/
package com.ylink.hibiscus.logistics.app.record;


import com.ylink.hibiscus.app.logistics.record.RecordLineParcelAppService;
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
import com.ylink.hibiscus.entity.logistics.record.CountRecord;
import com.ylink.hibiscus.entity.logistics.record.CountRecordAll;
import com.ylink.hibiscus.entity.logistics.record.RecordLineParcel;
import com.ylink.hibiscus.logistics.service.record.RecordLineParcelManager;
import com.ylink.hibiscus.model.logistics.record.RecordLineParcelSearch;
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
import java.util.List;


/**
  * @author luojinxu
  * @date 2019-07-31
  */
@Api("出入库记录线路袋接口")
@RestController()
@Slf4j
@RequestMapping("/v1/logistics/record/line/parcel")
public class RecordLineParcelAppServiceImpl extends BaseAppServiceImpl<RecordLineParcel,RecordLineParcelManager> implements RecordLineParcelAppService {

    @Override
    @ApiOperation(value = "保存实体")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "保存实体参数",required = true)
    })
    @RequestMapping(value = "/curd",method = RequestMethod.POST)
    public void create(@Validated @NotNull(message = "请求参数不能为空")
    @RequestBody RestfulRequest<ModelResource<RecordLineParcel>> request) {
        getManager().create(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "保存实体集合")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "保存实体集合参数",required = true)
    })
    @RequestMapping(value = "/curds",method = RequestMethod.POST)
    public void createWithCollection(@Validated @NotNull(message = "请求参数不能为空")
    @RequestBody RestfulRequest<ModelResource<Collection<RecordLineParcel>>> request) {
        getManager().create(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "更新实体")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "更新实体参数",required = true)
    })
    @RequestMapping(value = "/curd",method = RequestMethod.PUT)
    public void update(@Validated @NotNull(message = "请求参数不能为空")
    @RequestBody RestfulRequest<ModelResource<RecordLineParcel>> request) {
        getManager().update(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "更新实体集合")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "更新实体集合参数",required = true)
    })
    public void updateWithCollection(
    @Validated @NotNull(message = "请求参数不能为空")
    @RequestBody  RestfulRequest<ModelResource<Collection<RecordLineParcel>>> request) {
        getManager().update(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "删除实体集合")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "删除实体集合参数",required = true)
    })
    public void removeWithCollection(
    @Validated @NotNull(message = "请求参数不能为空")
    @RequestBody RestfulRequest<ModelResource<Collection<RecordLineParcel>>> request) {
        getManager().remove(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "分页查询（实体为查询条件）")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "request",value = "分页查询、查询条件参数",required = true)
    })
    @RequestMapping(value = "/list/query/criteria/entity",method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<ModelResource<ListPage<RecordLineParcel>>> list(
        @Validated @NotNull(message = "请求参数不能为空")
        @RequestBody RestfulRequest<PaginationResouce<RecordLineParcel, RecordLineParcel>> request) {
        ListPage<RecordLineParcel> page  = getManager().list(request.getResouces().getPage(),request.getResouces().getSearch());
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
    public RestfulResponse<ModelResource<ListPage<RecordLineParcel>>> listWithTerm(
        @Validated @NotNull(message = "请求参数不能为空")
        @RequestBody RestfulRequest<PaginationResouce<RecordLineParcel, Collection<Term>>> request) {
            ListPage<RecordLineParcel> page  = getManager().list(request.getResouces().getPage(),request.getResouces().getSearch());
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
    public RestfulResponse<ModelResource<ListPage<RecordLineParcel>>> listWithSearchCondition(
        @Validated @NotNull(message = "请求参数不能为空")
        @RequestBody RestfulRequest<PaginationResouce<RecordLineParcel, SearchCondition>> request) {
        ListPage<RecordLineParcel> page  = getManager().list(request.getResouces().getPage(),request.getResouces().getSearch());
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
        @RequestBody RestfulRequest<ModelResource<RecordLineParcel>> request) {
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
        @RequestBody RestfulRequest<ModelResource<RecordLineParcel>> request) {
            Boolean isUniqueProperty = getManager().isUniqueProperty(property,request.getResouces().getEntity());
            return RestfulResponse.builder()
            .success()
            .setResouces(ModelResource.builder().setEntity(isUniqueProperty).build())
            .build();
    }

    @Override
    @ApiOperation(value = "出入库记录统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "siteId", value = "站点编号", required = true),
            @ApiImplicitParam(name = "planId", value = "计划编号"),
            @ApiImplicitParam(name = "date", value = "日期", required = true)
    })
    @RequestMapping(value = "/count/line/record/all", method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<ModelResource<List<CountRecordAll>>> statistics(@RequestParam(value = "siteId") String siteId,
                                                                           @RequestParam(value = "planId" , required = false) String planId,
                                                                           @RequestParam(value = "date")  String date) {
        try {
            return RestfulResponse.builder()
                    .success()
                    .setResouces(ModelResource.builder().setEntity(getManager().statistics(siteId, planId, date)).build())
                    .build();
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error("调用“出入库记录统计”时,发生错误,错误信息:[{}]",e.getMessage());
            }
            throw new ApplicationException("logistics系统发生系统错误,具体请查看logistics系统日志.");
        }
    }

    @Override
    @ApiOperation(value = "出入库记录计划统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "siteId", value = "站点编号"),
            @ApiImplicitParam(name = "planId", value = "计划编号", required = true),
            @ApiImplicitParam(name = "lineId", value = "线路编号"),
            @ApiImplicitParam(name = "date", value = "日期", required = true)
    })
    @RequestMapping(value = "/count/line/record", method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<ModelResource<List<CountRecord>>> statisticsLine(@RequestParam(value = "siteId", required = false) String siteId,
                                                                            @RequestParam(value = "planId")  String planId,
                                                                            @RequestParam(value = "lineId", required = false) String lineId,
                                                                            @RequestParam(value = "date")  String date) {
        try {
            return RestfulResponse.builder()
                    .success()
                    .setResouces(ModelResource.builder().setEntity(getManager().statisticsLine(siteId, planId, lineId, date)).build())
                    .build();
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error("调用“出入库记录统计”时,发生错误,错误信息:[{}]",e.getMessage());
            }
            throw new ApplicationException("logistics系统发生系统错误,具体请查看logistics系统日志.");
        }
    }

    @Override
    @ApiOperation(value = "出入库记录计划统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "siteId", value = "站点编号", required = true),
            @ApiImplicitParam(name = "date", value = "日期", required = true)
    })
    @RequestMapping(value = "/count/plan/record", method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<ModelResource<List<CountRecord>>> statisticsPlan(@RequestParam(value = "siteId") String siteId,
                                                                            @RequestParam(value = "date")  String date) {
        try {
            return RestfulResponse.builder()
                    .success()
                    .setResouces(ModelResource.builder().setEntity(getManager().statisticsPlan(siteId,date)).build())
                    .build();
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error("调用“出入库记录统计”时,发生错误,错误信息:[{}]",e.getMessage());
            }
            throw new ApplicationException("logistics系统发生系统错误,具体请查看logistics系统日志.");
        }
    }

    @Override
    @ApiOperation(value = "查询袋记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request",value = "请求参数集合",required = true)
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<ModelResource<List<RecordLineParcel>>> listRecordLineParcel(@Validated @NotNull(message = "请求参数不能为空")
                                                                                           @RequestBody RestfulRequest<ModelResource<RecordLineParcelSearch>> request) {
        try {
            RecordLineParcelSearch search = request.getResouces().getEntity();
            if(search == null){
                search = new RecordLineParcelSearch();
            }
            return RestfulResponse.builder()
                    .success()
                    .setResouces(ModelResource.builder().setEntity(getManager().listRecordLineParcel(search)).build())
                    .build();
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error("调用“出入库记录统计”时,发生错误,错误信息:[{}]",e.getMessage());
            }
            throw new ApplicationException("logistics系统发生系统错误,具体请查看logistics系统日志.");
        }
    }

    @Override
    @ApiOperation(value = "出入库录入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request",value = "请求参数集合",required = true)
    })
    @RequestMapping(value = "/complete", method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<ModelResource<String>>  completeRecordLineParcels(
            @Validated @NotNull(message = "请求参数不能为空")
            @RequestBody RestfulRequest<ModelResource<RecordLineParcelSearch>> request) {
        try {
            RecordLineParcelSearch search = request.getResouces().getEntity();
            String grade = getManager().completeRecordLineParcels(search.getSiteId(),search.getPlanId(),search.getPlanLineId(),
                search.getStaffId(),search.getRecordLineParcelIds(),search.getDate());
            return RestfulResponse.builder()
                    .success()
                    .setResouces(ModelResource.builder().setEntity(grade).build())
                    .build();
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error("调用“出入库录入”时,发生错误,错误信息:[{}]",e.getMessage());
            }
            throw new ApplicationException("logistics系统发生系统错误,具体请查看logistics系统日志.");
        }
    }

    @Override
    @ApiOperation(value = "出入库袋处理（出入库记录删除）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request",value = "请求参数集合",required = true)
    })
    @RequestMapping(value = "/correction", method = RequestMethod.POST)
    @ResponseBody
    public void correctionRecordLineParcel(@Validated @NotNull(message = "请求参数不能为空")
                               @RequestBody RestfulRequest<ModelResource<RecordLineParcel>> request) {
        try {
            RecordLineParcel recordLineParcel = request.getResouces().getEntity();
            getManager().handle(recordLineParcel);
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error("调用“调用袋记录处理”时,发生错误,错误信息:[{}]",e.getMessage());
            }
            throw new ApplicationException("logistics系统发生系统错误,具体请查看logistics系统日志.");
        }
    }

    @ApiOperation(value = "复核特殊袋")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "出入库袋记录id", required = true, dataType = "String")
    })
    @PutMapping("/check/status")
    @Override
    public void doCheckStatus(String id) {
        try {
            getManager().doCheckStatus(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e){
            if (log.isErrorEnabled()){
                log.error("调用“复核特殊袋处理”时,发生错误,错误信息:[{}]",e.getMessage());
            }
            e.printStackTrace();
            throw new ApplicationException("根据出入库袋记录主键修改复核特殊袋状态[异常],详情请查看[logistics]系统日志");
        }
    }


}