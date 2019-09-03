/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.record;

import com.ylink.hibiscus.common.base.enums.HandleStatus;
import com.ylink.hibiscus.common.base.enums.LineParcelStatus;
import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.common.base.utils.DateUtils;
import com.ylink.hibiscus.entity.logistics.business.Parcel;
import com.ylink.hibiscus.entity.logistics.record.RecordLineParcel;
import com.ylink.hibiscus.entity.logistics.record.RecordParcelAlarm;
import com.ylink.hibiscus.logistics.dao.record.RecordParcelAlarmMapper;
import com.ylink.hibiscus.logistics.service.base.SequenceManager;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

/**
 * @author luojinxu
 * @date 2019-07-30
 */
@Service("recordParcelAlarmManager")
public class RecordParcelAlarmManagerImpl extends BaseMybatisBaManagerImpl<RecordParcelAlarm, RecordParcelAlarmMapper> implements RecordParcelAlarmManager {

    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private RecordLineParcelManager recordLineParcelManager;

    //创建出入库记录告警信息
    @Override
    public void creatRecordParcelAlarm(RecordLineParcel recordLineParcel,Parcel parcel,String status){
        RecordParcelAlarm dto = new RecordParcelAlarm();
        RecordParcelAlarm(dto,recordLineParcel);
        dto.setRecordParcelId(parcel.getId());
        dto.setRecordParcelName(parcel.getName());
        dto.setRecordStaffId(recordLineParcel.getRecordStaffId());
        dto.setRecordStaffName(recordLineParcel.getRecordStaffName());
        dto.setStatus(status);
        dto.setCreatedTime(new Date());
        this.create(dto);
    }

    @Override
    public void creatRecordParcelAlarm(RecordLineParcel recordLineParcel,String status) {
        RecordParcelAlarm dto = new RecordParcelAlarm();
        RecordParcelAlarm(dto,recordLineParcel);
        dto.setRecordParcelId(recordLineParcel.getRecordParcelId());
        dto.setRecordParcelName(recordLineParcel.getRecordParcelName());
        dto.setRecordStaffId(recordLineParcel.getRecordStaffId());
        dto.setRecordStaffName(recordLineParcel.getRecordStaffName());
        dto.setStatus(status);
        dto.setCreatedTime(new Date());
        this.create(dto);
    }

    private void RecordParcelAlarm(RecordParcelAlarm dto,RecordLineParcel recordLineParcel){
        dto.setDate(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_yyyyMMdd));
        dto.setRecordId(recordLineParcel.getId());
        dto.setId(recordLineParcel.getId());
        dto.setSiteId(recordLineParcel.getSiteId());
        dto.setSiteName(recordLineParcel.getSiteName());
        dto.setPlanId(recordLineParcel.getPlanId());
        dto.setPlanName(recordLineParcel.getPlanName());
        dto.setPlanLineId(recordLineParcel.getPlanLineId());
        dto.setPlanLineName(recordLineParcel.getPlanLineName());
        dto.setPlanParcelId(recordLineParcel.getPlanParcelId());
        dto.setPlanParcelName(recordLineParcel.getPlanParcelName());
        dto.setRecordPlanId(recordLineParcel.getRecordPlanId());
        dto.setRecordPlanName(recordLineParcel.getRecordPlanName());
        dto.setRecordLineId(recordLineParcel.getRecordLineId());
        dto.setRecordLineName(recordLineParcel.getRecordLineName());
        dto.setPlanStaffId(recordLineParcel.getPlanStaffId());
        dto.setPlanStaffName(recordLineParcel.getPlanStaffName());
        dto.setHandleStatus(HandleStatus.NEW.getCode());
    }

    @Override
    public void handle(RecordParcelAlarm recordParcelAlarm) {
        // 告警信息
        RecordParcelAlarm recordParcelAlarmDB = get(recordParcelAlarm.getId());
        if (Objects.nonNull(recordParcelAlarmDB)){
            // 修改出入库记录状态为成功
            RecordLineParcel recordLineParcel = new RecordLineParcel();
            recordLineParcel.setId(recordParcelAlarmDB.getRecordId());
            if (Objects.equals(EnumUtils.getEnum(HandleStatus.class,recordParcelAlarm.getHandleStatus()),HandleStatus.INVALID)){
                recordLineParcel.setStatus(LineParcelStatus.INVALID.name());
            } else {
                recordLineParcel.setStatus(LineParcelStatus.SUCCESS.name());
            }
            recordLineParcelManager.update(recordLineParcel);
        }

        // 修改告警
        recordParcelAlarm.setHandleTime(DateUtils.getCurrentDate());
        update(recordParcelAlarm);
    }

    @Override
    public RecordParcelAlarm queryWithRecordId(@NotBlank String recordId) {
        return getDao().queryWithRecordId(recordId);
    }

    @Override
    public RecordParcelAlarm queryRecordParcelAlarm(@NotBlank String date, @NotBlank String planId,@NotBlank  String planLineId, @NotBlank String recordParcelId) {
        return getDao().queryRecordParcelAlarm(date, planId, planLineId, recordParcelId);
    }
}