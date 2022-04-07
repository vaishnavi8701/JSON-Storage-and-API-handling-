package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	private int id;
	
	private int age;
	
	private String name;
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", age=" + age + ", name=" + name + ", locked=" + locked + "]";
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int id, int age, String name, Boolean locked) {
		super();
		this.id = id;
		this.age = age;
		this.name = name;
		this.locked = locked;
	}
	private Boolean locked;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

}
