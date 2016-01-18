package com.yitong.weixin.modules.wechat.model;

import com.yitong.weixin.common.utils.DateUtils;

import java.util.Date;

/**
 * Created by jz on 2016/1/15 14:36
 **/
public class WeixinMenuStatsModel {

    private Date beginDate;

    private Date endDate;

    private Integer statsWay = -7;

    public WeixinMenuStatsModel() {}

    public WeixinMenuStatsModel(Date beginDate, Date endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public static WeixinMenuStatsModel getDefaultMenuStatsModel() {
        Date date = DateUtils.getDateByDay(null);
        return new WeixinMenuStatsModel(DateUtils.addDays(date, -7), DateUtils.addDays(date, -1));
    }

    public static WeixinMenuStatsModel getYesterdayMenuStatsModel() {
        Date date = DateUtils.getDateByDay(null);
        return new WeixinMenuStatsModel(DateUtils.addDays(date, -1), DateUtils.addDays(date, -1));
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
