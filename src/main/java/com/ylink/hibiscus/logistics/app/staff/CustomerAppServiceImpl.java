/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-03-28
 */
package com.ylink.hibiscus.logistics.app.staff;

import com.ylink.hibiscus.app.logistics.staff.CustomerAppService;
import com.ylink.hibiscus.common.base.app.BaseAppServiceImpl;
import com.ylink.hibiscus.common.base.dao.SearchCondition;
import com.ylink.hibiscus.common.base.dao.Term;
import com.ylink.hibiscus.common.base.model.page.ListPage;
import com.ylink.hibiscus.common.base.resource.base.ModelResource;
import com.ylink.hibiscus.common.base.resource.base.PaginationResouce;
import com.ylink.hibiscus.common.base.resource.communication.RestfulRequest;
import com.ylink.hibiscus.common.base.resource.communication.RestfulResponse;
import com.ylink.hibiscus.entity.logistics.staff.Customer;
import com.ylink.hibiscus.logistics.service.staff.CustomerManager;
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
* @author lilinjun
* @date 2019-07-28
*/
@Api("员工接口")
@RestController()
@Slf4j
@RequestMapping("/v1/logistics/staff/customer")
public class CustomerAppServiceImpl extends BaseAppServiceImpl<Customer, CustomerManager> implements CustomerAppService {


    @Override
    @ApiOperation(value = "保存实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request",value = "保存实体参数",required = true)
    })
    @RequestMapping(value = "/curd",method = RequestMethod.POST)
    public void create(@Validated @NotNull(message = "请求参数不能为空")
                       @RequestBody RestfulRequest<ModelResource<Customer>> request) {
        getManager().create(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "保存实体集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request",value = "保存实体集合参数",required = true)
    })
    @RequestMapping(value = "/curds",method = RequestMethod.POST)
    public void createWithCollection(@Validated @NotNull(message = "请求参数不能为空")
                                     @RequestBody RestfulRequest<ModelResource<Collection<Customer>>> request) {
        getManager().create(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "更新实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request",value = "更新实体参数",required = true)
    })
    @RequestMapping(value = "/curd",method = RequestMethod.PUT)
    public void update(@Validated @NotNull(message = "请求参数不能为空")
                       @RequestBody RestfulRequest<ModelResource<Customer>> request) {
        getManager().update(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "更新实体集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request",value = "更新实体集合参数",required = true)
    })
    public void updateWithCollection(
            @Validated @NotNull(message = "请求参数不能为空")
            @RequestBody  RestfulRequest<ModelResource<Collection<Customer>>> request) {
        getManager().update(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "删除实体集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request",value = "删除实体集合参数",required = true)
    })
    public void removeWithCollection(
            @Validated @NotNull(message = "请求参数不能为空")
            @RequestBody RestfulRequest<ModelResource<Collection<Customer>>> request) {
        getManager().remove(request.getResouces().getEntity());
    }
    @Override
    @ApiOperation(value = "分页查询（实体为查询条件）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request",value = "分页查询、查询条件参数",required = true)
    })
    @RequestMapping(value = "/list/query/criteria/entity",method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<ModelResource<ListPage<Customer>>> list(
            @Validated @NotNull(message = "请求参数不能为空")
            @RequestBody RestfulRequest<PaginationResouce<Customer, Customer>> request) {
        ListPage<Customer> page  = getManager().list(request.getResouces().getPage(),request.getResouces().getSearch());
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
    public RestfulResponse<ModelResource<ListPage<Customer>>> listWithTerm(
            @Validated @NotNull(message = "请求参数不能为空")
            @RequestBody RestfulRequest<PaginationResouce<Customer, Collection<Term>>> request) {
        ListPage<Customer> page  = getManager().list(request.getResouces().getPage(),request.getResouces().getSearch());
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
    public RestfulResponse<ModelResource<ListPage<Customer>>> listWithSearchCondition(
            @Validated @NotNull(message = "请求参数不能为空")
            @RequestBody RestfulRequest<PaginationResouce<Customer, SearchCondition>> request) {
        ListPage<Customer> page  = getManager().list(request.getResouces().getPage(),request.getResouces().getSearch());
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
            @RequestBody RestfulRequest<ModelResource<Customer>> request) {
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
            @RequestBody RestfulRequest<ModelResource<Customer>> request) {
        Boolean isUniqueProperty = getManager().isUniqueProperty(property,request.getResouces().getEntity());
        return RestfulResponse.builder()
                .success()
                .setResouces(ModelResource.builder().setEntity(isUniqueProperty).build())
                .build();
    }
}
