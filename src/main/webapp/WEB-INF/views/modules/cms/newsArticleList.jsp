<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>选择图文消息</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/common/resmsg.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript">
	var _articles = [];
	

	// 初始化文章类型
	function selectNews(newsId){
		if (top.mainFrame.cmsMainFrame){
			top.mainFrame.cmsMainFrame._selectedNews = newsId;
			top.mainFrame.cmsMainFrame._selectedPic = $("#article"+newsId + " .preview_pic").attr("src");
		}else{
			top.mainFrame._selectedNews = newsId;
			top.mainFrame._selectedPic = $("#article"+newsId + " .preview_pic").attr("src");
		}
		top_selectedNews = newsId;
		$(".cover_appmsg_item .appmsg_check_mask").removeClass("appmsg_checked_mask");
		$("#article" + newsId + " .appmsg_check_mask" ).addClass("appmsg_checked_mask");
		
	}
		$(document).ready(function() {
			// 初始化图文消息
			for(var i=0; i<_articles.length; i++)
			{
				var art = _articles[i];
				$("#"+art.id + " .article_title").html(art.desJson.title);
				$("#"+art.id + " .preview_pic").attr("src",art.image);
				$("#"+art.id + " .article_content").html(art.desJson.desc);
				
				// 如果是多图文消息，显示缩略图
				var titems = art.desJson.item;
				if(titems && titems.length > 0)
				{
					for(var k=0; k<titems.length; k++)
					{
						
						var titem = titems[k];
						$("#"+art.id).parent().append('<div id="' + art.id +"_" + k + '" class="appmsg_item">'
								+ '<img  class="js_appmsg_thumb appmsg_thumb" style="display:block" src="' + titem.localpicurl + '">'
								+ '<h4 id="itemTitle' + k + '">'+titem.title+'</h4>'
								
								+ '</div>'
						);
					}
				}
			}
			// 初始化文章类型
	//		$("#msgType").val(${article.msgType});
			$("input[name=id]").each(function(){
				var articleSelect = null;
				if (top.mainFrame.cmsMainFrame){
					articleSelect = top.mainFrame.cmsMainFrame.articleSelect;
				}else{
					articleSelect = top.mainFrame.articleSelect;
				}
				for (var i=0; i<articleSelect.length; i++){
					if (articleSelect[i][0]==$(this).val()){
						this.checked = true;
					}
				}
				$(this).click(function(){
					var id = $(this).val(), title = $(this).attr("title");
					if (top.mainFrame.cmsMainFrame){
						top.mainFrame.cmsMainFrame.articleSelectAddOrDel(id, title);
					}else{
						top.mainFrame.articleSelectAddOrDel(id, title);
					}
				});
			});
		});
		function view(href){
			top.$.jBox.open('iframe:'+href,'查看文章',$(top.document).width()-220,$(top.document).height()-120,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
				}
			});
			return false;
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<div class="container-fluid">
	<form:form id="searchForm" modelAttribute="article" action="${ctx}/cms/article/listNews" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>图文消息类型：</label><form:select class="span3" id="msgType" style="margin-right:15px;" value="${article.msgType}" path="msgType">
                    <form:option value="1">单图文消息</form:option>
                    <form:option value="2">多图文消息</form:option>
                </form:select>&nbsp;
					        
		<label>栏目：</label><sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
					title="栏目" url="/cms/category/treeData" module="article"/>
		<label>标题：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
	</form:form>
	<div class="row-fluid">
		<c:forEach items="${page.list}" var="article">
			<script type="text/javascript">
				_articles.push({
					id : "article${article.id}",
					desJson: ${article.desJson},
					image: "${article.image}"
				});
			</script>
			<div class="span4 ">
			<div class="appmsg multi editing">
				<div id="previewMain" class="appmsg_content" >
					<div class="js_appmsg_item"  id="article${article.id}" >
				        <div class="appmsg_info">
				            <em class="appmsg_date"></em>
				        </div>
				        <div class="cover_appmsg_item">
				            <h4 class="appmsg_title "><a id="preview_title00" class="article_title"></a></h4>
				            <div class="appmsg_thumb_wrp">
				                <img id="preview_pic" class="js_appmsg_thumb appmsg_thumb preview_pic" style="display:block;">
				            </div>
				            <div class="appmsg_check_mask">
							<a class="icon-check" href="javascript:selectNews('${article.id}');"></a>
							</div>
				        </div>
					</div>
					<!-- 
		           	<div id="item1" class="appmsg_item" id="article${article.id}">
					    <img id="itemImg1" class="js_appmsg_thumb appmsg_thumb preview_pic">
					    <i id="itemImg_defalut1" class="appmsg_thumb default">缩略图</i>
					    <h4 id="itemTitle1 article_title"></h4>
					</div> -->
				</div>
		    </div>
		    </div>
		    <!-- 
			<div class="span5" id="article${article.id}">
				<div class="thumbnail" style="padding-left:10px;padding-right:10px;">
		           	<p class="article_title" id="preview_title"></p>
		            <img id="preview_pic" class="preview_pic" style="display: block; width: 360px;height: 200px">
		           	<p class="article_content" id="preview_desc"></p>
	      		</div>
		   	</div>
		   	 -->
		</c:forEach>
	</div>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>