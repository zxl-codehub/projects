<%@page contentType="text/html;charset=UTF-8" %>
<%@page session="false"%>

<%
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        String uid = null, pwd = null;
        for (int i = 0; i < cookies.length; i++) {
            if ("uid".equals(cookies[i].getName())) {
                uid = cookies[i].getValue();
            } else if ("pwd".equals(cookies[i].getName())) {
                pwd = cookies[i].getValue();
            }
        }
        if(uid != null && pwd != null) {
            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher("/user/login?uid="+uid+"&pwd="+pwd);
            requestDispatcher.forward(request, response);
            return;
        }
    }
%>

<!DOCTYPE html>
<html lang='zh-CN'>
<head>
    <meta charset='UTF-8'>
    <title>login page</title>
    <style>
        h1, form {
            text-align: center;
        }
    </style>
    <script src='md5.min.js'></script>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            let btn = document.querySelector('#login');
            btn.addEventListener('click', function (e) {
                let uid = document.querySelector('#uid').value;
                let pwd = document.querySelector('#pwd').value;
                if (uid === '' || pwd === '') {
                    alert('用户名或密码不能为空');
                    e.preventDefault();
                    return;
                }
                if (uid.length > 20 || pwd.length > 20) {
                    alert('账号或密码的长度不能超过20');
                    e.preventDefault();
                    return;
                }
                document.querySelector('#hidden_pwd').value = md5(pwd);
            });
        });
    </script>
</head>
<body>
    <h1>登录页面</h1>
    <hr>
    <form action='${pageContext.request.contextPath}/user/login' method='post'>
        <label>账号：<input type='text' name='uid' id='uid'></label><br>
        <label>密码：<input type='password' id='pwd'></label><br>
        <input hidden type='password' name='pwd' id='hidden_pwd'>
        <label><input type='checkbox' name='exemptionLogin'>十天内免登录</label><br>
        <input type='submit' value='登录' id='login'>
        <input type='reset' value='重置'>
    </form>
</body>
</html>
