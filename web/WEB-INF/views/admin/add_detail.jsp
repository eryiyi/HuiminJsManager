<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<div class="row">
    <div id="breadcrumb" class="col-xs-12">
        <a href="#" class="show-sidebar">
            <i class="fa fa-bars"></i>
        </a>
        <ol class="breadcrumb pull-left">
            <li><a href="javascript:void(0)" onclick="toPage('mainPage','')">主页</a></li>
            <li><a href="javascript:void (0);">管理员</a></li>
            <li><a href="javascript:void (0);">添加管理员-会员详情</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>添加管理员-会员详情</span>
                </div>
            </div>
            <div class="box-content">
                <%--<h4 class="page-header">会员详情</h4>--%>
                <form class="form-horizontal" role="form">
                    <input type="hidden" value="${empVO.mm_emp_id}" id="mm_manager_id">
                    <input type="hidden" value="${empVO.mm_emp_password}" id="mm_manager_password">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户名</label>

                        <div class="col-sm-4">
                            <input type="text" id="mm_manager_nickname" readonly="true" class="form-control"
                                   value="${empVO.mm_emp_nickname}" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户手机号</label>

                        <div class="col-sm-4">
                            <input type="text" id="mm_manager_mobile" readonly="true" class="form-control"
                                   value="${empVO.mm_emp_mobile}" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-2 control-label">公司名称</label>

                        <div class="col-sm-4">
                            <input type="text" id="mm_emp_company" readonly="true" class="form-control"
                                   value="${empVO.mm_emp_company}" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">注册日期</label>

                        <div class="col-sm-4">
                            <input type="text" id="mm_emp_regtime" readonly="true" class="form-control"
                                   value="${empVO.mm_emp_regtime}">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-2 control-label">所属省份</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="mm_emp_provinceId">
                                    <option value="${empVO.mm_emp_provinceId}">${empVO.provinceName}</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">所属城市</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="mm_emp_cityId">
                                <option value="${empVO.mm_emp_cityId}">${empVO.cityName}</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">所属县区</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="mm_emp_countryId">
                                <option value="${empVO.mm_emp_countryId}">${empVO.areaName}</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">管理员类型</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="mm_manager_type">
                                <c:forEach items="${managerTypes}" var="e" varStatus="st">
                                    <option value="${e.typeId}">${e.typeName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">选择角色</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="permissions">
                                <c:forEach items="${roles}" var="e" varStatus="st">
                                    <option value="${e.id}">${e.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="saveRole('${empVO.mm_emp_id}')">添加
                            </button>
                            <button type="button" class="btn btn-primary" onclick="javascript :history.back(-1)">返回
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    function saveRole(mm_emp_id) {

        var mm_manager_id = $("#mm_manager_id").val();
        var mm_manager_mobile = $("#mm_manager_mobile").val();
        var mm_manager_nickname = $("#mm_manager_nickname").val();
        var mm_manager_password = $("#mm_manager_password").val();
        var mm_manager_type = $("#mm_manager_type").val();
        var mm_manager_area_uuid;
        if (mm_manager_type == '0') {
            mm_manager_area_uuid = '';
        }
        if (mm_manager_type == '1') {
            mm_manager_area_uuid = $("#mm_emp_countryId").val();
        }
        if (mm_manager_type == '2') {
            mm_manager_area_uuid = $("#mm_emp_cityId").val();
        }
        if (mm_manager_type == '3') {
            mm_manager_area_uuid = $("#mm_emp_provinceId").val();
        }
        if (mm_manager_type == '4') {
            mm_manager_area_uuid = '';
        }
        var mm_manager_is_use = '1';
        var permissions = $("#permissions").val();

        if (mm_manager_type.replace(/\s/g, '') == '') {
            alert("请选择管理员类型");
            return;
        }
        if (mm_manager_area_uuid.replace(/\s/g, '') == '') {
            alert("请选择要管理的区域");
            return;
        }
        if (permissions.replace(/\s/g, '') == '') {
            alert("请选择角色");
            return;
        }
        $.ajax({
            cache: true,
            type: "POST",
            url: "/admin/addAdmin.do",
            data: {
                "mm_manager_id": mm_manager_id,
                "mm_manager_mobile": mm_manager_mobile,
                "mm_manager_nickname": mm_manager_nickname,
                "mm_manager_password": mm_manager_password,
                "mm_manager_type": mm_manager_type,
                "mm_manager_area_uuid": mm_manager_area_uuid,
                "mm_manager_is_use": mm_manager_is_use,
                "permissions": permissions
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("添加成功");
                    window.location.href = "#module=/admin/list&page=1"+ "&_t=" + new Date().getTime();
                } else {
                    var _case = {1: "添加失败,该用户如果已经是管理员，不能重复设置"};
                    alert(_case[data.code])
                }
            }
        });
    }
    ;

    function selectCitys() {
        var province = $("#mm_emp_provinceId").val();
        $.ajax({
            cache: true,
            type: "POST",
            url: "/getAllCitys.do",
            data: {
                "father": province
            },
            async: false,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    <%--var citys = ${listCitysAll};--%>
                    var citys = data.data;
                    var ret = "<option value=''>" + '请选择城市' + "</option>";
                    for (var i = citys.length - 1; i >= 0; i--) {
                        if (citys[i].father == province) {
                            ret += "<option value='" + citys[i].cityID + "'>" + citys[i].city + "</option>";
                        }
                    }
                    $("#mm_emp_cityId").html(ret);
                } else {
                    var _case = {1: "获取数据失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
    ;

    function selectCountrys() {
        var city = $("#mm_emp_cityId").val();
        $.ajax({
            cache: true,
            type: "POST",
            url: "/getAllCountrys.do",
            data: {
                "father": city
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    var countrys = data.data;
                    var ret = "<option value=''>" + '请选择县区' + "</option>";
                    for (var i = countrys.length - 1; i >= 0; i--) {
                        if (countrys[i].father == city) {
                            ret += "<option value='" + countrys[i].areaID + "'>" + countrys[i].area + "</option>";
                        }
                    }
                    $("#mm_emp_countryId").html(ret);
                } else {
                    var _case = {1: "获取数据失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
    ;


</script>


