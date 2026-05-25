package com.seeu;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataSource.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DataSource.close();
    }
}
