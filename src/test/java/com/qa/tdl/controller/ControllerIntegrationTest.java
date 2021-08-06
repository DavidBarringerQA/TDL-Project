package com.qa.tdl.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.tdl.models.ToDoItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:item-schema.sql", "classpath:item-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ControllerIntegrationTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper mapper;

	private GregorianCalendar getCal(){
		return new GregorianCalendar(2021, 7, 7, 10, 0, 0);
	}
	
	@Test
	void testCreate() throws Exception{
		Date time = new Date();
		ToDoItem item = new ToDoItem("Testing item", 3, time, false);
		String itemJSON = mapper.writeValueAsString(item);
		RequestBuilder mockRequest = post("/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(itemJSON);
		ToDoItem expected = new ToDoItem(2L, "Testing item", 3, time, false);
		String expectedJSON = mapper.writeValueAsString(expected);
		ResultMatcher matchStatus = status().isCreated();
		ResultMatcher matchBody = content().json(expectedJSON);
		mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
	}

	@Test
	void testReadAll() throws Exception{
		RequestBuilder mockRequest = get("/");
		List<ToDoItem> expected = new ArrayList<>();
		expected.add(new ToDoItem(1L, "AUTO ITEM", 5, getCal().getTime(), false));
		String expectedJSON = mapper.writeValueAsString(expected);
		ResultMatcher matchStatus = status().isOk();
		ResultMatcher matchBody = content().json(expectedJSON);
		mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
	}

	@Test
	void testReadOne() throws Exception{
		RequestBuilder mockRequest = get("/1");
		ToDoItem expected = new ToDoItem(1L, "AUTO ITEM", 5, getCal().getTime(), false);
		String expectedJSON = mapper.writeValueAsString(expected);
		ResultMatcher matchStatus = status().isOk();;
		ResultMatcher matchBody = content().json(expectedJSON);
		mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
	}

	@Test
	void testUpdate() throws Exception{
		ToDoItem item = new ToDoItem("AUTO ITEM", 5, getCal().getTime(), true);
		String itemJSON = mapper.writeValueAsString(item);
		RequestBuilder mockRequest = put("/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(itemJSON);
		ToDoItem expected = new ToDoItem(1L, "AUTO ITEM", 5, getCal().getTime(), true);
		String expectedJSON = mapper.writeValueAsString(expected);
		ResultMatcher matchStatus = status().isOk();
		ResultMatcher matchBody = content().json(expectedJSON);
		mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
	}

	@Test
	void testDelete() throws Exception{
		RequestBuilder mockRequest = delete("/1");
		ResultMatcher matchStatus = status().isOk();
		ResultMatcher matchBody = content().string("true");
		mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
	}

	@Test
	void testCreateFail() throws Exception{
		Date time = new Date();
		ToDoItem item = new ToDoItem(null, 3, time, false);
		String itemJSON = mapper.writeValueAsString(item);
		RequestBuilder mockRequest = post("/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(itemJSON);
		ResultMatcher matchStatus = status().isBadRequest();
		mock.perform(mockRequest).andExpect(matchStatus);
	}

	@Test
	void testReadFail() throws Exception{
		RequestBuilder mockRequest = get("/2");
		ResultMatcher matchStatus = status().isNotFound();
		mock.perform(mockRequest).andExpect(matchStatus);
	}

	@Test
	void testUpdateFailBody() throws Exception{
		ToDoItem item = new ToDoItem("AUTO ITEM", 5, null, true);
		String itemJSON = mapper.writeValueAsString(item);
		RequestBuilder mockRequest = put("/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(itemJSON);
		ResultMatcher matchStatus = status().isBadRequest();
		mock.perform(mockRequest).andExpect(matchStatus);
	}

	@Test
	void testUpdateFailReq() throws Exception{
		ToDoItem item = new ToDoItem("AUTO ITEM", 5, new Date(), false);
		String itemJSON = mapper.writeValueAsString(item);
		RequestBuilder mockRequest = put("/2")
			.contentType(MediaType.APPLICATION_JSON)
			.content(itemJSON);
		ResultMatcher matchStatus = status().isNotFound();
		mock.perform(mockRequest).andExpect(matchStatus);
	}

	@Test
	void testDeleteFail() throws Exception{
		RequestBuilder mockRequest = delete("/2");
		ResultMatcher matchStatus = status().isNotFound();
		mock.perform(mockRequest).andExpect(matchStatus);
	}
}
