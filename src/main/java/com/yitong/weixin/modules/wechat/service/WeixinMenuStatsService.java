package com.yitong.weixin.modules.wechat.service;

import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.common.utils.DateUtils;
import com.yitong.weixin.modules.wechat.dao.WeixinMenuDao;
import com.yitong.weixin.modules.wechat.entity.WeixinMenu;
import com.yitong.weixin.modules.wechat.model.WeixinMenuStatsModel;
import com.yitong.weixin.modules.wechat.utils.AcctUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jz on 2016/1/15 14:30
 **/
@Service
@Transactional(readOnly = true)
public class WeixinMenuStatsService extends CrudService<WeixinMenuDao, WeixinMenu> {

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getChickConutYesterday(WeixinMenuStatsModel model) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 转化成Long型查询
        Date beginTime = model.getBeginDate() == null ? new Date() : model.getBeginDate();
        Date endTime = model.getEndDate() == null ? new Date() : model.getEndDate();
        //扩大有边界包含当前天
        endTime = DateUtils.addDays(endTime, 1);
        long beginDate = beginTime.getTime() / 1000, endDate = endTime.getTime() / 1000;
        params.put("beginDate",beginDate);
        params.put("endDate",endDate);
        params.put("acctOpenId", AcctUtils.getOpenId());
        List<Map<String, Object>> list = dao.menuChickStatsYesterday(params);
        return list;
    }
}
