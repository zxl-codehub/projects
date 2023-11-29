package filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class CheckLoginOrNotFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        String requestPath = request.getServletPath();
//        System.out.println(requestPath);
        if ("/login.jsp".equals(requestPath) ||
                "/md5.min.js".equals(requestPath) ||
                "/user/login".equals(requestPath) ||
                "/user/logout".equals(requestPath) ||
                session.getAttribute("name") != null) {
            chain.doFilter(request, response);
        }else {
            response.sendRedirect(request.getContextPath());
        }
    }
}
