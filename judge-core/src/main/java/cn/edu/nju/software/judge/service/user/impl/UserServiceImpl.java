package cn.edu.nju.software.judge.service.user.impl;

import cn.edu.nju.software.judge.beans.User;
import cn.edu.nju.software.judge.beans.UserExample;
import cn.edu.nju.software.judge.dao.UserMapper;
import cn.edu.nju.software.judge.model.UserModel;
import cn.edu.nju.software.judge.service.user.UserService;
import cn.edu.nju.software.judge.utils.GUIDUtils;
import cn.edu.nju.software.judge.utils.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
@Service
@Transactional
public class UserServiceImpl implements UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserMapper userMapper;

    public UserModel loginIn(UserModel model) {
        String name = model.getUserName();
        String password = model.getPassword();
        //先验证用户名是否存在
        final User user = userMapper.findUser(name);
        if (null == user) {
            throw new RuntimeException("用户不存在");
        }

        final String md5_password = user.getPassword();

        final String md5Salt = user.getMd5Salt();

        final String encrypt = Md5Utils.encrypt(password + md5Salt);

        if (!StringUtils.equals(encrypt, md5_password)) {
            throw new RuntimeException("密码错误");
        }

        return PO2Model(user);

    }

    /**
     * @param model
     * @return
     */
    @Override
    public UserModel register(UserModel model) {
        String username = model.getUserName();
        User exist = userMapper.findUser(username);
        if (exist != null) {
            throw new RuntimeException("This UserName is already exist!");
        }

        User temp = new User();
        temp.setUserName(model.getUserName());
        temp.setNickName(model.getNickName());

        final String guid = GUIDUtils.getGUID();

        temp.setMd5Salt(guid);
        final String md5_password = Md5Utils.encrypt(model.getPassword() + guid);
        temp.setPassword(md5_password);
        temp.setEmail(model.getEmail());
        temp.setSolved(0);
        temp.setSubmit(0);
        final LocalDateTime now = LocalDateTime.now();
        temp.setAddDatetime(now);
        temp.setCreateTime(now);
        temp.setUpdateDatetime(now);
        temp.setDel(-1);
        userMapper.insert(temp);
        return PO2Model(temp);
    }

    @Override
    public List<User> getOrderUserlistBySubmitandSolved(UserExample example) {
        return userMapper.selectByExample(example);
    }


    private UserModel PO2Model(User user) {
        if (null == user) {
            return null;
        }
        try {
            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(user, userModel);
            return userModel;
        } catch (Exception e) {
            LOGGER.error("PO2Model error", e);
        }
        return null;
    }

}
