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
            <li><a href="javascript:void (0);">管理员详情</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>管理员详情</span>
                </div>
            </div>
            <div class="box-content">
                <h4 class="page-header">管理员</h4>

                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">账号</label>

                        <div class="col-sm-4">
                            <input type="text" readonly="true" id="manager_admin" name="manager_admin"
                                   class="form-control" value="${admin.manager_admin}" data-toggle="tooltip"
                                   data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">密码</label>

                        <div class="col-sm-4">
                            <input type="text" id="manager_pass" name="manager_pass" class="form-control"
                                   placeholder="新密码" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">角色</label>

                        <div class="col-lg-8">

                            <c:if test="${roleRname != null}">
                                <div id="rname">${roleRname}</div>
                            </c:if>
                            <c:if test="${role != null}">
                                <div id="rname">${role.rname}</div>
                            </c:if>

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>

                        <div class="col-lg-8">
                            <c:if test="${admin.is_use=='0'}">
                                <div id="status">启用</div>
                            </c:if>
                            <c:if test="${admin.is_use=='1'}">
                                <div id="status">禁用</div>
                            </c:if>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="updatePwr('${admin.manager_id}')">
                                密码修改
                            </button>
                            <button type="button" class="btn btn-primary"
                                    onclick="manageEmp('${admin.manager_id}','0')">启用
                            </button>
                            <button type="button" class="btn btn-primary"
                                    onclick="manageEmp('${admin.manager_id}','1')">禁用
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
    function updatePwr(manager_id) {
        var manager_pass = $("#manager_pass").val();
        var manager_admin = $("#manager_admin").val();
        if (manager_pass.replace(/\s/g, '') == '') {
            alert("密码不能为空");
            return;
        }
        if (manager_pass.length < 6 || manager_pass.length > 18) {
            alert("密码长度至少6位,最多18位");
            return;
        }
        manager_pass = hex_md5(manager_pass);
        $.ajax({
            cache: true,
            type: "POST",
            url: "/changePass.do",
            data: {
                "manager_pass": manager_pass,
                "manager_id": manager_id,
                "manager_admin": manager_admin
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("修改成功");
                    window.location.href = "#module=admin/list&page=1"+ "&_t=" + new Date().getTime();
                } else {
                    var _case = {1: "修改失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
    ;

    function manageEmp(_id, _type) {
        var manager_admin = $("#manager_admin").val();
        $.ajax({
            cache: true,
            type: "POST",
            url: "/admin/updateType.do",
            data: {"manager_id": _id, "is_use": _type, "manager_admin": manager_admin},
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("操作成功");
                    window.location.href = "#module=admin/list&page=1"+ "&_t=" + new Date().getTime();
                } else {
                    var _case = {1: "操作失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
    ;

</script>


