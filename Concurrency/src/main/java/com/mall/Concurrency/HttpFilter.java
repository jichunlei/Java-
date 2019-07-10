package com.mall.Concurrency;

import com.mall.Concurrency.threadlocal.RequestHolder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/10 08:44
 * @Description: 过滤器
 */
@Slf4j
public class HttpFilter implements Filter {
    public void destroy() {
        log.info("destroy()...");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        log.info("do filter, {}, {}", Thread.currentThread().getId(), request.getServletPath());
        RequestHolder.add(Thread.currentThread().getId());
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        log.info("init(..)...");
    }

}
