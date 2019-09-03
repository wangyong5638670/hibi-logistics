/**
 * 版权所有(C) 2019 深圳市雁联计算系统有限公司
 * 创建: luojinxu 2019-03-28
 */
package com.ylink.hibiscus.logistics.service.business;

import com.ylink.hibiscus.common.base.enums.Status;
import com.ylink.hibiscus.common.base.service.BaseMybatisBaManagerImpl;
import com.ylink.hibiscus.entity.logistics.business.Line;
import com.ylink.hibiscus.logistics.dao.business.LineMapper;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author lilinjun
 * @date 2019-03-28
 */
@Service("lineManager")
public class LineManagerImpl extends BaseMybatisBaManagerImpl<Line, LineMapper> implements LineManager{

    @Override
    public List<Line> listWithSiteIdStats(@NotBlank String siteId, Status status) {
        return getDao().listWithSiteIdStats(siteId, status);
    }
}
