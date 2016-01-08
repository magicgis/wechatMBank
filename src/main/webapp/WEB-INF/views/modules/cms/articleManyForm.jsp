<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/common/resmsg.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript">
		var topset= 0;
		var itemCount = 1;
		var editCurrent = 0;
		var itemArray = [1];
		$(document).ready(function() {
			$("#title").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
				if($.trim($("#title").val()) == ""){
					editMain($("#iconFirstEdit"));
					$("#title").focus();
					top.$.jBox.tip('请填写标题','warning');
				}else if($.trim($("#keywords").val()) == ""){
					editMain($("#iconFirstEdit"));
					$("#keywords").focus();
					top.$.jBox.tip('请填写关键字','warning');
				}else if ($.trim($("#url").val())==""){
					editMain($("#iconFirstEdit"));
					$("#url").focus();
					top.$.jBox.tip('请填写原文链接','warning');
				}else if(!checkUrl($("#url").val())){
					editMain($("#iconFirstEdit"));
					$("#url").focus();
					top.$.jBox.tip('请填写合法原文链接','warning');
				}else if ($("#image").val()==""){
					editMain($("#iconFirstEdit"));
					top.$.jBox.tip('请选择封面图片','warning');
				}else if(!validateItem()){
						return;
				} else{
					var content = "<MsgType><![CDATA[news]]></MsgType>"
							+ "<ArticleCount>" + (parseInt(itemCount) + 1) + "</ArticleCount>"
							+ "<Articles>"
							+ "<item>"
							+ "<Title><![CDATA[" + $("#title").val() + "]]></Title>"
							+ "<Description><![CDATA[]]></Description>"
							+ "<PicUrl><![CDATA[" + "${fns:getConfig('picIp')}" + $("#image").val() + "]]></PicUrl>"
							+ "<Url><![CDATA[" + $("#url").val() + "]]></Url>"
							+ "</item>";
							var contentJson = '{"title": "' + $('#title').val() + '", "picurl": "' + '${fns:getConfig("picIp")}' + $('#image').val() + '", "url": "' + $('#url').val() + '", "item":[';
						
						$.each(itemArray, function(key, value) {
							content = content + "<item>"
							+ "<Title><![CDATA[" + $("#itemTitle" + value).html() + "]]></Title>"
							+ "<Description><![CDATA[]]></Description>"
							+ "<PicUrl><![CDATA[" + "${fns:getConfig('picIp')}" + $("#itemImg" + value).attr("src") + "]]></PicUrl>"
							+ "<Url><![CDATA[" + $("#itemUrl" + value).val() + "]]></Url>"
							+ "</item>";
							
							contentJson = contentJson + '{"title": "' + $('#itemTitle' + value).html() + '", "picurl": "' + '${fns:getConfig("picIp")}' + $('#itemImg' + value).attr('src') + '", "localpicurl": "' + $('#itemImg' + value).attr('src') + '", "url": "' + $('#itemUrl' + value).val() + '"}';
							if(key < itemArray.length - 1){
								contentJson = contentJson + ", ";
							}
						});
						content = content + "</Articles>";
						contentJson = contentJson + "]}";
						
						$("#description").val(content);
						$("#desJson").val(contentJson);
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
			
			$("#title").keyup(function() {
				$("#preview_title").html($(this).val());
			});
			$("#title").change(function() {
				$("#preview_title").html($(this).val());
			});
			
			$("#image").change(function() {
				if($(this).val() != ""){
					$("#preview_pic").attr("src", $(this).val());
					$("#preview_pic").css("display", "block");
					$("#preview_default").css("display", "none");
				} else {
					$("#preview_pic").removeAttr("src");
					$("#preview_default").css("display", "block");
					$("#preview_pic").css("display", "none");
				}
			});
			
			$("#titleItem").keyup(function() {
				$("#itemTitle" + editCurrent).html($(this).val());
			});
			$("#titleItem").change(function() {
				//alert($(this).val());
				$("#itemTitle" + editCurrent).html($(this).val());
			});
			
			$("#urlItem").keyup(function() {
				$("#itemUrl" + editCurrent).val($(this).val());
			});
			$("#urlItem").change(function() {
				$("#itemUrl" + editCurrent).val($(this).val());
			});
			
			$("#imageItem").change(function() {
				if($(this).val() != ""){
					$("#itemImg" + editCurrent).attr("src", $(this).val());
					$("#itemImg" + editCurrent).css("display", "block");
					$("#itemImg_defalut" + editCurrent).css("display", "none");
				} else {
					$("#itemImg" + editCurrent).removeAttr("src");
					$("#itemImg_defalut" + editCurrent).css("display", "block");
					$("#itemImg" + editCurrent).css("display", "none");
				}
			});
			
			var json = jQuery.parseJSON($('#desJson').val());
			if(json!=null){
				$("#url").val(json.url);
				$.each(json.item, function(index, item){ 
					if(index > 0){
						addItem();
					}
					
					$("#itemTitle" + (index + 1)).html(item.title);
					$("#itemUrl" + (index + 1)).val(item.url);
					
					$("#itemImg" + (index + 1)).attr("src", item.localpicurl);
					$("#itemImg" + (index + 1)).css("display", "block");
					$("#itemImg_defalut" + (index + 1)).css("display", "none");
				});
			}
			$("#title").change();
			$("#image").change();
		});
		
		function validateItem(){
			var result = true;
			$.each(itemArray, function(key, value) {
				if($("#itemTitle" + value).html() == ""){
					$("#editBtn_preview"+value).click();
					showItemWarning('请填写标题', $("#titleItem"), value);
					result = false;
					return result;
				} else if($("#itemUrl" + value).val() == ""){
					$("#editBtn_preview"+value).click();
					showItemWarning('请填写原文链接', $("#urlItem"), value);
					result = false;
					return result;
				} else if(!checkUrl($("#itemUrl" + value).val())){
					$("#editBtn_preview"+value).click();
					showItemWarning('请填写合法原文链接', $("#urlItem"), value);
					result = false;
					return result;
				} else if($("#itemImg" + value).attr("src") == undefined){
					$("#editBtn_preview"+value).click();
					showItemWarning('请选择封面图片', $("#itemImgSelectBtn"), value);
					result = false;
					return result;
				}
			});
			
			return result;
		}
		
		function checkUrl(url){
			url = url.replace(/\%/g,"");
			var strRegex = /^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i; 
	     	var re = new RegExp(strRegex);
	     	return re.exec(url);
	  	}
		
		function showMainWarning(message, obj){
			if(editCurrent > 0){
				editMain();
			}
			
			obj.focus();
			top.$.jBox.tip(message,'warning');
		}
		
		function showItemWarning(message, obj, i) {
			if(editCurrent != i){
				$("#editBtn_preview" + i).click();
			}
			
			obj.focus();
			top.$.jBox.tip(message,'warning');
		}
		
		function editMain(){
			editCurrent = 0;
			$("#editMain").show();
			$("#editItem").hide();
			
			$("#arrow").css("margin-top", 30);
		}
		
		function editItem(obj){
			
			editCurrent = $(obj).parent().attr("current");
			var titleItem = $("#itemTitle" + editCurrent).html();
			
			$("#titleItem").val($("#itemTitle" + editCurrent).html());
			
			$("#urlItem").val($("#itemUrl" + editCurrent).val());
			$("#imageItem").val($("#itemImg" + editCurrent).attr("src"));
			imageItemPreview();
			$("#editMain").hide();
			$("#editItem").show();
			topset = $(obj).parent().parent().offset().top;
			$("#arrow").css("margin-top", $(obj).parent().parent().offset().top - $("#previewMain").offset().top + 30);
			$("#editItem").css("margin-top", $(obj).parent().parent().offset().top - $("#previewMain").offset().top);
		
	
		}
		
		function delItem(obj){
			if(itemCount < 2){
				top.$.jBox.tip('无法删除，多条图文至少需要2条消息','warning');
				return;
			}
			if($(obj).parent().attr("current") < editCurrent){
				topset=topset-103;
				$("#editMain").hide();
				$("#editItem").show();


				
				$("#arrow").css("margin-top",topset - $("#previewMain").offset().top + 30 );
				$("#editItem").css("margin-top", topset - $("#previewMain").offset().top );
			}
			if($(obj).parent().attr("current") == editCurrent){
				editMain();
			}
			$(obj).parent().parent().remove();
			itemCount --;
			//itemArray.splice(itemCount - 1, 1);
			for(var i=0;i<itemArray.length;i++){
				if($(obj).parent().attr("current") == itemArray[i]){
					itemArray.splice(i, 1);
					break;
				}				
			}
		}
		var countId = 1;
		function addItem(){
			if(itemCount > 4){
				top.$.jBox.tip('你最多只可以加入6条图文消息','warning');
				return;
			}
			itemCount ++;
			countId ++;
			itemArray.push(countId);
			var append_html = '<div id="item' + countId + '" class="appmsg_item">'
			+ '<input type="hidden" id="itemUrl' + countId + '">'
			+ '<img id="itemImg' + countId + '" class="js_appmsg_thumb appmsg_thumb">'
			+ '<i id="itemImg_defalut' + countId + '" class="appmsg_thumb default">缩略图</i>'
			+ '<h4 id="itemTitle' + countId + '"></h4>'
			+ '<div current="' + countId + '" class="appmsg_edit_mask">'
			+ '<span style="visibility:hidden">.</span>'
			+ '<a id="editBtn_preview' + countId + '" class="icon-edit" href="javascript:void(0)" onclick="editItem(this)"></a>'
			+ '<a class="icon-trash" href="javascript:void(0);" onclick="delItem(this)"></a>'
			+ '</div>'
			+ '</div>';
			$("#previewMain").append(append_html);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/article/?category.id=${article.category.id}">模板列表</a></li>
		<li class="active"><a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}&msgType=2'><c:param name='category.name' value='${article.category.name}'/></c:url>">模板${not empty article.id?'修改':'添加'}</a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="article" action="${ctx}/cms/article/save" method="post" class="form-horizontal">
	<div class="container-fluid" style="padding-left:0px;padding-right:0px">
  	<div class="row-fluid">
		<div class="span5">
			<div class="appmsg multi editing">
				<div id="previewMain" class="appmsg_content">
					<div id="itemMain" class="js_appmsg_item">
				        <div class="appmsg_info">
				            <em class="appmsg_date"></em>
				        </div>
				        <div class="cover_appmsg_item">
				            <h4 class="appmsg_title"><a id="preview_title"></a></h4>
				            <div class="appmsg_thumb_wrp">
				                <img id="preview_pic" class="js_appmsg_thumb appmsg_thumb">
				                <i id="preview_default" class="appmsg_thumb default">封面图片</i>
				            </div>
				            <div class="appmsg_edit_mask" >
				            	<span style="visibility:hidden">.</span>
				                <a class="icon-edit" id="iconFirstEdit" href="javascript:editMain(this);"></a>
				            </div>
				        </div>
					</div>
		           	
		           	<div id="item1" class="appmsg_item">
		           		<input type="hidden" id="itemUrl1">
					    <img id="itemImg1" class="js_appmsg_thumb appmsg_thumb">
					    <i id="itemImg_defalut1" class="appmsg_thumb default">缩略图</i>
					    <h4 id="itemTitle1"></h4>
					    <div current="1" class="appmsg_edit_mask">
					    	<span style="visibility:hidden">.</span>
					        <a id="editBtn_preview1" class="icon-edit" href="javascript:void(0);" onclick="editItem(this)"></a>
					        <a class="icon-trash" href="javascript:void(0);" onclick="delItem(this)"></a>
					    </div>
					</div>
				</div>
				
				<div class="appmsg_add" id="imageAdd">
	                <a id="js_add_appmsg" href="javascript:addItem();" >
	                	<span style="visibility:hidden">.</span>
	                	<i class="icon-plus" style="margin-top:-5px;"></i>
	                </a>
	            </div>
		    </div>
	   	</div>
	   	<div class="span7">
	   		<div id="arrow"  class="arrow-left" >
	   		</div>
	  		<div id="editMain" class="thumbnail" style="margin-left:10px ">
				<form:hidden path="id"/>
				<form:hidden path="msgType" value="2"/>
				<form:hidden path="description"/>
				<form:hidden path="desJson"/>
				
				<tags:message content="${message}"/>
				<div class="control-group" style="padding-top:20px;">
					<label class="control-label" style="width:65px;">归属栏目:</label>
					<div class="controls" style="margin-left:70px;margin-top:3px;">
						<span>${article.category.name}<span>
						<input id="category" name="category.id" value="${article.category.id}" type="hidden" />
		               <!--  <tags:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
							title="栏目" url="/cms/category/treeData" module="article" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="true" cssClass="required"/> -->
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" style="width:65px;">标题:</label>
					<div class="controls" style="margin-left:70px;padding-right:15px;">
						<p>
						<form:input path="title" htmlEscape="false" maxlength="100" class="span12 required"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" style="width:65px;">关键字:</label>
					<div class="controls" style="margin-left:70px;padding-right:15px;">
						<p>
						<form:input path="keywords" htmlEscape="false" maxlength="100" class="span12 required"/>
						<span class="help-inline">多个关键字，用空格分隔。</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" style="width:65px;">原文链接:</label>
					<div class="controls" style="margin-left:70px;padding-right:15px;">
						<p>
						<input id="url" type="url" maxlength="200" class="span12 required"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" style="width:65px;margin-top:10px">封面图片:</label>
					<div class="controls" style="margin-left:70px;padding-right:15px;">
						<form:hidden path="image" htmlEscape="false" maxlength="255" class="input-xlarge"/>
						<sys:ckfinder input="image" type="images" uploadPath="/cms/article" selectMultiple="false"/>
					</div>
				</div>
				<div class="control-group" style="display:none">
					<div class="controls"  style="margin-left:10px;padding-right:15px;">
						<form:textarea id="content" htmlEscape="true" path="articleData.content" rows="4" maxlength="200" class="input-xxlarge"/>
						<sys:ckeditor height="220" replace="content" uploadPath="/cms/article" />
					</div>
				</div>
			</div>
			
			<div id="editItem" class="thumbnail hide" style="margin-left:10px ">
				<div class="control-group" style="padding-top:20px;">
					<label class="control-label" style="width:65px;">标题:</label>
					<div class="controls" style="margin-left:70px;padding-right:15px;">
						<p>
						<input id="titleItem" maxlength="100" class="span12 required"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" style="width:65px;">原文链接:</label>
					<div class="controls" style="margin-left:70px;padding-right:15px;">
						<p>
						<input id="urlItem" type="url" maxlength="200" class="span12 required"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" style="width:65px;margin-top:10px">封面图片:</label>
					<div class="controls" style="margin-left:70px;padding-right:15px;">
						<input type="hidden" id="imageItem" maxlength="255" class="input-xlarge"/>
						<sys:ckfinder input="imageItem" uploadPath="/cms/article" type="images" selectMultiple="false"/>
					</div>
				</div>
				<div class="control-group" style="display:none">
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
			<input id="btnSubmit1" class="btn btn-primary" type="submit" value="提 交"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>