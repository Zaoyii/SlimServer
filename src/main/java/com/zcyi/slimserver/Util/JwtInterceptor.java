package com.zcyi.slimserver.Util;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcyi.slimserver.Base.ApiResult;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截请求 判断请求头里的token是否合法
 *
 * @author ZaoYi
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头中的令牌
        String token = request.getHeader("Authorization");
        String s;
        try {
            UtilMethod.verify(token);
            return true;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            s = new ObjectMapper().writeValueAsString(ApiResult.failed("无效签名"));
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            s = new ObjectMapper().writeValueAsString(ApiResult.failed("token过期"));
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            s = new ObjectMapper().writeValueAsString(ApiResult.failed("token算法不一致"));

        } catch (Exception e) {
            s = new ObjectMapper().writeValueAsString(ApiResult.failed("token无效"));
            e.printStackTrace();
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(s);
        return false;
    }

}
