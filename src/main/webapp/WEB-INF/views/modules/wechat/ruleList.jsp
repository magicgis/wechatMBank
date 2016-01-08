<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>应答规则管理</title>
    <meta name="decorator" content="default" />
    <script type="text/javascript">
        function page(n,s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        // 确认对话框
		function auditConfirmx(mess, href,ruleId){
			top.$.jBox.confirm(mess,'系统提示',function(v,h,f){
				if(v=='ok'){
					$("#audit").show();
					$("#query"+ruleId).hide();
					var clickRule = $("#clickRule").val();//获取上次点击位置
					if(clickRule == ""){//第一次点击
						$("#clickRule").val("query"+ruleId);
						$("#toHref").val(href);
					}else{
						$("#"+clickRule).show();
						$("#clickRule").val("query"+ruleId);
						$("#toHref").val(href);
					}
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
			return false;
		}
    </script>
</head>
<body>

<ul class="nav nav-tabs">
    <li class="active"><a href="javascript:void();">规则列表</a></li>
   	<li class=""><a href="${ctx}/wechat/weixinMenuList/form">新建规则</a></li>
</ul>

<form:form id="searchForm" modelAttribute="weixinMenuList"
           action="${ctx}/wechat/weixinMenuList/list" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
    <div>
        <label>规则编码：</label>
        <form:input id="code" path="code" class="input-medium" type="text" maxlength="50" />
        <label>规则名称：</label>
        <form:input id="name" path="name" class="input-medium" type="text" maxlength="50" />
        &nbsp;&nbsp;&nbsp;
        <input id="btnSubmit" class="btn btn-primary"
               type="submit" value="查询" onclick="page()"/>
    </div>
</form:form>

<tags:message content="${message}" />
<table id="contentTable" class="table table-striped table-bordered ">
    <thead>
    <tr>
        <th>规则编码</th>
        <th>规则名称</th>
        <!-- <th>签约检查</th> -->
        <!--<th>消息</th> -->
		<th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="rule">
        <tr>
            <td><a href="${ctx}/wechat/weixinMenuList/form?id=${rule.id}">${rule.code}</a></td>
            <td>${rule.name}</td>
           	<!-- 审核员 不能修改用户数据-->
        	<td>
                <a href="${ctx}/wechat/weixinMenuList/form?id=${rule.id}">修改</a>
                <a id="query${rule.id}" href="${ctx}/wechat/weixinMenuList/delete?id=${rule.id}&code=${rule.code}" onclick="return confirmx('确认要删除该规则吗？', this.href,${rule.id})">删除</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<input id="clickRule" name="clickRule" type="hidden" value="">
</body>
</html>