package com.tutego.date4u.Service;

public class AdminUserService implements UserService{
    @Override
    public void createUser(String name) {
        System.out.println("Admin Created");
    }
}
