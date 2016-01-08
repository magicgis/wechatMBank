<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单规则管理</title>
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
		<li class="active"><a href="${ctx}/wechat/weixinMenuList/">菜单规则列表</a></li>
		<shiro:hasPermission name="wechat:weixinMenuList:edit"><li><a href="${ctx}/wechat/weixinMenuList/form">菜单规则添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="weixinMenuList" action="${ctx}/wechat/weixinMenuList/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>列表功能名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>列表功能名称</th>
				<shiro:hasPermission name="wechat:weixinMenuList:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weixinMenuList">
			<tr>
				<td><a href="${ctx}/wechat/weixinMenuList/form?id=${weixinMenuList.id}">
					${weixinMenuList.name}
				</a></td>
				<shiro:hasPermission name="wechat:weixinMenuList:edit"><td>
    				<a href="${ctx}/wechat/weixinMenuList/form?id=${weixinMenuList.id}">修改</a>
					<a href="${ctx}/wechat/weixinMenuList/delete?id=${weixinMenuList.id}" onclick="return confirmx('确认要删除该菜单规则吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>