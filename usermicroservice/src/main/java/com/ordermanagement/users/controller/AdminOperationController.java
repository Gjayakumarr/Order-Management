package com.ordermanagement.users.controller;

import com.ordermanagement.users.Model.UserDetailsV1;
import com.ordermanagement.users.response.UserResponse;
import com.ordermanagement.users.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/admin")
@RestController
public class AdminOperationController {
    private static final Logger logger = LogManager.getLogger(AdminOperationController.class);


    private final UserService userService;

    public AdminOperationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public UserResponse addUser(@RequestBody UserDetailsV1 userDetails) {
        logger.info("AdminOperationController /addUser api call with : {}", userDetails.toString());
        return userService.addUser(userDetails);
    }

    @PostMapping("/updateUser")
    public UserResponse updateUser(@RequestBody UserDetailsV1 userDetails) {
        logger.info("AdminOperationController /updateUser api call with : {}", userDetails.toString());
        return userService.updateUser(userDetails);
    }

    @GetMapping("/resetPassword/{username}")
    public Map<String, String> resetPassword(@PathVariable String username) {
        logger.info("Generating new password for user: " + username);

        String newPassword = userService.forgotPassword(username);

        Map<String, String> response = new HashMap<>();
        response.put("newPassword", newPassword);

        return response;
    }

    @PostMapping("/deactivate/{userId}")
    public UserResponse deActivateUser(@PathVariable int userId) {
        logger.info("AdminOperationController /deactivate api call with : {}", userId);
        return userService.deActivate(userId);
    }

    @PostMapping("/activate/{userId}")
    public UserResponse activateUser(@PathVariable int userId) {
        logger.info("AdminOperationController /activate api call with : {}", userId);
        return userService.activate(userId);
    }

    @GetMapping("/details")
    public UserResponse getUserDetails() {
        logger.info("AdminOperationController /deleteUser api call");
        return userService.getUserDetails();
    }


}
