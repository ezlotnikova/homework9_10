package com.gmail.zlotnikova.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.zlotnikova.repository.ConnectionRepository;
import com.gmail.zlotnikova.repository.TableRepository;
import com.gmail.zlotnikova.repository.enums.CreateActionEnum;
import com.gmail.zlotnikova.repository.enums.DropActionEnum;
import com.gmail.zlotnikova.repository.impl.ConnectionRepositoryImpl;
import com.gmail.zlotnikova.repository.impl.TableRepositoryImpl;
import com.gmail.zlotnikova.service.TableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TableServiceImpl implements TableService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static TableService instance;
    private TableRepository tableRepository;
    private ConnectionRepository connectionRepository;
    private Boolean tablesDeleted;
    private Boolean tablesCreated;

    private TableServiceImpl(ConnectionRepository connectionRepository, TableRepository tableRepository,
            Boolean tablesDeleted, Boolean tablesCreated) {
        this.connectionRepository = connectionRepository;
        this.tableRepository = tableRepository;
        this.tablesDeleted = tablesDeleted;
        this.tablesCreated = tablesCreated;
    }

    public static TableService getInstance() {
        if (instance == null) {
            instance = new TableServiceImpl(
                    ConnectionRepositoryImpl.getInstance(),
                    TableRepositoryImpl.getInstance(),
                    false,
                    false);
        }
        return instance;
    }

    @Override
    public void deleteAllTables() {
        if (!tablesDeleted) {
            try (Connection connection = connectionRepository.getConnection()) {
                connection.setAutoCommit(false);
                try {
                    for (DropActionEnum action : DropActionEnum.values()) {
                        tableRepository.executeQuery(connection, action.getQuery());
                    }
                    connection.commit();
                    tablesDeleted = true;
                } catch (SQLException e) {
                    connection.rollback();
                    logger.error(e.getMessage(), e);
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void createAllTables() {
        if (!tablesCreated) {
            try (Connection connection = connectionRepository.getConnection()) {
                connection.setAutoCommit(false);
                try {
                    for (CreateActionEnum action : CreateActionEnum.values()) {
                        tableRepository.executeQuery(connection, action.getQuery());
                    }
                    connection.commit();
                    tablesCreated = true;
                } catch (SQLException e) {
                    connection.rollback();
                    logger.error(e.getMessage(), e);
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}