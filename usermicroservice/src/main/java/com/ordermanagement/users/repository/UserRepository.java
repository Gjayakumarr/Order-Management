package com.ordermanagement.users.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ordermanagement.users.Model.UserDetailsV1;

import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<UserDetailsV1, Integer> {

    @Query(value = "SELECT u.* FROM users u WHERE u.username = :username", nativeQuery = true)
    Map<String, Object> findByEmail(@Param("username") String username);

    @Query(value = "select u from users u order by u.createdOn desc", nativeQuery = true)
    List<Map<String, Object>> getUserDetails();

    @Modifying
    @Transactional
    @Query("UPDATE users u SET u.isDeleted = true, u.deletedOn = CURRENT_TIMESTAMP WHERE u.id = :userId")
    int deleteUser(int userId);

    @Modifying
    @Transactional
    @Query("UPDATE users u SET u.isDeleted = false WHERE u.id = :userId")
    int activateUser(@Param("userId") int userId);

    @Modifying
    @Transactional
    @Query("UPDATE users u SET u.password = :passWord WHERE u.username = :username")
    int updatePassword(@Param("username") String username, @Param("passWord") String passWord);

    @Query(value = "SELECT password FROM users WHERE username = :username", nativeQuery = true)
    String getOldPassword(@Param("username") String username);

}
