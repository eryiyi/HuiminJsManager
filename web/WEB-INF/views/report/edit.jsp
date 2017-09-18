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
            <li><a href="javascript:void (0);">投诉中心</a></li>
            <li><a href="javascript:void (0);">投诉中心</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>投诉处理</span>
                </div>
            </div>
            <div class="box-content">
                <form class="form-horizontal" role="form">
                    <input type="hidden" value="${report.reportid}" id="reportid">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">投诉人</label>

                        <div class="col-sm-4">
                            <input type="text" id="nicknameJbr" class="form-control" value="${report.nicknameJbr}" readonly="true"
                                    data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">被投诉人</label>

                        <div class="col-sm-4">
                            <input type="text" id="nickname" class="form-control" value="${report.nickname}" readonly="true"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-2 control-label">举报内容</label>

                        <div class="col-sm-4">
                            <textarea cols="10" rows="10" class="form-control" value="${report.content}"
                                      readonly="true" data-toggle="tooltip" data-placement="bottom"
                                      title="Tooltip for name">${report.content}</textarea>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-2 control-label">是否处理</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="is_read">
                                <option value="0" ${report.is_read=='0'?'selected':''}>未处理</option>
                                <option value="1" ${report.is_read=='1'?'selected':''}>已处理</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">举报日期</label>
                        <div class="col-sm-4">
                            <input type="text" id="dateline" class="form-control" value="${um:format(report.dateline, 'yyyy-MM-dd HH:mm:ss')}" readonly="true"
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
        var reportid = $("#reportid").val();
        var is_read = $("#is_read").val();

        if (is_read.replace(/\s/g, '') == '') {
            alert("请选择是否处理");
            return;
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/report/update.do",
            data: {
                "reportid": reportid,
                "is_read": is_read
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


