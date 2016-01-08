<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单规则管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
		<li><a href="${ctx}/wechat/weixinMenuList/">菜单规则列表</a></li>
		<li class="active"><a href="${ctx}/wechat/weixinMenuList/form?id=${weixinMenuList.id}">菜单规则<shiro:hasPermission name="wechat:weixinMenuList:edit">${not empty weixinMenuList.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="wechat:weixinMenuList:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="weixinMenuList" action="${ctx}/wechat/weixinMenuList/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">功能编码：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">列表功能名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属上级功能编码：</label>
			<div class="controls">
				<form:input path="superCode" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">显示顺序：</label>
			<div class="controls">
				<form:input path="orderN" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">响应类型，0-显示子功能列表，1-显示消息内容，2-调用接口，3-外部链接：</label>
			<div class="controls">
				<form:input path="actionType" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应答消息id：</label>
			<div class="controls">
				<form:input path="articleId" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网页链接：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否隐藏：</label>
			<div class="controls">
				<form:input path="isHide" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选中后显示的消息头：</label>
			<div class="controls">
				<form:input path="message" htmlEscape="false" maxlength="1024" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息类型（0：文本；1：图文；2：自定义）：</label>
			<div class="controls">
				<form:input path="msgType" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">自定义消息处理类，msgType=2 使用此类：</label>
			<div class="controls">
				<form:input path="msgClass" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">绑定检查，0：不检查，1：检查：</label>
			<div class="controls">
				<form:input path="isBinded" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">keywords：</label>
			<div class="controls">
				<form:input path="keywords" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="wechat:weixinMenuList:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>