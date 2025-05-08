package com.ordermanagement.users.dao.daoImpl;

import com.ordermanagement.users.Model.UserDetailsV1;
import com.ordermanagement.users.dao.UserDao;
import com.ordermanagement.users.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetailsV1 addUser(UserDetailsV1 userDetails) {
        return repository.save(userDetails);
    }

    @Override
    public UserDetailsV1 updateUser(UserDetailsV1 userDetails) {
        return repository.save(userDetails);
    }

    @Override
    public int deleteUser(int userId) {
        return repository.deleteUser(userId);
    }

    @Override
    public int activate(int userId) {
        return repository.activateUser(userId);
    }

    @Override
    public List<Map<String, Object>> getUserDetails() {
        return repository.getUserDetails();
    }

    @Override
    public int updatePassword(String username, String oldPassword, String newPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String matcher = repository.getOldPassword(username);
        return !passwordEncoder.matches(oldPassword, matcher) ? -1 : repository.updatePassword(username, passwordEncoder.encode(newPassword));
    }

    @Override
    public String forgotPassword(String username) {
        String temporaryPassword = generateTemporaryPassword();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(temporaryPassword);

        repository.updatePassword(username, encodedPassword);

        return temporaryPassword;
    }

    private String generateTemporaryPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
