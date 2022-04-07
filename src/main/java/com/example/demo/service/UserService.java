package com.example.demo.service;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService extends Thread{

	ReadWriteLock lock = new ReentrantReadWriteLock();
	Lock writeLock = lock.writeLock();
	Lock readLock = lock.readLock();
	
	
	public String createUser(User user) throws Exception{
		String response;
		try {
			File file=new File(Integer.toString(user.getId())+ ".json");
			
			if(!file.exists()) {
  			ObjectMapper obj=new ObjectMapper();
			user.setLocked(false);
			obj.writeValue(file, user);
			response="User created successfully";
			}
			else {
				response="User Already exists";
			}
			
			return response;
		}
		catch(Exception e) {
			System.out.println(e);
			return "Exception";
		}
	}

		
	public String getspecificusers(int id) throws Exception {
		  User user=new User();
		  String response;
			     try {
			    	 readLock.tryLock();
			    	 try {
			    		File file=new File(Integer.toString(id)+ ".json");
			    	 if(file.exists()) {
			    	ObjectMapper obj=new ObjectMapper();
					user = obj.readValue(new File(Integer.toString(id)+".json"), User.class);
			    	Thread.sleep(5000);
			    	response=user.toString();
			    	 }
			    	 else {
			    		 response="File not exists";
			    	 }
			    	 return response;
			     }
				       
				        catch(Exception e) {
				        	System.out.println(e);
				        	return "Overlapping Exception Occurs while getting user" ;
				        }
			    	 finally {
			  		   readLock.unlock();
			  		}
			    		
				  
			     }
			     catch(Exception e) {
			        	System.out.println(e);
			        	return "Some one trying to update or delete" ;
			        }
			
		
	
	}

	
	public String deleteUser(int id) throws Exception {
		// TODO Auto-generated method stub
		try {
		writeLock.tryLock();
		try {
		File file=new File(Integer.toString(id)+ ".json");
		ObjectMapper obj=new ObjectMapper();
		User user = obj.readValue(new File(Integer.toString(id)+".json"), User.class);
		Thread.sleep(5000);
		
		 String response;
         if(file.exists()) {
        	 if(user.getLocked()) {
        		 
        		 response="Some User Locked the file";
        	 }else {
		file.delete();
		response="File deleted Successful";
        	 }
         }else {
        	 response="No file exists";
         }
		
		return response;
		}
		catch(Exception e){
			return "Overlapping occurs during delete";
		}
		finally {
		   writeLock.unlock();
		}
		}
		catch(Exception e) {
			return "Someone deleting or using the file";
		}
		
	}

	public String updateUser(int id, int age, String name) throws Exception {
		// TODO Auto-generated method stub
		try {
		 writeLock.tryLock();
		try {
		Thread.sleep(5000);

 		ObjectMapper obj=new ObjectMapper();
 		File file=new File(Integer.toString(id)+ ".json");
		 String response;
         if(file.exists()) {

     		User user = obj.readValue(new File(Integer.toString(id)+".json"), User.class);
        	 if(user.getLocked()) {
        		 
        		 response="Some User Locked the file";
        	 }else {
        	 user.setLocked(true);
        	 System.out.println("Locked");
        	    user.setAge(age);
        	    user.setId(id);
        	    user.setName(name);
				user.setLocked(false);
				obj.writeValue(file, user);
				response="User updated successfully";
			}
         }
         else {
        	 response="No file exists";
         }
		return response;
		}
		catch(Exception e) {
			System.out.println("Exception Occured");
			return ("Overlapping of update occurs");
		}
		finally {

			  writeLock.unlock();
		}
		}
		catch(Exception e) {
			return "Someone updating or using the file ";
		}
	}
	
	
	public String lockUser(int id) throws Exception {
        String response;
		ObjectMapper obj=new ObjectMapper();
		File file=new File(Integer.toString(id)+ ".json");
		if(file.exists()) {
		User user = obj.readValue(new File(Integer.toString(id)+".json"), User.class);
		if(user.getLocked()) {
			response="Already locked";
		}
		else {
			user.setLocked(true);
			obj.writeValue(file,user);
			response="Locked User";
		}
		return response;
		}
		else {
			return "No file exists";
		}
		
	}
	
	public String unlockUser(int id) throws Exception {
		
		 String response;
			ObjectMapper obj=new ObjectMapper();
			File file=new File(Integer.toString(id)+ ".json");
			if(file.exists()) {
			User user = obj.readValue(new File(Integer.toString(id)+".json"), User.class);
			if(!user.getLocked()) {
				response="Already unlocked";
			}
			else {
				user.setLocked(false);
				obj.writeValue(file,user);
				response="Unlocked User";
			}
			return response;
			}
			else {
				return "No file exists";
			}
	}

}



//
//public String getspecificuser(int id) throws Exception {
//	  User user=new User();
//		     try {
//		     FileInputStream fileInputStream = new FileInputStream(Integer.toString(id)+".json");
//		    		    FileChannel channel = fileInputStream.getChannel();
//		    		    FileLock lock = channel.lock(0, Long.MAX_VALUE, true) ;
//		    	
//		    	String users=channel.toString();
//		    	System.out.println(users);
//		    	ObjectMapper obj=new ObjectMapper();
//				user = obj.readValue(new File(Integer.toString(id)+".json"), User.class);
//				File file=new File(Integer.toString(user.getId())+ ".json");
//			
//	           
//		    	Thread.sleep(10000);
//		    	lock.release();
//		    	lock.close();
//		    	
//		    	channel.close();
//		    	obj.writeValue(file,user);
//		    	fileInputStream.close();
//		    	
//		     }
//			       
//			        catch(Exception e) {
//			        	System.out.println(e);
//			        	return "Overlapping Exception Occurs while getting user" ;
//			        }
//		    		
//			  
//		
//				return	user.toString();
//
//}
