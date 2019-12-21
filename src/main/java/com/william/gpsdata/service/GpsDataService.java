package com.william.gpsdata.service;

import com.william.gpsdata.dao.DataDao;
import com.william.gpsdata.dao.UserDao;
import com.william.gpsdata.dto.DataDto;
import com.william.gpsdata.dto.UserDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GpsDataService {

    private static final Log log = LogFactory.getLog(GpsDataService.class);

    @Autowired
    private DataDao dataDao;
    @Autowired
    private UserDao userDao;

    public UserDto getUser(String name) {
        return BeanConverter.toUserDto(userDao.findByName(name));
    }

    public List<UserDto> getAllUsers() {
        return BeanConverter.toUserDto(userDao.findAll());
    }

    @Transactional
    public String createUser(UserDto userDto) {
        userDao.save(BeanConverter.toUser(userDto));
        return "Successful created!";
    }

    @Transactional
    public String updateUser(String name, UserDto userDto) {
        userDao.deleteByName(name);
        userDao.save(BeanConverter.toUser(userDto));
        return "Successfully updated!";
    }

    @Transactional
    public String deleteUser(String name) {
        userDao.deleteByName(name);
        return "Successfully deleted!";
    }

    @Transactional
    public String createData(DataDto dataDto) {
        log.debug("find if user name exists...");
        if(!isUserExist(dataDto.getUserName())) {
            return "Failure: user dose not exist :(";
        }
        log.debug("user name exists");
        dataDao.save(BeanConverter.toData(dataDto));
        return "Successful added!";
    }

    public boolean isUserExist(String name) {
        return userDao.countByName(name) == 1;
    }

    public List<DataDto> getDataByUserName(String userName) {
        if(log.isDebugEnabled()) {
            log.debug("getting user " + userName + "'s location data...");
        }

        return BeanConverter.toDataDto(dataDao.findAllByUserName(userName));
    }

    public List<DataDto> getDataByUserNameWithAmount(String userName, int amount) {
        return BeanConverter.toDataDto(dataDao.findAllByUserNameWithAmount(userName, amount));
    }
}
