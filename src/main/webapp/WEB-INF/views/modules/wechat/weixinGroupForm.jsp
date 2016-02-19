<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信分组管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#groupOldName").val($("#groupName").val());
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/wechat/weixinGroup/">微信分组列表</a></li>
		<li class="active"><a href="${ctx}/wechat/weixinGroup/form?id=${weixinGroup.id}">${not empty weixinGroup.id?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="weixinGroup" action="${ctx}/wechat/weixinGroup/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="groupId"/>
		<form:hidden path="groupOldName"/>
		<form:hidden path="acctOpenId"/>
		<form:hidden path="groupTimes"/>
		
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">分组名称：</label>
			<div class="controls">
				<form:input path="groupName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分组备注：</label>
			<div class="controls">
				<form:input path="groupComment" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>