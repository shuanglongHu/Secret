package controller;

import domain.User;
import service.UserService;

public class UserController {
    private UserService userService = new UserService();

    public int register(String username, String password) {
        return userService.register(username, password);
    }

    public User findUser(String username) {
        return userService.findUser(username);
    }
}
