<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>规则管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
    	var flag = false;
        $(document).ready(function() {
            $("#inputForm").validate({
                submitHandler: function(form){
                	 var patt_str=/^[A-Za-z]+$/;
                     if ($("#name").val()==""){
                         $("#name").focus();
                         top.$.jBox.tip('请填写规则名称','warning');
                     } else if($("#code").val()==""){
                         $("#code").focus();
                         top.$.jBox.tip('请填写规则编码','warning');
                     } else	if(!patt_str.exec($("#code").val())){
                   			$("#code").focus();
                           top.$.jBox.tip('规则编码格式不符','error');
               		 } else {
               		 	if($("#code").attr("readonly")){
               		 		setReadonlyByArray(["name","msg_type"],true);
               		 		flag = true;
               		 	}else{
               		 		setReadonlyByArray(["name","code","msg_type"],true);
               		 		falg = false;
               		 	}
			            loading('正在提交，请稍等...');
			            //验证上级审核员登录名和登录密码
			            form.submit();
			        }	
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
    <li class=""><a href="${ctx}/wechat/weixinMenuList/list">规则列表</a></li>
	<li class="active"><a href="javascript:void();">规则${not empty weixinMenuList.id?"修改":"添加"}</a></li>
</ul>
<form:form id="inputForm" modelAttribute="weixinMenuList" action="${ctx}/wechat/weixinMenuList/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">规则名称:</label>
        <div class="controls">
            <form:input path="name" maxlength="200" class="input-xxlarge required"/>
        </div>
    </div>
    <c:choose>
    <c:when test="${param.id!=null}">
      <div class="control-group">
        <label class="control-label">规则编码:</label>
        <div class="controls">
            <form:input path="code" class="input-xxlarge required" type="text" value="" maxlength="200" readonly="true"/>
        </div>
    </div>
    </c:when>
    <c:otherwise>
    <div class="control-group">
        <label class="control-label">规则编码:</label>
        <div class="controls">
            <form:input path="code" class="input-xxlarge required" type="text" value="" maxlength="200" />
        </div>
    </div>
    </c:otherwise>
    </c:choose>
    <div class="control-group" style="display:none;">
        <label class="control-label">签约检查:</label>
        <div class="controls">
        	<form:checkbox path="isBinded" value="1" />
        	<span class="help-inline">判断是否登录</span>
        </div>
    </div>
    <div class="control-group" style="display:none;">
        <label class="control-label">欢迎语:</label>
        <div class="controls">
            <form:textarea path="message" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
        </div>
    </div>
    <legend>
        <h4>应答模式</h4>
    </legend>
    <div class="control-group" style="display:none;">
        <label class="control-label">
        <form:radiobutton path="actionType" value="0" />功能列表</label>
    </div>
    <div class="control-group">
    	<c:if test=""></c:if>
        <label class="control-label">规则类型</label>
        <div class="controls">
            <form:select id="msg_type" path="msgType" style="width:160px;margin-right:15px;">
                <form:option value="0">文本</form:option>
                <form:option value="1">单图文</form:option>
                <form:option value="2">多图文</form:option>
            </form:select>
            <a id="relationButton" href="javascript:" class="btn">指定模板</a>
            <form:hidden id="articleDataRelation" path="articleId" />
            <ul id="articleSelectList" style="display: inline-block;list-style: none;"></ul>
            <script type="text/javascript">
                var articleSelect = [];
                function articleSelectAddOrDel(id,title){
                    articleSelect=[id,title];
                    articleSelectRefresh();
                }
                function articleSelectDel(){
                    articleSelect=[];
                    articleSelectRefresh();
                }
                function articleSelectRefresh(){
                    $("#articleDataRelation").val("");
                    $("#articleSelectList").children().remove();
                    if(articleSelect.length > 0){
                        $("#articleSelectList").append("<li>"+articleSelect[1]+"&nbsp;<a href=\"javascript:\" onclick=\"articleSelectDel();\">×</a></li>");
                        $("#articleDataRelation").val(articleSelect[0]);
                    }

                }
                var articleVal = $("#articleDataRelation").val();
                if(articleVal && "" != articleVal) {
	                $.getJSON("${ctx}/cms/article/findByIds", {ids:$("#articleDataRelation").val()}, function(data){
	                    if(0 < data.length){
	                    	data = data[0];
	                        articleSelect=[data[1], data[2]];
	                    }
	                    articleSelectRefresh();
	                });
                }
                $("#relationButton").click(function(){
                	var msgType = $("#msg_type").val(); // 规则类型 0 文本 1 单图文 2 多图文
                    top.$.jBox.open("iframe:${ctx}/wechat/weixinMenuList/selectListRadio?pageSize=8&msgType="+msgType, "指定模板",$(top.document).width()-220,$(top.document).height()-180,{
                        buttons:{"确定":true}, loaded:function(h){
                            $(".jbox-content", top.document).css("overflow-y","hidden");
                        }
                    });
                });
            </script>
        </div>
    </div>
    <div class="control-group" style="display:none;">
        <label class="control-label"> <form:radiobutton path="actionType" value="2"/>接口处理</label>
        <div class="controls">
             <form:input path="url" type="text" placeholder="接口URL" />
        </div>
    </div>
  	<div class="form-actions" id="query">
        <input id="btnSubmit1" class="btn btn-primary" type="submit" value="提 交"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>