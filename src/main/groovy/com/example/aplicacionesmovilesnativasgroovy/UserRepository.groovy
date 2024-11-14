package com.example.aplicacionesmovilesnativasgroovy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

interface UserRepository extends JpaRepository<User, Long> {

 @Query("SELECT u FROM User u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.role WHERE u.username = :username")
    User findByUsernameWithRoles(@Param("username") String username)

 @Query("SELECT u FROM User u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.role")
    List<User> findAllWithRoles()

    User findByUsername(String username);


}
