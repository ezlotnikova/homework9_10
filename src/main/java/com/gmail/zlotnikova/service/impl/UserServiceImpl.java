package com.gmail.zlotnikova.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gmail.zlotnikova.repository.ConnectionRepository;
import com.gmail.zlotnikova.repository.UserInformationRepository;
import com.gmail.zlotnikova.repository.UserRepository;
import com.gmail.zlotnikova.repository.impl.ConnectionRepositoryImpl;
import com.gmail.zlotnikova.repository.impl.UserInformationRepositoryImpl;
import com.gmail.zlotnikova.repository.impl.UserRepositoryImpl;
import com.gmail.zlotnikova.repository.model.User;
import com.gmail.zlotnikova.repository.model.UserInformation;
import com.gmail.zlotnikova.service.UserService;
import com.gmail.zlotnikova.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static UserService instance;
    private UserRepository userRepository;
    private UserInformationRepository userInformationRepository;
    private ConnectionRepository connectionRepository;

    private UserServiceImpl(
            ConnectionRepository connectionRepository,
            UserRepository userRepository,
            UserInformationRepository userInformationRepository
    ) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
        this.userInformationRepository = userInformationRepository;
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl(
                    ConnectionRepositoryImpl.getInstance(),
                    UserRepositoryImpl.getInstance(),
                    UserInformationRepositoryImpl.getInstance());
        }
        return instance;
    }

    @Override
    public UserDTO add(UserDTO addUserDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User databaseUser = convertDTOToDatabaseUser(addUserDTO);
                User addedUser = userRepository.add(connection, databaseUser);
                UserInformation databaseUserInformation = databaseUser.getUserInformation();
                databaseUserInformation.setUserId(addedUser.getId());
                userInformationRepository.add(connection, databaseUser.getUserInformation());
                UserDTO addedUserDTO = convertDatabaseUserToDTO(addedUser);
                connection.commit();
                return addedUserDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return addUserDTO;
    }

    @Override
    public List<UserDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.findAll(connection);
                List<UserDTO> userDTOList = new ArrayList<>();
                for (User user : users
                ) {
                    UserDTO userDTO = convertDatabaseUserToDTO(user);
                    userDTOList.add(userDTO);
                }
                connection.commit();
                return userDTOList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Integer deleteById(Long id) {
        Integer rowsDeleted = null;
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userInformationRepository.delete(connection, id);
                rowsDeleted = userRepository.delete(connection, id);
                connection.commit();
                return rowsDeleted;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return rowsDeleted;
    }

    private UserDTO convertDatabaseUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setActive(user.getActive());
        userDTO.setAge(user.getAge());
        if (user.getUserInformation() != null) {
            userDTO.setTelephone(user.getUserInformation().getTelephone());
            userDTO.setAddress(user.getUserInformation().getAddress());
        }
        return userDTO;
    }

    private User convertDTOToDatabaseUser(UserDTO userDTO) {
        User databaseUser = new User();
        databaseUser.setUsername(userDTO.getUsername());
        databaseUser.setPassword(userDTO.getPassword());
        databaseUser.setActive(userDTO.getActive());
        databaseUser.setAge(userDTO.getAge());

        UserInformation userInformation = new UserInformation();
        userInformation.setAddress(userDTO.getAddress());
        userInformation.setTelephone(userDTO.getTelephone());
        databaseUser.setUserInformation(userInformation);

        return databaseUser;
    }

}