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
            <li><a href="javascript:void(0)">会员管理</a></li>
            <li><a href="javascript:void(0)">会员列表</a></li>
        </ol>

    </div>
</div>
<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box ui-draggable ui-droppable">
            <div class="box-header">
                <div class="box-name ui-draggable-handle">
                    <i class="fa fa-table"></i>
                    <span>会员列表</span>
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
                                   placeholder="昵称|手机号">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-4">
                            <input class="form-control" id="tjmobile" value="${query.tjmobile}" type="text"
                                   placeholder="推荐人手机号|昵称">
                        </div>
                    </div>

                    <div class="form-group">
                        <select class="form-control" id="is_use">
                            <option value="">--选择会员状态--</option>
                            <option value="0" ${query.is_use=='0'?'selected':''}>启用</option>
                            <option value="1" ${query.is_use=='1'?'selected':''}>禁用</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <select class="form-control" id="rzstate2">
                            <option value="">--是否VIP--</option>
                            <option value="0" ${query.rzstate2=='0'?'selected':''}>否</option>
                            <option value="1" ${query.rzstate2=='1'?'selected':''}>是</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <div class="col-md-2 col-lg-2">
                            <button type="button" onclick="P_daoru_Select()"
                                    class="btn w12 form-control btn-block btn-danger btn-sm">批量导入
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
                        <th>#</th>
                        <th>昵称</th>
                        <th>账号</th>
                        <th>手机号</th>
                        <th>头像</th>
                        <th>角色</th>
                        <th>邀请码/推荐人</th>
                        <th>排序数字</th>
                        <th>签名</th>
                        <th>注册日期</th>
                        <th>是否VIP</th>
                        <th>是否使用</th>
                        <th>操作</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="e" varStatus="st">
                        <tr>
                            <td>${st.index+1}</td>
                            <td>${e.nickname}</td>
                            <td>${e.emp_number}</td>
                            <td>${e.mobile}</td>
                            <td><img src="${e.cover}" style="width:60px;height: 60px;"></td>
                            <td>
                                <c:if test="${e.rolestate=='0'}">技师</c:if>
                                <c:if test="${e.rolestate=='1'}">会员</c:if>
                            </td>
                            <td>
                                <c:if test="${e.rolestate=='0'}">${e.yqnum}</c:if>
                                <c:if test="${e.rolestate=='1'}">${e.tjnickname}</c:if>
                            </td>

                            <td>${e.topnum}</td>
                            <td>${e.sign}</td>
                            <td>${um:format(e.dateline, 'yy-MM-dd HH:mm')}</td>
                            <td>
                                <c:if test="${e.rzstate2=='0'}">否</c:if>
                                <c:if test="${e.rzstate2=='1'}">是</c:if>
                            </td>

                            <td>
                                <c:if test="${e.is_use=='0'}">禁用</c:if>
                                <c:if test="${e.is_use=='1'}">使用</c:if>
                            </td>
                            <td>
                                <a class="btn btn-default btn-sm" href="#module=/emp/toEditPwr&empid=${e.empid}"
                                   role="button">改密</a>
                            </td>
                            <td>
                                <a class="btn btn-default btn-sm" href="#module=/emp/toEdit&empid=${e.empid}"
                                   role="button">编辑</a>
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
    function searchIndex(e) {
        if (e.keyCode != 13) return;
        var _index = $("#index").val();
        var size = getCookie("contract_size");
        var is_use = $("#is_use").val();
        var keywords = $("#keywords").val();
        var rzstate2 = $("#rzstate2").val();
        var tjmobile = $("#tjmobile").val();
        if (_index <= ${page.pageCount} && _index >= 1) {
            window.location.href = "#module=/emp/list&page=" + _index
            + "&size=" + size
            + "&is_use=" + is_use
            + "&rzstate2=" + rzstate2
            + "&keywords=" + keywords
            + "&tjmobile=" + tjmobile
            + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }
    function nextPage(_page) {
        var page = parseInt(_page);
        var size = $("#size").val();
        var is_use = $("#is_use").val();
        var rzstate2 = $("#rzstate2").val();
        var keywords = $("#keywords").val();
        var tjmobile = $("#tjmobile").val();
        addCookie("contract_size", size, 36);
        if ((page <= ${page.pageCount} && page >= 1)) {
            window.location.href = "#module=/emp/list&page=" + page
            + "&size=" + size
            + "&is_use=" + is_use
            + "&rzstate2=" + rzstate2
            + "&keywords=" + keywords
            + "&tjmobile=" + tjmobile
            + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }

    function searchOrder(_page) {
        var page = parseInt(_page);
        var size = $("#size").val();
        var is_use = $("#is_use").val();
        var rzstate2 = $("#rzstate2").val();
        var keywords = $("#keywords").val();
        var tjmobile = $("#tjmobile").val();
        addCookie("contract_size", size, 36);
        if ((page <= ${page.pageCount} && page >= 1)) {
            window.location.href = "#module=/emp/list&page=" + page
            + "&size=" + size
            + "&is_use=" + is_use
            + "&rzstate2=" + rzstate2
            + "&keywords=" + keywords
            + "&tjmobile=" + tjmobile
            + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }

    function P_daoru_Select() {
        window.location.href = "#module=/data/toAddEmp";
    }

</script>


