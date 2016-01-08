<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/wechat/weixinMessage/">消息列表</a></li>
		<shiro:hasPermission name="wechat:weixinMessage:edit"><li><a href="${ctx}/wechat/weixinMessage/form">消息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="weixinMessage" action="${ctx}/wechat/weixinMessage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>接收微信用户所在分组：</label>
				<form:input path="toUser" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>发送微信用户OPENID：</label>
				<form:input path="fromUser" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>微信内容：</label>
				<form:input path="content" htmlEscape="false" maxlength="2048" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${weixinMessage.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${weixinMessage.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>接收微信用户所在分组</th>
				<th>发送微信用户OPENID</th>
				<th>链接标题</th>
				<shiro:hasPermission name="wechat:weixinMessage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weixinMessage">
			<tr>
				<td><a href="${ctx}/wechat/weixinMessage/form?id=${weixinMessage.id}">
					${weixinMessage.toUser}
				</a></td>
				<td>
					${weixinMessage.fromUser}
				</td>
				<td>
					${weixinMessage.title}
				</td>
				<shiro:hasPermission name="wechat:weixinMessage:edit"><td>
    				<a href="${ctx}/wechat/weixinMessage/form?id=${weixinMessage.id}">修改</a>
					<a href="${ctx}/wechat/weixinMessage/delete?id=${weixinMessage.id}" onclick="return confirmx('确认要删除该消息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>