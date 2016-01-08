<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>用户批量修改</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	
	$(document).ready(function() {
		
		$("#batUpdateForm").validate({
			submitHandler : function(form) {
	            loading('正在提交，请稍等...');
	            form.submit();
	            setTimeout(function(){
					window.parent.window.jBox.close();//关闭父窗口
	            },'200');
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
			}
		});
		
	});
</script>
</head>
<body>
	<!-- 装载的jsp页面 -->
	<div style="margin-top: 100px;"></div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="row-fluid">
					<form:form id="batUpdateForm" modelAttribute="wxuBatUpdateModel"
						action="${ctx}/wechat/weixinUser/updateBatUserGroup" target="mainFrame" method="post"
						class="breadcrumb form-search">
						<form:hidden path="groupIds" />
						<div id="selectGroup">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label>请选择分组&nbsp; </label>
							<sys:treeselect id="batUpdateGroupId" name="batUpdateGroupId" value="${wxuBatUpdateModel.weixinGroup.id == null ? 0 : wxuBatUpdateModel.weixinGroup.id }" 
								labelName="group.groupName" labelValue="${wxuBatUpdateModel.weixinGroup.groupName == null ? '未分组' : wxuBatUpdateModel.weixinGroup.groupName}" 
								title="分组" url="/wechat/weixinGroup/treeData?type=1" cssClass="input-small" allowClear="true"/>
							   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input id="btnSubmit1"
								class="btn btn-primary" type="submit" value="确认" />
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>