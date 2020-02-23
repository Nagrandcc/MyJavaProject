package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    void save(User user);

    /**
     * 根据code查询用户
     * @param code
     * @return
     */
    public User findByCode(String code);

    /**
     * 激活用户
     *
     * @param code
     */
    void activeUser(String code);

    /**
     * 通过用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndePassword(String username, String password);

    /**
     * 查询用户激活状态
     * @param user
     * @return
     */
    User findByStatus (User user);

}
