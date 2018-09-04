package com.example.hanzi.model;

import com.example.hanzi.common.HanziUtil;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class PinyinMap extends Model<PinyinMap> {
	public static final PinyinMap dao = new PinyinMap().dao();
	public static final String table = "pinyinmap";

	public boolean add(String name, String pinyin) {
		try {
			String pystr = HanziUtil.genPinyinStr(pinyin);
			PinyinMap pymap = PinyinMap.dao.findFirst("select * from "+PinyinMap.table+" where name=? and pinyin=?",name,pystr);
			if(pymap==null) {
				pymap = new PinyinMap();
				pymap.set("name", name).set("pinyin", pystr).save();
			}
			return true;
		}catch(Exception ex) {
			return false;
		}
	}
}