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
            <li><a href="javascript:void(0)">群组列表</a></li>
        </ol>
    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box ui-draggable ui-droppable">
            <div class="box-header">
                <div class="box-name ui-draggable-handle">
                    <i class="fa fa-table"></i>
                    <span>群组列表</span>
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

                <form class="form-inline">
                    <div class="form-group">
                        <div class="col-sm-4">
                            <input class="form-control" id="keywords" value="${query.keywords}" type="text"
                                   placeholder="群名称">
                        </div>
                    </div>
                    <div class="form-group">

                        <div class="col-md-2 col-lg-2">
                            <button type="button" onclick="saveSingle()"
                                    class="btn w12 form-control btn-block btn-danger btn-sm">非兴趣爱好群添加
                            </button>
                        </div>

                    </div>
                    <button type="submit" onclick="searchOrder('1')"
                            class="btn form-control btn-warning btn-sm btn-block">查找
                    </button>
                </form>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>图片</th>
                        <th>名称</th>
                        <th>详细介绍</th>
                        <th>是否使用</th>
                        <th>操作</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="e" varStatus="st">
                        <tr>
                            <td><img src="${e.pic}" style="width: 60px;height: 60px;"></td>
                            <td>${e.title}</td>
                            <td>${e.content}</td>
                            <td>
                                <c:if test="${e.is_use=='0'}">否</c:if>
                                <c:if test="${e.is_use=='1'}">是</c:if>
                            </td>
                            <td>
                                <a class="btn btn-default btn-sm" href="javascript:void (0)"
                                   onclick="editRole('${e.groupid}')" role="button">编辑</a>
                            </td>
                            <td>
                                <a class="btn btn-default btn-sm" href="javascript:void (0)"
                                   onclick="groupManager('${e.groupid}')" role="button">群管理员</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div style="margin-top: 20px;border-top: 1px solid #dedede;padding-bottom:15px; height: 50px">
                    <span style="line-height:28px;margin-top:25px;padding-left:10px; float: left">共${page.count}条/${page.pageCount}页</span>
                    <ul class="pagination" style="padding-left:100px; float: right">
                        <li>
                            <a style="margin-right:20px">每页显示&nbsp;<select name="size" id="size"
                                                                           onchange="nextPage('1')">
                                <option value="10" ${query.size==10?'selected':''}>10</option>
                                <option value="20" ${query.size==20?'selected':''}>20</option>
                                <option value="30" ${query.size==30?'selected':''}>30</option>
                                <option value="100" ${query.size==100?'selected':''}>100</option>
                            </select>&nbsp;条</a>
                        </li>
                        <c:choose>
                            <c:when test="${page.page == 1}">
                                <li><a href="javascript:void(0)">首页</a></li>
                                <li><a href="javascript:void(0)"><span class="left">《</span></a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0);" onclick="nextPage('1')">首页</a></li>
                                <li><a href="javascript:void(0);" onclick="nextPage('${page.page-1}')"><span
                                        class="left">《</span></a></li>
                            </c:otherwise>
                        </c:choose>
                        <li><a style="height: 30px; width: 100px">第<input style="width: 40px;height:20px;" type="text"
                                                                          id="index" name="index"
                                                                          onkeyup="searchIndex(event)"
                                                                          value="${page.page}"/> 页</a></li>

                        <c:choose>
                            <c:when test="${page.page == page.pageCount}">
                                <li><a href="javascript:void(0)"><span class="right">》</span></a></li>
                                <li><a href="javascript:void(0)">末页</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0);" onclick="nextPage('${page.page+1}')"><span
                                        class="right">》</span></a></li>
                                <li><a href="javascript:void(0);" onclick="nextPage('${page.pageCount}')">末页</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>


            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    function searchOrder(_page) {
        var page = parseInt(_page);
        var size = $("#size").val();

        var keywords = $("#keywords").val();
        addCookie("contract_size", size, 36);
        if ((page <= ${page.pageCount} && page >= 1)) {
            window.location.href = "#module=/groups/list&page=" + page
            + "&size=" + size

            + "&keywords=" + keywords
            + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }


    function searchIndex(e) {
        if (e.keyCode != 13) return;
        var _index = $("#index").val();
        var size = getCookie("contract_size");
        var keywords = $("#keywords").val();
        if (_index <= ${page.pageCount} && _index >= 1) {
            window.location.href = "#module=/groups/list&page=" + _index + "&size=" + size
            + "&keywords=" + keywords
            + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }
    function nextPage(_page) {
        var page = parseInt(_page);
        var size = $("#size").val();
        var keywords = $("#keywords").val();
        addCookie("contract_size", size, 36);
        if ((page <= ${page.pageCount} && page >= 1)) {
            window.location.href = "#module=/groups/list&page=" + page + "&size=" + size
            + "&keywords=" + keywords
            + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }


    function editRole(_id) {
        $.ajax({
            type: "GET",
            data: {"id": _id},
            url: "/groups/toDetail.do",
            success: function (response) {
                $("#content").html(response);
            }
        });
    }

    function saveSingle() {
        window.location.href = "#module=/groups/toAddGroups" + "&_t=" + new Date().getTime();
    }


    //群组管理员
    function groupManager(_id) {
        $.ajax({
            type: "GET",
            data: {"id": _id},
            url: "/groups/listGroupManagers.do",
            success: function (response) {
                $("#content").html(response);
            }
        });
    }

</script>


