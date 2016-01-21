<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.yitong.weixin.modules.wechat.service.WeixinMessageStatsService.RptFieldEnum"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page import="com.yitong.weixin.common.utils.DateUtils" %>
<%
	String oneyearago=DateUtils.monthStr(12);
%>
<html>
<head>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/highcharts/highcharts.js" type="text/javascript"></script>
<%--<script src="${ctxStatic}/highcharts/js/modules/exporting.js" type="text/javascript"></script>--%>
<title>消息分析</title>
<script type="text/javascript">
		
	//计算日期之间的脚本
	function datePicker(index) {
		var timePicker = new Date();
		if (index != 0) {
			timePicker = new Date(timePicker.valueOf() + index * 24 * 60 * 60
					* 1000);
		}
		var year = timePicker.getFullYear();
		var month = timePicker.getMonth() + 1;
		var date = timePicker.getDate();
		return year + "-" + (month < 10 ? "0" + month : month) + "-"
				+ (date < 10 ? "0" + date : date);
	}

	//表单提交
	function checkAndSubmit() {
		var oneYearAgo= "<%=oneyearago%>";
		if ($("#endDate").val() == "" || $("#startDate").val() == "") {
			top.$.jBox.tip('查询的日期不能为空', 'warning');
			return false;
		} else {
			if($("#endDate").val() < $("#startDate").val()) {
			top.$.jBox.tip('开始时间不能大于结束时间', 'warning');
			return false;
			}
			
			if($("#startDate").val() <  oneYearAgo){
			top.$.jBox.tip('最多只能查询最近1年记录', 'warning');
			return false;
			}
			$('#searchForm').submit();
			return true;
		}
	}
	
	// 改变时间区间
	function changeTimeArea(n) {
		var endDatePicker = datePicker(-1);//结束时间
		var startDatePicker = datePicker(n);
		$("#endDate").val(endDatePicker);//查询结束时间插件赋值
		$("#startDate").val(startDatePicker);//查询开始时间插件赋值
	}

	// 分析类型改变事件
	function selectChange() {
		if ('<%=RptFieldEnum.HOUR_OF_DAY%>' == $('#rpt_type').val()) {
			$('#dateType7').attr('checked', true);
			changeTimeArea(-1);
		} else if('<%=RptFieldEnum.DAY_OF_MONTH%>' == $('#rpt_type').val()) {
			$('#dateType30').attr('checked', true);
			changeTimeArea(-30);
		} else if('<%=RptFieldEnum.WEEK_OF_YEAR%>' == $('#rpt_type').val()) {
			$('#dateType30').attr('checked', true);
			changeTimeArea(-90);
		} else if('<%=RptFieldEnum.MONTH%>' == $('#rpt_type').val()) {
			$('#dateType30').attr('checked', true);
			changeTimeArea(-365);
		}
		checkAndSubmit();
	}

	$(document).ready(function() {
		//动态改变日期框
		$("input[name='dateType']").click(function() {
			changeTimeArea(parseInt($(this).val()));
		});
        $('#container').highcharts({
			credits:{
				enabled:false // 禁用版权信息
			},
            title: {
                text: '趋势图',
                x: -20 //center
            },
            subtitle: {
                text: '',
                x: -20
            },
            xAxis: {
            	labels: { 
                    step:5
          		},
                categories: [${imgLabels}]
            },
            yAxis: {
                title: {
                    text: '数量(次)'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: '(人|次)'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: [{
                name: '消息发送人数',
                data: [${imgUserCounts}]
            }, {
                name: '消息发送次数',
                data: [${imgMsgCounts}]
            }, {
                name: '人均发送次数',
                data: [${imgAvgCounts}]
            }]
        });
     });
    </script>
</head>
<body>
	<!-- 导航栏 -->
	<ul class="nav nav-tabs">
		<li class="active"><a href="javascript:void(null);">消息分析</a></li>
	</ul>

	<!-- 主体部分 -->

	<c:set var="rptTypeEnum" value="<%=RptFieldEnum.DAY_OF_MONTH%>" />
	<c:if test="${rptTypeEnum eq rptModel.type}">
		<!-- 昨日指标 -->
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<table id="contentTable" class="table table-bordered ">
						<thead>
							<tr>
								<th style="width: 100%;" colspan="3">昨日关键指标</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td style="width: 33%; text-align: center;" rowspan="6">
									<h5>消息发送人数</h5>
									<h3>${yesterday.USER_COUNT ==null ? 0 : yesterday.USER_COUNT}</h3>
									<!-- 日&nbsp;&nbsp;--<br /> 周&nbsp;&nbsp;--<br /> 月&nbsp;&nbsp;--<br /> -->
								</td>
								<td style="width: 33%; text-align: center;" rowspan="6">
									<h5>消息发送次数</h5>
									<h3>${yesterday.MSG_COUNT ==null ?  0 : yesterday.MSG_COUNT}</h3>
									<!-- 日&nbsp;&nbsp;--<br /> 周&nbsp;&nbsp;--<br /> 月&nbsp;&nbsp;--<br /> -->
								</td>
								<td style="width: 34%; text-align: center;" rowspan="6">
									<h5>人均发送次数</h5>
									<h3>${yesterday.AVG_MSG_COUNT ==null ? 0 : yesterday.AVG_MSG_COUNT}</h3>
									<!-- 日&nbsp;&nbsp;--<br />周&nbsp;&nbsp;--<br /> 月&nbsp;&nbsp;--<br /> -->
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
	<!-- 工具条按钮 -->
	<form:form id="searchForm" action="${ctx}/messageStats/messageAnalyse" method="post"
		modelAttribute="rptModel" class="breadcrumb form-search">
		<div class="controls">
			<label class="control-label">分析类型：&nbsp;</label>
			<form:select id="rpt_type" path="type" items="${rptModel.msgRptList}" itemLabel="label"
			itemValue="name" onchange="selectChange()"/>
			<label class="control-label">按时间统计：&nbsp;</label>
			<fmt:formatDate value="${rptModel.startDate}" var="startDate" pattern="yyyy-MM-dd" />
			<form:input id="startDate" path="startDate" value="${startDate}" type="text"
				readonly="readonly" maxlength="20" class="input-medium Wdate"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			<label style="margin: 0px 3px;">--</label>
			<fmt:formatDate value="${rptModel.endDate}" var="endDate" pattern="yyyy-MM-dd" />
			<form:input id="endDate" path="endDate" value="${endDate}" type="text"
				readonly="readonly" maxlength="20" class="input-medium Wdate"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			<span style="margin: 0px 20px;">
			<form:radiobutton id="dateType7" path="dateType" value="-7" label="7天" />
			<form:radiobutton id="dateType14" path="dateType" value="-14" label="14天" />
			<form:radiobutton id="dateType30" path="dateType" value="-30" label="30天" />
			</span> &nbsp;&nbsp; 
			<input class="btn btn-primary" type="submit" value="查询" onclick="return checkAndSubmit()"/>
		</div>
	</form:form>
    <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

	<!-- 详细数据 -->
	<div
		style="margin: 30px 0px 15px 20px; font-size: 14px; height: 14px; position: relative; z-index: 7; line-height: 14px; border-left: 4px solid #6B6B6B; padding-left: 3px;">
		详细数据</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<table id="detailedTable" class="table table-bordered ">
					<!-- table-striped  -->
					<thead>
						<tr>
							<th style="width: 25%;">时间</th>
							<th style="width: 25%;">消息发送人数</th>
							<th style="width: 25%;">消息发送次数</th>
							<th style="width: 25%;">人均发送次数</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="item">
							<tr>
								<td style="width: 25%;">${item.CDATE}</td>
								<td style="width: 25%; text-align: right">${item.USER_COUNT > 0 ? item.USER_COUNT : 0}</td>
								<td style="width: 25%; text-align: right">${item.MSG_COUNT > 0 ? item.MSG_COUNT : 0}</td>
								<td style="width: 25%; text-align: right">${item.AVG_MSG_COUNT > 0 ? item.AVG_MSG_COUNT : 0}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
