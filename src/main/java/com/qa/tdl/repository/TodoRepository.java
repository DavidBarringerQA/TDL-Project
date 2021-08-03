package com.qa.tdl.repository;

import com.qa.tdl.models.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<ToDoItem, Long>{
	
}
