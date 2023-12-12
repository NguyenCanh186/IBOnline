package com.vmg.ibo.core.repository;

import com.vmg.ibo.core.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
    @Query(value = "SELECT u FROM User u WHERE (u.isAdminRoot = false OR u.isAdminRoot IS NULL) AND (:username IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%')))")
    Page<User> findAllUser(@Param("username") String username, Pageable pageable);
}
