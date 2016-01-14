package com.yitong.weixin.modules.wechat.web;

import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.common.web.BaseController;
import com.yitong.weixin.modules.wechat.model.WeixinUserStatsModel;
import com.yitong.weixin.modules.wechat.service.WeixinUserStatsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jz on 2016/1/11 16:26
 **/
@Controller
@RequestMapping(value="${adminPath}/userStats")
public class WeixinUserStatsController extends BaseController {

    @Resource
    private WeixinUserStatsService userStatsService;

    @RequestMapping(value="/userAnalyse")
    public String userStats(WeixinUserStatsModel wusModel, Model model){
        //昨日指数
        WeixinUserStatsModel yesterday = WeixinUserStatsModel.getYesterdayWusModel();
        List<Map<String, Object>> yesterdayData = userStatsService.selectByDay(yesterday);
        model.addAttribute("yesterdayData", yesterdayData.get(0));

        //详细数据
        List<Map<String, Object>> list = userStatsService.selectByDay(wusModel);

		/* 绘图数据 */
        StringBuilder labelSb = new StringBuilder();
        StringBuilder userAddNumSb = new StringBuilder();
        StringBuilder userCancelNumSb = new StringBuilder();
        StringBuilder userLeaveNumSb = new StringBuilder();
        StringBuilder userAllCountSb = new StringBuilder();
        for(Iterator<Map<String, Object>> i = list.iterator(); i.hasNext();) {
            Map<String, Object> m = i.next();
            labelSb.append('\'').append(StringUtils.toString(m.get("DATEINFO"), "")).append('\'');
            userAddNumSb.append(StringUtils.toString(m.get("DAYADDNUM"), "0"));
            userCancelNumSb.append(StringUtils.toString(m.get("DAYCANCELNUM"), "0"));
            userLeaveNumSb.append(StringUtils.toString(m.get("DAYLEAVENUM"), "0"));
            userAllCountSb.append(StringUtils.toString(m.get("ALLNUM"), "0"));
            if(i.hasNext()) {
                labelSb.append(',');
                userAddNumSb.append(',');
                userCancelNumSb.append(',');
                userLeaveNumSb.append(',');
                userAllCountSb.append(',');
            }
        }
		/* 添加属性  */
        model.addAttribute("wusModel", wusModel);
        Collections.reverse(list); // 对list数组逆序处理
        model.addAttribute("list", list);
        model.addAttribute("imgLabels", labelSb);
        model.addAttribute("imgNewUserCounts", userAddNumSb);
        model.addAttribute("imgCancelUserCounts", userCancelNumSb);
        model.addAttribute("imgLeaveUserCounts", userLeaveNumSb);
        model.addAttribute("imgAllUserCounts", userAllCountSb);
        return "modules/wechat/userAnalyse";
    }

    @RequestMapping(value="/default")
    public String getUserStas(Model model){
        return userStats(WeixinUserStatsModel.getDefaultWusModel(),model);
    }
}
