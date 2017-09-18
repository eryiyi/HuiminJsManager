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
            <li><a href="javascript:void(0)">关于我们</a></li>
            <li><a href="javascript:void(0)">关于我们</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>关于我们</span>
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
                <h4 class="page-header">关于我们</h4>

                <form class="form-horizontal" role="form">
                    <input type="hidden" id="companyid" value="${aboutUs.companyid}">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">名称</label>

                        <div class="col-sm-4">
                            <input type="text" value="${aboutUs.title}" id="title" class="form-control"
                                   placeholder="名称" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-2 control-label">介绍</label>

                        <div class="col-sm-4">
                            <textarea  cols="45" rows="5" value="${aboutUs.content}" id="content1" class="form-control"
                                       placeholder="介绍" data-toggle="tooltip" data-placement="bottom"
                                       title="Tooltip for name">${aboutUs.content}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">使命</label>

                        <div class="col-sm-4">
                            <input type="text" value="${aboutUs.cont1}" id="cont1" class="form-control"
                                   placeholder="使命" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">愿景</label>

                        <div class="col-sm-4">
                            <input type="text" value="${aboutUs.cont2}" id="cont2" class="form-control"
                                   placeholder="愿景" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">价值观</label>

                        <div class="col-sm-4">
                            <input type="text" value="${aboutUs.cont3}" id="cont3" class="form-control"
                                   placeholder="价值观" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>



                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="saveP()">保存</button>
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
        var companyid = $("#companyid").val();
        var title = $("#title").val();
        var content = $("#content1").val();
        var cont1 = $("#cont1").val();
        var cont2 = $("#cont2").val();
        var cont3 = $("#cont3").val();

        if (title.replace(/\s/g, '') == '') {
            alert("请输入名称");
            return;
        }
        if (content.replace(/\s/g, '') == '') {
            alert("请输入介绍");
            return;
        }

        if (cont1.replace(/\s/g, '') == '') {
            alert("请输入使命");
            return;
        }
        if (cont2.replace(/\s/g, '') == '') {
            alert("请输入愿景");
            return;
        }if (cont3.replace(/\s/g, '') == '') {
            alert("请输入价值观");
            return;
        }
        $.ajax({
            cache: true,
            type: "POST",
            url: "/aboutUs/addAboutUs.do",
            data: {
                "companyid": companyid,
                "title": title,
                "content": content,
                "cont1": cont1,
                "cont2": cont2,
                "cont3": cont3
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("执行成功");
                    window.location.href = "#aboutUs/add"+ "&_t=" + new Date().getTime();
                } else {
                    alert("执行失败，请检查")
                }
            }
        });
    }
</script>


