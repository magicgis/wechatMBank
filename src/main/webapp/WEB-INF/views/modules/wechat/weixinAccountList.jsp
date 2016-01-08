<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>多公众号管理</title>
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
		<li class="active"><a href="${ctx}/wechat/weixinAccount/">公众号列表</a></li>
		<li><a href="${ctx}/wechat/weixinAccount/form">添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="weixinAccount" action="${ctx}/wechat/weixinAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>公众号名：</label>
				<form:input path="acctName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>OpenId：</label>
				<form:input path="acctOpenId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>公众号名</th>
				<th>公众号OpenId</th>
				<th>app_id</th>
				<th>app_sercet</th>
				<th>token</th>
				<th>aeskey</th>
				<th>用户名</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weixinAccount">
			<tr>
				<td><a href="${ctx}/wechat/weixinAccount/form?id=${weixinAccount.id}">
					${weixinAccount.acctName}
				</a></td>
				<td>
					${weixinAccount.acctOpenId}
				</td>
				<td>
					${weixinAccount.appId}
				</td>
				<td>
					${weixinAccount.appSercet}
				</td>
				<td>
					${weixinAccount.token}
				</td>
				<td>
					${weixinAccount.aeskey}
				</td>
				<td>
					${weixinAccount.user.loginName}
				</td>
				<td>
    				<a href="${ctx}/wechat/weixinAccount/form?id=${weixinAccount.id}">修改</a>
					<a href="${ctx}/wechat/weixinAccount/delete?id=${weixinAccount.id}" onclick="return confirmx('确认要删除该增删改差吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>