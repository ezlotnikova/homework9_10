package com.gmail.zlotnikova.repository.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.zlotnikova.repository.ConnectionRepository;
import com.gmail.zlotnikova.util.PropertyUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.zlotnikova.repository.constant.ConnectionConstant.DATABASE_PASSWORD;
import static com.gmail.zlotnikova.repository.constant.ConnectionConstant.DATABASE_URL;
import static com.gmail.zlotnikova.repository.constant.ConnectionConstant.DATABASE_USERNAME;
import static com.gmail.zlotnikova.repository.constant.ConnectionConstant.PREPARED_STATEMENTS_CACHE_ENABLE;
import static com.gmail.zlotnikova.repository.constant.ConnectionConstant.PREPARED_STATEMENTS_CACHE_LIMIT;
import static com.gmail.zlotnikova.repository.constant.ConnectionConstant.PREPARED_STATEMENTS_CACHE_SIZE;

public class ConnectionRepositoryImpl implements ConnectionRepository {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static HikariDataSource ds;
    private static ConnectionRepository instance;

    private ConnectionRepositoryImpl() {
    }

    public static ConnectionRepository getInstance() {
        if (instance == null) {
            instance = new ConnectionRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (ds == null) {
            try {
                Class.forName(MYSQL_JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage(), e);
            }
            PropertyUtil propertyUtil = PropertyUtil.getInstance();
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(propertyUtil.getProperty(DATABASE_URL));
            config.setUsername(propertyUtil.getProperty(DATABASE_USERNAME));
            config.setPassword(propertyUtil.getProperty(DATABASE_PASSWORD));
            config.addDataSourceProperty(PREPARED_STATEMENTS_CACHE_ENABLE,
                    propertyUtil.getProperty(PREPARED_STATEMENTS_CACHE_ENABLE));
            config.addDataSourceProperty(PREPARED_STATEMENTS_CACHE_SIZE,
                    propertyUtil.getProperty(PREPARED_STATEMENTS_CACHE_SIZE));
            config.addDataSourceProperty(PREPARED_STATEMENTS_CACHE_LIMIT,
                    propertyUtil.getProperty(PREPARED_STATEMENTS_CACHE_LIMIT));
            ds = new HikariDataSource(config);
        }
        return ds.getConnection();
    }

}