import bean.Employee;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.DBUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@WebServlet(urlPatterns = {"/emp/list", "/emp/add", "/emp/del", "/emp/update", "/emp/display"})
public class EmpServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestPath = request.getServletPath();
//        System.out.println(requestPath);
        switch(requestPath) {
            case "/emp/list":
                doList(request, response);
                break;
            case "/emp/add":
                doAdd(request, response);
                break;
            case "/emp/del":
                doDel(request, response);
                break;
            case "/emp/update":
                doUpdate(request, response);
                break;
            case "/emp/display":
                doDisplay(request, response);
                break;
        }
    }

    private void doDisplay(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("select * from emp where id=?");
            preparedStatement.setInt(1, Integer.parseInt(request.getParameter("id")));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Employee emp = new Employee();
                emp.setId(resultSet.getInt("id"));
                emp.setName(resultSet.getString("name"));
                emp.setSalary(resultSet.getDouble("salary"));
                emp.setDept(resultSet.getString("dept"));
                request.setAttribute("emp", emp);
                request.getRequestDispatcher("/emp/display.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/error.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
    }

    private void doUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Connection connection = DBUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String tag = request.getParameter("tag");
        try {
            if ("1".equals(tag)) {
                preparedStatement = connection.prepareStatement("select * from emp where id=?");
                int id = Integer.parseInt(request.getParameter("id"));
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    double salary = resultSet.getDouble("salary");
                    String dept = resultSet.getString("dept");
                    Employee emp = new Employee(id, name, salary, dept);
                    request.setAttribute("emp", emp);
                    request.getRequestDispatcher("/emp/update.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/error.jsp");
                }
            } else if ("2".equals(tag)) {
                preparedStatement = connection.prepareStatement("update emp set name=?, salary=?, dept=? where id=?");
                preparedStatement.setString(1, request.getParameter("name"));
                preparedStatement.setDouble(2, Double.parseDouble(request.getParameter("salary")));
                preparedStatement.setString(3, request.getParameter("dept"));
                preparedStatement.setInt(4, Integer.parseInt(request.getParameter("id")));
                int count = preparedStatement.executeUpdate();
                if (count == 1) {
                    response.sendRedirect(request.getContextPath() + "/emp/list");
                } else {
                    response.sendRedirect(request.getContextPath() + "/error.jsp");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/error.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
    }

    private void doDel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement("delete from emp where id=?");
            preparedStatement.setInt(1, Integer.parseInt(request.getParameter("id")));
            count = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection, preparedStatement);
        }
        if (count == 1) {
            response.sendRedirect(request.getContextPath() + "/emp/list");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void doAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> names = parameterMap.keySet();
        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            String[] vals = parameterMap.get(next);
            if("".equals(vals[0])) {
                response.sendRedirect(request.getContextPath() + "/error.jsp");
                return;
            }
        }
        Connection connection = DBUtils.getConnection();
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement("insert into emp values(?, ?, ?, ?)");
            preparedStatement.setInt(1, Integer.parseInt(request.getParameter("id")));
            preparedStatement.setString(2, request.getParameter("name"));
            preparedStatement.setDouble(3, Double.parseDouble(request.getParameter("salary")));
            preparedStatement.setString(4, request.getParameter("dept"));
            count = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection, preparedStatement);
        }
        if (count == 1) {
            response.sendRedirect(request.getContextPath() + "/emp/list");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void doList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Employee> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("select * from emp");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee emp = new Employee();
                emp.setId(resultSet.getInt("id"));
                emp.setName(resultSet.getString("name"));
                emp.setSalary(resultSet.getDouble("salary"));
                emp.setDept(resultSet.getString("dept"));
                list.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
        request.setAttribute("emps", list);
        request.getRequestDispatcher("/emp/list.jsp").forward(request, response);
    }
}
