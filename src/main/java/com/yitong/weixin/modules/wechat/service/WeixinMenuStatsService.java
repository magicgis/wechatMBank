package com.yitong.weixin.modules.wechat.service;

import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.common.utils.DateUtils;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.modules.wechat.dao.WeixinMenuDao;
import com.yitong.weixin.modules.wechat.entity.WeixinMenu;
import com.yitong.weixin.modules.wechat.model.WeixinMenuStatsModel;
import com.yitong.weixin.modules.wechat.utils.AcctUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jz on 2016/1/15 14:30
 **/
@Service
@Transactional(readOnly = true)
public class WeixinMenuStatsService extends CrudService<WeixinMenuDao, WeixinMenu> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 日期格式化
    private final String DATE = "CDATE";// 日期
    private final String ID = "ID";
    private final String NAME = "NAME";
    private int inFrontNum = 2;//图表显示点击次数前inFrontNum的菜单

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getChickConutYesterday(WeixinMenuStatsModel model) {
        Map<String, Object> params = buildParmas(model);
        List<Map<String, Object>> list = dao.menuChickStatsYesterday(params);
        return list;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getChickConutByDay(WeixinMenuStatsModel model) {
        Map<String, Object> params = buildParmas(model);
        List<Map<String, Object>> list = dao.menuChickStatsByDay(params);
//        List<Map<String, Object>> formatList = formatList(model.getBeginDate(), model.getEndDate(),list);
        return list;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getChickConutStats(WeixinMenuStatsModel model) {
        Map<String, Object> params = buildParmas(model);
        List<Map<String, Object>> list = dao.menuChickStats(params);
        return list;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getContainerMap(WeixinMenuStatsModel model,List<Map<String, Object>> containerData,List<Map<String, Object>> detailedData) {
        List<Map<String, Object>> menuInFrontList = getMenuInFrontList(detailedData);
        List<Map<String, Object>> formatDateList = formatDateList(model.getBeginDate(), model.getEndDate());
        List<Map<String, Object>> chickCountList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> userCountList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> avgChickCountList = new ArrayList<Map<String, Object>>();
        Map<String, Object> containerMap = new HashMap<String, Object>();
        for (Map<String, Object> menu : menuInFrontList){
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("name","'"+menu.get(NAME)+"'");
            StringBuilder chickCount = new StringBuilder("[");
            StringBuilder userCount = new StringBuilder("[");
            StringBuilder avgChickCount = new StringBuilder("[");
            StringBuilder labelSb = new StringBuilder();
            for(Iterator<Map<String, Object>> i = formatDateList.iterator(); i.hasNext();){
                Map<String, Object> date = i.next();
                labelSb.append('\'').append(StringUtils.toString(date.get(DATE), "")).append('\'');
                boolean flag = true;
                for (Map<String, Object> data : containerData){

                    if (menu.get(ID).equals(data.get(ID)) && date.get(DATE).equals(data.get(DATE))){
                        flag = false;
                        chickCount.append(StringUtils.toString(data.get("CHICK_COUNT"), "0"));
                        userCount.append(StringUtils.toString(data.get("USER_COUNT"), "0"));
                        avgChickCount.append(StringUtils.toString(data.get("AVG_CHICK_COUNT"), "0"));
                    }
                }
                if (flag){
                    chickCount.append("0");
                    userCount.append("0");
                    avgChickCount.append("0");
                }
                if(i.hasNext()) {
                    labelSb.append(',');
                    chickCount.append(',');
                    userCount.append(',');
                    avgChickCount.append(',');
                }else {
                    chickCount.append(']');
                    userCount.append(']');
                    avgChickCount.append(']');
                }
            }
            containerMap.put("labelSb",labelSb);
            params.put("data",chickCount);
            chickCountList.add(new HashMap<String, Object>(params));
            params.put("data",userCount);
            userCountList.add(new HashMap<String, Object>(params));
            params.put("data",avgChickCount);
            avgChickCountList.add(new HashMap<String, Object>(params));
        }
        containerMap.put("chickCountList", chickCountList.toString().replace("=",":"));
        containerMap.put("userCountList",userCountList.toString().replace("=",":"));
        containerMap.put("avgChickCountList",avgChickCountList.toString().replace("=",":"));
        return containerMap;
    }

    private Map<String, Object> buildParmas (WeixinMenuStatsModel model){
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
        return params;
    }

    private List<Map<String, Object>> getMenuInFrontList(List<Map<String, Object>> list){
        List<Map<String, Object>> menuInFront = new ArrayList<Map<String, Object>>(list);
        if (menuInFront==null || menuInFront.size()<=inFrontNum){
            return menuInFront;
        }
        // 整合重复数据
        for (int i = 0; menuInFront.size()>inFrontNum; i++) {
            Map<String, Object> m = list.get(i);
            int count = 0;
            for (int j = i + 1; j < list.size(); j++) {
                if (((BigDecimal)m.get("CHICK_COUNT")).compareTo((BigDecimal)list.get(j).get("CHICK_COUNT"))<=0){
                    count++;
                }
            }
            if (count>=inFrontNum){
                menuInFront.remove(i);
            }
        }
        return menuInFront;
    }

    private List<Map<String, Object>> formatDateList(Date startDate, Date endDate){
        List<Map<String, Object>> formatDate = new ArrayList<Map<String, Object>>();
        // 获得开始和结束时间范围
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        // 格式化时间显示和补充缺少的时间
        Calendar indexCal = Calendar.getInstance();
        indexCal.setTime(startCal.getTime());
        while (indexCal.getTime().before(DateUtils.addDays(endDate, 1))) {
            Map<String, Object> e = new HashMap<String, Object>();
            e.put(DATE, sdf.format(indexCal.getTime()));
            formatDate.add(e);
            indexCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return formatDate;
    }

    private List<Map<String, Object>> formatList(Date startDate, Date endDate,List<Map<String, Object>> list) {
        if (null == list) {
            return null;
        }

        list = new LinkedList<Map<String, Object>>(list);
        ListIterator<Map<String, Object>> listIte = list.listIterator();

        // 获得开始和结束时间范围
        Calendar startCal = Calendar.getInstance();
        if (null != startDate) {
            startCal.setTime(startDate);
            if (null == endDate) {
                endDate = new Date();
            }
        } else {
            endDate = getCreateDate(list.get(list.size() - 1));
            startCal.setTime(getCreateDate(list.get(0)));
        }

        // 格式化时间显示和补充缺少的时间
        Calendar indexCal = Calendar.getInstance();
        indexCal.setTime(startCal.getTime());
        boolean hasMore = listIte.hasNext();
        while (indexCal.getTime().before(endDate) || hasMore) {
            Map<String, Object> e = null;
            Date cdate = null;
            if (hasMore) {
                e = listIte.next();
                cdate = getCreateDate(e);
            }
            if (null == e || cdate.after(indexCal.getTime())) {
                if (hasMore) {
                    listIte.previous();
                }
                e = new HashMap<String, Object>();
                cdate = indexCal.getTime();
                listIte.add(e);
            }

            e.put(DATE, sdf.format(cdate));
            System.out.println(indexCal.getTime());
            indexCal.add(Calendar.DAY_OF_MONTH, 1);
            if (!listIte.hasNext()) {
                hasMore = false;
            }
        }

        return list;
    }

    /**
     * 获取结果集的创建时间
     *
     * @param map
     * @return
     */
    private Date getCreateDate(Map<String, Object> map) {
        if (null == map) {
            return null;
        }
        Object cdateStr = map.get(DATE);
        if (null == cdateStr) {
            return null;
        }
        try {
            return sdf.parse(cdateStr.toString());
        } catch (ParseException e) {
            return null;
        }
    }
}
