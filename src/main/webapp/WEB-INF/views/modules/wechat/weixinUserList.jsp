<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnSubmit1").click(function(){
				loading('正在同步数据，请稍等...');
				$.get("${ctx}/wechat/weixinUser/sync", function(data){
					window.location.reload();
				}); 
			});
			
			// 全选复选框js效果
			$("#allCheckBox").click(function(){
				if(this.checked){
					$("input[name='leftCheckBox']").each(function(){this.checked=true;});
				}else{
					$("input[name='leftCheckBox']").each(function(){this.checked=false;}); 
				}
			});
			
			// 批量修改用户分组
			$("#batUpdateGroup").click(function(){
				var groupId = [];
				$("input[name='leftCheckBox']").each(function(){
					if(this.checked && this.id != "allCheckBox"){
						groupId.push($(this).prop("id"));
					}
				});
				if(groupId.length > 0){
					//改为jbox弹出框形式
					top.$.jBox("iframe:${ctx}/wechat/weixinUser/batUpdateUsersGroup/"+groupId.join(','), {
					    title: "批量修改用户分组",  
					    width: 600,  
					    height: 450,
					    buttons: { '关闭': true },
					    submit : function(v, h, f){
					    	 if (v == 0) {  
					             return true; // close the window  
					         }
					    }
					}); 
					//window.location = "${ctx}/user/batUpdateUsersGroup/"+groupId.join(',');
				} else {
					top.$.jBox.tip("请至少选中列表中的一行", 'error');
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
		<li class="active"><a href="${ctx}/wechat/weixinUser/">微信用户列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="weixinUser" action="${ctx}/wechat/weixinUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户昵称:</label>
				<form:input path="userName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>用户分组:</label><sys:treeselect id="group" name="group.id" value="${weixinUser.group.id}" labelName="group.groupName" labelValue="${weixinUser.group.groupName}" 
				title="分组" url="/wechat/weixinGroup/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="batUpdateGroup" class="btn btn-primary span2" type="button" value="分组批量修改" /></li>
			<li class="btns"><input id="btnSubmit1" class="btn btn-primary span2" type="button" value="一键同步用户"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width: 5%; text-align: center;"><input id="allCheckBox" name="leftCheckBox" type="checkbox" /></th>
				<th>用户头像</th>
				<th>用户昵称</th>
				<th>用户分组</th>
				<th>用户最近访问时间</th>
				<th>用户关注时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weixinUser">
			<tr>
				<td style="width: 5%; text-align: center;"><input
											type="checkbox" id="${weixinUser.id}=${weixinUser.userName}=${weixinUser.group.groupName}=${weixinUser.openId}" name="leftCheckBox" /></td>
				<td style="width: 80px; height: 80px;">
					<div class="thumbnail">
			   			<img style="width: 80px; height: 80px;" src="${weixinUser.headImgUrl }"/>
				   	</div>
				</td>
				<td><a href="${ctx}/wechat/weixinUser/form?id=${weixinUser.id}">
					${weixinUser.userName}
				</a></td>
				<td>
					${weixinUser.group.groupName}
				</td>
				<td>
					${weixinUser.lastActiveTime}
				</td>
				<td>
					${weixinUser.subscribeTime}
				</td>
				<td>
    				<a href="${ctx}/wechat/weixinUser/form?id=${weixinUser.id}">查看详情</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>