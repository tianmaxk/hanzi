package com.example.hanzi.model;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Users extends Model<Users> {
	public static final Users dao = new Users().dao();
	public static final String table = "users";
	
}
