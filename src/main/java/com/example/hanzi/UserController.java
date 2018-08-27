package com.example.hanzi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanzi.model.Users;

@RestController  
@RequestMapping("/user")
public class UserController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @RequestMapping("/findByName")
    public Users findByName(String name){
    	log.info("findByName:"+name);
    	return Users.dao.findFirst("select * from "+Users.table+" where name=?",name);
    }
    
    @RequestMapping("/insert")
    public String insert(String name, String email){
    	log.info("insert:"+name);
    	new Users().set("name", name).set("email", email).save();
    	return "success";
    }
    
    @RequestMapping("/list")
    public String list(){
    	log.info("list");
    	List<Users> users = Users.dao.find("select * from "+Users.table);
    	return users.toString();
    }
}
