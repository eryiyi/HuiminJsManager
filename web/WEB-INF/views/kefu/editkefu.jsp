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
            <li><a href="javascript:void(0)">客服管理</a></li>
            <li><a href="javascript:void(0)">编辑客服</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>编辑客服</span>
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
                <h4 class="page-header">编辑客服</h4>

                <form class="form-horizontal" role="form">
                    <input type="hidden" id="mm_tel_id" value="${levelObj.mm_tel_id}">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">客服电话</label>

                        <div class="col-sm-4">
                            <input type="text" id="mm_tel" value="${levelObj.mm_tel}" class="form-control"
                                   placeholder="客服QQ/电话" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="saveP()">确定</button>
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
        var mm_tel_id = $("#mm_tel_id").val();
        var mm_tel = $("#mm_tel").val();

        if (mm_tel.replace(/\s/g, '') == '') {
            alert("请输入正确的客服QQ");
            return;
        }


        $.ajax({
            cache: true,
            type: "POST",
            url: "/kefu/editKefu.do",
            data: {
                "mm_tel_id": mm_tel_id,
                "mm_tel": mm_tel
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("执行成功");
                    window.location.href = "#module=kefu/list"+ "&_t=" + new Date().getTime();
                } else {
                    alert("执行失败，请检查")
                }
            }
        });
    }
</script>

