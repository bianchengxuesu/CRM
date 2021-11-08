package com.bjpowernode.crm.web.filter;

import com.bjpowernode.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入到验证是否登录过的过滤器");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getServletPath();
        //不应该拦截的资源，直接放行
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)) {
            filterChain.doFilter(req,resp);

        } else {
            //验证其他资源
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            if (user != null) {
                //不空说明登录过，放行
                filterChain.doFilter(servletRequest,servletResponse);
            } else {
                //没登录过，重定向到登录页
            /*
                重定向的路径怎么写?
                在实际项目开发中，对于路径的使用，不论操作的是前端还是后端，应该一律使用绝对路径关于转发和重定向的路径的写法如下:
            转发:
                使用的是一种特殊的绝对路径的使用方式，这种绝对路径前面不加/项目名，这种路径也称之为内部路径
                /Login.jsp
            重定向:
                使用的是传统绝对路径的写法，前面必须以/项目名开头，后面跟具体的资源路径/ crm/Login.jsp
                为什么使用重定向，使用转发不行吗?
                转发之后，路径会停留在老路径上，而不是跳转之后最新资源的路径
                我们应该在为用户跳转到登录页的同时，将浏览器的地址栏应该自动设置为当前的登录页的路径

                ${pageContext.request.getContextPath}  获取项目名
             */
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
            }
        }



    }

    @Override
    public void destroy() {

    }
}
