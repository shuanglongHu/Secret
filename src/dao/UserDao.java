package dao;

import domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import tools.JDBCUtils;

import java.sql.SQLException;

public class UserDao {
    private QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

    public int register(String username, String password) {
        String sql = "INSERT INTO user(username,password) VALUES(?,?);";
        Object[] params = {username, password};
        int res = 0;
        try {
            res = queryRunner.execute(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("用户注册出错！");
        }
        return res;
    }

    public User findUser(String username) {
        String sql = "SELECT * from user where username = (?);";
        Object[] params = {username};
        User user = null;
        try {
            user = queryRunner.query(sql, new BeanHandler<>(User.class), params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查找用户出错！");
        }
        return user;
    }
}
