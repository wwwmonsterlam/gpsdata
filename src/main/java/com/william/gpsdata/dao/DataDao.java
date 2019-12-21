package com.william.gpsdata.dao;

import com.william.gpsdata.po.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDao extends JpaRepository<Data, Integer> {

    @Modifying
    @Query(value = "SELECT * FROM data WHERE user_name=? ORDER BY id DESC", nativeQuery = true)
    List<Data> findAllByUserName(String userName);

    @Query(value = "SELECT * FROM data WHERE user_name=?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
    List<Data> findAllByUserNameWithAmount(String userName, int amount);
}
