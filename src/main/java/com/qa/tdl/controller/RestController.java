package com.qa.tdl.controller;

import java.util.List;
import javax.validation.Valid;
import com.qa.tdl.models.ToDoItem;
import com.qa.tdl.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class RestController{

	private TodoService service;

	@Autowired
	public RestController(TodoService service){
		this.service = service;
	}

	private HttpHeaders getHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@GetMapping("/")
	public ResponseEntity<List<ToDoItem>> getAll(){
		List<ToDoItem> items = service.getAll();

		HttpHeaders headers = getHeaders();
		ResponseEntity<List<ToDoItem>> res = new ResponseEntity<>(items, headers, HttpStatus.OK);
		System.out.println(res);
		return res;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ToDoItem> getById(@PathVariable("id") Long id){
		ToDoItem item = service.getById(id);

		if(item == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		HttpHeaders headers = getHeaders();
		return new ResponseEntity<ToDoItem>(item, headers, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ToDoItem> updateById(@PathVariable("id") Long id, @Valid @RequestBody ToDoItem item){
		ToDoItem updatedItem = service.updateById(id, item);

		HttpHeaders headers = getHeaders();
		return new ResponseEntity<ToDoItem>(updatedItem, headers, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
		Boolean result = service.deleteById(id);

		HttpHeaders headers = getHeaders();
		if(result){
			return new ResponseEntity<Boolean>(result, headers, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/create")
	public ResponseEntity<ToDoItem> create(@Valid @RequestBody ToDoItem item){
		System.out.println(item);
		ToDoItem createdItem = service.create(item);
		HttpHeaders headers = getHeaders();
		
		if(createdItem == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<ToDoItem>(createdItem, headers, HttpStatus.CREATED);
	}
}
