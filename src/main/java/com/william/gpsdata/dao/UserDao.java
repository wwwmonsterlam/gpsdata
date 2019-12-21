package com.william.gpsdata.dao;

import com.william.gpsdata.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    void deleteByName(String name);

    User findByName(String name);

    @Query(value = "SELECT COUNT(*) FROM users WHERE name=?", nativeQuery = true)
    int countByName(String name);
}
