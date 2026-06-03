package com.seeu.services;

import com.seeu.domains.Credentials;

public interface AuthService {
    boolean authenticate(Credentials credentials) throws Exception;
}
