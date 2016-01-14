package com.yitong.weixin.modules.wechat.model;

import com.yitong.weixin.common.utils.DateUtils;

import java.util.Date;

public class WeixinUserStatsModel {
	private Date beginDate;
	private Date endDate;
	private Integer statsWay = -7;

	public static WeixinUserStatsModel getDefaultWusModel() {
		Date date = DateUtils.getDateByDay(null);
		return new WeixinUserStatsModel(DateUtils.addDays(date, -7), date);
	}

	public static WeixinUserStatsModel getYesterdayWusModel() {
		Date date = DateUtils.getDateByDay(null);
		Date yesterday = DateUtils.addDays(date, -1);
		return new WeixinUserStatsModel(yesterday, date);
	}

	public WeixinUserStatsModel(Date beginDate, Date endDate) {
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public WeixinUserStatsModel() {
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getStatsWay() {
		return statsWay;
	}

	public void setStatsWay(Integer statsWay) {
		this.statsWay = statsWay;
	}

}
