package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (DataAccessException e) {

        }
        return user;
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @Override
    public void save(User user) {
        String sql = "INSERT INTO tab_user(username,password,name,birthday,sex,telephone,email,status,code) VALUES (?,?,?,?,?,?,?,?,?)";
        template.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    /**
     * 根据code查用户
     *
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {

        }
        return user;
    }

    /**
     * 激活用户
     *
     * @param code
     */
    @Override
    public void activeUser(String code) {
        String sql = "UPDATE tab_user SET status='Y' WHERE code=?";
        template.update(sql, code);
    }

    /**
     * 通过用户名密码查询user
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndePassword(String username, String password) {
        User user1 = null;
        try {
            String sql = "select * from tab_user where username = ? AND password=?";
            user1 = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (DataAccessException e) {

        }
        return user1;
    }

    /**
     * 查询用户激活状态
     *
     * @param user
     * @return
     */
    @Override
    public User findByStatus(User user) {
        User user1 = null;
        try {
            String sql = "select * from tab_user where username = ? AND password=? AND status=?";
            user1 = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword(), 'Y');
        } catch (DataAccessException e) {

        }
        return user1;
    }
}
