package cn.edu.nju.software.judge.aop;

import cn.edu.nju.software.judge.model.UserModel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录验证
 */
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserModel userModel = (UserModel) request.getSession().getAttribute("user");
        if (userModel == null){
            return false;
        }
        return true;
    }
}
