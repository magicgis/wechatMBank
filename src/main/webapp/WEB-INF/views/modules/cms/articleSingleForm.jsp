<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/common/resmsg.css" type="text/css" rel="stylesheet" />
	
	<script type="text/javascript">
		 $(document).ready(function() {
			$("#title").focus();
			$("#inputForm").validate({
				submitHandler: function(form){

					if($.trim($("#title").val()) == ""){
						$("#title").focus();
						top.$.jBox.tip('请填写标题','warning');
					}else if($.trim($("#keywords").val()) == ""){
						$("#keywords").focus();
						top.$.jBox.tip('请填写关键字','warning');
					}else if($.trim($("#desc").val()) == ""){
						$("#desc").focus();
						top.$.jBox.tip('请填写描述内容','warning');
					}else if ($.trim($("#url").val())==""){
						$("#url").focus();
						top.$.jBox.tip('请填写原文链接','warning');
					}/* else if(!checkUrl($("#url").val())){
						$("#url").focus();
						top.$.jBox.tip('请填写合法原文链接','warning');
					} */else if ($("#image").val()==""){
						top.$.jBox.tip('请选择封面图片','warning');
					}else{
						$("#description").val("<MsgType><![CDATA[news]]></MsgType>"
								+ "<ArticleCount>1</ArticleCount>"
								+ "<Articles>"
								+ "<item>"
								+ "<Title><![CDATA[" + $("#title").val() + "]]></Title>"
								+ "<Description><![CDATA[" + $("#desc").val() + "]]></Description>"
								+ "<PicUrl><![CDATA[" + "${fns:getConfig('picIp')}" + $("#image").val() + "]]></PicUrl>"
								+ "<Url><![CDATA[" + $("#url").val() + "]]></Url>"
								+ "</item>"
								+ "</Articles>");
							
						$("#desJson").val('{"title": "' + $("#title").val() + '", "desc": "' + $("#desc").val() + '", "picurl": "' + '${fns:getConfig("picIp")}' + $("#image").val() + '", "url": "' + $("#url").val() + '"}');
						loading('正在提交，请稍等...');
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
			
			$("#image").change(function() {
				if($(this).val() != ""){
					$("#preview_pic").attr("src", $(this).val());
					$("#preview_pic").show();
					$("#preview_default").hide();
				} else {
					$("#preview_pic").removeAttr("src");
					$("#preview_pic").hide();
					$("#preview_default").show();
				}
			});
			$("#title").keyup(function() {
				$("#preview_title").html($(this).val());
			});
			$("#title").change(function() {
				$("#preview_title").html($(this).val());
			});
			
			$("#desc").keyup(function() {
				$("#preview_desc").html($(this).val());
			});
			$("#desc").change(function() {
				$("#preview_desc").html($(this).val());
			});
			
			if($.trim($('#desJson').val())!=""){
				var json = jQuery.parseJSON($('#desJson').val());
				$("#desc").val(json.desc);
				$("#url").val(json.url);
			}
			
			$("#title").change();
			$("#desc").change();
			$("#image").change();
		}); 
		function checkUrl(url){
			var strRegex = '^((https|http|ftp|rtsp|mms)?://)' 
				+ '?(([0-9a-zA-Z_!~*\'().&=+$%-]+: )?[0-9a-zA-Z_!~*\'().&=+$%-]+@)?'
				+ '(([0-9]{1,3}.){3}[0-9]{1,3}'
				+ '|' 
				+ '([0-9a-zA-Z_!~*\'()-]+.)*'
				+ '([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z].'
				+ '[a-zA-Z]{2,6})'
				+ '(:[0-9]{1,4})?' 
				+ '((/?)|' 
				+ '(/[0-9a-zA-Z_!~*\'().;?:@&=+$,%#-]+)+/?)$'; 
	     	var re = new RegExp(strRegex);
	     	return re.exec(url);
	  	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/article/?category.id=${article.category.id}">模板列表</a></li>
		<li class="active"><a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}&msgType=1'><c:param name='category.name' value='${article.category.name}'/></c:url>">模板<shiro:hasPermission name="cms:article:edit">${not empty article.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:article:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="article" action="${ctx}/cms/article/save" method="post" class="form-horizontal">
	<div class="container-fluid" style="padding-left:0px;padding-right:0px">
  	<div class="row-fluid">
		<div class="span5">
			<div class="thumbnail" style="padding-left:10px;padding-right:10px;">
	           	<p class="article_title" id="preview_title"></p>
	            <img id="preview_pic" style="display: none;">
	            <i class="appmsg_thumb default" id="preview_default" >封面图片</i>
	           	<p class="article_content" id="preview_desc"></p>
      		</div>
      		<div class="thumbnail" style="padding-left:10px;padding-right:10px;margin-top:10px;">
      			<div class="control-group" style="margin-top:5px;">
					<label class="control-label" style="width:65px;">标题:</label>
					<div class="controls" style="margin-left:70px;padding-right:15px;">
						<p>
						<form:input path="title" htmlEscape="false" maxlength="100" class="span12 required" id="title"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" style="width:65px;">关键字:</label>
					<div class="controls" style="margin-left:70px;padding-right:15px;">
						<p>
						<form:input path="keywords" htmlEscape="false" maxlength="100" class="span12 required" id="keywords"/>
						<span class="help-inline">多个关键字，用空格分隔。</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" style="width:65px;">描述内容:</label>
					<div class="controls" style="margin-left:70px;padding-right:15px;">
						<form:input id ="desc" path="remarks" htmlEscape="false" maxlength="100" class="span12 required"/>
					</div>
				</div>
				<div class="control-group" style="padding-top:25px;">
	      			<label class="control-label" style="width:65px;">封面图片:</label>
					<div class="controls" style="margin-left:70px;padding-right:15px;">
		                <input type="hidden" id="image" name="image" value="${article.imageSrc}" />
						<sys:ckfinderArtSinSelect input="image" type="images" uploadPath="/cms/article" selectMultiple="false"/>
					</div>
				</div>
			</div>
	   	</div>
	   	<div class="span7">
	   		<div class="arrow-left" >
	   		</div>
	  		<div class="thumbnail" style="margin-left:10px ">
				<form:hidden path="id"/>
				<form:hidden path="msgType" value="1"/>
				<form:hidden path="desJson"/>
				<form:hidden path="description"/>
				
				<tags:message content="${message}"/>
				<div class="control-group" style="margin-top:10px;">
					<label class="control-label" style="width:65px;">归属分类:</label>
					<div class="controls" style="margin-left:70px;margin-top:3px;">
						<span>${article.category.name}<span>
						<input id="category" name="category.id" value="${article.category.id}" type="hidden" />
		                <!--<tags:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
							title="栏目" url="/cms/category/treeData" module="article" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="true" cssClass="required"/>-->
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" style="width:65px;">原文链接:</label>
					<div class="controls" style="margin-left:70px;padding-right:15px;">
						<p><input id="url" type="text" maxlength="200" class="span12"/>
					</div>
				</div>
				<div class="control-group">
					<div class="controls"  style="margin-left:10px;padding-right:15px;">
						<form:textarea id="content" htmlEscape="true" path="articleData.content" rows="4" maxlength="200" class="input-xxlarge"/>
						<sys:ckeditor height="220" replace="content" uploadPath="/cms/article" />
					</div>
				</div>
			</div>
		</div>
		</div>
		</div>
		<div class="form-actions" id="query">
			<input class="btn btn-primary" type="submit" value="提 交"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>