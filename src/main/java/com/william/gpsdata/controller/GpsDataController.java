package com.william.gpsdata.controller;

import com.william.gpsdata.dto.DataDto;
import com.william.gpsdata.dto.UserDto;
import com.william.gpsdata.service.GpsDataService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gpsdata")
public class GpsDataController {

    private static final Log log = LogFactory.getLog(GpsDataController.class);

    @Autowired
    private GpsDataService gpsDataService;

    @PostMapping("/createUser")
    public String createUser(@RequestBody UserDto userDto){
        if(log.isTraceEnabled()) {
            log.trace("user " + userDto.getName() + " is being created...");
        }

        String result = gpsDataService.createUser(userDto);

        if(log.isTraceEnabled()) {
            log.trace("user " + userDto.getName() + " created.");
        }

        return result;
    }

    @GetMapping("/allUsers")
    public List<UserDto> getAllUsers() {
        log.trace("getting all users from database...");

        List<UserDto> users = gpsDataService.getAllUsers();

        log.trace("all users are sucessfully get from the database.");

        return users;
    }

    @GetMapping("/{name}")
    public UserDto getUser(@PathVariable String name) {
        if(log.isTraceEnabled()) {
            log.trace("getting user " + name + " from the database...");
        }

        UserDto users = gpsDataService.getUser(name);

        if(log.isTraceEnabled()) {
            log.trace("user " + name + " is successfully get from the database...");
        }

        return users;
    }

    @PutMapping("/{name}")
    public String updateUser(@PathVariable String name, @RequestBody UserDto userDto) {
        if(log.isTraceEnabled()) {
            log.trace("user " + name + " is being updated...");
        }

        String result = gpsDataService.updateUser(name, userDto);

        log.trace(result);

        return result;
    }

    @DeleteMapping("/{name}")
    public String deleteUser(@PathVariable String name) {
        if(log.isTraceEnabled()) {
            log.trace("user " + name + " is being deleted...");
        }

        String result = gpsDataService.deleteUser(name);

        log.trace(result);

        return result;
    }

    @PostMapping("/data")
    public String createData(@RequestBody DataDto dataDto) {

        String result = gpsDataService.createData(dataDto);

        if(log.isTraceEnabled()) {
            log.trace("user " + dataDto.getUserName() + "'s data added");
        }

        return result;
    }

    @GetMapping("/data")
    public List<DataDto> getDataByUserName(@RequestParam(value = "userName", required = true) String userName,
                                 @RequestParam(value = "amount", required = false, defaultValue = "0") int amount) {
        List<DataDto> result = null;

        if(amount < 0 || !gpsDataService.isUserExist(userName)) {return result;}

        if(log.isTraceEnabled()) {
            log.trace("getting user " + userName + "'s data...");
        }

        if(amount == 0) {result =  gpsDataService.getDataByUserName(userName);}
        else {result =  gpsDataService.getDataByUserNameWithAmount(userName, amount);}

        if(log.isTraceEnabled()) {
            log.trace(userName + "'s data is successfully get");
        }

        return result;
    }
}
