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
            <li><a href="javascript:void(0)">管理员</a></li>
            <li><a href="javascript:void(0)">添加管理员</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box ui-draggable ui-droppable">
            <div class="box-header">
                <div class="box-name ui-draggable-handle">
                    <i class="fa fa-table"></i>
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
                <form class="form-inline">
                    <div class="form-group">
                        <div class="col-sm-4">
                            <input class="form-control" id="keywords" value="${query.keywords}" type="text"
                                   placeholder="姓名|手机号">
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
                        <select class="form-control" id="sex">
                            <option value="">--选择性别--</option>
                            <option value="0" ${query.sex=='0'?'selected':''}>女</option>
                            <option value="1" ${query.sex=='1'?'selected':''}>男</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="education">
                            <option value="">--选择受教育程度--</option>
                            <option value="1" ${query.education=='1'?'selected':''}>不限</option>
                            <option value="2" ${query.education=='2'?'selected':''}>专科以下</option>
                            <option value="3" ${query.education=='3'?'selected':''}>专科</option>
                            <option value="4" ${query.education=='4'?'selected':''}>本科</option>
                            <option value="5" ${query.education=='5'?'selected':''}>研究生及以上</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="marriage">
                            <option value="">--选择婚姻状况--</option>
                            <option value="0" ${query.marriage=='0'?'selected':''}>不限</option>
                            <option value="1" ${query.marriage=='1'?'selected':''}>单身</option>
                            <option value="2" ${query.marriage=='2'?'selected':''}>离异</option>
                            <option value="3" ${query.marriage=='3'?'selected':''}>丧偶</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="state">
                            <option value="">--选择状态--</option>
                            <option value="1" ${query.state=='1'?'selected':''}>单身</option>
                            <option value="2" ${query.state=='2'?'selected':''}>交往中</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="rzstate1">
                            <option value="">--选择身份认证--</option>
                            <option value="0" ${query.rzstate1=='0'?'selected':''}>未认证</option>
                            <option value="1" ${query.rzstate1=='1'?'selected':''}>已认证</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="rzstate2">
                            <option value="">--选择会员认证--</option>
                            <option value="0" ${query.rzstate2=='0'?'selected':''}>未认证</option>
                            <option value="1" ${query.rzstate2=='1'?'selected':''}>已认证</option>
                            <option value="2" ${query.rzstate2=='2'?'selected':''}>体验会员</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="rzstate3">
                            <option value="">--选择诚信认证--</option>
                            <option value="0" ${query.rzstate3=='0'?'selected':''}>未认证</option>
                            <option value="1" ${query.rzstate3=='1'?'selected':''}>已认证</option>
                        </select>
                    </div>
                    <button type="submit" onclick="searchOrder('1')"
                            class="btn form-control btn-warning btn-sm btn-block">查找
                    </button>
                </form>
                <%--<p>For basic styling add the base class <code>.table</code> to any <code>&lt;table&gt;</code>.</p>--%>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>姓名</th>
                        <th>手机号</th>
                        <th>头像</th>
                        <th>年龄</th>
                        <th>性别</th>
                        <th>身高</th>
                        <th>教育程度</th>
                        <th>婚姻状态</th>
                        <th>单身状态</th>
                        <th>身份认证</th>
                        <th>会员认证</th>
                        <th>诚信认证</th>
                        <th>注册日期</th>
                        <th>是否使用</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="e" varStatus="st">
                        <tr>
                            <td>${st.index+1}</td>
                            <td>${e.nickname}</td>
                            <td>${e.mobile}</td>
                            <td><img src="${e.cover}" style="width:60px;height: 60px;"></td>
                            <td>${e.age}</td>
                            <td>
                                <c:if test="${e.sex=='0'}">女</c:if>
                                <c:if test="${e.sex=='1'}">男</c:if>
                            </td>
                            <td>${e.heightl}</td>
                            <td>
                                <c:if test="${e.education=='1'}">不限</c:if>
                                <c:if test="${e.education=='2'}">专科以下</c:if>
                                <c:if test="${e.education=='3'}">专科</c:if>
                                <c:if test="${e.education=='4'}">本科</c:if>
                                <c:if test="${e.education=='5'}">研究生以上</c:if>
                            </td>
                            <td>
                                <c:if test="${e.marriage=='0'}">不限</c:if>
                                <c:if test="${e.marriage=='1'}">未婚</c:if>
                                <c:if test="${e.marriage=='2'}">离异</c:if>
                                <c:if test="${e.marriage=='3'}">丧偶</c:if>
                            </td>
                            <td>
                                <c:if test="${e.state=='1'}">单身</c:if>
                                <c:if test="${e.state=='2'}">交往中</c:if>
                            </td>
                            <td>
                                <c:if test="${e.rzstate1=='0'}">未认证</c:if>
                                <c:if test="${e.rzstate1=='1'}">已认证</c:if>
                            </td>
                            <td>
                                <c:if test="${e.rzstate2=='0'}">未认证</c:if>
                                <c:if test="${e.rzstate2=='1'}">已认证</c:if>
                                <c:if test="${e.rzstate2=='2'}">体验会员</c:if>
                            </td>
                            <td>
                                <c:if test="${e.rzstate3=='0'}">未认证</c:if>
                                <c:if test="${e.rzstate3=='1'}">已认证</c:if>
                            </td>
                            <td>${um:format(e.dateline, 'yy-MM-dd HH:mm')}</td>
                            <td>
                                <c:if test="${e.is_use=='0'}">禁用</c:if>
                                <c:if test="${e.is_use=='1'}">使用</c:if>
                                <c:if test="${e.is_use=='2'}">未完善资料</c:if>
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
        var sex = $("#sex").val();
        var education = $("#education").val();
        var marriage = $("#marriage").val();
        var state = $("#state").val();
        var keywords = $("#keywords").val();
        var rzstate1 = $("#rzstate1").val();
        var rzstate2 = $("#rzstate2").val();
        var rzstate3 = $("#rzstate3").val();
        if (_index <= ${page.pageCount} && _index >= 1) {
            window.location.href = "#module=/emp/listManager&page=" + _index
            + "&size=" + size
            + "&is_use=" + is_use
            + "&sex=" + sex
            + "&education=" + education
            + "&marriage=" + marriage
            + "&state=" + state
            + "&rzstate1=" + rzstate1
            + "&rzstate2=" + rzstate2
            + "&rzstate3=" + rzstate3
            + "&keywords=" + keywords
            + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }
    function nextPage(_page) {
        var page = parseInt(_page);
        var size = $("#size").val();
        var is_use = $("#is_use").val();
        var sex = $("#sex").val();
        var education = $("#education").val();
        var marriage = $("#marriage").val();
        var state = $("#state").val();
        var rzstate1 = $("#rzstate1").val();
        var rzstate2 = $("#rzstate2").val();
        var rzstate3 = $("#rzstate3").val();
        var keywords = $("#keywords").val();
        addCookie("contract_size", size, 36);
        if ((page <= ${page.pageCount} && page >= 1)) {
            window.location.href = "#module=/emp/listManager&page=" + page
            + "&size=" + size
            + "&is_use=" + is_use
            + "&sex=" + sex
            + "&education=" + education
            + "&marriage=" + marriage
            + "&state=" + state
            + "&rzstate1=" + rzstate1
            + "&rzstate2=" + rzstate2
            + "&rzstate3=" + rzstate3
            + "&keywords=" + keywords
            + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }

    function searchOrder(_page) {
        var page = parseInt(_page);
        var size = $("#size").val();
        var is_use = $("#is_use").val();
        var sex = $("#sex").val();
        var education = $("#education").val();
        var marriage = $("#marriage").val();
        var state = $("#state").val();
        var rzstate1 = $("#rzstate1").val();
        var rzstate2 = $("#rzstate2").val();
        var rzstate3 = $("#rzstate3").val();
        var keywords = $("#keywords").val();
        addCookie("contract_size", size, 36);
        if ((page <= ${page.pageCount} && page >= 1)) {
            window.location.href = "#module=/emp/listManager&page=" + page
            + "&size=" + size
            + "&is_use=" + is_use
            + "&sex=" + sex
            + "&education=" + education
            + "&marriage=" + marriage
            + "&state=" + state
            + "&rzstate1=" + rzstate1
            + "&rzstate2=" + rzstate2
            + "&rzstate3=" + rzstate3
            + "&keywords=" + keywords
            + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }
</script>


