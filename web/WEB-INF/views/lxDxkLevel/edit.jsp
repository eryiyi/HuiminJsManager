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
            <li><a href="javascript:void(0)">定向卡等级管理</a></li>
            <li><a href="javascript:void(0)">定向卡等级更新</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>定向卡等级更新</span>
                </div>
                <div class="box-icons">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                    <a class="expand-link">
                        <i class="fa fa-expand"></i>
                    </a>
                    <a class="close-link">
                        <i class="fa fa-times"></i>
                    </a>
                </div>
                <div class="no-move"></div>
            </div>
            <div class="box-content">
                <h4 class="page-header">定向卡等级更新</h4>

                <form class="form-horizontal" role="form">
                    <input type="hidden" value="${adObj.lx_dxk_level_id}" id="lx_dxk_level_id">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">等级</label>

                        <div class="col-sm-4">
                            <input type="text" id="lx_dxk_name" value="${adObj.lx_dxk_name}" class="form-control"
                                   placeholder="等级" data-toggle="tooltip" data-placement="bottom" readonly="true"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-2 control-label">昵称</label>--%>

                        <%--<div class="col-sm-4">--%>
                            <%--<input type="text" id="lx_dxk_nick" value="${adObj.lx_dxk_nick}" class="form-control"--%>
                                   <%--placeholder="昵称" data-toggle="tooltip" data-placement="bottom"--%>
                                   <%--title="Tooltip for name">--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">返利百分比</label>

                        <div class="col-sm-4">
                            <input type="text" id="lx_dxk_rate" value="${adObj.lx_dxk_rate}" class="form-control"
                                   placeholder="返利百分比" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="saveP()">更新</button>
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
    function saveP() {
        var lx_dxk_level_id = $("#lx_dxk_level_id").val();
//        var lx_dxk_nick = $("#lx_dxk_nick").val();
        var lx_dxk_rate = $("#lx_dxk_rate").val();

        $.ajax({
            cache: true,
            type: "POST",
            url: "/lxDxkLevelController/edit.do",
            data: {
                "lx_dxk_level_id": lx_dxk_level_id,
//                "lx_dxk_nick": lx_dxk_nick,
                "lx_dxk_rate": lx_dxk_rate
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("执行成功");
                    window.location.href = "#module=lxDxkLevelController/list"+ "&_t="+ new Date().getTime();
                } else {
                    alert("执行失败，请检查")
                }
            }
        });
    }
</script>
