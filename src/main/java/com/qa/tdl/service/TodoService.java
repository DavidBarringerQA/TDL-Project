package com.qa.tdl.service;

import java.util.List;
import com.qa.tdl.models.ToDoItem;
import com.qa.tdl.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService{

	private TodoRepository repository;

	@Autowired
	public TodoService(TodoRepository repository){
		this.repository = repository;
	}

	public List<ToDoItem> getAll(){
		return repository.findAll();
	}

	public ToDoItem getById(Long id){
		if(!repository.existsById(id)){
			return null;
		}
		return repository.getById(id);
	}

	public ToDoItem updateById(Long id, ToDoItem item){
		item.setId(id);
		if(!repository.existsById(id)){
			return null;
		}
		return repository.saveAndFlush(item);
	}

	public boolean deleteById(Long id){
		if(repository.existsById(id)){
			repository.deleteById(id);
			return true;
		}
		return false;
	}

	public ToDoItem create(ToDoItem item){
		return repository.saveAndFlush(item);
	}
}
