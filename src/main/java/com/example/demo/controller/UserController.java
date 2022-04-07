package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userservice;

	
	@PostMapping(value = "/create/user")
	public ResponseEntity<String> createUser(@RequestBody User user) throws Exception {
		userservice.createUser(user);
		return new ResponseEntity<String>("User created successfully", HttpStatus.CREATED);
	}
	
//	@GetMapping(value="/getalluser")
//	public ResponseEntity<List<User>> getUser() {
//		return new ResponseEntity<List<User>>(userservice.getalluser(),HttpStatus.OK);
//	}	
	
	@GetMapping(value="/getspecific/user")
	public ResponseEntity<String> getUser(int id) throws Exception{
		String response=userservice.getspecificusers(id);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}	
	
	@PutMapping(value="/update/{id}")
	public ResponseEntity<String>  updateUser(@PathVariable("id") int id, @RequestParam("age") int age, @RequestParam("name") String name)throws Exception {
		System.out.println("Update");
		String response=userservice.updateUser(id,age,name);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	

	@PutMapping("/lockit/{id}")
	public ResponseEntity<String>  lockUser(@PathVariable("id") int id)throws Exception {
		System.out.println("Locking it");
		String response=userservice.lockUser(id);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}

	@PutMapping("/unlockit/{id}")
	public ResponseEntity<String>  unlockUser(@PathVariable("id") int id)throws Exception {
		System.out.println("Unlock it");
		String response=userservice.unlockUser(id);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	
	@DeleteMapping(value="/delete/user/{id}")
	public ResponseEntity<String>  deleteUser( @PathVariable("id") int id) throws Exception{
		System.out.println("Delete");
		String response=userservice.deleteUser(id);
		return new ResponseEntity<String>(response,HttpStatus.OK);
		
	}

}
