<%@page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
    <meta charset='UTF-8'>
    <title>add employee page</title>
    <style>
        h1 {
            text-align: center;
        }
        form {
            text-align: center;
        }
    </style>
</head>
<body>
<h1>添加员工</h1>
<hr>
<form action='${pageContext.request.contextPath}/emp/add' method='post'>
    员工号：<input type='number' name='id'><br>
    员工姓名：<input type='text' name='name'><br>
    员工薪水：<input type='text' name='salary'><br>
    员工所属部门：<input type='text' name='dept'><br>
    <input type='submit' value='添加'>
</form>
</body>
</html>
