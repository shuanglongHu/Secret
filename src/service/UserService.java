package service;

import dao.UserDao;
import domain.User;

public class UserService {
    private UserDao userDao = new UserDao();

    public int register (String username, String password) {
        return userDao.register(username, password);
    }


    public User findUser(String username) {
        return userDao.findUser(username);
    }
}
