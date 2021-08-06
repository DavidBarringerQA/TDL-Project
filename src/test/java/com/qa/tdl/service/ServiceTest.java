package com.qa.tdl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.qa.tdl.models.ToDoItem;
import com.qa.tdl.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ServiceTest {

	@MockBean
	private TodoRepository repository;

	@Autowired
	private TodoService service;

	@Test
	void testCreate(){
		Date time = new Date();
		ToDoItem item = new ToDoItem("Test", 2, time, false);
		ToDoItem result = new ToDoItem(1L, "Test", 2, time, false);

		Mockito.when(repository.saveAndFlush(item)).thenReturn(result);

		assertEquals(service.create(item), result);
		Mockito.verify(repository, Mockito.times(1)).saveAndFlush(item);
	}

	@Test
	void testGetAll(){
		List<ToDoItem> items = new ArrayList<>();
		items.add(new ToDoItem(1L, "Test", 3, new Date(), false));

		Mockito.when(repository.findAll()).thenReturn(items);
		
		assertEquals(items, service.getAll());
		Mockito.verify(repository, Mockito.times(1)).findAll();
	}

	@Test
	void testGetById(){
		ToDoItem item = new ToDoItem(1L, "Test", 2, new Date(), false);

		Mockito.when(repository.existsById(1L)).thenReturn(true);
		Mockito.when(repository.getById(1L)).thenReturn(item);

		assertEquals(item, service.getById(1L));
		Mockito.verify(repository, Mockito.times(1)).existsById(1L);
		Mockito.verify(repository, Mockito.times(1)).getById(1L);
	}

	@Test
	void testUpdateById(){
		ToDoItem result = new ToDoItem(1L, "Test", 3, new Date(), true);

		Mockito.when(repository.existsById(1L)).thenReturn(true);
		Mockito.when(repository.saveAndFlush(result)).thenReturn(result);

		assertEquals(result, service.updateById(1L, result));
		Mockito.verify(repository, Mockito.times(1)).existsById(1L);
		Mockito.verify(repository, Mockito.times(1)).saveAndFlush(result);
	}

	@Test
	void testDeleteById(){
		Mockito.when(repository.existsById(1L)).thenReturn(true);

		assertTrue(service.deleteById(1L));
		Mockito.verify(repository, Mockito.times(1)).existsById(1L);
		Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
	}

	@Test
	void getByIdFail(){
		Mockito.when(repository.existsById(1L)).thenReturn(false);

		assertEquals(null, service.getById(1L));
		Mockito.verify(repository, Mockito.times(1)).existsById(1L);
		Mockito.verify(repository, Mockito.times(0)).getById(1L);
	}

	@Test
	void updateByIdFail(){
		ToDoItem item = new ToDoItem(1L, "Test", 3, new Date(), true);
		
		Mockito.when(repository.existsById(1L)).thenReturn(false);

		assertEquals(null, service.updateById(1L, item));
		Mockito.verify(repository, Mockito.times(1)).existsById(1L);
		Mockito.verify(repository, Mockito.times(0)).saveAndFlush(item);
	}

	@Test
	void deleteByIdFail(){
		Mockito.when(repository.existsById(1L)).thenReturn(false);

		assertEquals(false, service.deleteById(1L));
		Mockito.verify(repository, Mockito.times(1)).existsById(1L);
		Mockito.verify(repository, Mockito.times(0)).deleteById(1L);
	}
}
