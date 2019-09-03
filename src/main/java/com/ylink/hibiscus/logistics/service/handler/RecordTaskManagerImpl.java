/*
  版权所有(C) 2019 深圳市雁联计算系统有限公司
  创建: YangHan 2019-05-15
 */
package com.ylink.hibiscus.logistics.service.handler;

import com.ylink.hibiscus.common.base.exception.BusinessException;
import com.ylink.hibiscus.common.base.resource.base.ModelResource;
import com.ylink.hibiscus.common.base.resource.communication.RestfulResponse;
import com.ylink.hibiscus.common.base.utils.DateUtils;
import com.ylink.hibiscus.entity.settings.base.workcalendar.WorkCalendar;
import com.ylink.hibiscus.logistics.service.plan.PlanManager;
import com.ylink.hibiscus.logistics.service.record.RecordPlanManager;
import com.ylink.hibiscus.remote.client.setting.base.workcalendar.WorkCalendarAppServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lilinjun
 * @date 2019-08-01
 */
@Slf4j
@Service("recordTaskManager")
public class RecordTaskManagerImpl implements RecordTaskManager {

    @Autowired
    private RecordHandlerManager recordHandlerManager;

    @Autowired
    private PlanManager planManager;
    @Autowired
    private RecordPlanManager recordPlanManager;

    @Autowired
    private WorkCalendarAppServiceClient workCalendarAppServiceClient;

    @Override
    public void handle(String date) {
        try{
            //当前日期
            //
            if(StringUtils.isEmpty(date)) {

                date = DateUtils.format(new Date(),DateUtils.DATE_FORMAT_yyyyMMdd);
            }
            long begin = System.currentTimeMillis();
            if(log.isDebugEnabled()){
                log.debug("出入库记录任务开始...");
            }
            //加一天处理
            Date dateAdd= DateUtils.addDay(DateUtils.parse(date,DateUtils.DATE_FORMAT_yyyyMMdd),1);

            /**查询出计划信息*/
            //将字符串格式yyyyMMdd的字符串转为日期，格式"yyyy-MM-dd"
            String  dateStr = DateUtils.format(dateAdd,DateUtils.DATE_FORMAT_yyyy_MM_dd);

            //判断目标日期是否为工作日
            RestfulResponse<ModelResource<Boolean>> response= workCalendarAppServiceClient.isHoliday(WorkCalendar.DEFAULT_WORK_CALENDAR,dateStr);
            Boolean exist = response.getResouces().getEntity();
            if (exist) {
                if(log.isErrorEnabled()){
                    log.error("目标日期不是工作日，不生成出入库记录:",dateStr);
                }
                return ;
            }
            List<String> planIds = planManager.planIds(dateStr);
            /**出入库计划记录录入**/
            date = DateUtils.format(dateAdd,DateUtils.DATE_FORMAT_yyyyMMdd);
            recordHandlerManager.process(planIds,date);


            long end = System.currentTimeMillis();
            if(log.isDebugEnabled()){
                log.debug("出入库记录任任务完成,耗时:{}秒.", ((end - begin) / 1000));
            }
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error("出入库记录处理时发生错误,错误信息:",e);
            }
            throw new BusinessException("出入库记录处理时,发生错误,具体查看日志.");
        }
    }

    @Override
    public void handleFinish(String date) {
        try{
            if(StringUtils.isEmpty(date)) {
                date = DateUtils.format(new Date(),DateUtils.DATE_FORMAT_yyyyMMdd);
            }
            long begin = System.currentTimeMillis();
            if(log.isDebugEnabled()){
                log.debug("完成计划任务开始...");
            }

            /**查询出计划信息*/
            List<String> planIds = recordPlanManager.planIds(date);
            /**完成计划**/
            recordHandlerManager.processFinish(planIds,date);


            long end = System.currentTimeMillis();
            if(log.isDebugEnabled()){
                log.debug("完成计划任务完成,耗时:{}秒.", ((end - begin) / 1000));
            }
        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error("完成计划任务处理时发生错误,错误信息:",e);
            }
            throw new BusinessException("完成计划任务处理时,发生错误,具体查看日志.");
        }
    }


}
