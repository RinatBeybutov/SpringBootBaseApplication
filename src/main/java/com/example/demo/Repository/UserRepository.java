package com.example.demo.Repository;

import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(nativeQuery = true, value = "select * from users order by id")
    List<User> findAllAsk();

    @Query(nativeQuery = true, value = "select * from users where name=:name limit 1")
    User findAllByName(String name);
}
