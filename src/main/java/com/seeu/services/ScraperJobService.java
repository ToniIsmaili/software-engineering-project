package com.seeu.services;

import com.seeu.domains.ScraperJob;
import com.seeu.domains.ScraperLog;

import java.util.List;

public interface ScraperJobService {
    List<ScraperJob> getByRetailerId(String retailerId) throws Exception;

    void startJob(String retailerId) throws Exception;

    void endJob(String retailerId, String jobId, String status) throws Exception;

    List<ScraperLog> getLogs(String jobId) throws Exception;

    void addLog(ScraperLog scraperLog) throws Exception;
}
