<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>客服管理</title>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta name="author" content="http://www.yitong.com.cn"/>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/angular/angular.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/angular/angular-resource.js" type="text/javascript"></script>
    <script src="${ctxStatic}/angular/angular-route.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value:'default'}/bootstrap.min.css"
          type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/common/common.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/qunfa/common.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/common/jeesite.min.js" type="text/javascript"></script>
    <script type="text/javascript">include('ckeditor_lib', '${ctxStatic}/ckeditor/', ['ckeditor.js']);</script>
    <script type="text/javascript">
        var contentCkeditor,ckfinderAPI;

        var app = angular.module('weixin', ['ngResource', 'ngRoute'])
                .controller("MessageCtrl", function ($scope, $http) {
                    Array.prototype.indexOf = function (val) {
                        for (var i = 0; i < this.length; i++) {
                            if (this[i] == val) return i;
                        }
                        return -1;
                    };
                    Array.prototype.remove = function (val) {
                        var index = this.indexOf(val);
                        if (index > -1) {
                            this.splice(index, 1);
                        }
                    };
                    Array.prototype.set = function (oldValue, newValue) {
                        var index = this.indexOf(oldValue);
                        if (index > -1) {
                            this.splice(index, 1, newValue);
                        }
                    };

                    var  tmp_article= {};
                    $scope.msgtype = "news";//news | text
                    $scope.allType = 0;//群发规则 0 全部 1 分组
                    /*$scope.groups = [
                        { id: 0, name: '未分组' },
                        { id: 1, name: '未绑定' },
                        { id: 2, name: '已绑定' },
                        { id: 3, name: 'VIP' }
                    ];*/
                    $scope.groups = ${groupJson};
                    $scope.articles = [
                        {
                            "title": "",
                            "description": "",
                            "url": "",
                            "picurl": "",
                            "edit": true
                        },
                        {
                            "title": "",
                            "description": "",
                            "url": "",
                            "picurl": "",
                            "edit": false
                        }
                    ];
                    $scope.content = "";
                    $scope.index=0;
                    $scope.addArticle = function () {
                        $scope.updateList();
                        $scope.articles.push({
                            "title": "",
                            "description": "",
                            "url": "",
                            "picurl": "",
                            "edit": true
                        });
                        $scope.index=$scope.articles.length-1;
                    }
                    $scope.removeArticle = function (article) {
                        $scope.articles.remove(article);
                        $scope.index=$scope.articles.length-1;
                    }

                    $scope.changeEdit=function(article){
                        $scope.updateList();
                        $scope.index=  $scope.articles.indexOf(article);
                        tmp_article=article;//缓存当前编辑
                        article.edit= true;
                        $scope.preview(article.picurl);
                    }
                    $scope.updateList = function () {
                       // $scope.articles[$scope.index].description= contentCkeditor.document.getBody().getHtml();
                        angular.forEach($scope.articles, function (v) {
                                v.edit = false;
                        });
                        //contentCkeditor.destroy();
                    }



                    $scope.imageFinderOpen=function(article){
                        tmp_article=article;
                        var date = new Date(), year = date.getFullYear(), month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1);
                        var url = "${ctxStatic}/ckfinder/ckfinder.html?type=images&start=images:/cms/article/"+year+"/"+month+
                                "/&action=js&func=imageSelectAction&thumbFunc=imageThumbSelectAction&cb=imageCallback&dts=0&sm=0";
                        windowOpen(url,"文件管理",1000,700);
                    }

                    $scope.deleteImage=function(article) {
                        article.picurl="";
                        $('.imagePreview').empty();
                    }

                    $scope.imageSelectAction=function(fileUrl, data, allFiles){
                        var url="", files=ckfinderAPI.getSelectedFiles();
                        for(var i=0; i<files.length; i++){//
                            url += files[i].getUrl();//
                            if (i<files.length-1) url+="|";
                        }//
                        var index = $scope.articles.indexOf(tmp_article);
                        $scope.articles[index].picurl=url;
                        $scope.preview(url);
                    }

                    $scope.preview=function(url){
                        $('.imagePreview').html('<img class="thubnail_img" src="'+url+'" style="max-width:200px;max-height:200px;_height:200px;border:0;padding:3px;">');
                    }
                    $scope.sendMsg = function(){
                    	// 群发消息
                    	// 判断发消息类型
                    	// 0 1 分组
                    	allType
                    	// 分组
						
						var params = {
                    			msgType : $scope.msgtype,
                    			groupId : $scope.allType == 0 ? -1 : $scope.group_id,
                    			cotent : $scope.msgtype == "text" ? $scope.content : angular.fromJson(angular.toJson($scope.articles))
                    	}
						// 1 分组还是全部用户
						
						// 2 回复类型
						
						// 3 取内容
						
                        $http.post('${ctx}/menu/save', params).
                                success(function (data) {
                                    if (data.result == "error") {
                                        message(data.message, '', 'error');
                                    } else {
                                        message("保存成功", '', 'success');
                                        setTimeout(function () {
                                            location.reload()
                                        }, 1000);
                                    }
                                }).
                                error(function (data, status, headers, config) {
                                    message("保存出错", '', 'error');
                                });
                    	//}
                    }

                    $scope.$watch("msgtype",function(newValue){
                        if(newValue=="text"){
                            //contentCkeditor.destroy();
                            $scope.articles = [
                                {
                                    "title": "",
                                    "description": "",
                                    "url": "",
                                    "picurl": "",
                                    "edit": true
                                }
                            ];
                        } else{
                             $scope.content="";
                        }
                    })

                });

       /* app.directive('ckeditor', function () {
            return {
                restrict: "AE",
                scope: {},
                template: '',
                link: function (scope, element, attrs) {
                    scope.$watch(attrs.id, function () {
                        contentCkeditor = CKEDITOR.replace(attrs.id);
                        contentCkeditor.config.height = "";//
                        contentCkeditor.config.ckfinderPath = "${ctxStatic}/ckfinder";
                        var date = new Date(), year = date.getFullYear(), month = (date.getMonth() + 1) > 9 ? date.getMonth() + 1 : "0" + (date.getMonth() + 1);
                        contentCkeditor.config.ckfinderUploadPath = "/cms/article/" + year + "/" + month + "/";
                    });

                }
            }

        });*/


        function imageSelectAction(fileUrl, data, allFiles){
            $("#MessageCtrl").scope().imageSelectAction(fileUrl, data, allFiles);
        }
        function imageThumbSelectAction(fileUrl, data, allFiles){
            var url="", files=ckfinderAPI.getSelectedFiles();
            for(var i=0; i<files.length; i++){
                url += files[i].getThumbnailUrl();
                if (i<files.length-1) url+="|";
            }
        }
        function imageCallback(api){
            ckfinderAPI = api;
        }

    </script>
</head>
<body ng-app="weixin">
<ul class="nav nav-tabs" id="myTab">
    <li class=""><a href="${ctx}/wxMsg/list">消息列表</a></li>
    <li class="active"><a href="javascript:void();">群发新消息</a></li>
</ul>

<div ng-controller="MessageCtrl" id="MessageCtrl">
    <table class="tb">
        <tbody>
        <tr>
            <th style="width: 89px;"><label>群发对象</label></th>
            <td>
                <select class="span3" style="margin-right:15px;" ng-model="allType">
                    <option value="0">全部用户</option>
                    <option value="1">按分组选择</option>
                </select>
                <select class="span3" ng-if="allType==1" ng-model="group_id">
                    <option ng-repeat="group in groups" ng-value="group.id">{{group.name}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <th><label>回复类型</label></th>
            <td>
                <select class="span6" ng-model="msgtype">
                    <option value="text">
                        基本文字回复
                    </option>
                    <option value="news">
                        基本混合图文回复
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <th><label>回复内容</label></th>
            <td ng-if="msgtype== 'news'">
                <div class="alert alert-block alert-new " id="basic-item" style="width: 660px">
                    <h4 class="alert-heading">添加回复内容</h4>
                    <div ng-repeat="article in articles">
                    <hr>
                        <div class="item" ng-if="article.edit">
                            <table >
                                <tbody>
                                <tr>
                                    <td><label for="">标题</label></td>
                                    <td>
                                        <input type="text" ng-model="article.title" class="input-xlarge" required/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 80px"><label>选择图片</label></td>
                                    <td>
                                        <a href="javascript:" ng-click="imageFinderOpen(article);" class="btn">选择</a>&nbsp;<a href="javascript:" ng-click="deleteImage(article);" class="btn">清除</a>
                                        <br/>
                                        <div class="imagePreview">
                                            <img ng-src="{{article.picurl}}" style="max-width:200px;max-height:200px;_height:200px;border:0;padding:3px;">
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="content{{articles.length}}">描述</label></td>
                                    <td>
                                        <textarea  id="content{{articles.length}}" maxlength="200"
                                                  ng-model="article.description" style="width: 475px;"
                                                  rows="4"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="">跳转地址</label></td>
                                    <td><input type="text" ng-model="article.url" class="input-xlarge" required/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="item" ng-if="article.edit == false">
                            <div id="show" class="alert alert-info reply-news-list  hide reply-news-list-first" style="display: block;width: 615px;padding-right: 12px;">
                                <div class="reply-news-list-detail">
                                	<div class="reply-news-list-cover"><img ng-src="{{article.picurl}}" alt=""></div>
                                    <div class="title">{{article.title}}</div>
                                    <%--<div class="content">{{article.description}}</div>--%>
                                    <span class="pull-right">
                                        <a ng-click="removeArticle(article)" href="javascript:;" style="margin-right:5px;">删除</a>
                                        <a ng-click="changeEdit(article)" href="javascript:;">编辑</a>
                                    </span>
                                </div>
                            </div>
                            <hr>
                        </div>
                    </div>
                   <div class="reply-news-edit-button"><a href="javascript:;" ng-click="addArticle()" class="btn"><i class="icon-plus"></i> 添加多条内容</a></div>
                </div>
            </td>
            <td ng-if="msgtype== 'text'">
                <textarea style="height:200px;width: 460px;" class="basic-content-new"
                          cols="70" autocomplete="off" ng-model="content"></textarea>
            </td>
        </tr>
        <tr>
            <th></th>
            <td>
                <button type="submit" class="btn btn-primary span3" name="submit" value="提交" onclick="sendMsg();" style="margin-left: 0px">发送</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>