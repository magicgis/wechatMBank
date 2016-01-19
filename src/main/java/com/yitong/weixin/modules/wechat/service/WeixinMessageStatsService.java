package com.yitong.weixin.modules.wechat.service;

import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.common.utils.DateUtils;
import com.yitong.weixin.modules.wechat.dao.WeixinMessageDao;
import com.yitong.weixin.modules.wechat.entity.WeixinMessage;
import com.yitong.weixin.modules.wechat.utils.AcctUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 消息分析服务
 */
@Service
@Transactional(readOnly = true)
public class WeixinMessageStatsService extends CrudService<WeixinMessageDao, WeixinMessage> {

	/**
	 * 用于组成SQL的协助枚举类
	 * @author lc3@yitong.com.cn
	 *
	 */
	public static enum RptFieldEnum {
		YEAR(Calendar.YEAR, "", "YYYY", "yyyy", "yyyy'年'"),
		MONTH(Calendar.MONTH, "月报", "MM", "yyyy-MM", "yyyy'年 %s月'"),
		WEEK_OF_YEAR(Calendar.WEEK_OF_YEAR, "周报", "WW", "yyyy-ww", "yyyy'年 第'w'周'"),
		DAY_OF_MONTH(Calendar.DAY_OF_MONTH, "日报", "DD", "yyyy-MM-dd", "yyyy-MM-dd"),
		HOUR_OF_DAY(Calendar.HOUR_OF_DAY, "小时报", "HH24", "yyyy-MM-dd-HH", "yyyy-MM-dd H'点'");
		
		private static final String CREATE_DATE_FIELD_NAME = "CDATE";
		private String method;
		private int type;
		private SimpleDateFormat patten;
		private SimpleDateFormat formatPatten;
		private String label;
		private RptFieldEnum(int type, String label, String method, String patten, String formatPatten) {
			this.method = method;
			this.label = label;
			this.type = type;
			this.patten = new SimpleDateFormat(patten);
			this.formatPatten = new SimpleDateFormat(formatPatten);
		}
		/**
		 * 分析类型显示名称
		 * @return
		 */
		public String getLabel() {
			return this.label;
		}
		/**
		 * 分析类型名称
		 * @return
		 */
		public String getName() {
			return name();
		}
		/**
		 * 对应查询列的别名
		 * @return
		 */
		public String getFieldName() {
			return this.method;
		}
		/**
		 * 对应的查询表达式
		 * @return
		 */
		public String getFieldExp() {
			return this.method;
		}

		/**
		 * 格式化结果输出
		 * @param startDate 开始时间
		 * @param endDate 结束时间
		 * @param list 结果列表
		 * @return
		 */
		public List<Map<String, Object>> formatList(Date startDate, Date endDate, List<Map<String, Object>> list) {
			if(null == list) {
				return null;
			}
			
			list = new LinkedList<Map<String,Object>>(list);
			ListIterator<Map<String, Object>> listIte = list.listIterator();
			
			// 获得开始和结束时间范围
			Calendar startCal = Calendar.getInstance();
			if(null != startDate) {
				startCal.setTime(startDate);
				if(null == endDate) {
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
			while(indexCal.getTime().before(DateUtils.addDays(endDate, 1)) || hasMore) {
				Map<String, Object> e = null;
				Date cdate = null;
				if(hasMore) {
					e = listIte.next();
					cdate = getCreateDate(e);
				}
				if(null == e || cdate.after(indexCal.getTime())) {
					if(hasMore) {
						listIte.previous();
					}
					e = new HashMap<String, Object>();
					cdate = indexCal.getTime();
					listIte.add(e);
				}
				
				e.put(CREATE_DATE_FIELD_NAME, formatDate(cdate));
				indexCal.add(this.type, 1);
				if(!listIte.hasNext()) {
					hasMore = false;
				}
			}

			return list;
		}
		/**
		 * 获取结果集的创建时间
		 * @param map
		 * @return
		 */
		private Date getCreateDate(Map<String, Object> map) {
			if(null == map) {
				return null;
			}
			Object cdateStr = map.get(CREATE_DATE_FIELD_NAME);
			if(null == cdateStr) {
				return null;
			}
			try {
				return this.patten.parse(cdateStr.toString());
			} catch (ParseException e) {
			}
			return null;
		}
		/**
		 * 格式化时间
		 * @param date
		 * @return
		 */
		private String formatDate(Date date) {
			String rs = null == date ? "" : this.formatPatten.format(date);
			if(this == MONTH) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				rs = String.format(rs, 1 + cal.get(this.type));
			}
			return rs;
		}
	}
	/**
	 * 消息分析接口
	 * @param rptType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> genReportByCalType(RptFieldEnum rptType, Date startDate, Date endDate) {
		Assert.notNull(rptType, "统计类型传值不正确");
		Set<RptFieldEnum> fieldSet = EnumSet.noneOf(RptFieldEnum.class);
		fieldSet.add(RptFieldEnum.YEAR);
		switch(rptType) {
		case HOUR_OF_DAY:
			fieldSet.add(RptFieldEnum.HOUR_OF_DAY);
		case DAY_OF_MONTH:
			fieldSet.add(RptFieldEnum.DAY_OF_MONTH);
		case MONTH:
			fieldSet.add(RptFieldEnum.MONTH);
			break;
		case WEEK_OF_YEAR:
			fieldSet.add(RptFieldEnum.WEEK_OF_YEAR);
			break;
		default: 
			throw new IllegalArgumentException("统计类型不合法");
		}
		List<String> fieldExpList = new ArrayList<String>();
		for(RptFieldEnum e : fieldSet) {
			fieldExpList.add(e.getFieldExp());
		}
		Map<String, Object> params = new HashMap<String, Object>();
		if(null == endDate) {
			endDate = new Date();
		}
		params.put("startDate",startDate);
		params.put("endDate",DateUtils.addDays(endDate, 1));
		params.put("formatDate",StringUtils.collectionToDelimitedString(fieldExpList, "-"));
		params.put("acctOpenId", AcctUtils.getOpenId());
		List<Map<String, Object>> list = dao.findMessageStatsList(params);
		return rptType.formatList(startDate, endDate, list);
	}
	
}
