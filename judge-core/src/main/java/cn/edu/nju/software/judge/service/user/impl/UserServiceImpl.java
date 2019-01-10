package cn.edu.nju.software.judge.service.user.impl;

import cn.edu.nju.software.judge.beans.User;
import cn.edu.nju.software.judge.dao.UserMapper;
import cn.edu.nju.software.judge.model.UserModel;
import cn.edu.nju.software.judge.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    public boolean loginIn(UserModel model){
        String name = model.getUser();
        String password = model.getPassword();
        //先验证用户名是否存在
        String nameResult = userMapper.findUsername(name);
        if (nameResult == null){
            return false;
        }else {
            //若存在验证和密码是否匹配 匹配返回true 否则返回false
            User user = userMapper.findUser(name);
            return password.equals(user.getPassword());
        }
    }

    /**
     *
     * @param model
     * @return
     */
    @Override
    public String register(UserModel model){
        String username = model.getUser();
        User exist = userMapper.findUser(username);
        if (exist != null){
            return "This UserName is already exist!";
        }else {
            User temp = new User();
            temp.setUserName(model.getUser());
            temp.setPassword(model.getPassword());
            temp.setEmail(model.getEmail());
            Integer result = userMapper.insert(temp);
            if (result==null){
                return "Register fail!";
            }
        }
        return "Success";
    }

}
