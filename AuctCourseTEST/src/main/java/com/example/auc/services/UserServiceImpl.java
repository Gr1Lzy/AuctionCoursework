package com.example.auc.services;

import com.example.auc.inmemory.InMemoryData;
import com.example.auc.model.User;

import java.util.function.UnaryOperator;

public class UserServiceImpl implements UserService {

    InMemoryData db;
    UnaryOperator<String> passwordHasher;

    public UserServiceImpl(InMemoryData db, UnaryOperator<String> passwordHasher) {
        this.db = db;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public User getByLogin(String login) {
        return db.getUserByLogin(login);
    }

    @Override
    public User getUserById(Integer id) { return db.getUserById(id); }

    @Override
    public boolean checkPassword(User user, String password) {
        return user.getPasswordHash().equals(passwordHasher.apply(password));
    }
}

