package com.example.hanzi.model;

import com.example.hanzi.common.HanziUtil;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Pinyin extends Model<Pinyin> {
	public static final Pinyin dao = new Pinyin().dao();
	public static final String table = "pinyin";
	
	public boolean add(String pinyin,String fayin) {
		try {
			boolean existed = true;
			Pinyin py = Pinyin.dao.findFirst("select * from "+Pinyin.table+" where name=?",pinyin);
			if(py==null) {
				existed = false;
				py = new Pinyin();
			}
			py.set("name", pinyin);
			py.set("py", HanziUtil.genPinyinStr(pinyin));
			py.set("fayin", fayin);
			if(existed) {
				py.update();
			} else {
				py.save();
			}
			return true;
		}catch(Exception ex) {
			return false;
		}
	}
}