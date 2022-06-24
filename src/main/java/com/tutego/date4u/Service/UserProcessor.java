package com.tutego.date4u.Service;

import org.springframework.stereotype.Component;

@Component
public interface UserProcessor {
    public void createUser(String name);
}
