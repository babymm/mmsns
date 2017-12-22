//正则表达式检测是否是手机号码
function isPhone(str) {
    var reg = /^1[3|4|5|7|8][0-9]{9}$/
    return reg.test(str);
}
//正则表达式检测是否是邮箱
function isEmail(str) {
    var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
    return reg.test(str);
}
//发送短信验证码
function sendPhoneMessage() {
    var phone = $("input[name='phone']").val();
    if (phone == null || !isPhone(phone)) {
        layer.alert("手机号码格式不正确!", {icon: 5});
    } else {
        $.post("/common/sms/send", {phone: phone}, function (respData) {
            if (respData.code == 200) {
                $("input[name='smsId']").val(respData.data);
                layer.alert("短信验证码发送成功!", {icon: 1});
                $("#sendMessageBtn").attr("disabled", true);
                $("#sendMessageBtn").css("background", "silver");
                $("#sendMessageBtn").text("60");
                var sendMessageInterval = setInterval(function () {
                    var time = $("#sendMessageBtn").text();
                    if (time > 0) {
                        $("#sendMessageBtn").text(time - 1);
                    } else {
                        $("#sendMessageBtn").attr("disabled", false);
                        $("#sendMessageBtn").css("background", "#31b968");
                        $("#sendMessageBtn").text("发送");
                        clearInterval(sendMessageInterval);
                    }
                }, 1000);
            } else {
                layer.alert(respData.data, {icon: 5});
            }
        });
    }
}
//手机号码注册
function registerPhone() {
    var username = $("input[name='username']").val();
    if (username == null || username.trim().length < 6) {
        layer.msg("会员名称格式错误!", {icon: 5, time: 2000});
        return false;
    }
    var phone = $("input[name='phone']").val();
    if (phone == null || !isPhone(phone)) {
        layer.msg("手机号码格式错误!", {icon: 5, time: 2000});
        return false;
    }
    var password = $("input[name='password']").val();
    if (password == null || password == '' || password.length < 6) {
        layer.msg("密码格式错误!", {icon: 5, time: 2000});
        return false;
    }
    var repassword = $("input[name='repassword']").val();
    if (repassword == null || repassword == '' || password != repassword) {
        layer.msg("输入密码不一致", {icon: 5, time: 2000});
        return false;
    }
    var code = $("input[name='code']").val();
    if (code == null || code == '') {
        layer.msg("短信验证码不能为空", {icon: 5, time: 2000});
        return false;
    }
    var smsId = $("input[name='smsId']").val();
    $.post("/register/phone", {
        username: username,
        phone: phone,
        password: password,
        code: code,
        smsId: smsId
    }, function (respData) {
        if (respData.code == 200) {
            layer.msg('手机号码注册成功', {
                icon: 1,
                time: 2000
            }, function () {
                top.window.location.href = "/login";
            });
        } else {
            layer.alert(respData.data, {icon: 5});
        }
    });
}
//邮箱注册
function registerEmail() {
    var username = $("input[name='username']").val();
    if (username == null || username.trim().length < 6) {
        layer.msg("会员名称格式错误!", {icon: 5, time: 2000});
        return false;
    }
    var email = $("input[name='email']").val();
    if (email == null || !isEmail(email)) {
        layer.msg("邮箱格式错误!", {icon: 5, time: 2000});
        return false;
    }
    var password = $("input[name='password']").val();
    if (password == null || password == '' || password.length < 6) {
        layer.msg("密码格式错误!", {icon: 5, time: 2000});
        return false;
    }
    var repassword = $("input[name='repassword']").val();
    if (repassword == null || repassword == '' || password != repassword) {
        layer.msg("输入密码不一致", {icon: 5, time: 2000});
        return false;
    }
    $.post("/register/email", {username: username, email: email, password: password}, function (respData) {
        if (respData.code == 200) {
            layer.msg('账号激活邮箱已经发送成功,请登录邮箱查看并进行激活!', {
                icon: 1,
                time: 2000
            }, function () {
                top.window.location.href = "/login";
            });
        } else {
            layer.alert(respData.data, {icon: 5});
        }
    });
}
//通过手机号码来重设密码
function iforgetPhone() {
    var phone = $("input[name='phone']").val();
    if (phone == null || !isPhone(phone)) {
        layer.msg("手机号码格式错误!", {icon: 5, time: 2000});
        return false;
    }
    var password = $("input[name='password']").val();
    if (password == null || password == '' || password.length < 6) {
        layer.msg("密码格式错误!", {icon: 5, time: 2000});
        return false;
    }
    var repassword = $("input[name='repassword']").val();
    if (repassword == null || repassword == '' || password != repassword) {
        layer.msg("输入密码不一致", {icon: 5, time: 2000});
        return false;
    }
    var code = $("input[name='code']").val();
    if (code == null || code == '') {
        layer.msg("短信验证码不能为空", {icon: 5, time: 2000});
        return false;
    }
    var smsId = $("input[name='smsId']").val();
    $.post("/iforget/phone", {
        phone: phone,
        password: password,
        code: code,
        smsId: smsId
    }, function (respData) {
        if (respData.code == 200) {
            layer.msg('密码重置成功', {
                icon: 1,
                time: 2000
            }, function () {
                top.window.location.href = "/login";
            });
        } else {
            layer.alert(respData.data, {icon: 5});
        }
    });
}
//发送邮箱验证码
function sendEmailMessage() {
    var email = $("input[name='email']").val();
    if (email == null || !isEmail(email)) {
        layer.alert("邮箱格式不正确!", {icon: 5});
    } else {
        $.post("/common/email/send", {email: email}, function (respData) {
            if (respData.code == 200) {
                $("input[name='smsId']").val(respData.data);
                layer.alert("邮箱验证码发送成功!", {icon: 1});
                $("#sendEmailBtn").attr("disabled", true);
                $("#sendEmailBtn").css("background", "silver");
                $("#sendEmailBtn").text("60");
                var sendMessageInterval = setInterval(function () {
                    var time = $("#sendEmailBtn").text();
                    if (time > 0) {
                        $("#sendEmailBtn").text(time - 1);
                    } else {
                        $("#sendEmailBtn").attr("disabled", false);
                        $("#sendEmailBtn").css("background", "#31b968");
                        $("#sendEmailBtn").text("发送");
                        clearInterval(sendMessageInterval);
                    }
                }, 1000);
            } else {
                layer.alert(respData.data, {icon: 5});
            }
        });
    }
}
//通过邮箱验证码来重置密码
function iforgetEmail() {
    var email = $("input[name='email']").val();
    if (email == null || !isEmail(email)) {
        layer.msg("邮箱格式不正确!", {icon: 5, time: 2000});
        return false;
    }
    var password = $("input[name='password']").val();
    if (password == null || password == '' || password.length < 6) {
        layer.msg("密码格式错误!", {icon: 5, time: 2000});
        return false;
    }
    var repassword = $("input[name='repassword']").val();
    if (repassword == null || repassword == '' || password != repassword) {
        layer.msg("输入密码不一致", {icon: 5, time: 2000});
        return false;
    }
    var code = $("input[name='code']").val();
    if (code == null || code == '') {
        layer.msg("邮箱验证码不能为空", {icon: 5, time: 2000});
        return false;
    }
    var smsId = $("input[name='smsId']").val();
    $.post("/iforget/email", {
        email: email,
        password: password,
        code: code
    }, function (respData) {
        if (respData.code == 200) {
            layer.msg('密码重置成功', {
                icon: 1,
                time: 2000
            }, function () {
                top.window.location.href = "/login";
            });
        } else {
            layer.alert(respData.data, {icon: 5});
        }
    });
}