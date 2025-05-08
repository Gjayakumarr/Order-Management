package com.ordermanagement.users.service.serviceImpl;

import com.ordermanagement.users.Model.UserDetailsV1;
import com.ordermanagement.users.dao.UserDao;
import com.ordermanagement.users.response.UserResponse;
import com.ordermanagement.users.service.UserService;
import com.ordermanagement.users.util.Constant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Override
    public UserResponse addUser(UserDetailsV1 userDetails) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userDetails.getPassword());
        userDetails.setPassword(encodedPassword);
        UserDetailsV1 result = userDao.addUser(userDetails);
        UserResponse response = new UserResponse();
        response.setStatusCode(Constant.RESPONSE_CODE.CREATED);
        response.setMessage(Constant.RESPONSE_MESSAGES.CREATED);
        response.setSuccess(Constant.TRUE);
        logger.info("Response :: {}",response.toString());
        return response;
    }

    @Override
    public UserResponse updateUser(UserDetailsV1 userDetails) {
        UserDetailsV1 result = userDao.updateUser(userDetails);
        UserResponse response = new UserResponse();
        response.setStatusCode(Constant.RESPONSE_CODE.SUCCESS);
        response.setMessage(Constant.RESPONSE_MESSAGES.UPDATION);
        response.setSuccess(Constant.TRUE);
        logger.info("Response :: {}",response.toString());
        return response;
    }

    @Override
    public UserResponse deActivate(int userId) {
        int result = userDao.deleteUser(userId);
        UserResponse response = new UserResponse();
        if (result > 0) {
            response.setStatusCode(Constant.RESPONSE_CODE.SUCCESS);
            response.setMessage(Constant.RESPONSE_MESSAGES.SUCCESS_DELETE);
            response.setSuccess(Constant.TRUE);
        } else {
            response.setStatusCode(Constant.RESPONSE_CODE.BAD_REQUEST);
            response.setMessage(Constant.RESPONSE_MESSAGES.FAILURE_DELETE);
            response.setSuccess(Constant.FALSE);
        }
        logger.info("Response :: {}",response.toString());
        return response;
    }

    @Override
    public UserResponse activate(int userId) {
        int result = userDao.activate(userId);
        UserResponse response = new UserResponse();
        if (result > 0) {
            response.setStatusCode(Constant.RESPONSE_CODE.SUCCESS);
            response.setMessage(Constant.RESPONSE_MESSAGES.UPDATION);
            response.setSuccess(Constant.TRUE);
        } else {
            response.setStatusCode(Constant.RESPONSE_CODE.BAD_REQUEST);
            response.setMessage(Constant.RESPONSE_MESSAGES.UPDATION_FAILED);
            response.setSuccess(Constant.FALSE);
        }
        logger.info("Response :: {}",response.toString());
        return response;
    }

    @Override
    public UserResponse getUserDetails() {
        List<Map<String, Object>> userDetails = userDao.getUserDetails();
        UserResponse response = new UserResponse();
        response.setStatusCode(Constant.RESPONSE_CODE.SUCCESS);
        response.setMessage(Constant.RESPONSE_MESSAGES.SUCCESS);
        response.setListOfUsers(userDetails);
        response.setSuccess(Constant.TRUE);
        logger.info("Response :: {}",response.toString());
        return response;
    }

    @Override
    public UserResponse updatePassword(String username, String oldPassword, String newPassword) {
        int result = userDao.updatePassword(username, oldPassword, newPassword);
        UserResponse response = new UserResponse();
        if (result == -1) {
            response.setStatusCode(Constant.RESPONSE_CODE.SUCCESS);
            response.setMessage("Incorrect Old Password");
            response.setSuccess(Constant.FALSE);
        } else if (result > 0) {
            response.setStatusCode(Constant.RESPONSE_CODE.SUCCESS);
            response.setMessage(Constant.RESPONSE_MESSAGES.UPDATION);
            response.setSuccess(Constant.TRUE);
        } else {
            response.setStatusCode(Constant.RESPONSE_CODE.BAD_REQUEST);
            response.setMessage(Constant.RESPONSE_MESSAGES.UPDATION_FAILED);
            response.setSuccess(Constant.FALSE);
        }
        logger.info("Response :: {}",response.toString());
        return response;
    }

    @Override
    public String forgotPassword(String username) {
        return userDao.forgotPassword(username);

    }
}
