package com.william.gpsdata;

import com.william.gpsdata.controller.GpsDataController;
import com.william.gpsdata.dao.DataDao;
import com.william.gpsdata.dao.UserDao;
import com.william.gpsdata.po.Data;
import com.william.gpsdata.po.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class GpsdataApplicationTests {

	private static final Log log = LogFactory.getLog(GpsdataApplicationTests.class);

	@MockBean
	UserDao userDao;
	@MockBean
	DataDao dataDao;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private GpsDataController gpsDataController;

	@BeforeClass
	public static void beforeClass() throws Exception {
		log.trace("Testing class: GpsdataApplicationTests...");
	}

	@AfterClass
	public static void afterClass() throws Exception {
		log.trace("class GpsdataApplicationTests passed tests");
	}

	@Test
	public void testCreateUser() throws Exception {
		final boolean[] visited = {false};
		String name = "linlin";

		Mockito.doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				Object[] args = invocationOnMock.getArguments();
				User user = (User) args[0];
				Assert.assertEquals(name, user.getName());
				visited[0] = true;
				return null;
			}
		}).when(userDao).save(Mockito.any(User.class));

		String jsonData = "{\"name\":\"" + name + "\"}";

		this.mockMvc.perform(MockMvcRequestBuilders.post("/gpsdata/createUser")
				.contentType(MediaType.APPLICATION_JSON).content(jsonData))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());

		Assert.assertTrue(visited[0]);
	}

	@Test
	public void testGetAllUsers() throws Exception {
		log.trace("executing testGetAllUsers()...");

		// create data to help simulate dao layer operations
		List<User> list = new ArrayList<>();
		User user1 = new User();
		String userName1 = "user name 1";
		user1.setName(userName1);
		list.add(user1);
		User user2 = new User();
		String userName2 = "user name 2";
		user2.setName(userName2);
		list.add(user2);

		// use Mockito to simulate dao layer operations
		Mockito.when(userDao.findAll()).thenReturn(list);

		mockMvc.perform(MockMvcRequestBuilders.get("/gpsdata/allUsers"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(userName1)))
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(userName2)));
	}

	@Test
	public void testGetUser() throws Exception {
		log.trace("executing testGetUser()...");

		// create data to help simulate dao layer operations
		Map<String, User> userMap = new HashMap<>();
		User user1 = new User();
		String userName1 = "user name 1";
		user1.setName(userName1);
		userMap.put(userName1, user1);

		// use Mockito to simulate dao layer operations
		Mockito.when(userDao.findByName(userName1)).thenReturn(userMap.get(userName1));

		mockMvc.perform(MockMvcRequestBuilders.get("/gpsdata/" + userName1))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(userName1)));
	}

	@Test
	public void testDeleteUser() throws Exception {
		log.trace("executing testDeleteUser()...");

		final boolean[] visited = {false};
		String name = "linlin";

		Mockito.doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				Object[] args = invocationOnMock.getArguments();
				String userName = (String) args[0];
				Assert.assertEquals(name, userName);
				visited[0] = true;
				return null;
			}
		}).when(userDao).deleteByName(Mockito.any(String.class));

		this.mockMvc.perform(MockMvcRequestBuilders.delete("/gpsdata/" + name))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());

		Assert.assertTrue(visited[0]);
	}

	@Test
	public void testUpdateUser() throws Exception {
		log.trace("executing testUpdateUser()...");

		final boolean[] visited = {false, false};
		String name = "linlin";
		String updatedName = "lulu";

		Mockito.doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				Object[] args = invocationOnMock.getArguments();
				String userName = (String) args[0];
				Assert.assertEquals(name, userName);
				visited[0] = true;
				return null;
			}
		}).when(userDao).deleteByName(Mockito.any(String.class));

		Mockito.doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				Object[] args = invocationOnMock.getArguments();
				User user = (User) args[0];
				Assert.assertEquals(updatedName, user.getName());
				visited[1] = true;
				return null;
			}
		}).when(userDao).save(Mockito.any(User.class));

		String jsonData = "{\"name\":\"" + updatedName + "\"}";

		this.mockMvc.perform(MockMvcRequestBuilders.put("/gpsdata/" + name)
				.contentType(MediaType.APPLICATION_JSON).content(jsonData))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());

		Assert.assertTrue(visited[0]);
		Assert.assertTrue(visited[1]);
	}

	@Test
	public void testCreateData() throws Exception {
		log.trace("executing testCreateData()...");

		final boolean[] visited = {false};
		String userName = "linlin";
		Date date = new Date(2345);
		char nOrS = 'N';
		double latitude = 43;
		char wOrE = 'E';
		double longitude = 34;

		Mockito.when(userDao.countByName(userName)).thenReturn(1);

		Mockito.doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				Object[] args = invocationOnMock.getArguments();
				Data data = (Data) args[0];
				Assert.assertEquals(userName, data.getUserName());
				Assert.assertEquals(nOrS, data.getnOrS());
				Assert.assertEquals(wOrE, data.getwOrE());
				Assert.assertTrue(latitude - data.getLatitude() < 0.0001
						&& latitude - data.getLatitude() > -0.0001);
				Assert.assertTrue(longitude - data.getLongitude() < 0.0001
						&& longitude - data.getLongitude() > -0.0001);
				visited[0] = true;
				return null;
			}
		}).when(dataDao).save(Mockito.any(Data.class));

		String jsonData = "{\"userName\":\"" + userName + "\","
				+ "\"date\":\"" + date + "\","
				+ "\"nOrS\":\"" + nOrS + "\","
				+ "\"latitude\":\"" + latitude + "\","
				+ "\"wOrE\":\"" + wOrE + "\","
				+ "\"longitude\":\"" + longitude + "\"}";

		this.mockMvc.perform(MockMvcRequestBuilders.post("/gpsdata/data")
				.contentType(MediaType.APPLICATION_JSON).content(jsonData))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());

		Assert.assertTrue(visited[0]);
	}

	@Test
	public void testGetDataByUserName() throws Exception {
		log.trace("executing testGetDataByUserName()...");

		// create data to help simulate dao layer operations
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

		int amount = 0;

		// use Mockito to simulate dao layer operations
		Mockito.when(dataDao.findAllByUserName(userName)).thenReturn(list);
		Mockito.when(userDao.countByName(userName)).thenReturn(1);

		mockMvc.perform(MockMvcRequestBuilders
				.get(String.format("/gpsdata/data?userName=%s", userName, amount)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(userName)))
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("S")))
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("N")));
	}
}
