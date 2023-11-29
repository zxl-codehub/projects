import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.DBUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/user/login", "/user/logout"})
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestPath = request.getServletPath();
//        System.out.println(requestPath);
        switch (requestPath) {
            case "/user/login":
                doLogin(request, response);
                break;
            case "/user/logout":
                doLogout(request, response);
                break;
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String pwd = request.getParameter("pwd");
        Connection connection = DBUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement =
                    connection.prepareStatement("select * from user where uid=? and pwd=?");
            preparedStatement.setString(1, uid);
            preparedStatement.setString(2, pwd);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("name", resultSet.getString("name"));
                if ("on".equals(request.getParameter("exemptionLogin"))) {
                    Cookie uidCookie = new Cookie("uid", uid);
                    Cookie pwdCookie = new Cookie("pwd", pwd);
                    int time = 60 * 60 * 24 * 10; //经过60 * 60 * 24 * 10分钟，也就是10天以后失效
                    uidCookie.setMaxAge(time);
                    pwdCookie.setMaxAge(time);
                    uidCookie.setPath(request.getContextPath());
                    pwdCookie.setPath(request.getContextPath());
                    response.addCookie(uidCookie);
                    response.addCookie(pwdCookie);
                }
                response.sendRedirect(request.getContextPath() + "/emp/list");
            } else {
                Cookie uidCookie = new Cookie("uid", null);
                uidCookie.setMaxAge(0);
                uidCookie.setPath(request.getContextPath());
                Cookie pwdCookie = new Cookie("pwd", null);
                pwdCookie.setMaxAge(0);
                pwdCookie.setPath(request.getContextPath());
                response.addCookie(uidCookie);
                response.addCookie(pwdCookie);

                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("<h1>你的账号或密码错误:(</h1>");
                out.print("<hr>");
                out.print("<a href='"+request.getContextPath()+"'>点击跳转到登录页面</a>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie uidCookie = new Cookie("uid", null);
        uidCookie.setMaxAge(0);
        uidCookie.setPath(request.getContextPath());
        Cookie pwdCookie = new Cookie("pwd", null);
        pwdCookie.setMaxAge(0);
        pwdCookie.setPath(request.getContextPath());
        response.addCookie(uidCookie);
        response.addCookie(pwdCookie);

        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        response.sendRedirect(request.getContextPath());
    }
}
