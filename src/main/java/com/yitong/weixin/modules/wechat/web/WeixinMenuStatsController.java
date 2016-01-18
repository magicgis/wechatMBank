package com.yitong.weixin.modules.wechat.web;

import com.yitong.weixin.common.web.BaseController;
import com.yitong.weixin.modules.wechat.model.WeixinMenuStatsModel;
import com.yitong.weixin.modules.wechat.service.WeixinMenuStatsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jz on 2016/1/15 14:22
 **/
@Controller
@RequestMapping(value="${adminPath}/menuStats")
public class WeixinMenuStatsController extends BaseController {

    @Resource
    private WeixinMenuStatsService menuStatsService;

    @RequestMapping(value="/default")
    public String getMenuStas(Model model){
        return menuAnalyse(WeixinMenuStatsModel.getDefaultMenuStatsModel(),model);
    }

    @RequestMapping(value="/menuAnalyse")
    public String menuAnalyse(WeixinMenuStatsModel msModel, Model model){
        //昨日指数
        WeixinMenuStatsModel yesterday = WeixinMenuStatsModel.getYesterdayMenuStatsModel();
        List<Map<String, Object>> yesterdayData = menuStatsService.getChickConutYesterday(yesterday);
        model.addAttribute("yesterdayData", yesterdayData.size()>0?yesterdayData.get(0):new HashMap<String,Object>());

        model.addAttribute("msModel", msModel);
        return "modules/wechat/menuAnalyse";
    }
}
