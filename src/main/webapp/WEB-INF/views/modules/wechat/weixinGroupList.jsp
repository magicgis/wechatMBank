<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信分组管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate({
				submitHandler: function(form){
					loading('正在同步数据，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
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
		<li class="active"><a href="${ctx}/wechat/weixinGroup/">微信分组列表</a></li>
		<li><a href="${ctx}/wechat/weixinGroup/form">微信分组添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="weixinGroup" action="${ctx}/wechat/weixinGroup/sync" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary span2" type="submit" value="一键同步分组"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>分组名称</th>
				<th>分组备注</th>
				<th>微信号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weixinGroup">
			<tr>
				<td><a href="${ctx}/wechat/weixinGroup/form?id=${weixinGroup.id}">
					${weixinGroup.groupName}
				</a></td>
				<td>
					${weixinGroup.groupComment}
				</td>
				<td>
					${weixinGroup.acctOpenId}
				</td>
				<td>
    				<a href="${ctx}/wechat/weixinGroup/form?id=${weixinGroup.id}">同步修改</a>&nbsp;
					<a href="${ctx}/wechat/weixinGroup/delete?id=${weixinGroup.id}" onclick="return confirmx('确认要删除该微信分组吗？', this.href)">同步删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>