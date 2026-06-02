package com.seeu.services;

import com.seeu.domains.Retailer;
import com.seeu.domains.Scraper;

public class ScraperServiceImpl implements ScraperService {

    @Override
    public Scraper get(String retailerId) throws Exception {
        return null;
    }

    @Override
    public void save(Retailer retailer) throws Exception {
    }

    @Override
    public void delete(String retailerId) throws Exception {
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
