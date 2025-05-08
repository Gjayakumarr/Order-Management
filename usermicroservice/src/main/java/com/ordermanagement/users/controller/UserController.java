package com.ordermanagement.users.controller;

import com.ordermanagement.users.response.UserResponse;
import com.ordermanagement.users.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/updatePassword")
    public UserResponse updatePassword(@RequestParam("username") String username, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        logger.info("UserController /updatePassword api call with : {}", username);
        return userService.updatePassword(username, oldPassword, newPassword);
    }
}
