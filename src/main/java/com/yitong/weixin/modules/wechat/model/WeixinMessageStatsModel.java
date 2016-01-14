package com.yitong.weixin.modules.wechat.model;

import com.yitong.weixin.common.utils.DateUtils;
import com.yitong.weixin.modules.wechat.service.WeixinMessageStatsService.RptFieldEnum;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class WeixinMessageStatsModel {
	/**
	 * 默认的消息分析
	 * @return
	 */
	public static WeixinMessageStatsModel getDefaultMessageStatsModel() {
		Date date = DateUtils.getDateByDay(null);
		WeixinMessageStatsModel rs = new WeixinMessageStatsModel(RptFieldEnum.DAY_OF_MONTH, DateUtils.addDays(date, -7), date);
		return rs;
	}

	/**
	 * 昨日消息分析
	 * @return
	 */
	public static WeixinMessageStatsModel getYesterdayMessageStatsModel() {
		Date date = DateUtils.getDateByDay(null);
		WeixinMessageStatsModel rs = new WeixinMessageStatsModel(RptFieldEnum.DAY_OF_MONTH, DateUtils.addDays(date, -1), date);
		return rs;
	}

	private static final List<RptFieldEnum> RPT_TYPE_LIST = Collections.unmodifiableList(
			Arrays.asList(RptFieldEnum.HOUR_OF_DAY, RptFieldEnum.DAY_OF_MONTH, RptFieldEnum.WEEK_OF_YEAR, RptFieldEnum.MONTH));

	private RptFieldEnum type = RptFieldEnum.DAY_OF_MONTH;

	private Date startDate;

	private Date endDate;

	private Integer dateType = -7;

	public WeixinMessageStatsModel() {
		super();
	}

	public WeixinMessageStatsModel(RptFieldEnum type) {
		super();
		this.type = type;
	}

	public WeixinMessageStatsModel(RptFieldEnum type, Date startDate, Date endDate) {
		super();
		this.type = type;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public RptFieldEnum getType() {
		return type;
	}

	public void setType(RptFieldEnum type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<RptFieldEnum> getMsgRptList() {
		return RPT_TYPE_LIST;
	}

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}
}
