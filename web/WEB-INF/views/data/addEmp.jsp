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
            <li><a href="javascript:void (0);">会员管理</a></li>
            <li><a href="javascript:void (0);">批量导入会员</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>导入数据</span>
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
                <h4 class="page-header">详情</h4>

                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">选择文件</label>

                        <div class="col-sm-10">
                            <input type="file" name="file" id="fileUpload" style="float: left;"/>
                            <input type="button" value="上传" onclick="uploadImage()" style="float: left;"/><br/><br/>

                            <div id="imageDiv" style="padding: 10px"></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">文件地址</label>

                        <div class="col-sm-4">
                            <input type="text" readonly="true" id="hidden_data" class="form-control"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <%--<p style="color: #c6080d"> *提示：导入数据前请先添加该文件对应的用户信息，否则导入数据不成功。</p>--%>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="saveData()">提交</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%--<div class="col-xs-4 col-sm-4">--%>
<script type="text/javascript">
    function uploadImage() {
        $.ajaxFileUpload(
                {
                    url: "/uploadUnCompressImage.do?_t=" + new Date().getTime(),            //需要链接到服务器地址
                    secureuri: false,//是否启用安全提交，默认为false
                    fileElementId: 'fileUpload',                        //文件选择框的id属性
                    dataType: 'json',                                     //服务器返回的格式，可以是json, xml
                    success: function (data, status)  //服务器成功响应处理函数
                    {
                        if (data.success) {
                            $("#hidden_data").val(data.data);
                            alert("上传成功");
                        } else {
                            if (data.code == 1) {
                                alert("上传文件失败");
                            } else if (data.code == 2) {
                                alert("上传图片格式只能为：xls");
                            } else if (data.code == 3) {
                                alert("请选择上传文件");
                            } else {
                                alert("上传失败");
                            }
                        }
                    }
                }
        );
    }

    function saveData() {
        var file = $("#hidden_data").val();
        if (file == '') {
            alert("请先上传文件");
        }
        if (confirm("确定要导入数据么？")) {
            $.ajax({
                url: "data/resolveEmp.do",
                data: {"fileName": file},
                type: "POST",
                success: function (_data) {
                    var data = $.parseJSON(_data);
                    if (data.success) {
                        alert("导入数据成功");
                        history.go(-1);
                    } else {
                        alert(data.message)
                    }
                }
            });
        }
    }
</script>


