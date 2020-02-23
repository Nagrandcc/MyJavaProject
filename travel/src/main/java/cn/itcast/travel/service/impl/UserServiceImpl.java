package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @Override
    public Boolean regist(User user) {
        User u = userDao.findByUsername(user.getUsername());
        if (u != null) {
            //用户名存在,注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        userDao.save(user);
        //3.激活邮件发送
        String content = "<a href ='http://localhost/travel/user/active?code=" + user.getCode() + "'>点击激活[纯真旅游网]</a>";
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        return true;

    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public Boolean activeUser(String code) {
        User userbycode = userDao.findByCode(code);
        if (userbycode != null) {
            //激活
            userDao.activeUser(code);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndePassword(user.getUsername(),user.getPassword());
    }
}
