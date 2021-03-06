package me.smn.example.idgen.service;

import me.smn.example.idgen.MyConfig;
import me.smn.idgen.TypeIdGenerator;
import me.smn.idgen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    TypeIdGenerator typeIdGenerator;

    public Long createUser(String nick){
        User u = User.builder().id(typeIdGenerator.generateId())
                .nick(nick).build();

        //save user to store

        return u.getId();
    }
}
