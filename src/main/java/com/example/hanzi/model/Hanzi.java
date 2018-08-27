package com.example.hanzi.model;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Hanzi extends Model<Hanzi> {
	public static final Hanzi dao = new Hanzi().dao();
	public static final String table = "hanzi";
	
}