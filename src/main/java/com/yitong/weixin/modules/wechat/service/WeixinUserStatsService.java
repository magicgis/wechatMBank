package com.yitong.weixin.modules.wechat.service;

import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.common.utils.DateUtils;
import com.yitong.weixin.modules.wechat.dao.WeixinUserDao;
import com.yitong.weixin.modules.wechat.entity.WeixinUser;
import com.yitong.weixin.modules.wechat.model.WeixinUserStatsModel;
import com.yitong.weixin.modules.wechat.utils.AcctUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class WeixinUserStatsService extends CrudService<WeixinUserDao, WeixinUser> {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 日期格式化
	private final String DATE = "DATEINFO";// 日期
	private final String DAY_ADD_NUM = "DAYADDNUM";// 日增数目
	private final String DAY_CANCEL_NUM = "DAYCANCELNUM";// 日减数目
	private final String DAY_LEAVE_NUM = "DAYLEAVENUM";// 日净增数目
	private final String ALLNUM = "ALLNUM";// 累积总数

	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectByDay(WeixinUserStatsModel model) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 转化成Long型查询
		Date beginTime = model.getBeginDate() == null ? new Date() : model.getBeginDate();
		Date endTime = model.getEndDate() == null ? new Date() : model.getEndDate();
		//扩大有边界包含当前天
		endTime = DateUtils.addDays(endTime, 1);
		long beginDate = beginTime.getTime() / 1000, endDate = endTime.getTime() / 1000;
		params.put("beginDate",beginDate);
		params.put("endDate",endDate);
		params.put("acctOpenId",AcctUtils.getOpenId());
		List<Map<String, Object>> listMap = dao.findUserListByDay(params);
		Integer allNum = getBeginTimeAllNum(beginTime);
		List<Map<String, Object>> formatList = getDateMap(beginTime, endTime,
				formatLongToDate(listMap), allNum);
		return formatList;
	}

	/**
	 * 将时间戳转换日期并整合
	 *
	 * @param list
	 */
	private List<Map<String, Object>> formatLongToDate(
			List<Map<String, Object>> list) {
		if (null == list) {
			return null;
		}

		// 日期转换
		for (Map<String, Object> map : list) {
			BigDecimal cdateNum = (BigDecimal) map.get(DATE);
			Date cdate = new Date(cdateNum.longValue() * 1000);
			map.put(DATE, sdf.format(cdate));
		}
		List<Map<String, Object>> mapList = new LinkedList<Map<String, Object>>();
		// 整合重复数据
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> m = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				Map<String, Object> mm = list.get(j);
				if (mm.get(DATE) != null && mm.get(DATE).equals(m.get(DATE))) {
					BigDecimal dayAddNum = ((BigDecimal) (m.get(DAY_ADD_NUM) == null ? BigDecimal.ZERO : m.get(DAY_ADD_NUM))).add((BigDecimal) (mm
							.get(DAY_ADD_NUM) == null ? BigDecimal.ZERO : mm.get(DAY_ADD_NUM)));
					BigDecimal dayCancelNum = ((BigDecimal) (m.get(DAY_CANCEL_NUM) == null ? BigDecimal.ZERO : m.get(DAY_CANCEL_NUM)))
							.add((BigDecimal) (mm.get(DAY_CANCEL_NUM) == null ? BigDecimal.ZERO : mm.get(DAY_CANCEL_NUM)));
					BigDecimal dayLeaveNum = ((BigDecimal) (m.get(DAY_LEAVE_NUM) == null ? BigDecimal.ZERO : m.get(DAY_LEAVE_NUM))).add((BigDecimal) (mm
							.get(DAY_LEAVE_NUM) == null ? BigDecimal.ZERO : mm.get(DAY_LEAVE_NUM)));
					m.put(DAY_ADD_NUM, dayAddNum);
					m.put(DAY_CANCEL_NUM, dayCancelNum);
					m.put(DAY_LEAVE_NUM, dayLeaveNum);
					mm.remove(DATE);
				}
			}
		}
		// 清空多余重复的map
		for (Map<String, Object> ml : list) {
			if (ml.get(DATE) != null)
				mapList.add(ml);
		}
		return mapList;
	}

	/**
	 * 格式化时间，补充没有的时间
	 *
	 * @param startDate
	 * @param endDate
	 * @param startNum
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> getDateMap(Date startDate, Date endDate,
												 List<Map<String, Object>> list, Integer startNum) {
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
			indexCal.add(Calendar.DAY_OF_MONTH, 1);
			if (!listIte.hasNext()) {
				hasMore = false;
			}
		}

		return statsAllNum(startDate, startNum, list);
	}

	/**
	 * 获取起始累积总数
	 *
	 * @param beginDate
	 * @return
	 */
	private Integer getBeginTimeAllNum(Date beginDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginTime",beginDate.getTime() / 1000);// 数据库中时间戳精确到秒
		params.put("acctOpenId",AcctUtils.getOpenId());
		List<Map<String, Object>> map = dao.fingUserNumByTime(params);
		Integer allCount = 0;// 累计总数
		if (map != null) {
			allCount = Integer.parseInt(map.get(0).get(ALLNUM)+"");
		}
		return allCount;
	}

	/**
	 * 设置累积值
	 *
	 * @param beginDate
	 * @param startNum
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> statsAllNum(Date beginDate,
												  Integer startNum, List<Map<String, Object>> list) {
		// 设置起始值
		if (list == null) {
			return null;
		}

		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				list.get(i).put(ALLNUM, new BigDecimal(startNum));
			} else {
				BigDecimal lastAllNum = (BigDecimal) (list.get(i - 1).get(ALLNUM) == null ? BigDecimal.ZERO
						: list.get(i - 1).get(ALLNUM));
				BigDecimal leaveNum = (BigDecimal) (list.get(i)
						.get(DAY_LEAVE_NUM) == null ? BigDecimal.ZERO : (BigDecimal) list.get(
						i).get(DAY_LEAVE_NUM));
				list.get(i).put(ALLNUM, lastAllNum.add(leaveNum));
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
		}
		return null;
	}
}
