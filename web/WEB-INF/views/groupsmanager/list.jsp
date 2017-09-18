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
            <li><a href="javascript:void(0)">群管理员</a></li>
        </ol>
    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box ui-draggable ui-droppable">
            <div class="box-header">
                <div class="box-name ui-draggable-handle">
                    <i class="fa fa-table"></i>
                    <span>群管理员</span>
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
                <form action="" class="form">
                    <div class="form-group">
                        <div class="col-md-2 col-lg-2">
                            <button type="button" onclick="toAddGroupManager()"
                                    class="btn w12 form-control btn-block btn-danger btn-sm">添加管理员
                            </button>
                        </div>
                    </div>
                </form>
                <input type="hidden" id="groupid" name="groupid" value="${groupid}">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>群名称</th>
                        <th>管理员姓名</th>
                        <th>管理员头像</th>
                        <%--<th>操作</th>--%>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="e" varStatus="st">
                        <tr>
                            <td>${e.title}</td>
                            <td>${e.nickname}</td>
                            <td><img src="${e.cover}" style="width: 60px;height: 60px;"></td>
                            <%--<td>--%>
                                <%--<a class="btn btn-default btn-sm" href="javascript:void (0)"--%>
                                   <%--onclick="deletegroupManager('${e.emp_group_manager_id}','${e.groupid}')" role="button">删除</a>--%>
                            <%--</td>--%>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //群组管理员
    function deletegroupManager(_id,_groupid) {
        var groupid = $("#groupid").val();
        $.ajax({
            cache: true,
            type: "POST",
            url: "/groups/deleteGroupManagerById.do",
            data: {"emp_group_manager_id": _id,"groupid": _groupid},
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("删除群组管理员成功");
                    window.location.href = "#module=groups/listGroupManagers&id="+groupid+ "&_t=" + new Date().getTime();
                } else {
                    alert("删除群组管理员失败，请检查")
                }
            }
        });
    }

    function toAddGroupManager() {
        var groupid = $("#groupid").val();
        $.ajax({
            type: "GET",
            data: {"groupid": groupid},
            url: "/groups/toAddGroupsManager.do",
            success: function (response) {
                $("#content").html(response);
            }
        });
    }

</script>


