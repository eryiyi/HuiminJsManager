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
            <li><a href="javascript:void (0);">朋友圈动态</a></li>
            <li><a href="javascript:void (0);">编辑动态</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>编辑动态</span>
                </div>
            </div>
            <div class="box-content">
                <form class="form-horizontal" role="form">
                    <input type="hidden" value="${recordVO.record_id}" id="record_id">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">昵称</label>

                        <div class="col-sm-4">
                            <input type="text" id="nickname" class="form-control" value="${recordVO.nickname}" readonly="true"
                                    data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">动态类型</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="record_type">
                                <option value="1" ${recordVO.record_type=='1'?'selected':''}>文字</option>
                                <option value="2" ${recordVO.record_type=='2'?'selected':''}>图片</option>
                                <option value="3" ${recordVO.record_type=='3'?'selected':''}>视频</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">内容</label>

                        <div class="col-sm-4">
                            <input type="text" id="record_cont" class="form-control" value="${recordVO.record_cont}" readonly="true"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <c:forEach items="${listPics}" var="eee" varStatus="st">
                        <img src="${eee}" style="width: auto;height: 100px;">
                    </c:forEach>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">是否删除</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="record_use">
                                <option value="0" ${recordVO.record_use=='0'?'selected':''}>否</option>
                                <option value="1" ${recordVO.record_use=='1'?'selected':''}>是</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">发布日期</label>
                        <div class="col-sm-4">
                            <input type="text" id="dateline" class="form-control" value="${um:format(recordVO.record_dateline, 'yyyy-MM-dd HH:mm:ss')}" readonly="true"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="saveEmp()">确定
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

    function saveEmp(mm_emp_id) {
        var record_id = $("#record_id").val();
        var record_use = $("#record_use").val();

        if (record_use.replace(/\s/g, '') == '') {
            alert("请选择是否删除");
            return;
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/recordController/update.do",
            data: {
                "record_id": record_id,
                "record_use": record_use
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("处理成功");
                    history.go(-1);
                } else {
                    alert(data.message)
                }
            }
        });
    }
    ;

</script>


