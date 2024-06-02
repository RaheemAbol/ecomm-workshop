package org.abol.springstarter.services;

import org.abol.springstarter.models.BaseUser;

import java.util.List;

public interface UserService {
    void saveUser(BaseUser user);
    List<BaseUser> getAllUsers();
    BaseUser getUserById(int id);
    void deleteUser(int id);
    BaseUser findByEmail(String email);
}
