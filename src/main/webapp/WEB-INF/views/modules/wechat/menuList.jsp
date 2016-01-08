<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html xmlns:ng="https://angularjs.org" ng-app="weixin" id="ng-app">
<head>
<title>自定义菜单管理</title>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta name="author" content="http://www.yitong.com.cn"/>
<script src="${ctxStatic}/angular/json2.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/common.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/angular/angular.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/angular/sortable.js" type="text/javascript"></script>
<script src="${ctxStatic}/angular/angular-resource.js" type="text/javascript"></script>
<script src="${ctxStatic}/angular/angular-route.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value:'default'}/bootstrap.min.css"
      type="text/css" rel="stylesheet"/>
<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/menu/menu.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
angular.module('menuFilters', []).filter('count', function () {
    return function (menus) {
        var count = 0;
        $.each(menus, function (i, v) {
            if (v.isHide == false) {
                count++;
            }
        });
        return count;
    };
});

var app = angular.module('weixin', ['ui.sortable', 'ngResource', 'ngRoute', 'menuFilters']).factory("Menu", function ($resource) {
            return $resource('${ctxStatic}/menu/:menuId.json', {}, {
                queryAll: {method: "GET", params: {id: 'menu'}, isArray: true},
                update: {method: "POST", params: {id: 'menu'}, isArray: true}
            });
        }).controller("MenuCtrl", function ($scope, Menu, $http) {
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

            $("#modal-message").modal("hide").on('hidden', function () {
                $scope.saveMenuAction();
            });

            $("#viewList").on("click", ".jsView", function () {
                var $sub = $(this).next(".sub_pre_menu_box");
                $(".sub_pre_menu_box").not($sub).hide();
                $sub.toggle();
            });

            $scope.menus = ${menuJson};
            //console.log($scope.menus);
            //$scope.menus = Menu.queryAll();

            $scope.menu_cache = {}; //操作单独menu
            $scope.submenu_cache={};
            $scope.del_ids = [];//记录删除的menu的id

            $scope.menuSortableOptions = {
                handle: '.icon-move'
            };
            $scope.subMenuSortableOptions = {
                handle: '.icon-move'
            };


            $scope.addMenu = function () {
                var $length = $scope.menus.length;
                if ($length >= 3) {
                    message('主菜单不能超过3个.', '', 'error');
                    return;
                }
                $scope.menus.push({id: 0, name: '', url: '', code: '', subMenu: [], pid: 0, isHide: 0, type: 2, message: ""});
            };

            $scope.setSubMenu = function (menu) {
                var $length = menu.subMenu.length;
                if ($length >= 5) {
                    message('子菜单不能超过5个.', '', 'error');
                    return;
                }
                if(menu.name=="" || menu.name== undefined){
                    message('当前菜单名称不能为空.', '', 'error');
                    return;
                }
                $scope.menu_cache = menu;
                $scope.submenu_cache={};
                $("#modal-message").modal('show');
                //menu.subMenu.push({id: 0, name: '', url: '', modules: [], code: '', pid: menu.id, isHide:false,type:1,message:""});
            }

            $scope.addSubMenu = function () {
                if ($(".submenu.ng-invalid").length > 0) {
                	message('名称与规则编码不能为空', '', 'error');
                    return;
                }
                $http.post('${ctx}/wechat/menu/check', {code: $scope.submenu_cache.code}).
                        success(function (data) {
                            if (data.result == "error") {
                                message(data.message, '', 'error');
                                return;
                            } else {
                            	var id = $scope.menu_cache.id;
                                $.each($scope.menus, function (i, v) {
                                    if (v.id == id) {
                                        v = $scope.menu_cache;
                                        v.subMenu.push({id: 0, name:  $scope.submenu_cache.name, url: '', modules: [], code: $scope.submenu_cache.code, pid: id, isHide:0,type:0,message:""});
                                        return v;
                                    }
                                });
                                $("#modal-message").modal('hide');
                                //$scope.saveMenu();
                            }
                        }).
                        error(function (data, status, headers, config) {
                            message("检验出错", '', 'error');
                            return;
                        });
            }

          	/*$scope.addModule = function () {
                $scope.menu_cache.modules.push({id: 0, name: '', code: ''});
            }

            $scope.deleteModule = function (module) {
                $scope.menu_cache.modules.remove(module);
            }

            $scope.changeSubType = function (type) {
                $scope.menu_cache.type = type;
            }*/
            $scope.deleteMenu = function (menu) {
                if (menu.id != 0) {
                    $scope.del_ids.push(menu.id);
                }
                $scope.menus.remove(menu);
            }

            $scope.deleteSubMenu = function (menu, subMenu) {
                if (subMenu.id != 0) {
                    $scope.del_ids.push(subMenu.id);
                }
                menu.subMenu.remove(subMenu);
            }

            /*$scope.setMenuAction = function (menu) {
             $scope.menu_cache = menu;
             $("#modal-message").modal('show');
             if(menu.type==1){
             $('#myTab a:first').tab('show');
             }else{
             $('#myTab a:eq(1)').tab('show');
             }

             }*/

            $scope.saveMenuAction = function () {
                var id = $scope.menu_cache.id;
                $.each($scope.menus, function (i, v) {
                    if (v.id == id) {
                        v = $scope.menu_cache;
                        return v;
                    }
                    $.each(v.subMenu, function (i1, v1) {
                        if (v1.id == id) {
                            v1 = $scope.menu_cache;
                            return v1;
                        }
                    });
                });
            }

            $scope.loadRules = function (e) {
                //console.log("load rules");

            }

            $scope.selectRule = function (mod, rid) {
                //console.log(rid + mod);
            }

            $scope.publishMenu = function () {
				 $http.get('${ctx}/wechat/menu/publish').success(function (data) {
	                    if (data.result == "error") {
	                        message(data.message, '', 'error');
	                    }else{
	                    	if (data.errcode != 0) {
	                               message(data.errmsg, '', 'error');
	                           } else {
	                               message("发布菜单成功", '', 'success');
	                               setTimeout(function () {
	                                   location.reload()
	                               }, 1000);
	                           }
	                    }
                  }).error(function () {
                      message("发布菜单出错", '', 'error');
                  });
            }

            $scope.saveMenu = function () {
				var menuJson = angular.fromJson(angular.toJson($scope.menus));
				console.log($scope.del_ids);
                $http.post('${ctx}/wechat/menu/save', {menuJson: menuJson, ids: $scope.del_ids}).success(function (data){
                	if (data.result == "error") {
                        message(data.message, '', 'error');
                    } else {
                        message("保存菜单成功", '', 'success');
                        setTimeout(function () {
                            location.reload();
                        }, 1000);
                    }
                }).error(function (data, status, headers, config) {
                    message("保存菜单出错", '', 'error');
                });
			}
        });
</script>
</head>
<body style="overflow-x: auto;width: 997px;">
<div ng-controller="MenuCtrl">
    <div class="mobile_preview" id="mobileDiv">
        <div class="mobile_preview_hd"><strong class="nickname">微信银行</strong></div>
        <div class="mobile_preview_bd">
            <ul id="viewShow" class="show_list"></ul>
        </div>
        <div class="mobile_preview_ft">
            <ul class="pre_menu_list" id="viewList">
                <li class="pre_menu_item with{{ menus | count }} jsViewLi" ng-repeat="menu in menus">
                    <div ng-if="menu.isHide==0">
                        <a href="javascript:void(0);" class="jsView">{{ menu.name }}</a>

                        <div ng-if="menu.subMenu | count " class="sub_pre_menu_box jsSubViewDiv" style="display: none;">
                            <ul class="sub_pre_menu_list">
                                <li ng-repeat="subMenu in menu.subMenu">
                                    <div ng-if="subMenu.isHide==0">
                                        <a href="javascript:void(0);" class="jsSubView">{{ subMenu.name }}</a>
                                    </div>
                                </li>
                            </ul>
                            <i class="arrow arrow_out"></i>
                            <i class="arrow arrow_in"></i>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div class="menu-left">
        <div class="form form-horizontal">
            <h4>菜单设计器
                <small>编辑和设置微信公众号码, 必须是服务号才能编辑自定义菜单。</small>
            </h4>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>
                        <span style="margin-right: 20px;">隐藏</span>
                        <span style="margin-left: 40px;">名称</span>
                        <span style="margin-left: 220px;">编码(非中文)</span>
                        <span style="margin-left: 150px;">操作</span>
                    </th>
                </tr>
                </thead>
                <tbody ui-sortable="menuSortableOptions" ng-model="menus" class="mlist">
                <tr ng-repeat="menu in menus" class="hover">
                    <td>
                        <div>
                            <input type="checkbox" ng-model="menu.isHide"/>
                            <input type="text" class="span4" ng-model="menu.name" maxlength="4" required
                                   placeholder="名称"> &nbsp; &nbsp;
                            <input type="text" class="span3" ng-model="menu.code" placeholder="初始内容" readonly="readonly"> &nbsp; &nbsp;
                            <a href="javascript:;" class="icon-move" title="拖动调整此菜单位置"></a> &nbsp;
                            <a href="javascript:;" ng-click="deleteMenu(menu)" class="icon-remove-sign"
                               title="删除此菜单"></a> &nbsp;
                            <a href="javascript:;" ng-click="setSubMenu(menu);" title="添加子菜单"
                               class="icon-plus-sign"></a>
                        </div>
                        <div ui-sortable="subMenuSortableOptions" ng-model="menu.subMenu">
                            <div class="sub_menu_li" ng-repeat="subMenu in menu.subMenu">
                                <input type="checkbox" ng-model="subMenu.isHide"/>
                                <input type="text" class="span3" ng-model="subMenu.name" maxlength="8" required
                                       placeholder="名称"> &nbsp; &nbsp;
                                <input type="text" class="span3" ng-model="subMenu.code" placeholder="初始内容" readonly="readonly"> &nbsp;
                                &nbsp;
                                <a href="javascript:;" class="icon-move" title="拖动调整此菜单位置"></a> &nbsp;
                                <a href="javascript:;" ng-click="deleteSubMenu(menu,subMenu)" class="icon-remove-sign"
                                   title="删除此菜单"></a>
                            </div>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="well well-small" style="margin-top:20px;">
                <a href="javascript:;" ng-click="addMenu()">添加菜单 <i class="icon-plus-sign" title="添加菜单"></i></a> &nbsp;
                &nbsp; &nbsp; <span class="help-inline">可以使用 <i class="icon-move"></i> 进行拖动排序</span> &nbsp;
                &nbsp; &nbsp; <span class="help-inline">可以使用勾选选择框进行隐藏</span>
            </div>
			<h4>操作
                <small>设计好菜单后再进行保存操作</small>
            </h4>
            <table class="table" id="query">
                <tbody>
                <tr>
                    <td>
                        <input id="btnSubmit1" type="button" ng-click="saveMenu()" value="保存菜单结构" class="btn btn-primary span3">
                        <span class="help-block">保存当前菜单结构到数据库</span>
                    </td>
                    <td>
                        <input id="btnSubmit4" type="button" ng-click="publishMenu()" value="发布菜单结构" class="btn btn-primary span3">
                        <span class="help-block">发送至公众平台, 由于缓存可能需要在24小时内生效</span>
                    </td>
                </tr>
                </tbody>
            </table>            
        </div>
    </div>
    <div id="modal-message" class="modal hide fade out" tabindex="-1" role="dialog" aria-hidden="false"
         style="width: 900px; margin-left: -450px;">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">添加子菜单</h3></div>
        <div class="modal-body" style="max-height: 700px;">
            <div class="form-horizontal">
                <div class="control-group">
                    <label class="control-label">名字</label>

                    <div class="controls">
                        <input class="submenu" type="text" ng-model="submenu_cache.name" required maxlength="8"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">规则编码</label>

                    <div class="controls">
                        <input class="submenu" type="text" ng-model="submenu_cache.code" required/>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn" ng-click="addSubMenu()">添加</button>
        </div>
    </div>
</div>
</body>
</html>