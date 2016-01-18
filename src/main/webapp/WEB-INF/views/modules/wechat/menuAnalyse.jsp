<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page import="com.yitong.weixin.common.utils.DateUtils" %>
<%
    String oneyearago=DateUtils.monthStr(12);
%>
<html>
<head>
    <meta name="decorator" content="default" />
    <title>用户分析</title>
    <script src="${ctxStatic}/highcharts/highcharts.js" type="text/javascript"></script>
    <script type="text/javascript">
        //计算日期之间的脚本
        function datePicker(index){
            var timePicker = new Date();
            if(index != 0){
                timePicker = new Date(timePicker.valueOf() + index*24* 60 * 60 * 1000);
            }
            var year = timePicker.getFullYear();
            var month = timePicker.getMonth()+1;
            var date = timePicker.getDate();
            return year+"-"+(month<10?"0"+month:month)+"-"+(date<10?"0"+date:date);
        }

        //表单提交
        function checkAndSubmit(){
            var oneYearAgo= "<%=oneyearago%>";
            if ($("#endDate").val() == "" || $("#beginDate").val() == "") {
                top.$.jBox.tip('查询的日期不能为空', 'warning');
                return false;
            } else {
                if($("#endDate").val() < $("#beginDate").val()) {
                    top.$.jBox.tip('开始时间不能大于结束时间', 'warning');
                    return false;
                }
                if($("#beginDate").val() <  oneYearAgo){
                    top.$.jBox.tip('最多只能查询最近1年记录', 'warning');
                    return false;
                }
//                $('#searchForm').submit();
                return true;
            }
        }

        $(document).ready(function(){
            //动态改变日期框
            $("input[name='statsWay']").click(function(){
                var endDatePicker = datePicker(0);//结束时间
                var beginDatePicker = datePicker(parseInt($(this).val()));;//开始时间
                $("#endDate").val(endDatePicker);//查询结束时间插件赋值
                $("#beginDate").val(beginDatePicker);//查询开始时间插件赋值
            });
        });
    </script>
</head>
<body>
<!-- 导航栏 -->
<ul class="nav nav-tabs">
    <li class="active"><a href="javascript:void(null);">菜单分析</a></li>
</ul>

<!-- 主体部分 -->


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
                        <h5>菜单点击次数</h5>
                        <h3>${ yesterdayData.CHICK_COUNT == null ? 0 : yesterdayData.CHICK_COUNT }</h3>
                        <!-- 日&nbsp;&nbsp;--<br />
                        周&nbsp;&nbsp;--<br />
                        月&nbsp;&nbsp;--<br /> -->
                    </td>
                    <td style="width: 33%; text-align: center;" rowspan="6">
                        <h5>菜单点击人数</h5>
                        <h3>${ yesterdayData.USER_COUNT == null ? 0 : yesterdayData.USER_COUNT }</h3>
                        <!-- 日&nbsp;&nbsp;--<br />
                        周&nbsp;&nbsp;--<br />
                        月&nbsp;&nbsp;--<br /> -->
                    </td>
                    <td style="width: 34%; text-align: center;" rowspan="6">
                        <h5>人均点击次数</h5>
                        <h3>${ yesterdayData.AVG_CHICK_COUNT == null ? 0 : yesterdayData.AVG_CHICK_COUNT }</h3>
                        <!-- 日&nbsp;&nbsp;--<br />
                        周&nbsp;&nbsp;--<br />
                        月&nbsp;&nbsp;--<br /> -->
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- 查询条件表单 -->
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <form:form id="searchForm" action="${ctx}/menuStats/menuAnalyse" method="post"
                       class="breadcrumb form-search" modelAttribute="msModel">
                <div class="controls">
                    <label class="control-label">按时间统计&nbsp;&nbsp;</label>
                    <fmt:formatDate value="${ msModel.beginDate }" var="beginDate" pattern="yyyy-MM-dd" />
                    <form:input id="beginDate" path="beginDate" type="text" readonly="readonly"
                                maxlength="20" class="input-medium Wdate" value="${beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
                    <label style="margin: 0px 3px;">--</label>
                    <fmt:formatDate value="${ msModel.endDate }" var="endDate" pattern="yyyy-MM-dd" />
                    <form:input id="endDate" path="endDate" type="text" readonly="readonly" maxlength="20"
                                class="input-medium Wdate" value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
						<span style="margin: 0px 20px;">
							<form:radiobutton path="statsWay" value="-7" label="7天" />&nbsp;
							<form:radiobutton path="statsWay" value="-14" label="14天" />&nbsp;
							<form:radiobutton path="statsWay" value="-30" label="30天" />&nbsp;
						</span> &nbsp;&nbsp;
                    <input class="btn btn-primary" type="submit" value="查询" onclick="return checkAndSubmit()"/>
                </div>
            </form:form>
        </div>
    </div>
</div>

<!-- 图表插件 -->
<%--<div id="container" style="min-width: 310px; height: 400px;"></div>--%>

<!-- 详细数据 -->
<%--<div style="margin: 30px 0px 15px 20px; font-size: 14px; height: 14px; position: relative; z-index: 7; line-height: 14px; border-left: 4px solid #6B6B6B; padding-left: 3px;">详细数据</div>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <table id="detailedTable" class="table table-bordered table-striped">
                <!-- table-striped  -->
                <thead>
                <tr>
                    <th style="width: 20%;">时间</th>
                    <th style="width: 20%;text-align: center;">新增关注数</th>
                    <th style="width: 20%;text-align: center;">新取消关注数</th>
                    <th style="width: 20%;text-align: center;">净增关注数</th>
                    <th style="width: 20%;text-align: center;">累积关注数</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="map">
                    <tr>
                        <td style="width: 30%;">${map.DATEINFO}</td>
                        <td style="width: 15%;text-align: center;">${ map.DAYADDNUM == null ? 0 : map.DAYADDNUM }</td>
                        <td style="width: 15%;text-align: center;">${ map.DAYCANCELNUM  == null ? 0 : map.DAYCANCELNUM}</td>
                        <td style="width: 15%;text-align: center;">${ map.DAYLEAVENUM == null? 0 : map.DAYLEAVENUM}</td>
                        <td style="width: 15%;text-align: center;">${ map.ALLNUM  == null ? 0 : map.ALLNUM}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>--%>
</body>
</html>