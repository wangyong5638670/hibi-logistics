/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.record;


import com.ylink.hibiscus.common.base.enums.*;
import com.ylink.hibiscus.common.base.exception.BusinessException;
import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.common.base.utils.DateUtils;
import com.ylink.hibiscus.entity.logistics.business.Line;
import com.ylink.hibiscus.entity.logistics.business.Parcel;
import com.ylink.hibiscus.entity.logistics.business.Site;
import com.ylink.hibiscus.entity.logistics.plan.Plan;
import com.ylink.hibiscus.entity.logistics.record.*;
import com.ylink.hibiscus.entity.logistics.staff.Staff;
import com.ylink.hibiscus.logistics.dao.record.RecordLineParcelMapper;
import com.ylink.hibiscus.logistics.service.base.SequenceManager;
import com.ylink.hibiscus.logistics.service.business.LineManager;
import com.ylink.hibiscus.logistics.service.business.ParcelManager;
import com.ylink.hibiscus.logistics.service.business.SiteManager;
import com.ylink.hibiscus.logistics.service.plan.PlanManager;
import com.ylink.hibiscus.logistics.service.staff.StaffManager;
import com.ylink.hibiscus.model.logistics.record.RecordLineParcelSearch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author luojinxu
 * @date 2019-07-30
 */
@Slf4j
@Service("recordLineParcelManager")
public class RecordLineParcelManagerImpl extends BaseMybatisBaManagerImpl<RecordLineParcel, RecordLineParcelMapper> implements RecordLineParcelManager {

    @Autowired
    private RecordPlanManager recordPlanManager;

    @Autowired
    private RecordPlanLineManager recordPlanLineManager;

    @Autowired
    private RecordParcelAlarmManager recordParcelAlarmManager;
    @Autowired
    private LineManager lineManager;
    @Autowired
    private ParcelManager parcelManager;
    @Autowired
    private SiteManager siteManager;
    @Autowired
    private PlanManager planManager;
    @Autowired
    private StaffManager staffManager;
    @Autowired
    private SequenceManager sequenceManager;


    @Override
    public List<CountRecordAll> statistics(String siteId, String planId, String date) {
        List<CountRecordAll> countRecordAlls = new ArrayList<CountRecordAll>();
        List<CountRecord> countRecords = getDao().statistics(siteId, planId, date);
        for (CountRecord cr: countRecords) {
            RecordPlanLine query = recordPlanLineManager.query(cr.getPlanId(), cr.getLineId(), date);
            if (null != query){
                cr.setLineStatus(query.getStatus());
                if (StringUtils.isEmpty(cr.getDate())){
                    cr.setDate(date);
                }
            }
        }
        List<String> planIds = countRecords.stream().map(CountRecord::getPlanId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(planIds)){
            return null;
        }
        planIds = new ArrayList(new TreeSet(planIds));
        planIds.remove(null);
        planIds.remove("");
        for (String pId:planIds) {
            List<CountRecord> collect = countRecords.stream().filter(countRecord -> pId.equals(countRecord.getPlanId())).collect(Collectors.toList());
            CountRecordAll countRecordAll = new CountRecordAll();
            RecordPlan recordPlan = recordPlanManager.query(pId,date);
            if (null!=recordPlan){
                countRecordAll.setStatus(recordPlan.getStatus());
            }
            countRecordAll.setPlanId(pId);
            countRecordAll.setPlanName(recordPlan.getName());
            countRecordAll.setPlanType(recordPlan.getType());
            countRecordAll.setCountRecords(collect);
            countRecordAlls.add(countRecordAll);
        }
        return countRecordAlls;
    }

    @Override
    public List<CountRecord> statisticsLine(String siteId, String planId,  String lineId,String date) {
        List<CountRecord> countRecords = getDao().statisticsLine(siteId, planId,lineId, date);
        return countRecords;
    }

    @Override
    public List<CountRecord> statisticsPlan(String siteId, String data) {
        return getDao().statisticsPlan(siteId, data);
    }

    @Override
    public void newRecordLineParcels(List<String> planIds, String date) {
        getDao().newRecordLineParcels(planIds, date);
    }

    @Override
    public String completeRecordLineParcels(String siteId, String planId, String lineId, String staffId, List<String> recordLineParcelIds,String date) {
        //是否存在特殊袋
        String grades = null;
        if(StringUtils.isEmpty(date)){
            //当前日期获取
            date = DateUtils.format(new Date(),DateUtils.DATE_FORMAT_yyyyMMdd);
        }
        //1.袋编号存在
        if(!CollectionUtils.isEmpty(recordLineParcelIds)){
            if(StringUtils.isEmpty(lineId)||StringUtils.isEmpty(planId)||StringUtils.isEmpty(siteId)){
                //1.1 无值 入库存在问题，当袋编号存在时做袋出入库记录处理,计划线路编号，计划编号，站点编号必填。
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],信息:当袋编号存在时做袋出入库记录处理,计划线路编号[{}]，计划编号[{}]，站点编号[{}]必填", "RecordPlanManager", "completePlan", lineId,planId,siteId);
                }
                throw new BusinessException("当袋编号存在时做袋出入库记录处理,计划线路编号，计划编号，站点编号必填",ResponseCodeType.LOGISTICS_PARAMETER_ERROR);
            }
            //查询站点信息
            Site site = siteManager.get(siteId);
            if(null == site){
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],更具站点编号[{}]查询站点信息为空", "RecordPlanManager", "completeRecordLineParcel", siteId);
                }
                throw new BusinessException("更具站点编号查询站点信息为空",ResponseCodeType.LOGISTICS_SITE_NOT_EXISTENCE);
            }
            //查询计划信息
            Plan plan = planManager.get(planId);
            if(null == plan){
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],更具计划编号[{}]查询站点信息为空", "RecordPlanManager", "completeRecordLineParcel",planId);
                }
                throw new BusinessException("更具计划编号查询计划信息为空",ResponseCodeType.LOGISTICS_PLAN_NOT_EXISTENCE);
            }
            //查询线路信息
            Line line = lineManager.get(lineId);
            if(null == line){
                if (log.isErrorEnabled()) {
                    log.error("服务名:[{}],方法:[{}],更具线路编号[{}]查询站点信息为空", "RecordPlanManager", "completeRecordLineParcel",lineId);
                }
                throw new BusinessException("更具线路编号查询线路信息为空",ResponseCodeType.LOGISTICS_LINE_NOT_EXISTENCE);
            }

            //查询员工信息
            Staff staff = staffManager.get(staffId);
            if(null == staff){
                staff = new Staff();
            }
            //遍历
            for(String recordLineParcelId:recordLineParcelIds){
                //1.2完成袋入库
                String grade = doRecordLineParcel(site,plan,line,staff,recordLineParcelId,date);
                //判断是否存在特殊袋
                if(GradeType.SPECIAL.getCode().equals(grade)){
                    grades= grade;
                }
            }
            return grades;
        }
        //2.袋编号不存在，则属于完成计划
        recordPlanLineManager.completeRecordLine(planId, lineId, date);
        return null;
    }

    @Override
    public List<RecordLineParcel> listRecordLineParcel(@NotNull RecordLineParcelSearch search) {
        return getDao().listRecordLineParcel(search);
    }

    @Override
    public void handle(RecordLineParcel recordLineParcel) {
        if(recordLineParcel == null||StringUtils.isEmpty(recordLineParcel.getId())){
            if (log.isErrorEnabled()) {
                log.error("服务名:[{}],方法:[{}],出入库袋记录信息为空！", "RecordPlanManager", "completeRecordLineParcel");
            }
            throw new BusinessException("出入库袋记录信息为空！",ResponseCodeType.LOGISTICS_RECORD_NOT_EXISTENCE);
        }

        this.update(recordLineParcel);

        //袋记录表id和告警表id相同
        RecordParcelAlarm recordParcelAlarm= recordParcelAlarmManager.get(recordLineParcel.getId());
        if(recordParcelAlarm == null){
            return;
        }
        recordParcelAlarm.setHandleTime(new Date());
        recordParcelAlarm.setHandleStatus(HandleStatus.INVALID.getCode());
        recordParcelAlarm.setHandleRemark(recordLineParcel.getDescription());
        recordParcelAlarmManager.update(recordParcelAlarm);
    }

    @Override
    public void doCheckStatus(@NotNull String id) {
        getDao().doCheckStatus(id,CheckStatus.APPROVED.getCode());
    }

    @Override
    public void updateStatusToSuccess(String date, String planId, String planLineId, String parcelId, LineParcelStatus lineParcelStatus) {
        getDao().updateStatusToSuccess(date, planId, planLineId, parcelId, lineParcelStatus.name());
    }

    /**1.2 袋出入库记录**/
    private String doRecordLineParcel(Site site, Plan plan, Line line, Staff staff,String recordLineParcelId,String date){
        //查询袋信息
        Parcel parcel = parcelManager.get(recordLineParcelId);
        if(null == parcel){
            if (log.isErrorEnabled()) {
                log.error("服务名:[{}],方法:[{}],更具袋编号查询袋信息为空:[{}]", "RecordPlanManager", "completeRecordLineParcel", recordLineParcelId);
            }
            throw new BusinessException("更具袋编号查询袋信息为空",ResponseCodeType.LOGISTICS_PARCEL_NOT_EXISTENCE);
        }
        String grade= parcel.getGrade();
        /**1.2.1 更具计划袋编号查询原计划出入库记录是否存在**/
        List<RecordLineParcel> recordLineParcels = getDao().query(recordLineParcelId,date);
        RecordLineParcel recordLineParcel = new RecordLineParcel();
        if(CollectionUtils.isEmpty(recordLineParcels)){
            //查询告警信息是否已经存在
            judge(date,plan,line,recordLineParcelId);
            //新增一条记录和差错告警记录
            RecordLineParcel dto = createRecordLineParcel(recordLineParcel,site, plan, line, staff, parcel,LineParcelStatus.ERROR.getCode());
            //创建出入库记录告警信息
            recordParcelAlarmManager.creatRecordParcelAlarm(dto,parcel,LineParcelStatus.ERROR.getCode());
            return grade;
        }
        //遍历原计划记录袋信息，
        for(RecordLineParcel obj:recordLineParcels){
            //判断是否有符合原计划
            if(obj.getPlanLineId().equals(line.getId()) && obj.getPlanId().equals(plan.getId())){
                if(LineParcelStatus.SUCCESS.getCode().equals(obj.getStatus())){
                    if (log.isInfoEnabled()) {
                        log.info("服务名:[{}],方法:[{}],当前袋子已经入库:[{}]", "RecordPlanManager", "completeRecordLineParcel", recordLineParcelId);
                    }
                    throw new BusinessException("当前袋子已经入库!",ResponseCodeType.LOGISTICS_ALREADY_WAREHOUSING);
                }
                //告警处理
                if(LineParcelStatus.LOST.getCode().equals(obj.getStatus())){
                    recordParcelAlarmHandle(obj.getId());
                }

                newRecordLineParcel(obj,plan, line, staff, parcel);
                obj.setRecordTime(new Date());
                obj.setStatus(LineParcelStatus.SUCCESS.getCode());
                this.update(obj);
                return grade;
            }else if(!obj.getPlanLineId().equals(line.getId()) && obj.getPlanId().equals(plan.getId())){
                recordLineParcel = obj;
            }
        }
        /**1.2.2 不在计划线路记录里，则新增一条记录，状态为错误，差错告警表也添加一条信息*/
        if(recordLineParcel != null){
            //查询告警信息是否已经存在
            judge(date,plan,line,recordLineParcelId);
            //新增一条记录和差错告警记录
            RecordLineParcel dto = createRecordLineParcel(recordLineParcel,site, plan, line, staff, parcel,LineParcelStatus.ERROR.getCode());
            //创建出入库记录告警信息
            recordParcelAlarmManager.creatRecordParcelAlarm(dto,parcel,LineParcelStatus.ERROR.getCode());
            /***1.2.2.1 结束**/
            return grade;
        }
        /***1.2.3 不在计划记录里**/
        RecordLineParcel lineParcel = new RecordLineParcel();
        //查询告警信息是否已经存在
        judge(date,plan,line,recordLineParcelId);
        //新增一条记录和差错告警记录
        RecordLineParcel dto = createRecordLineParcel(lineParcel,site, plan, line, staff, parcel,LineParcelStatus.ERROR.getCode());
        //创建出入库记录告警信息
        recordParcelAlarmManager.creatRecordParcelAlarm(dto,parcel,LineParcelStatus.ERROR.getCode());
        /***1.2.2.1 结束**/
        return grade;


    }

    //告警处理
    private void recordParcelAlarmHandle(String recordId){
        RecordParcelAlarm recordParcelAlarm = recordParcelAlarmManager.queryWithRecordId(recordId);
        if(recordParcelAlarm ==null){
            if (log.isErrorEnabled()) {
                log.error("服务名:[{}],方法:[{}],当前袋子未入告警信息:[{}]", "RecordPlanManager", "completeRecordLineParcel", recordId);
            }
            return;
        }
        recordParcelAlarm.setHandleStatus(HandleStatus.INVALID.getCode());
        recordParcelAlarm.setHandleTime(new Date());

        recordParcelAlarmManager.update(recordParcelAlarm);
    }

    //查询告警信息是否已经存在
    private void judge(String date,Plan plan,Line line,String recordLineParcelId){
        //
        RecordParcelAlarm recordParcelAlarm = recordParcelAlarmManager.queryRecordParcelAlarm(date,plan.getId(),line.getId(),recordLineParcelId);
        if(recordParcelAlarm !=null){
            if (log.isErrorEnabled()) {
                log.error("服务名:[{}],方法:[{}],当前袋子已经入库:[{}]", "RecordPlanManager", "completeRecordLineParcel", recordLineParcelId);
            }
            throw new BusinessException("当前袋子已经入库!",ResponseCodeType.LOGISTICS_ALREADY_WAREHOUSING);
        }
    }

    //创建出入库记录袋
    private RecordLineParcel createRecordLineParcel(RecordLineParcel recordLineParcel,Site site, Plan plan, Line line, Staff staff,Parcel parcel,String status){
        RecordLineParcel dto = new RecordLineParcel();
        String code= sequenceManager.newTransactionNext(SequenceKey.PLAN_LINE_PARCEL.name());
        String date= DateUtils.format(new Date(), DateUtils.DATE_FORMAT_yyyyMMdd);
        dto.setId(date+code);
        dto.setDate(date);
        dto.setPlanLineParcelId(code);
        dto.setSiteId(site.getId());
        dto.setSiteName(site.getName());
        dto.setPlanId(plan.getId());
        dto.setPlanName(plan.getName());
        dto.setPlanLineId(line.getId());
        dto.setPlanLineName(line.getName());
        dto.setRecordPlanId(recordLineParcel.getPlanId());
        dto.setRecordPlanName(recordLineParcel.getPlanName());
        dto.setRecordLineId(recordLineParcel.getPlanLineId());
        dto.setRecordLineName(recordLineParcel.getPlanLineName());
        dto.setRecordParcelId(parcel.getId());
        dto.setRecordParcelName(parcel.getName());
        dto.setRecordTime(new Date());
        dto.setCheckStatus(CheckStatus.NONE.getCode());
        dto.setStatus(status);
        dto.setCreatedTime(new Date());
        dto.setParcelLevel(parcel.getGrade());
        dto.setRecordStaffId(staff.getId());
        dto.setRecordStaffName(staff.getName());
        this.create(dto);
        return dto;
    }

    //公共部分
    private RecordLineParcel newRecordLineParcel(RecordLineParcel recordLineParcel,Plan plan, Line line, Staff staff,Parcel parcel){
        recordLineParcel.setRecordPlanId(plan.getId());
        recordLineParcel.setRecordPlanName(plan.getName());
        recordLineParcel.setRecordLineId(line.getId());
        recordLineParcel.setRecordLineName(line.getName());
        recordLineParcel.setRecordParcelId(parcel.getId());
        recordLineParcel.setRecordParcelName(parcel.getName());
        recordLineParcel.setParcelLevel(parcel.getGrade());
        recordLineParcel.setRecordStaffId(staff.getId());
        recordLineParcel.setRecordStaffName(staff.getName());
        return recordLineParcel;
    }


}