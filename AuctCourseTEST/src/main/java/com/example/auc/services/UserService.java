package com.example.auc.services;

import com.example.auc.model.User;

public interface UserService {

    User getByLogin(String login);

    boolean checkPassword(User user, String password);
}