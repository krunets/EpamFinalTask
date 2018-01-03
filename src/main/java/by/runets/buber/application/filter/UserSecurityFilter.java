package by.runets.buber.application.filter;

import by.runets.buber.domain.entity.User;
import by.runets.buber.infrastructure.constant.JspPagePath;
import by.runets.buber.infrastructure.constant.RequestParameter;
import by.runets.buber.infrastructure.constant.UserRoleType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = { "/index.jsp" }, servletNames = { "controller" })
public class UserSecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession(false);

        User user = null;

        if (session != null){
            user = (User) session.getAttribute(UserRoleType.USER);
            if (user != null && user.getRole().getRoleName().equalsIgnoreCase(UserRoleType.ADMIN)){
                session.getServletContext().getRequestDispatcher(JspPagePath.ADMIN_HOME_PAGE).forward(req, res);
            }
            if (user != null && user.getRole().getRoleName().equalsIgnoreCase(UserRoleType.DRIVER)){
                session.getServletContext().getRequestDispatcher(JspPagePath.DRIVER_HOME_PAGE).forward(req, res);
            }
            if (user != null && user.getRole().getRoleName().equalsIgnoreCase(UserRoleType.PASSENGER)){
                session.getServletContext().getRequestDispatcher(JspPagePath.PASSENGER_HOME_PAGE).forward(req, res);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
