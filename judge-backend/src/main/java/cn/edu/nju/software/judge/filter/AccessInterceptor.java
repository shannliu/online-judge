package cn.edu.nju.software.judge.filter;

import cn.edu.nju.software.judge.annotation.LoginRequired;
import cn.edu.nju.software.judge.constant.UserConstants;
import cn.edu.nju.software.judge.model.ContextHolder;
import cn.edu.nju.software.judge.vo.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_                               //
 * //                         o8888888o                              //
 * //                         88" . "88                              //
 * //                         (| ^_^ |)                              //
 * //                         O\  =  /O                              //
 * //                      ____/`---'\____                           //
 * //                    .'  \\|     |//  `.                         //
 * //                   /  \\|||  :  |||//  \                        //
 * //                  /  _||||| -:- |||||-  \                       //
 * //                  |   | \\\  -  /// |   |                       //
 * //                  | \_|  ''\---/''  |   |                       //
 * //                  \  .-\__  `-`  ___/-. /                       //
 * //                ___`. .'  /--.--\  `. . ___                     //
 * //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
 * //      ========`-.____`-.___\_____/___.-`____.-'========         //
 * //                           `=---='                              //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
 * //            佛祖保佑       永不宕机     永无BUG                    //
 * ////////////////////////////////////////////////////////////////////
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final HttpSession session = request.getSession();
        final Object attribute = session.getAttribute(UserConstants.USER);
        if(null != attribute) {
            ContextHolder.setUserContext(attribute);
        }
        if(handler instanceof HandlerMethod){

            HandlerMethod method = (HandlerMethod) handler;

            final LoginRequired methodLoginRequired = method.getMethodAnnotation(LoginRequired.class);

            if(null == methodLoginRequired){
                //如果该方法没有加是否登录的逻辑判断，则读取其类上面的注解
                final LoginRequired classLoginRequired = method.getBeanType().getAnnotation(LoginRequired.class);
                if(null == classLoginRequired || !classLoginRequired.value()){
                    return true;
                }
                //必须登录
                return isNotLogin(attribute,response);
            }
            //如果方法有注解，则以方法为准
            if(!methodLoginRequired.value()){
                return true;
            }
            return isNotLogin(attribute,response);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private boolean isNotLogin(Object attribute, HttpServletResponse response) throws Exception{
        if(null == attribute){
            Result result = Result.notLogin();
            response.getWriter().write(JSON.toJSONString(result));
            return false;
        }
        return true;
    }
}
