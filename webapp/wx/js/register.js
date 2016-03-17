var issend = false;
$(function(e){
    if($.cookie("code_time")!=null){
        var code_intever = setInterval(function(){
            var time = (parseInt($.cookie("code_time")) - new Date().getTime())/1000 ;
            if(parseInt(time)<=1){
                $.cookie("code_time",null);
                $('.getcode').attr('disable',0);
                $('.getcode').val('获取验证码');
                clearInterval(code_intever);
                issend = true;
            }else{
                $(".getcode").val('('+parseInt(time)+'s)后重试');
            }
        }, 1000);
    }else{
        issend = true;
    }


    $('.getcode').click(function(){
        if(!issend){
            return;
        }
        if($(this).attr("disable")=="1"){
            return;
        }
        if($('.mobile').val().length!=11){
            toast.show('手机号码填写不正确！');
            return;
        }
        var href = location.href;
        var verifycode = $('.verifycode').val();
//		var params = {};
//		params.verifycode = verifycode;
        if(href.indexOf('password.html')!=-1){
//			params.type = 2;
            $('.getcode').attr('disable',1);
            if(!$.cookie("code_time")||(parseInt($.cookie("code_time")) - new Date().getTime())/1000<=1){
                api.getVerificationCode($('.mobile').val(),2);
            }
            if(!$.cookie("code_time")){
                $.cookie("code_time",new Date().getTime()+60*1000*2)
            }
            var code_intever = setInterval(function(){
                var time = (parseInt($.cookie("code_time")) - new Date().getTime())/1000 ;
                if(parseInt(time)<=1){
                    $.cookie("code_time",null);
                    $('.getcode').attr('disable',0);
                    $('.getcode').val('获取验证码');
                    clearInterval(code_intever);
                }else{
                    $(".getcode").val('('+parseInt(time)+'s)后重试');
                }
            }, 1000);
        }else{
//			params.type = 1;
            ajax({verifycode:verifycode}, "public/api/checkVerifyCode",{success:function(){
                ajax({mobile:$('.mobile').val()}, "public/api/checkCustomerRegister",function(json){
                    $('.getcode').attr('disable',1);
                    if(!$.cookie("code_time")||(parseInt($.cookie("code_time")) - new Date().getTime())/1000<=1){
                        api.getVerificationCode($('.mobile').val(),1);
                    }
                    if(!$.cookie("code_time")){
                        $.cookie("code_time",new Date().getTime()+60*1000*2)
                    }
                    $('.img_verifycode').click();
                    var code_intever = setInterval(function(){
                        var time = (parseInt($.cookie("code_time")) - new Date().getTime())/1000 ;
                        if(parseInt(time)<=1){
                            $.cookie("code_time",null);
                            $('.getcode').attr('disable',0);
                            $('.getcode').val('获取验证码');
                            clearInterval(code_intever);
                        }else{
                            $(".getcode").val('('+parseInt(time)+'s)后重试');
                        }
                    }, 1000);
                });
            },error:function(json){
                $('.img_verifycode').click();
            }});
        }
    });

    $('.reighster').click(function(){
        if(validate()){
            if($(this).attr('result')=='no'||$(this).attr('result')==''||$(this).attr('result')==undefined){
                toast.show('请同意《捷乘服务协议》');
                return;
            }
            var p = $('.password').val();
            var p2 = $('.password2').val();
            if(p.trim()!=p2.trim()){
                toast.show('两次输入密码不一致！');
                return;
            }
            var params = {};
            params.mobile = $('.mobile').val();
            params.password = $('.password').val();
            params.code = $('.code').val();
            params.openid = $.cookie('openid');
            ajax(params, "public/api/customerRegister",function(json){
                toast.show('注册成功！');
                $.cookie('COOKIE_CUSTOMER_MOBILE',params.mobile,{path:"/"})
                setTimeout(function(){
                    send('login.html');
                }, 1500)
            });
        }
    });

    $('.updatepassword').click(function(){
        if(validate()){
            var p = $('.password').val();
            var p2 = $('.password2').val();
            if(p.trim()!=p2.trim()){
                toast.show('两次输入密码不一致！');
                return;
            }
            var params = {};
            params.mobile = $('.mobile').val();
            params.password = $('.password').val();
            params.code = $('.code').val();
            params.openid = $.cookie('openid');
            ajax(params, "public/api/customerUpdatePwd",function(json){
                toast.show('修改密码成功！');
                setTimeout(function(){
                    send('login.html');
                }, 1500)
            });
        }
    });
});