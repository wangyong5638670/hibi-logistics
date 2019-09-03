/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: zhoumingxing 2019-03-07
 */
package com.ylink.hibiscus.logistics.service.handler;

import com.ylink.hibiscus.common.base.exception.ApplicationException;
import com.ylink.hibiscus.common.base.exception.BusinessException;
import com.ylink.hibiscus.common.base.utils.DateUtils;
import com.ylink.hibiscus.logistics.service.record.RecordLineGroupManager;
import com.ylink.hibiscus.logistics.service.record.RecordLineParcelManager;
import com.ylink.hibiscus.logistics.service.record.RecordPlanLineManager;
import com.ylink.hibiscus.logistics.service.record.RecordPlanManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * 处理初始化出入库记录
 * @author lilinjun
 * @date 2019-03-07
 */
@Slf4j
@Service("channelRegistHandlerManager")
public class RecordHandlerManagerImpl implements RecordHandlerManager {


    @Autowired
    private RecordPlanManager recordPlanManager;

    @Autowired
    private RecordLineParcelManager recordLineParcelManager;

    @Autowired
    private RecordPlanLineManager recordPlanLineManager;
    @Autowired
    private RecordLineGroupManager recordLineGroupManager;


    @Override
    public void process(List<String> processData, String date) {
        //出入库记录袋组录入
        try{
            if(CollectionUtils.isEmpty(processData)){
                if(log.isErrorEnabled()){
                    log.error("处理初始化出入库记录时,参数[processData]为空!");
                }
                throw new BusinessException("处理初始化出入库记录时,参数[processData]为空.");
            }

            if(log.isInfoEnabled()){
                log.info("开始处理初始化出入库记录时间:[{}],数据共:[{}]条.",date,processData.size());
            }


            //出入库计划记录袋组
            recordLineGroupManager.newRecordLineGroups(date);
            //出入库计划记录录入
            recordPlanManager.newRecordPlans(processData,date);
            //出入库计划袋记录录入
            recordLineParcelManager.newRecordLineParcels(processData, date);
            //出入库计划线路记录录入
            recordPlanLineManager.newRecordPlanLines(processData, date);

        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error("处理初始化出入库记录时发生错误,错误信息:[{}]",e.getMessage());
                e.printStackTrace();
            }
            throw new BusinessException(MessageFormat.format("处理初始化出入库记录时发生错误,错误信息:[{1}]",e.getMessage()));
        }

    }

    @Override
    public void processFinish(List<String> processData, String date) {
        try{
            if(CollectionUtils.isEmpty(processData)){
                if(log.isErrorEnabled()){
                    log.error("处理完成计划时,参数[processData]为空!");
                }
                return;
            }
            if(log.isInfoEnabled()){
                log.info("开始处理完成计划时间:[{}],数据共:[{}]条.",date,processData.size());
            }
            for(String planId:processData){
                //完成计划
                recordPlanLineManager.completeRecordLine(planId, null, date);
            }


        }catch (Exception e){
            if(log.isErrorEnabled()){
                log.error("处理初始化出入库记录时发生错误,错误信息:[{}]",e.getMessage());
                e.printStackTrace();
            }
            throw new BusinessException(MessageFormat.format("处理初始化出入库记录时发生错误,错误信息:[{1}]",e.getMessage()));
        }
    }


}
