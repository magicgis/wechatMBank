<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%
	Calendar c=Calendar.getInstance();   
	DateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
	c.setTime(new Date());   
	c.add(Calendar.MONTH,-24);   
	Date d2=c.getTime();   
	String monthStr=df.format(d2); 
%>
<html ng-app="weixin" id="ng-app">
<head>
    <title>客服管理</title>
    <meta name="author" content="http://www.yitong.com.cn"/>
    <script src="${ctxStatic}/angular/json2.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value:'default'}/bootstrap.min.css"
          type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
    <!--[if lte IE 6]>
    <link href="${ctxStatic}/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
    <script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/common/common.js" type="text/javascript"></script>
    <link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/common/jeesite.min.js" type="text/javascript"></script>
    <%@include file="/WEB-INF/views/include/dialog.jsp" %>


    <script src="${ctxStatic}/angular/angular.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/angular/angular-resource.js" type="text/javascript"></script>
    <script src="${ctxStatic}/angular/angular-route.js" type="text/javascript"></script>
    <script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

    <link href="${ctxStatic}/chat/chat.css" rel="stylesheet" type="text/css"/>
    <style>
        table {
            font-size: 14px;
        }
    </style>
    <script type="text/javascript">
    	var _selectedNews = null;
    	var _selectedPic = null;
    	var _msgType = 1;
    	var minDate='<%=monthStr %>';
        var app = angular.module('weixin', ['ngResource', 'ngRoute'])
                .controller("MessageCtrl", function ($scope, $http) {
                    $scope.messages = [];
                    $scope.replayMessage = '';
                    $scope.openId = '';
                    $scope.page=1;
                    $scope.pageSize=20;
                    $scope.msgid="";
                    $scope.msgType = 1;	// 消息类型
                    $scope.hasMore=true;
                    $scope.loadMessage = function (msgid, openId) {
                        $scope.hasMore=true;
                        $scope.page=1;
                        $scope.messages = [];
                        $scope.msgid=msgid;
                        $scope.replayMessage = '';
                        $scope.openId = openId;
                        $scope.getMessages();
                    };
                    var a = 1 ;
                    $scope.getMessages=function(){
                        $("#more").html("正在加载...")
                        $http.get('${ctx}/wechat/weixinMessage/findPageByMsgId',{params: {"msgId": $scope.msgid,"pageNo":$scope.page,"pageSize": $scope.pageSize,"ie":a++},cache:false})
                                .success(function (data) {
                                    //console.log(data);
                                    $("#more").html("查看更多");
                                    if($scope.page*$scope.pageSize > data.count){
                                        $scope.hasMore=false;
                                    }
                                    var list=data.list.reverse();
                                    $.each($scope.messages,function(i,v){
                                       list.push(v);
                                    });
                                    $scope.messages=list;
                                    $("#modal-message").modal('show');
                                }).error(function () {
                                    message("请求有错误");
                                });
                    }

                    $scope.replay = function () {
                        if($.trim($scope.replayMessage) =="" && null == _selectedNews)  {
                            alert("回复信息不能为空");
                            return;
                        }
                        $http.get('${cfx}/custom/hight', {params: {"openId": $scope.openId, "msg": $scope.replayMessage, artId: _selectedNews?_selectedNews:""}})
                                .success(function (data) {
                                    $("#modal-message").modal('hide');
                                    message(data.MSG);
                                })
                                .error(function (data) {
                                    $("#modal-message").modal('hide');
                                    message("请求有错误");
                                });
                    }
                    $scope.getMore=function(){

                        $scope.page++;
                        $scope.getMessages()
                    }
                });
        var navMsgTypeClick = function(type){

    		_msgType = type;
    		_selectedNews = null;
    		_selectedPic = null;
    		// 如果是文本消息，不处理
    		if(1 == type)
    			return;
    		// 如果是图文消息，显示图文消息预览列表供用户选择
    		top.$.jBox.open("iframe:${ctx}/cms/article/listNews?pageSize=8&msgType="+(type-1), "选择图文消息",$(top.document).width()-220,$(top.document).height()-180,{
				buttons:{"确定":true}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},submit: function(v, h, f){
					// 如果用户确定，显示显示发现送图片
					if(_selectedNews)
					{
						$("#sendPic").attr("src",_selectedPic);
					}
					else
					{
						$("#navMsgType li:first a").click();
						_msgType = 1;
					}
				},closed:function(){
					if(1 == _msgType)
						$("#navMsgType li:first a").click();
				}
			});
    	}
        $(document).ready(function () {
        	
        	// 表格排序
            var orderBy = $("#orderBy").val().split(" ");
            $("#contentTable th.sort").each(function () {
                if ($(this).hasClass(orderBy[0])) {
                    orderBy[1] = orderBy[1] && orderBy[1].toUpperCase() == "DESC" ? "down" : "up";
                    $(this).html($(this).html() + " <i class=\"icon icon-arrow-" + orderBy[1] + "\"></i>");
                }
            });

            $("#contentTable th.sort").click(function () {
                var order = $(this).attr("class").split(" ");
                var sort = $("#orderBy").val().split(" ");
                for (var i = 0; i < order.length; i++) {
                    if (order[i] == "sort") {
                        order = order[i + 1];
                        break;
                    }
                }
                if (order == sort[0]) {
                    sort = (sort[1] && sort[1].toUpperCase() == "DESC" ? "ASC" : "DESC");
                    $("#orderBy").val(order + " DESC" != order + " " + sort ? "" : order + " " + sort);
                } else {
                    $("#orderBy").val(order + " ASC");
                }
                page();
            });
            if("" != "${messageModel.content}") {
            	$("#content").focus();
            } else {
            	$("#fromUserName").focus();
            }
            
            $('#modal-message').on('shown', function () {
            	$('#modal-body').scrollTop($('#modal-body').scrollHeight);
           	});
            $("#btnExport").click(function(){
            	var beginDate = $("#beginCreateDate").val();
            	var endDate = $("#endCreateDate").val();
            	if(minDate>beginDate){
    				alertinfo("无法查询二年前的历史消息！");	
    				return;
    			}
            	if(!comptime(beginDate,endDate)){
            		alert("日期范围跨度不能超过90天！");
            		return;
            	}
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/wxMsg/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
        });
        
        function comptime(beginTime,endTime) {
            var beginTimes = beginTime.split('-');
            var endTimes = endTime.split('-');
            beginTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] ;
            endTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0];
            var ret = ((Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000)-0;
            if(ret>90*24){
            	return false;
            }else{
            	return true;
            }
        }

        function page(n, s) {
        	var beginDate = $("#beginCreateDate").val();
        	if(minDate>beginDate){
				alertinfo("无法查询二年前的历史消息！");	
				return;
			}
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").attr("action", "${ctx}/wechat/weixinMessage/list");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body ng-controller="MessageCtrl">

<ul class="nav nav-tabs">
    <li class="active"><a href="javascript:void();">消息列表</a></li>
    <li class=""><a href="${ctx}/wxMsg/new">群发新消息</a></li>
</ul>

<form:form id="searchForm" modelAttribute="weixinMessage"
           action="${ctx}/wechat/weixinMessage/list" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>

    <div>
        <label>用户名：</label>
        <form:input id="fromUserName" path="weixinUser.userName" class="input-medium" type="text" maxlength="50"/>
        <label>消息：</label>
        <form:input id="content" path="content" class="input-medium" type="text" maxlength="50"/>&nbsp;
        <label>日期范围：&nbsp;</label><input id="beginCreateDate" name="beginCreateDate" type="text" maxlength="20" class="input-medium Wdate"
				value="${weixinMessage.beginCreateDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false, maxDate:'#F{$dp.$D(\'endCreateDate\')||\'%y-%M-%d\'}'});"/>
			<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="endCreateDate" name="endCreateDate" type="text" maxlength="20" class="input-medium Wdate"
				value="${weixinMessage.endCreateDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false, minDate:'#F{$dp.$D(\'beginCreateDate\')}', maxDate:'%y-%M-%d'});"/>
        &nbsp;&nbsp;&nbsp;
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
        &nbsp;<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
    </div>
</form:form>

<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered ">
    <thead>
    <tr>
        <th style="width: 13%;">用户名</th>
        <th>最后消息</th>
        <th style="width: 13%;">更新时间</th>
        <th style="width: 8%;">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="product">
        <tr>
            <td>${product.weixinUser.userName}</td>
            <td>${product.content}</td>
            <td>${product.createDateFomat}</td>
            <td>
                <a href="#" ng-click="loadMessage('${product.id}','${product.weixinUser.openId}');">回复</a>
                    <%--<a href="#">删除</a>--%>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>

<div id="modal-message" class="modal hide fade out" tabindex="-1" role="dialog" aria-hidden="false"
     style="width: 900px; margin-left: -450px;">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">回复消息</h3></div>
    <div class="modal-body" id="#modal-body" style="max-height: 700px;">
        <div class="container" style="width: 870px;height: 200px;">
            <div class="row-fluid">
                <div class="message-wrap" ng-model="messages">
                    <div ng-if="messages.length > 0">
                        <div class="msg-wrap">
                            <div class="alert alert-info msg-date" ng-if="hasMore">
                                <a ng-click="getMore()" id="more">查看更多</a>
                            </div>
                            <div ng-repeat="message in messages">
                                <div class="media msg {{message.fromUserName ? 'msg-user' :''}}">
                                <a class="pull-left" href="#">
                                    <img class="media-object" data-src="holder.js/64x64" alt="64x64"
                                         style="width: 32px; height: 32px;"
                                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAACqUlEQVR4Xu2Y60tiURTFl48STFJMwkQjUTDtixq+Av93P6iBJFTgg1JL8QWBGT4QfDX7gDIyNE3nEBO6D0Rh9+5z9rprr19dTa/XW2KHl4YFYAfwCHAG7HAGgkOQKcAUYAowBZgCO6wAY5AxyBhkDDIGdxgC/M8QY5AxyBhkDDIGGYM7rIAyBgeDAYrFIkajEYxGIwKBAA4PDzckpd+322243W54PJ5P5f6Omh9tqiTAfD5HNpuFVqvFyckJms0m9vf3EY/H1/u9vb0hn89jsVj8kwDfUfNviisJ8PLygru7O4TDYVgsFtDh9Xo9NBrNes9cLgeTybThgKenJ1SrVXGf1WoVDup2u4jFYhiPx1I1P7XVBxcoCVCr1UBfTqcTrVYLe3t7OD8/x/HxsdiOPqNGo9Eo0un02gHkBhJmuVzC7/fj5uYGXq8XZ2dnop5Mzf8iwMPDAxqNBmw2GxwOBx4fHzGdTpFMJkVzNB7UGAmSSqU2RoDmnETQ6XQiOyKRiHCOSk0ZEZQcUKlU8Pz8LA5vNptRr9eFCJQBFHq//szG5eWlGA1ywOnpqQhBapoWPfl+vw+fzweXyyU+U635VRGUBOh0OigUCggGg8IFK/teXV3h/v4ew+Hwj/OQU4gUq/w4ODgQrkkkEmKEVGp+tXm6XkkAOngmk4HBYBAjQA6gEKRmyOL05GnR99vbW9jtdjEGdP319bUIR8oA+pnG5OLiQoghU5OElFlKAtCGr6+vKJfLmEwm64aosd/XbDbbyIBSqSSeNKU+HXzlnFAohKOjI6maMs0rO0B20590n7IDflIzMmdhAfiNEL8R4jdC/EZIJj235R6mAFOAKcAUYApsS6LL9MEUYAowBZgCTAGZ9NyWe5gCTAGmAFOAKbAtiS7TB1Ng1ynwDkxRe58vH3FfAAAAAElFTkSuQmCC">
                                </a>

                                <div class="media-body">
                                    <small class="pull-right time"><i class="fa fa-clock-o"></i>{{message.createDateFomat}}
                                    </small>
                                    <h5 class="media-heading">{{message.weixinUser.userName ? message.weixinUser.userName :
                                        "客服"}}</h5>
                                    <small class="span10">{{message.content}}
                                    </small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div ng-if="messages.length == 0">
                    没有得到任何信息
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <div class="row-fluid">
        <div class="tabbable">
		    <ul class="nav nav-tabs" id="navMsgType">
			    <li class="active""><a href="#tab1" onclick="navMsgTypeClick(1);" data-toggle="tab">文本消息</a></li>
			    <li><a href="#tab2" onclick="navMsgTypeClick(2);" data-toggle="tab">单图文消息</a></li>
			    <li><a href="#tab2" onclick="navMsgTypeClick(3);" data-toggle="tab">多图文消息</a></li>
		    </ul>
		    <div class="tab-content">
			    <div class="send-wrap tab-pane active" id="tab1">
		            <textarea class="form-control send-message  span12" rows="4" placeholder="回复内容"
		                      ng-model="replayMessage"></textarea>
		        </div>
			    <div class="send-wrap tab-pane" id="tab2" style="text-align: left;">
		        	<div style="margin: 10px; padding:5px; background-color: white;">
		        	<img id="sendPic" alt="" style="width:200px; height:80px">
		        	</div>
		        </div>
			 </div>
	    </div>
	    <!-- 
    	<div>
    		 <select class="span3" id="msgSelect" style="margin-right:15px;" ng-model="msgType" ng-change="changMsg();">
                    <option value="1">文本消息</option>
                    <option value="2">单图文消息</option>
                    <option value="3">多图文消息</option>
                </select>
    	</div>
    	
        <div class="send-wrap" ng-if="msgType==1">
            <textarea class="form-control send-message  span12" rows="5" placeholder="回复内容"
                      ng-model="replayMessage"></textarea>
        </div>
        <div class="send-wrap" ng-if="msgType>1" style="text-align: left;">
        	<div style="margin: 10px; padding:5px; background-color: white;">
        	<img id="sendPic" alt="" style="width:160px; height:80px">
        	</div>
        </div>
         -->
            <a href="" class="text-right btn btn-info pull-right" role="button" ng-click="replay();"><i
                    class="fa fa-plus"></i>发送</a>
    </div>
</div>
</div>
</body>
</html>