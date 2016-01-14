package com.yitong.weixin.modules.wechat.web;

import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.common.web.BaseController;
import com.yitong.weixin.modules.wechat.model.WeixinMessageStatsModel;
import com.yitong.weixin.modules.wechat.service.WeixinMessageStatsService;
import com.yitong.weixin.modules.wechat.service.WeixinMessageStatsService.RptFieldEnum;
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
@RequestMapping("${adminPath}/messageStats")
public class WeixinMessageStatsController extends BaseController {
	
	@Resource
	private WeixinMessageStatsService weixinMessageStatsService;
	
	@RequestMapping("/default")
	public String index(Model model) {
		return genReportByCalType(WeixinMessageStatsModel.getDefaultMessageStatsModel(), model);
	}
	
	@RequestMapping("/messageAnalyse")
	public String genReportByCalType(WeixinMessageStatsModel rptModel, Model model) {
		// 昨日关键指标
		if(RptFieldEnum.DAY_OF_MONTH == rptModel.getType()) {
			WeixinMessageStatsModel yesRptModel = WeixinMessageStatsModel.getYesterdayMessageStatsModel();
			List<Map<String, Object>> yesterdayList = weixinMessageStatsService.genReportByCalType(yesRptModel.getType(),
					yesRptModel.getStartDate(), yesRptModel.getEndDate());
			model.addAttribute("yesterday", yesterdayList.get(0));
		}
		
		List<Map<String, Object>> list = weixinMessageStatsService.genReportByCalType(rptModel.getType(),
				rptModel.getStartDate(), rptModel.getEndDate());
		StringBuilder labelSb = new StringBuilder();
		StringBuilder msgCountSb = new StringBuilder();
		StringBuilder userCountSb = new StringBuilder();
		StringBuilder avgCountSb = new StringBuilder();
		for(Iterator<Map<String, Object>> i = list.iterator(); i.hasNext();) {
			Map<String, Object> m = i.next();
			labelSb.append('\'').append(StringUtils.toString(m.get("CDATE"), "")).append('\'');
			msgCountSb.append(StringUtils.toString(m.get("MSG_COUNT"), "0"));
			userCountSb.append(StringUtils.toString(m.get("USER_COUNT"), "0"));
			avgCountSb.append(StringUtils.toString(m.get("AVG_MSG_COUNT"), "0"));
			if(i.hasNext()) {
				labelSb.append(',');
				msgCountSb.append(',');
				userCountSb.append(',');
				avgCountSb.append(',');
			}
		}
		Collections.reverse(list);
		model.addAttribute("rptModel", rptModel);
		model.addAttribute("list", list);
		model.addAttribute("imgLabels", labelSb);
		model.addAttribute("imgMsgCounts", msgCountSb);
		model.addAttribute("imgUserCounts", userCountSb);
		model.addAttribute("imgAvgCounts", avgCountSb);
		return "modules/wechat/messageAnalyse";
	}

}
