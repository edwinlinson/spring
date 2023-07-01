package com.adith.smartcontactmanager.repository;


import com.adith.smartcontactmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select u from User u where u.email= :e")
    User getUserByUserName(@Param("e") String email);


   User findUserByEmail(String email);

    User findUserById(Integer id);

}
