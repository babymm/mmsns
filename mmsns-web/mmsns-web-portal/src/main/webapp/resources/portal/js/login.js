//正则表达式检测是否是手机号码
function isPhone(str){
    var reg=/^1[3|4|5|7|8][0-9]{9}$/
    return reg.test(str);
}
//发送短信验证码
function sendPhoneMessage() {
    var phone=$("input[name='phone']").val();
    if(phone==null||!isPhone(phone)){
        layer.alert("手机号码格式不正确!", {icon: 5});
    }else{
        $.post("/register/sendMessage", {phone:phone}, function (respData) {
            if (respData.code == 200) {
                $("input[name='smsId']").val(respData.data);
                layer.alert("短信验证码发送成功!",{icon: 1});
                $("#sendMessageBtn").attr("disabled",true);
                $("#sendMessageBtn").attr("value","60");
                var sendMessageInterval=setInterval(function () {
                    var time=$("#sendMessageBtn").attr("value");
                    if(time>0){
                        $("#sendMessageBtn").attr("value",time-1);
                    }else{
                        $("#sendMessageBtn").attr("disabled",false);
                        $("#sendMessageBtn").attr("value","发送");
                        clearInterval(sendMessageInterval);
                    }
                },1000);
            } else {
                layer.alert(respData.data, {icon: 5});
            }
        });
    }
}
//手机号码注册
function registerPhone() {
    var username=$("input[name='username']").val();
    if(username==null||username.trim().length<6){
        layer.msg("会员名称格式错误!", {icon: 5,time :2000});
        return false;
    }
    var phone=$("input[name='phone']").val();
    if(phone==null||!isPhone(phone)){
        layer.msg("手机号码格式错误!", {icon: 5,time :2000});
        return false;
    }
    var password=$("input[name='password']").val();
    if(password==null||password==''||password.length<6){
        layer.msg("密码错误!", {icon: 5,time :2000});
        return false;
    }
    var repassword=$("input[name='repassword']").val();
    if(repassword==null||repassword==''||password!=repassword){
        layer.msg("输入密码不一致", {icon: 5,time :2000});
        return false;
    }
    var code=$("input[name='code']").val();
    var smsId=$("input[name='smsId']").val();
    $.post("/register/phone", {username:username,phone:phone,password:password,code:code,smsId:smsId}, function (respData) {
        if (respData.code == 200) {
            layer.msg("手机号码注册成功.................跳转到登录页面");
            setTimeout(function () {
                top.window.location.href="/login";
            },1000);
        } else {
            layer.alert(respData.data, {icon: 5});
        }
    });
}