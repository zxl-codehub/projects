<%@page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
    <meta charset='UTF-8'>
    <title>update emp page</title>
    <style>
        h1,form {
            text-align: center;
        }
    </style>
</head>
<body>
    <h1>修改员工信息</h1>
    <hr>
    <form action='${pageContext.request.contextPath}/emp/update' method='post'>
        <input hidden type='number' name='tag' value='2'>
        员工号：<input type='number' disabled value='${emp.id}'><br>
        <input type='number' name='id' hidden value='${emp.id}'>
        员工姓名：<input type='text' name='name' value='${emp.name}'><br>
        员工薪水：<input type='text' name='salary' value='${emp.salary}'><br>
        员工所属部门：<input type='text' name='dept' value='${emp.dept}'><br>
        <input type='submit' value='修改'>
    </form>
</body>
</html>
