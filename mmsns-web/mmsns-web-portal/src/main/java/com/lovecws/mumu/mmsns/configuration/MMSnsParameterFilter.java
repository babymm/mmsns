package com.lovecws.mumu.mmsns.configuration;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 设置参数
 * @date 2017-12-21 16:33:
 */
public class MMSnsParameterFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        doFilter(servletRequest, servletResponse, filterChain);
    }

    @Override
    public void destroy() {

    }
}
