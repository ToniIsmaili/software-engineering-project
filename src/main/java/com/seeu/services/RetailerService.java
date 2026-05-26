package com.seeu.services;

import com.seeu.domains.Retailer;

import java.util.List;

public interface RetailerService {
    List<Retailer> getAll() throws Exception;

    void save(Retailer retailer) throws Exception;

    void delete(String id) throws Exception;
}
