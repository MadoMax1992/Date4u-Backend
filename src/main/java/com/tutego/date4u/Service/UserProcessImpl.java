package com.tutego.date4u.Service;

import org.springframework.beans.factory.annotation.Autowired;

public class UserProcessImpl implements UserProcessor{

    private UserService userService;

    @Autowired
    public void setUserService (UserService userService) {
        this.userService = userService;
    }

    @Override
    public void createUser(String name) {
        userService.createUser(name);

    }
}
