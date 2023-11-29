package filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckPageExistOrNotFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String requestPath = request.getServletPath();
        System.out.println("服务器时间"+ new SimpleDateFormat("HH:mm:ss").format(new Date()) + "，收到客户端"+request.getRemoteAddr()+"发来的请求路径：" + requestPath);
        switch (requestPath) {
            case "/login.jsp":
            case "/md5.min.js":
            case "/user/login":
            case "/user/logout":
            case "/emp/list":
            case "/emp/add":
            case "/emp/del":
            case "/emp/update":
            case "/emp/display":
            case "/emp/list.jsp":
            case "/emp/add.jsp":
            case "/emp/update.jsp":
            case "/emp/display.jsp":
            case "/error.jsp":
                chain.doFilter(request, response);
                break;
            default:
                request.getRequestDispatcher("/notExistPage.jsp").forward(request, response);
        }
    }
}
