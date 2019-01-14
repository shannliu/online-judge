package cn.edu.nju.software.judge.controller.user;

import cn.edu.nju.software.judge.beans.User;
import cn.edu.nju.software.judge.beans.UserExample;
import cn.edu.nju.software.judge.model.UserModel;
import cn.edu.nju.software.judge.service.user.UserService;
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
    @PostMapping("login.do")
    public String login(HttpServletRequest request, HttpServletResponse response,
                        @RequestBody UserModel model){
        //验证用户名和密码
        boolean exist = userService.loginIn(model);
        if (exist){
            HttpSession session = request.getSession();
            session.setAttribute("user",model);
            return "success";
        }else {
            return "error";
        }

    }
    @GetMapping("loginOut.do")
    public boolean loginOut(HttpServletRequest request, HttpServletResponse response,
                            @Param("username")String name){
        //先从数据库查对应的Usermodel
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        request.getSession().invalidate();
        return true;
    }

    @PostMapping("register.do")
    public String register(HttpServletRequest request, HttpServletResponse response,
                            @RequestBody UserModel model){
        String result = userService.register(model);
        return result;
    }

    @RequestMapping("getOrderUser.do")
    @ResponseBody
    public List<User> getOrderUserBySubmitAndSolved(UserExample example){
        example = new UserExample();
        example.setOrderByClause("solved desc,submit asc");
        return userService.getOrderUserlistBySubmitandSolved(example);
    }

}
