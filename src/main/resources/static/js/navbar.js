$(function () {

    var loginAjaxPost;

    $('#loginForm').formValidation({
        //message: 'This value is not valid',
        locale: 'zh_CN',
        excluded: ':disabled',
        // framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                trigger: 'blur',
                validators: {
                    notEmpty: {
                        message: '请输入用户名'
                    },
                    stringLength: {
                        min: 3,
                        max: 30,
                        //message: 'The username must be more than 6 and less than 30 characters long'
                    }
                }
            },
            password: {
                trigger: 'blur',
                validators: {
                    notEmpty: {
                        message: '请输入密码'
                    }
                }
            }
        }
    })
        .on('success.form.fv', function (e) {
            clearErrorMessage("#loginDialog")
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            // var $form = $(e.target);

            var $btn = $("#btnLoginSubmit").button("loading");

            var json = JSON.stringify({
                "username": $("#login_username").val(),
                "password": $("#login_password").val()
            });
            loginAjaxPost = $.ajax({
                type: "POST",
                url: "/postLogin",
                contentType: "application/json; charset=utf-8",
                data: json,
                dataType: "json",
                success: function (data) {
                    if (data) {
                        history.go(0)
                    } else {
                        $("#loginForm").before(getAlertContent("用户名或密码不正确，请重新输入"));
                        $btn.button("reset");
                    }
                },
                error: function (message) {
                    $("#loginForm").before(getAlertContent("登录失败，请稍后再试"));
                    $btn.button("reset");
                }
            });

        });

    $('#loginDialog').on('hidden.bs.modal', function (e) {
        clearErrorMessage("#loginDialog")
        $('#loginForm').formValidation('resetForm', true);
    })

})
