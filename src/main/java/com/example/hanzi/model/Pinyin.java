package com.example.hanzi.model;

import com.example.hanzi.common.HanziUtil;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Pinyin extends Model<Pinyin> {
	public static final Pinyin dao = new Pinyin().dao();
	public static final String table = "pinyin";
	
	public boolean add(String pinyin,String fayin) {
		try {
			String pystr = HanziUtil.genPinyinStr(pinyin);
			Pinyin py = Pinyin.dao.findFirst("select * from "+Pinyin.table+" where py=?",pystr);
			if(py==null) {
				py = new Pinyin();
				py.set("name", pinyin);
				py.set("py", pystr);
				py.set("fayin", fayin);
				py.save();
			}
			return true;
		}catch(Exception ex) {
			return false;
		}
	}
}