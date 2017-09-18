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
            <li><a href="javascript:void(0)">修改群组</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>修改群组</span>
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

                    <div class="form-group">
                        <label class="col-sm-2 control-label">名称</label>
                        <input type="hidden" id="groupid" name="groupid" value="${like.groupid}">
                        <input type="hidden" value="${like.pic}" id="pic">
                        <div class="col-sm-4">
                            <input type="text" id="title" class="form-control" placeholder="名称" value="${like.title}"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">图片</label>

                        <div class="col-sm-10 col-md-2">
                            <img class="img-thumbnail" name="imagePath" id="imageDiv" style="cursor: pointer" src="${like.pic}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>

                        <div class="col-sm-10">
                            <input type="file" name="file" id="fileUpload" style="float: left;"/>
                            <input type="button" value="上传" onclick="uploadImage('fileUpload','imageDiv')"
                                   style="float: left;"/><br/><br/>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-2 control-label">介绍</label>
                        <div class="col-sm-4">
                            <input type="text" id="content1" class="form-control" placeholder="介绍" value="${like.content}"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">排序数字</label>

                        <div class="col-sm-4">
                            <input type="text" id="topnum" class="form-control" placeholder="越大越靠前" value="${like.topnum}"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">是否可用</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="is_use">
                                <option value="0" ${like.is_use=='0'?'selected':''}>否</option>
                                <option value="1" ${like.is_use=='1'?'selected':''}>是</option>
                            </select>
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
        var groupid = $("#groupid").val();
        var is_use = $("#is_use").val();
        var title = $("#title").val();
        var content = $("#content1").val();
        var topnum = $("#topnum").val();

        if (title.replace(/\s/g, '') == '') {
            alert("请输入名称！");
            return;
        }
        if (content.replace(/\s/g, '') == '') {
            alert("请输入介绍！");
            return;
        }

        if (is_use.replace(/\s/g, '') == '') {
            alert("请选择是否可用！");
            return;
        }

        var regInt = /^([0-9]\d*)$/;
        if (topnum.replace(/\s/g, '') == '') {
            alert("排序不能为空");
            return;
        } else {
            if (!regInt.test(topnum)) {
                alert("排序必须是整数！");
                return;
            }
        }

        var imagePath = $("img[name='imagePath']").attr("src");
        if (imagePath == "" || imagePath == null) {
            imagePath = $("#pic").val();
            return;
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/groups/edit.do",
            data: {"groupid": groupid,
                "is_use": is_use,
                "title": title,
                "content": content,
                "pic": imagePath,
                "topnum": topnum},
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("执行成功");
                    window.location.href = "#module=groups/list&page=1"+ "&_t=" + new Date().getTime();
                } else {
                    alert("执行失败，请检查")
                }
            }
        });
    }

</script>

<script type="text/javascript">
    function uploadImage(_fileUpload, _imageDiv) {
        $.ajaxFileUpload(
                {
                    url: "/uploadUnCompressImage.do?_t=" + new Date().getTime(),            //需要链接到服务器地址
                    secureuri: false,//是否启用安全提交，默认为false
                    fileElementId: _fileUpload,                        //文件选择框的id属性
                    dataType: 'json',                                     //服务器返回的格式，可以是json, xml
                    success: function (data, status)  //服务器成功响应处理函数
                    {
                        if (data.success) {
                            document.getElementById(_imageDiv).src = data.data;
                        } else {
                            if (data.code == 1) {
                                alert("上传图片失败");
                            } else if (data.code == 2) {
                                alert("上传图片格式只能为：jpg、png、gif、bmp、jpeg");
                            } else if (data.code == 3) {
                                alert("请选择上传图片");
                            } else {
                                alert("上传失败");
                            }
                        }
                    }
                }
        );
    }

    function deleteImage(e, node) {
        if (e.button == 2) {
            if (confirm("确定移除该图片吗？")) {
                $(node).remove();
            }
        }
    }
    ;

</script>