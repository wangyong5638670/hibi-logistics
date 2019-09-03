/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: He Bingxing 2019-07-30
 */
package com.ylink.hibiscus.logistics.service.plan;

import com.ylink.hibiscus.common.base.enums.ScanType;
import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.entity.logistics.plan.PlanLineParcel;
import com.ylink.hibiscus.logistics.dao.plan.PlanLineParcelMapper;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 出入库计划线路袋manager
 *
 * @Author He Bingxing
 * @Date 2019-07-30
 */
@Service
public class PlanLineParcelManagerImpl extends BaseMybatisBaManagerImpl<PlanLineParcel, PlanLineParcelMapper> implements PlanLineParcelManager {

    @Override
    public List<PlanLineParcel> listWithPlanLineId(@NotBlank String planLineId) {
        return getDao().listWithPlanLineId(planLineId);
    }

    @Override
    public ScanType scan(String id) {
        //todo..........
        //员工01+6位数字
        String staffRegex = "^(01)\\d{6}$";
        Pattern staffPattern = Pattern.compile(staffRegex);
        Matcher staffMacher = staffPattern.matcher(id);
        while (staffMacher.find()){
            return ScanType.STAFF;
        }
        //时段线路   时段编码	00+1位数字+0	0010/0020/0030/0040 线路编码	11+8位数字	1187654321
        String lineRegex = "^(00[1|2|3|4]011)\\d{8}$";
        Pattern linePattern = Pattern.compile(lineRegex);
        Matcher lineMacher = linePattern.matcher(id);
        while (lineMacher.find()){
            return ScanType.LINE;
        }
        //完成线路   0000
        while ("0000".equals(id)){
            return ScanType.FINISH;
        }
        //袋 8位数字或10位数字或12位数字，以非0数字开头
        String parcelRegex = "^([1-9])\\d{7}|\\d{9}|\\d{11}$";
        Pattern parcelPattern = Pattern.compile(parcelRegex);
        Matcher parcelMacher = parcelPattern.matcher(id);
        while (parcelMacher.find()){
            return ScanType.PARCEL;
        }


        return null;
    }

    @Override
    public List<PlanLineParcel> listGroupsWithPlanLineId(@NotBlank String planLineId) {
        return getDao().listGroupsWithPlanLineId(planLineId);
    }

    @Override
    public void deleteWithPlanLineId(@NotBlank String planLineId) {
        getDao().deleteWithPlanLineId(planLineId);
    }
}
