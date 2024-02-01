package com.vmg.ibo.core.repository;

import com.vmg.ibo.core.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findByEmail(@Param("email") String email);
    @Query(value = "SELECT u FROM User u WHERE (u.isAdminRoot = false OR u.isAdminRoot IS NULL) AND (u.channelId = 0) AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    Page<User> findAllUser(@Param("email") String email, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.id = :roleId")
    List<User> findUsersByRoleId(@Param("roleId") Long roleId);

    Optional<User> findByEmailAndIsActive(String email, boolean isActive);

}
