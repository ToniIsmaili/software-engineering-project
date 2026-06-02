package com.seeu.services;

import com.seeu.domains.Retailer;
import com.seeu.domains.Scraper;

public interface ScraperService {
    Scraper get(String retailerId) throws Exception;

    void save(Retailer retailer) throws Exception;

    void delete(String retailerId) throws Exception;
}
