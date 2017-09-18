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
            <li><a href="javascript:void(0)">群组管理</a></li>
            <li><a href="javascript:void(0)">添加管理员</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>添加管理员</span>
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
                <form class="form-horizontal" role="form">
                    <input type="hidden" id="groupid" name="groupid" value="${groupid}">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">手机号</label>
                        <div class="col-sm-4">
                            <input type="text" id="title" class="form-control"  placeholder="输入管理员手机号"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                        <%--<div class="col-sm-4">--%>
                            <%--<textarea id="title" cols="10" rows="10" class="form-control"--%>
                                      <%--data-toggle="tooltip" data-placement="bottom" title="Tooltip for name"></textarea>--%>
                        <%--</div>--%>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="saveP()">添加</button>
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
        var groupid = $("#groupid").val();
        var title = $("#title").val();

        if (title.replace(/\s/g, '') == '') {
            alert("请输入管理员手机号！");
            return;
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/groups/addGroupManager.do",
            data: {"mobile": title,"groupid": groupid},
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("添加群组管理员成功");
                    window.location.href = "#module=groups/listGroupManagers&id="+groupid+ "&_t=" + new Date().getTime();
                } else {
                    alert("添加群组管理员失败，请检查")
                }
            }
        });
    }

</script>