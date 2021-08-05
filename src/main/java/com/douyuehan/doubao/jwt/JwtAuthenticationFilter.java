package com.douyuehan.doubao.jwt;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//OncePerRequestFilter请求过滤器的类
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    //请求进来了需要先经过doFilterInternal
    @Override
    protected void doFilterInternal(HttpServletRequest request, //代表请求
                                    HttpServletResponse response, //代表回应
                                    FilterChain filterChain) //过滤器链，自定义拦截多次
            throws ServletException, IOException {

        try {
            if(isProtectedUrl(request)) { //判断请求的url是不是受保护的url，受保护的请求需要提供token
//                System.out.println(request.getMethod());
                if(!request.getMethod().equals("OPTIONS"))
                    request = JwtUtil.validateTokenAndAddUserIdToHeader(request); //校验token并且添加到头信息
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response); //通过过滤器，进行后续操作
    }

    //定义受保护的url
    private boolean isProtectedUrl(HttpServletRequest request) {
        List<String> protectedPaths = new ArrayList<String>();
        //受保护的请求添加进入到列表中
        protectedPaths.add("/ums/user/info");
        protectedPaths.add("/ums/user/update");
        protectedPaths.add("/post/create");
        protectedPaths.add("/post/update");
        protectedPaths.add("/post/delete/*");
        protectedPaths.add("/comment/add_comment");
        protectedPaths.add("/relationship/subscribe/*");
        protectedPaths.add("/relationship/unsubscribe/*");
        protectedPaths.add("/relationship/validate/*");

        boolean bFind = false;
        for( String passedPath : protectedPaths ) {
            bFind = pathMatcher.match(passedPath, request.getServletPath());
            if( bFind ) {
                break;
            }
        }
        return bFind;
    }

}