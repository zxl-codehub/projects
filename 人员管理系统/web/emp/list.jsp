<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
    <meta charset='UTF-8'>
    <title>emp list</title>
    <style>
        td, th {
            border: 3px yellowgreen solid;
            text-align: center;
        }
        table {
            border-collapse: collapse;
            margin: 0 auto;
        }
        .add, .logout {
            display: block;
            text-align: center;
        }
        h1 {
            text-align: center;
            color: deepskyblue;
        }
    </style>
    <script>
        window.setInterval(function () {
            history.go(0);
        }, 1000 * 60);
    </script>
</head>
<body>
<h1>欢迎 ${name} 使用oa系统</h1>
<a href='javascript:void(0)' onclick='if(confirm("你确定要退出登录吗？")) location.href="${pageContext.request.contextPath}/user/logout"' class='logout'>[安全退出]</a>
<hr>
<table>
    <thead>
    <tr>
        <th>序号</th>
        <th>员工号</th>
        <th>姓名</th>
        <th>薪水</th>
        <th>所属部门</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${emps}" var="emp" varStatus="empStatus">
            <tr>
                <td>${empStatus.count}</td>
                <td>${emp.id}</td>
                <td>${emp.name}</td>
                <td>${emp.salary}</td>
                <td>${emp.dept}</td>
                <td>
                    <a href='javascript:void(0)' onclick='if(confirm("你确定要删除吗？")) location.href="${pageContext.request.contextPath}/emp/del?id=${emp.id}"'>删除</a>
                    <a href='javascript:void(0)' onclick='location.href="${pageContext.request.contextPath}/emp/update?id=${emp.id}&tag=1"'>修改</a>
                    <a href='javascript:void(0)' onclick='location.href="${pageContext.request.contextPath}/emp/display?id=${emp.id}"'>单独显示</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<hr>
<a href='${pageContext.request.contextPath}/emp/add.jsp' class='add'>添加员工</a>
</body>
</html>