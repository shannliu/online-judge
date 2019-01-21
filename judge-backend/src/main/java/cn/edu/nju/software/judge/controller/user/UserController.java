package cn.edu.nju.software.judge.controller.user;


import cn.edu.nju.software.judge.beans.User;
import cn.edu.nju.software.judge.beans.UserExample;
import cn.edu.nju.software.judge.constant.UserConstants;
import cn.edu.nju.software.judge.model.UserModel;
import cn.edu.nju.software.judge.service.user.UserService;
import cn.edu.nju.software.judge.vo.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

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
@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 登录
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping("login.do")
    public Result login(HttpServletRequest request, HttpServletResponse response,
                        @RequestBody UserModel model){
        //验证用户名和密码
        try {
            UserModel userModel = userService.loginIn(model);
            HttpSession session = request.getSession();
            session.setAttribute(UserConstants.USER, userModel);
            return Result.success();
        }catch (Exception e){
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @param name
     * @return
     */
    @GetMapping("loginOut.do")
    public Result loginOut(HttpServletRequest request, HttpServletResponse response,
                            @Param("username")String name){
        try {
            //先从数据库查对应的Usermodel
            HttpSession session = request.getSession();
            session.removeAttribute(UserConstants.USER);
            request.getSession().invalidate();
            return Result.success();
        }catch (Exception e){
            return Result.failure("退出失败");
        }
    }

    /**
     * 注册
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping("register.do")
    public Result register(HttpServletRequest request, HttpServletResponse response,
                            @RequestBody UserModel model){
        try {
            final UserModel register = userService.register(model);
            if(null == register){
                return Result.failure("注册失败");
            }

            HttpSession session = request.getSession();
            session.setAttribute(UserConstants.USER, register);
            return Result.success();
        }catch (Exception e){
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 获取排名用户
     * @param example
     * @return
     */
    @RequestMapping("getOrderUser.do")
    public Result getOrderUserBySubmitAndSolved(UserExample example){
        example = new UserExample();
        example.setOrderByClause("solved desc,submit asc");
        List<User> users = userService.getOrderUserlistBySubmitandSolved(example);
        if (users != null){
            return Result.success(users);
        }
        return Result.success(null);
    }

}
