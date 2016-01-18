<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板消息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit1").click(function(){
				loading('正在同步数据，请稍等...');
				$.get("${ctx}/wechat/weixinMessageTemplate/sync", function(data){
					window.location.reload();
				}); 
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
		<li class="active"><a href="${ctx}/wechat/weixinMessageTemplate/">模板消息列表</a></li>
		<li><a href="${ctx}/wechat/weixinMessageTemplate/form">模板消息添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="weixinMessageTemplate" action="${ctx}/wechat/weixinMessageTemplate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnSubmit1" class="btn btn-primary span2" type="button" value="一键同步模板消息"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>模板ID</th>
				<th>标题</th>
				<th>主行业</th>
				<th>副行业</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weixinMessageTemplate">
			<tr>
				<td><a href="${ctx}/wechat/weixinMessageTemplate/form?id=${weixinMessageTemplate.id}">
					${weixinMessageTemplate.templateId}
				</a></td>
				<td>
					${weixinMessageTemplate.title}
				</td>
				<td>
					${weixinMessageTemplate.primaryIndustry}
				</td>
				<td>
					${weixinMessageTemplate.deputyIndustry}
				</td>
				<td>
    				<a href="${ctx}/wechat/weixinMessageTemplate/form?id=${weixinMessageTemplate.id}">修改</a>
					<a href="${ctx}/wechat/weixinMessageTemplate/delete?id=${weixinMessageTemplate.id}&templateId=${weixinMessageTemplate.templateId}" onclick="return confirmx('确认要删除该模板消息吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>