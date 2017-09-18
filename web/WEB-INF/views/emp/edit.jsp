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
            <li><a href="javascript:void (0);">编辑会员</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>编辑会员</span>
                </div>
            </div>
            <div class="box-content">
                <form class="form-horizontal" role="form">
                    <input type="hidden" value="${emp.empid}" id="empid">
                    <input type="hidden" value="${cover}" id="cover">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">昵称</label>

                        <div class="col-sm-4">
                            <input type="text" id="nickname" class="form-control" value="${emp.nickname}"
                                    data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">头像</label>

                        <div class="col-sm-10 col-md-2">
                            <img class="img-thumbnail" name="imagePath" id="imageDiv" style="cursor: pointer"
                                 src="${emp.cover}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-10">
                           <p class="red"> *文件大小200K以内，建议尺寸：120px*120px</p>
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
                        <label class="col-sm-2 control-label">手机号</label>
                        <div class="col-sm-4">
                            <input type="text" id="mobile" class="form-control" value="${emp.mobile}" readonly="true"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">排序数字</label>
                        <div class="col-sm-4">
                            <input type="text" id="topnum" class="form-control" value="${emp.topnum}" placeholder="数字越大越靠前"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">签名</label>
                        <div class="col-sm-4">
                            <input type="text" id="sign" class="form-control" value="${emp.sign}" placeholder="签名"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">邀请码</label>
                        <div class="col-sm-4">
                            <input type="text" id="tjmobile" class="form-control" value="${emp.yqnum}" readonly="true"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">所在地-省</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="provinceid" onchange="selectCitys()">
                                <option value="">--选择省份--</option>
                                <c:forEach items="${listProvinces}" var="e" varStatus="st">
                                    <option value="${e.provinceid}"  ${emp.provinceid==e.provinceid?'selected':''}>${e.pname}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">所在地-城市</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="cityid">
                                <option value="${emp.cityid}" >${emp.cityName}</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">会员认证</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="rzstate2">
                                <option value="0" ${emp.rzstate2=='0'?'selected':''}>未认证</option>
                                <option value="1" ${emp.rzstate2=='1'?'selected':''}>已认证</option>
                                <option value="2" ${emp.rzstate2=='2'?'selected':''}>体验会员</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">角色</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="rolestate">
                                <option value="0" ${emp.rolestate=='0'?'selected':''}>技师</option>
                                <option value="1" ${emp.rolestate=='1'?'selected':''}>会员</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">是否使用</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="is_use">
                                <option value="0" ${emp.is_use=='0'?'selected':''}>禁用</option>
                                <option value="1" ${emp.is_use=='1'?'selected':''}>使用</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">注册日期</label>
                        <div class="col-sm-4">
                            <input type="text" id="dateline" class="form-control" value="${um:format(emp.dateline, 'yyyy-MM-dd HH:mm:ss')}" readonly="true"
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
        var empid = $("#empid").val();
        var nickname = $("#nickname").val();
        var rzstate2 = $("#rzstate2").val();

        var provinceid = $("#provinceid").val();
        var cityid = $("#cityid").val();
        var is_use = $("#is_use").val();
        var rolestate = $("#rolestate").val();
        var sign = $("#sign").val();
        var topnum = $("#topnum").val();

        if (nickname.replace(/\s/g, '') == '') {
            alert("姓名不能为空");
            return;
        }
        if (topnum.replace(/\s/g, '') == '') {
            alert("排序数字不能为空");
            return;
        }
        if (sign.replace(/\s/g, '') == '') {
            alert("签名不能为空");
            return;
        }

        var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
        if (provinceid.replace(/\s/g, '') == '') {
            alert("请选择省！");
            return;
        }
        if (cityid.replace(/\s/g, '') == '') {
            alert("请选择县");
            return;
        }
        if (rolestate.replace(/\s/g, '') == '') {
            alert("请选择角色");
            return;
        }  if (is_use.replace(/\s/g, '') == '') {
            alert("请选择是否禁用该用户");
            return;
        }
        var imagePath = $("img[name='imagePath']").attr("src");
        if (imagePath == "" || imagePath == null) {
            //空的  没有头像文件
            alert("请上传头像文件！");
            return;
        }
        if (imagePath.substring(0,4)=="http")
        {
            //说明没有新文件
            imagePath = $("#cover").val();
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/emp/edit.do",
            data: {
                "empid": empid,
                "topnum": topnum,
                "nickname": nickname,
                "cover": imagePath,
                "provinceid": provinceid,
                "cityid": cityid,
                "rzstate2": rzstate2,
                "rolestate": rolestate,
                "sign": sign,
                "is_use": is_use
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("修改成功");
                    history.go(-1);
                } else {
                    alert(data.message)
                }
            }
        });
    }
    ;


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


    function selectCitys() {
        var province = $("#provinceid").val();
        $.ajax({
            cache: true,
            type: "POST",
            url: "/getAllCitys.do",
            data: {
                "provinceid": province
            },
            async: false,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    var citys = data.data;
                    var ret = "<option value=''>" + '请选择城市' + "</option>";
                    for (var i = citys.length - 1; i >= 0; i--) {
                        if (citys[i].areaid == province) {
                            ret += "<option value='" + citys[i].cityid + "'>" + citys[i].cityName + "</option>";
                        }
                    }
                    $("#cityid").html(ret);
                } else {
                    var _case = {1: "获取数据失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
    ;


</script>


<script type="text/javascript">
    $(document).ready(function(){
        //找到li下所有img的值,单击图片事件
        $("#cardsfz").click(function(){
            $("#show").fadeIn(300);  //显示图片效果
            //设置显示放大后的图片位置
            document.getElementById("show").style.left=$(window).width()/2;
            document.getElementById("show").style.top=$(window).height()/2;
            //获得图片路径
            var photo_url=$(this).find("img").attr("src");
            //设置图片路径
            $("#photo").find("img").attr("src",photo_url);
            //单击放大后的图片消失
            $("#photo").click(function(){
                $("#show").fadeOut(300); //图片消失效果
            });
        });
    });
</script>



<style type="text/css">
    li{
        width:380px;
        float:left;
        list-style: none;
    }
    ul li img{
        width:240px;
        border:1px solid red;
        cursor: pointer;
    }
    #show{
        position: absolute;
        width:426px;
        height:293px;
        z-index: 200;
    }
    #info{
        position: absolute;
        top:300px;
        left:10px;
        margin-left: 28px;
        padding: 5px;
        width:416px;
        height: 45px;
    }
    #photo{
        position: absolute;
        top:-260px;
        left:220px;
        border:3px solid red;
        cursor: pointer;
    }
</style>