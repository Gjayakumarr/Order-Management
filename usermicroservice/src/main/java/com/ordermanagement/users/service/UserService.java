package com.ordermanagement.users.service;

import com.ordermanagement.users.Model.UserDetailsV1;
import com.ordermanagement.users.response.UserResponse;

public interface UserService {

    public UserResponse addUser(UserDetailsV1 userDetails);

    public UserResponse updateUser(UserDetailsV1 userDetails);

    public UserResponse deActivate(int userId);

    public UserResponse activate(int userId);

    public UserResponse getUserDetails();

    public UserResponse updatePassword(String username, String oldPassword, String newPassword);

    String forgotPassword(String username);
}
