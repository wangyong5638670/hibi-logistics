/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.record;


import com.ylink.hibiscus.common.base.dao.SearchCondition;
import com.ylink.hibiscus.common.base.enums.*;
import com.ylink.hibiscus.common.base.exception.BusinessException;
import com.ylink.hibiscus.common.base.model.page.ListPage;
import com.ylink.hibiscus.common.base.resource.base.ModelResource;
import com.ylink.hibiscus.common.base.resource.base.PaginationResouce;
import com.ylink.hibiscus.common.base.resource.communication.RestfulRequest;
import com.ylink.hibiscus.common.base.resource.communication.RestfulResponse;
import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.common.base.support.page.DisPlayPage;
import com.ylink.hibiscus.common.base.utils.DateUtils;
import com.ylink.hibiscus.entity.logistics.record.*;
import com.ylink.hibiscus.entity.logistics.staff.Staff;
import com.ylink.hibiscus.enums.logistics.PlanType;
import com.ylink.hibiscus.logistics.dao.record.RecordPlanLineMapper;
import com.ylink.hibiscus.logistics.service.plan.PlanLineParcelManager;
import com.ylink.hibiscus.logistics.service.staff.StaffManager;
import com.ylink.hibiscus.model.logistics.record.RecordLineParcelSearch;
import com.ylink.hibiscus.remote.client.logistics.record.RecordLineParcelAppServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author luojinxu
 * @date 2019-07-30
 */
@Slf4j
@Service("recordPlanLineManager")
public class RecordPlanLineManagerImpl extends BaseMybatisBaManagerImpl<RecordPlanLine, RecordPlanLineMapper> implements RecordPlanLineManager {

    @Autowired
    RecordLineParcelAppServiceClient recordLineParcelAppServiceClient;
    @Autowired
    private RecordPlanManager recordPlanManager;
    @Autowired
    PlanLineParcelManager planLineParcelManager;
    @Autowired
    private RecordLineParcelManager recordLineParcelManager;
    @Autowired
    private RecordPlanLineManager recordPlanLineManager;
    @Autowired
    private RecordParcelAlarmManager recordParcelAlarmManager;
    @Autowired
    private StaffManager staffManager;

    @Override
    public void newRecordPlanLines(List<String> planIds, String date) {
        getDao().newRecordPlanLines(planIds, date);
    }

    @Override
    public void completeRecordLine(String planId, String lineId,String date) {
        if(StringUtils.isEmpty(planId)||StringUtils.isEmpty(date)){
            if (log.isErrorEnabled()) {
                log.error("服务名:[{}],方法:[{}],信息:完成计划任务时,计划编号[{}]日期[{}]必填", "RecordPlanLineManager", "completeRecordLine", planId,date);
            }
            throw new BusinessException("完成计划任务时,计划编号，日期必填", ResponseCodeType.LOGISTICS_PARAMETER_ERROR);
        }
        //查询记录里未完成的计划袋
        RecordLineParcelSearch search = new RecordLineParcelSearch();
        search.setDate(date);
        search.setPlanLineId(lineId);
        search.setPlanId(planId);
        search.setStatus(LineParcelStatus.TODO.getCode());

        List<RecordLineParcel> list = recordLineParcelManager.listRecordLineParcel(search);
        //有未完成的袋
        if (!CollectionUtils.isEmpty(list)) {
            for(RecordLineParcel recordLineParcel:list){
                //更改记录状态
                recordLineParcel.setStatus(LineParcelStatus.LOST.getCode());
                recordLineParcelManager.update(recordLineParcel);

                //创建出入库记录告警信息
                recordParcelAlarmManager.creatRecordParcelAlarm(recordLineParcel,LineParcelStatus.LOST.getCode());

            }
        }
       //线路编号不为空
        if(StringUtils.isNotEmpty(lineId)){
            RecordPlanLine recordPlanLine= getDao().query(planId,lineId,date);
            if(recordPlanLine == null){
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],信息:通过线路编号，日期查询记录为,计划编号[{}]计划线路编号[{}]，日期[{}]", "RecordPlanLineManager", "completeRecordLine", planId,lineId,date);
                }
                throw new BusinessException("完成计划线路任务时,计划线路记录信息为空！",ResponseCodeType.LOGISTICS_RECORD_NOT_EXISTENCE);
            }
            //判断当前线路是否完成。未完成改为完成
            if(FinishStatus.TODO.getCode().equals(recordPlanLine.getStatus())){
                recordPlanLine.setStatus(FinishStatus.FINISH.getCode());
                this.update(recordPlanLine);
            }
            List<RecordPlanLine>  recordPlanLineList = recordPlanLineManager.findRecordPlanLine(planId,FinishStatus.TODO.getCode(),date);
            if(CollectionUtils.isEmpty(recordPlanLineList)){
                //更改所有未完成计划为完成
                recordPlanManager.update(planId, date, FinishStatus.TODO.getCode(),FinishStatus.FINISH.getCode());
            }

            return;
        }
        //线路为空，更改所有未完成计划线路为完成
        getDao().update(planId, date, FinishStatus.TODO.getCode(),FinishStatus.FINISH.getCode());
        //更改所有未完成计划为完成
        recordPlanManager.update(planId, date, FinishStatus.TODO.getCode(),FinishStatus.FINISH.getCode());




    }

    @Override
    public List<RecordPlanLine> findRecordPlanLine(@NotBlank String planId,@NotBlank String status,@NotBlank String date) {
        return getDao().findRecordPlanLine(planId, status, date);
    }


    @Override
    public RecordPlanLine query(@NotBlank String planId,@NotBlank String lineId,@NotBlank String date) {
        return getDao().query(planId, lineId, date);
    }

    @Override
    public RecordResponse record(String siteId,String recordPlanId, String recordLineId, String staffId, String code) {
        String lineName = null;
        String planName = null;
        String date = DateUtils.format(new Date(),DateUtils.DATE_FORMAT_yyyyMMdd);
        RecordResponse response = new RecordResponse();
        //判断条码的扫描类型
        ScanType scanType = planLineParcelManager.scan(code);
        if(null == scanType){
            if (log.isErrorEnabled()) {
                log.error("服务名:[{}],方法:[{}],信息:错误的条码[{}]", "planLineParcelManager", "scan", code);
            }
            throw new BusinessException("错误的条码！",ResponseCodeType.LOGISTICS_SCAN_NOT_EXISTENCE);
        }

        //1.员工条码
        if(ScanType.STAFF.equals(scanType)){
            //更具传的参数查询数据
            findRecordPlanAndRecordPlanLine(response,recordPlanId,recordLineId,staffId,date);
            //查询员工信息
            Staff staff= staffManager.get(code);
            if(staff == null){
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],信息:根据条码查询的员工信息为空，编号[{}]", "RecordPlanLineManager", "record", code);
                }
                throw new BusinessException("根据条码查询的员工信息为空！",ResponseCodeType.LOGISTICS_STAFF_NOT_EXISTENCE);
            }
            response.setStaffId(staff.getId());
            response.setStaffName(staff.getName());
            response.setDate(date);
            return response;
        }
        //2.时段线路 时段线路码=时段编码+线路编码
        if(ScanType.LINE.equals(scanType)){
            //更具传的参数查询数据
            findRecordPlanAndRecordPlanLine(response,null,null,staffId,date);
            //2.1 根据日期和条码里的时段信息得到计划信息
            RecordPlan recordPlan =type(siteId,code,date);
            if(recordPlan == null){
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],信息:根据条码查询的计划信息为空，编号[{}]", "RecordPlanLineManager", "record", code);
                }
                throw new BusinessException("根据条码查询的计划信息为空！",ResponseCodeType.LOGISTICS_PLAN_NOT_EXISTENCE);
            }
            //站点编号
            siteId = recordPlan.getSiteId();
            //得到计划编号
            recordPlanId = recordPlan.getPlanId();
            planName = recordPlan.getName();
            response.setRecordPlanId(recordPlanId);
            //计划线路编号
            //截取字符
            String lineId = code.substring(4);
            //2.2 根据日期和条码里的线路编号得到计划线路记录信息
            RecordPlanLine recordPlanLine= getDao().query(recordPlan.getPlanId(),lineId,date);
            if(recordPlanLine == null){
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],信息:通过线路编号，日期查询记录为,计划编号[{}]计划线路编号[{}]，日期[{}]", "RecordPlanLineManager", "record", recordPlan.getPlanId(),recordPlan.getPlanId(),lineId,date);
                }
                throw new BusinessException("根据日期和条码里的线路编号得到计划线路记录信息为空！",ResponseCodeType.LOGISTICS_RECORD_NOT_EXISTENCE);
            }
            recordLineId=recordPlanLine.getLineId();
            response.setLineName(recordPlanLine.getLineName());
            //线路状态
            response.setLineStatus(recordPlanLine.getStatus());

        }

        //3.袋 条码是袋编号
        if(ScanType.PARCEL.equals(scanType)){
            if(StringUtils.isEmpty(recordPlanId)||StringUtils.isEmpty(recordLineId)){
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],信息:通过线路编号，日期查询记录为,计划编号[{}]计划线路编号[{}]", "RecordPlanLineManager", "record", recordPlanId,recordLineId,date);
                }
                throw new BusinessException("当条码是袋编号时，计划编号，计划线路编号必填！",ResponseCodeType.LOGISTICS_PARAMETER_ERROR);
            }
            //更具传的参数查询数据
            findRecordPlanAndRecordPlanLine(response,recordPlanId,recordLineId,staffId,date);
            //根据计划查询信息得到站点编号
            RecordPlan recordPlan =recordPlanManager.query(recordPlanId,date);
            if(recordPlan == null){
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],信息:根据计划，日期查询的计划信息为空，编号[{}]", "RecordPlanLineManager", "record", recordPlanId);
                }
                throw new BusinessException("根据计划编号，日期查询的计划记录信息为空！",ResponseCodeType.LOGISTICS_RECORD_NOT_EXISTENCE);
            }
            //站点编号
            siteId = recordPlan.getSiteId();
            recordPlanId = recordPlan.getPlanId();
            planName = recordPlan.getName();
            List<String> recordLineParcelIds = new ArrayList<>();
            recordLineParcelIds.add(code);
            //袋完成入库
            String grade = recordLineParcelManager.completeRecordLineParcels(recordPlan.getSiteId(),recordPlanId,recordLineId,staffId,recordLineParcelIds,date);
            //判断是否存在特殊袋
            if(GradeType.SPECIAL.getCode().equals(grade)){
                response.setGrade(grade);
            }
        }
        //4.计划或线路完成
        if(ScanType.FINISH.equals(scanType)){
            if(StringUtils.isEmpty(recordPlanId)||StringUtils.isEmpty(siteId)){
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],信息:通过线路编号，日期查询记录为,站点编号[{}]计划编号[{}]计划线路编号[{}]，日期[{}]", "RecordPlanLineManager", "record",siteId,recordPlanId,recordLineId,date);
                }
                throw new BusinessException("线路完成时，计划编号和站点编号必填！",ResponseCodeType.LOGISTICS_PARAMETER_ERROR);
            }
            //线路完成入库
            recordLineParcelManager.completeRecordLineParcels(siteId,recordPlanId,recordLineId,staffId,null,date);

        }

        //2.3 出入库线路袋统计
        List<CountRecord> countRecords = recordLineParcelManager.statisticsLine(siteId,recordPlanId,recordLineId,date);
        CountRecord countRecord =new CountRecord(0,0,0,0);
        if(!CollectionUtils.isEmpty(countRecords)){
            countRecord=countRecords.get(0);
        }
        response.setRecordPlanId(recordPlanId);
        response.setPlanName(planName);
        response.setRecordLineId(recordLineId);
        //response.setLineName(lineName);
        response.setStaffId(staffId);
        response.setCountError(countRecord.getCountError());
        response.setCountLose(countRecord.getCountLost());
        response.setCountSuccess(countRecord.getCountSuccess());
        response.setCountTodo(countRecord.getCountTodo());
        response.setDate(date);
        return response;
    }

    //获取计划信息
    private RecordPlan type(String siteId,String code,String date){
        //根据条码判断出入库计划类型
        String planType= planType(code);
        if (log.isInfoEnabled()) {
            log.info("服务名:[{}],方法:[{}],信息:获取计划信息,更具出入库计划类型[{}]，日期[{}],code[{}]", "RecordPlanLineManager", "type",planType,date,code);
        }
        //根据时间和类型查询计划信息
        return recordPlanManager.queryRecordPlan(siteId,date,planType);
    }

    //根据条码判断出入库计划类型
    private String planType(String code){
        //截取字符
        String stypeCode = code.substring(0,4);
        if("0010".equals(stypeCode)){
            return PlanType.MORNING_OUT.getCode();
        }
        if("0020".equals(stypeCode)){
            return PlanType.NOON_IN.getCode();
        }
        if("0030".equals(stypeCode)){
            return PlanType.NOON_OUT.getCode();
        }
        if("0040".equals(stypeCode)){
            return PlanType.AFTERNOON_IN.getCode();
        }
        return null;
    }

    private void findRecordPlanAndRecordPlanLine(RecordResponse response,String recordPlanId, String recordLineId,String staffId,String date){
        //查计划
        if(StringUtils.isNotEmpty(recordPlanId)){
            RecordPlan recordPlan =recordPlanManager.query(recordPlanId,date);
            if(recordPlan == null){
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],信息:根据计划，日期查询的计划信息为空，编号[{}]", "RecordPlanLineManager", "findRecordPlanAndRecordPlanLine",recordPlanId);
                }
                throw new BusinessException("根据计划编号，日期查询的计划记录信息为空！",ResponseCodeType.LOGISTICS_RECORD_NOT_EXISTENCE);
            }
            response.setRecordPlanId(recordPlan.getPlanId());
            response.setPlanName(recordPlan.getName());
            //查线路
            if(StringUtils.isNotEmpty(recordLineId)){
                RecordPlanLine recordPlanLine= getDao().query(recordPlan.getPlanId(),recordLineId,date);
                if(recordPlanLine == null){
                    if (log.isErrorEnabled()) {
                        log.error("服务名:[{}],方法:[{}],信息:通过线路编号，日期查询记录为,计划编号[{}]计划线路编号[{}]，日期[{}]", "RecordPlanLineManager", "findRecordPlanAndRecordPlanLine",recordPlan.getPlanId(),recordPlan.getPlanId(),recordLineId,date);
                    }
                    throw new BusinessException("根据日期和条码里的线路编号得到计划线路记录信息为空！",ResponseCodeType.LOGISTICS_RECORD_NOT_EXISTENCE);
                }
                response.setRecordLineId(recordPlanLine.getLineId());
                response.setLineName(recordPlanLine.getLineName());
                //线路状态
                response.setLineStatus(recordPlanLine.getStatus());
            }
        }
        //员工信息
        if(StringUtils.isNotEmpty(staffId)){
            Staff staff= staffManager.get(staffId);
            if(staff == null){
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],信息:根据条码查询的员工信息为空，编号[{}]", "RecordPlanLineManager", "findRecordPlanAndRecordPlanLine", staffId);
                }
                throw new BusinessException("根据条码查询的员工信息为空！",ResponseCodeType.LOGISTICS_STAFF_NOT_EXISTENCE);
            }
            response.setStaffId(staff.getId());
            response.setStaffName(staff.getName());
        }
    }

}