package com.seeu.services;

import com.seeu.domains.Credentials;
import com.seeu.domains.User;

public interface UserService {
    User get(String userId) throws Exception;

    User getByEmail(String email) throws Exception;

    User signUp(Credentials credentials) throws Exception;

    void save(User user) throws Exception;

    void delete(String userId) throws Exception;
}
