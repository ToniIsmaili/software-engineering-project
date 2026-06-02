package com.seeu.services;

import com.seeu.domains.User;

public class UserServiceImpl implements UserService {

    @Override
    public User get(String userId) throws Exception {
        return null;
    }

    @Override
    public void save(User user) throws Exception {
    }

    @Override
    public void delete(String userId) throws Exception {
    }

    public static class SQL {
        public static final String GET_BY_ID = """
                """;

        public static final String SAVE = """
                """;

        public static final String DELETE = """
                """;
    }
}
