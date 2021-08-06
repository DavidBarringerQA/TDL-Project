package com.qa.tdl.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class ToDoItem{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String description;

	@NotNull
	@Max(5)
	@Min(1)
	private int priority;

	@NotNull
	private Date time;

	@NotNull
	private boolean completed;

	public ToDoItem(){}

	public ToDoItem(String description, int priority, Date time, boolean completed){
		this.description = description;
		this.priority = priority;
		this.time = time;
		this.completed = completed;
	}

	public ToDoItem(Long id, String description, int priority, Date time, boolean completed){
		this.id = id;
		this.description = description;
		this.priority = priority;
		this.time = time;
		this.completed = completed;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	  if(!(obj instanceof ToDoItem)){
			return false;
		}
		ToDoItem other = (ToDoItem)obj;
		if(id == 0L && other.id == 0L) {
			return super.equals(obj);
		}
		return id == other.id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
