<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
    <meta charset='UTF-8'>
    <title>display page</title>
    <style>
        h1, h2 {
            text-align: center;
        }
        a {
            display: block;
            text-align: center;
        }
    </style>
</head>
<body>
    <h1>${emp.name}的员工信息</h1>
    <h2>id：${emp.id}</h2>
    <h2>salary：${emp.salary}元</h2>
    <h2>dept：${emp.dept}</h2>
    <hr>
    <a href='${pageContext.request.contextPath}/emp/list'>返回员工列表</a>
</body>
</html>
