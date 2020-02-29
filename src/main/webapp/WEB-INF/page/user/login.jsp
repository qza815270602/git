<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>QQ登录</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <link href="<%=request.getContextPath()%>/static/jQuery-qqdl20160708/css/login.css" rel="stylesheet" />
    <script src="<%=request.getContextPath()%>/static/jQuery-qqdl20160708/js/jquery-1.9.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/static/jQuery-qqdl20160708/js/jquery.login.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/md5/md5-min.js"></script>
    <style>
        html{width:100%;     height:100%}
        body{width:100%;     height:100%;
            background: url("<%=request.getContextPath()%>/static/jQuery-qqdl20160708/images/312516.jpg") no-repeat;
            background-size:cover; }
    </style>
</head>
<body>
<!-- 代码 开始 -->
<p id="loginStart" style="text-align:center;">登录框</p>
<div id="_login_div_quick_">
    <div class="login_no_qlogin" id="login">
        <div id="header" class="header">
            <div class="logo"></div>
            <div class="switch" id="switch">
                <a class="switch_btn_focus" id="switch_login" href="javascript:void(0);" tabindex="8">帐号登录</a>
                <div class="switch_bottom" id="switch_bottom"></div>
            </div>
            <a id="close" class="close" href="javascript:void(0)" title="关闭" tabindex="9"></a>
        </div>
        <div class="web_qr_login" id="web_qr_login">
            <div class="web_qr_login_show" id="web_qr_login_show">
                <div class="web_login" id="web_login">
                    <div class="tips" id="tips">
                        <div class="error_tips" id="error_tips">
                            <span class="error_logo" id="error_logo"></span>
                            <span class="err_m" id="err_m"></span>
                        </div>
                        <div class="loading_tips" id="loading_tips">
                            <span id="loading_wording">登录中</span>
                            <img src="<%=request.getContextPath()%>/static/jQuery-qqdl20160708/images/load.gif" alt="加载中..." />
                        </div>
                    </div>
                    <div class="login_form">
                        <form id="fm" >
                            <input type="hidden" name="salt"  id="salt"/>
                            <div class="uinArea" id="uinArea">
                                <label class="input_tips" id="uin_tips" for="userName"></label>
                                <div class="inputOuter">
                                    <input type="text" placeholder="用户名/手机号/邮箱" onblur="getSalt(this)" class="inputstyle" id="userName" name="userName" value="" tabindex="1" />
                                    <a class="uin_del" id="uin_del" href="javascript:void(0);"></a>
                                </div>
                                <ul class="email_list" id="email_list"></ul>
                            </div>
                            <div class="pwdArea" id="pwdArea">
                                <label class="input_tips" id="pwd_tips" for="pwd"></label>
                                <div class="inputOuter">
                                    <input type="password" placeholder="密码" class="inputstyle password" id="pwd" name="password" value="" maxlength="16" tabindex="2" />
                                </div>
                                <div class="lock_tips" id="caps_lock_tips">
                                    <span class="lock_tips_row"></span>
                                    <span>大写锁定已打开
                                        </span>
                                </div>
                            </div>
                            <div class="submit">
                                <a class="login_button" href="javascript:void(0)">
                                    <input type="button" tabindex="6" value="登 录" class="btn" id="login_button" onclick="login()"  />
                                </a>
                            </div>
                        </form>
                    </div>
                    <div class="bottom" id="bottom_web">
                        <span class="dotted">|</span>
                        <a onclick="toFind()" style="color: #00FFFF">忘记密码</a>
                        <span class="dotted">|</span>
                        <a onclick="toAdd()" style="color: #00FF">注册</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $.login('#loginStart');

    //登录

    function login() {
        var index = layer.load(1, {shade: 0.3}, {shadeClose: true}); //解决网络延迟的加载重复操作问题
        layer.msg('请稍等', {
            icon: 1,
            time: 2000, //2秒关闭（如果不配置，默认是3秒）
            shade: [0.8, '#393D49']
        }, function () {
            //密码加盐 md5(md5(pwd)+salt)
            var pwd = $("#pwd").val();
            //最终密码
            var MD5 = md5(md5(pwd) + $("#salt").val());
            $("#pwd").val(MD5);
            $.post("<%=request.getContextPath()%>/user/login ",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    layer.msg(data.msg, {icon: 6});
                    if (data.code == -1) {
                        layer.msg(data.msg, {icon: 5});
                        return;
                    }
                    window.location.href = "<%=request.getContextPath()%>/index/toIndex";
                });
        });
    }

    //新增
    function toAdd(){
        layer.open({
            type: 2,
            title: [
                '新增',
                'background-color:#8DCE16; color:#fff;'
            ],
            shadeClose: true,
            shade: 0.3,
            area: ['380px', '80%'],
            content: '<%=request.getContextPath()%>/user/toAdd' //iframe的url
        });
    }

    //新增
    function toFind(){
        layer.open({
            type: 2,
            title: [
                '找回密码',
                'background-color:#8DCE16; color:#fff;'
            ],
            shadeClose: true,
            shade: 0.3,
            area: ['380px', '80%'],
            content: '<%=request.getContextPath()%>/user/toFind' //iframe的url
        });
    }

    //判断当前窗口路径与加载路径是否一致。
    if(window.top.document.URL != document.URL){
        //将窗口路径与加载路径同步
        window.top.location = document.URL;
    }
    //获取盐
    function getSalt(obj) {
        $.post("<%=request.getContextPath()%>/user/findSalt",
            {userName: obj.value},
            function (data) {
                if (data.code == 200) {
                    $("#salt").val(data.data);
                } else {
                    layer.msg(data.msg, {icon: 5});
                }
            })
    }
</script>
<!-- 代码 结束 -->
</body>
</html>
