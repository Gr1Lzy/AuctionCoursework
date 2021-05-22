package com.example.auc.services;

import com.example.auc.model.User;

public interface UserService {

    User getByLogin(String login);

    User getUserById(Integer id);

    boolean checkPassword(User user, String password);
}