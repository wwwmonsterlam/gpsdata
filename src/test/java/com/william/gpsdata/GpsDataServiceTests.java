package com.william.gpsdata;

import com.william.gpsdata.dao.DataDao;
import com.william.gpsdata.dao.UserDao;
import com.william.gpsdata.dto.DataDto;
import com.william.gpsdata.dto.UserDto;
import com.william.gpsdata.po.Data;
import com.william.gpsdata.po.User;
import com.william.gpsdata.service.BeanConverter;
import com.william.gpsdata.service.GpsDataService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GpsDataServiceTests {

    private static final Log log = LogFactory.getLog(GpsDataServiceTests.class);

    @MockBean
    UserDao userDao;
    @MockBean
    DataDao dataDao;

    @Autowired
    private GpsDataService gpsDataService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        log.trace("Testing class: GpsDataService...");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        log.trace("class GpsDataService passed tests");
    }

    @Test
    public void testGetUser() {
        log.trace("executing testGetUser()...");

        // create data to help simulate dao layer operations
        Map<String, User> userMap = new HashMap<>();
        User user1 = new User();
        String userName1 = "user name 1";
        user1.setName(userName1);
        userMap.put(userName1, user1);

        // use Mockito to simulate dao layer operations
        Mockito.when(userDao.findByName(userName1)).thenReturn(userMap.get(userName1));

        UserDto result = gpsDataService.getUser(userName1);

        Assert.assertEquals(result.getName(), userName1);
    }

    @Test
    public void testGetAllUsers() {
        log.trace("executing testGetAllUsers()...");

        // create data to help simulate dao layer operations
        Map<String, User> userMap = new HashMap<>();
        User user1 = new User();
        String userName1 = "user name 1";
        user1.setName(userName1);
        userMap.put(userName1, user1);
        User user2 = new User();
        String userName2 = "user name 2";
        user2.setName(userName2);
        userMap.put(userName2, user2);

        // use Mockito to simulate dao layer operations
        Mockito.when(userDao.findAll()).thenReturn(new ArrayList<User>(userMap.values()));

        List<UserDto> result = gpsDataService.getAllUsers();

        Assert.assertEquals(result.size(), userMap.size());
        Assert.assertNotEquals(result.get(0).getName(), result.get(1).getName());

        log.debug(userMap.get(userName1).getName());
        log.debug(result.get(0));
        log.debug(result.get(1));
        Assert.assertTrue(userMap.containsKey(result.get(0).getName()));
        Assert.assertTrue(userMap.containsKey(result.get(1).getName()));
    }

    @Test
    public void testCreateUser() {
        log.trace("executing testCreateUser()...");

        // create data to help simulate dao layer operations
        UserDto user1 = new UserDto();
        String userName1 = "user name 1";
        user1.setName(userName1);

        // use Mockito to simulate dao layer operations
        Mockito.when(userDao.save(BeanConverter.toUser(user1))).thenReturn(BeanConverter.toUser(user1));

        Assert.assertTrue(gpsDataService.createUser(user1).length() > 0);
    }

    @Test
    public void testCreateData() {
        log.trace("executing testCreateData()...");

        // create data to help simulate dao layer operations
        DataDto data = new DataDto();
        data.setDate(new Date(23232323));
        data.setUserName("haha");
        data.setnOrS('N');
        data.setLatitude(45);
        data.setwOrE('E');
        data.setLongitude(233);

        // use Mockito to simulate dao layer operations
        Mockito.when(dataDao.save(BeanConverter.toData(data))).thenReturn(BeanConverter.toData(data));

        Assert.assertTrue(gpsDataService.createData(data).length() > 0);
    }

    @Test
    public void testGetDataByUserName() {
        log.trace("executing testGetDataByUserName()...");

        // create data to help simulate dao layer operations
        Map<String, List<Data>> userMap = new HashMap<>();
        String userName = "haha";
        Data data = new Data();
        data.setDate(new Date(23232323));
        data.setUserName(userName);
        data.setnOrS('N');
        data.setLatitude(45);
        data.setwOrE('E');
        data.setLongitude(233);
        List<Data> list = new ArrayList<>();
        list.add(data);
        userMap.put(userName, list);

        // use Mockito to simulate dao layer operations
        Mockito.when(dataDao.findAllByUserName(userName)).thenReturn(userMap.get(userName));

        List<DataDto> result = gpsDataService.getDataByUserName(userName);

        Assert.assertEquals(userMap.size(), result.size());
        Assert.assertEquals(userName, result.get(0).getUserName());
    }

    @Test
    public void testGetDataByUserNameWithAmount() {
        log.trace("executing testGetDataByUserNameWithAmount()...");

        // create data to help simulate dao layer operations
        Map<String, List<Data>> userMap = new HashMap<>(2);
        String userName = "haha";
        Data data1 = new Data();
        data1.setDate(new Date(23232323));
        data1.setUserName(userName);
        data1.setnOrS('N');
        data1.setLatitude(45);
        data1.setwOrE('E');
        data1.setLongitude(233);
        Data data2 = new Data();
        data2.setDate(new Date(233));
        data2.setUserName(userName);
        data2.setnOrS('S');
        data2.setLatitude(85);
        data2.setwOrE('W');
        data2.setLongitude(1);

        List<Data> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);
        userMap.put(userName, list);

        log.debug("list's size: " + list.size());

        int amount = 1;

        // use Mockito to simulate dao layer operations
        Mockito.when(dataDao.findAllByUserNameWithAmount(userName, amount))
                .thenReturn(userMap.get(userName));

        List<DataDto> result = gpsDataService.getDataByUserNameWithAmount(userName, amount);

        log.debug("result's size: " + result.size());
        Assert.assertEquals(data1.getnOrS(), result.get(0).getnOrS());
    }
}
