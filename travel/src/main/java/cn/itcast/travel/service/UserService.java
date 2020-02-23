package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    Boolean regist(User user);

    /**
     * 激活用户
     * @param code
     */
    Boolean activeUser(String code);

    /**
     * 登录用户
     * @param user
     * @return
     */
    User login(User user);
}
