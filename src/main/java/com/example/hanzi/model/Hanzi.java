package com.example.hanzi.model;

import java.util.List;

import com.example.hanzi.common.HanziUtil;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Hanzi extends Model<Hanzi> {
	public static final Hanzi dao = new Hanzi().dao();
	public static final String table = "hanzi";
	
    //将汉字库里的拼音转换成拼音字符串
	public boolean convertToPinyinStr() {
		try {
			List<Hanzi> lst = Hanzi.dao.find("select * from "+Hanzi.table);
	    	for(int i=0;i<lst.size();i++) {
	    		Hanzi wenzi = lst.get(i);
	    		String pinyin = wenzi.get("pinyin");
	    		String py = HanziUtil.genPinyinStr(pinyin);
	    		wenzi.set("py", py);
	    		wenzi.update();
	    	}
	    	return true;
		}catch(Exception ex) {
			return false;
		}
	}
	
	//新增拼音映射表，为了多音字的匹配
	public boolean addPinyin(String name, String[] pylist, String[] fayinlist) {
		try {
			for(int i=0;i<pylist.length;i++) {
				if(Pinyin.dao.add(pylist[i], fayinlist[i])==false) {
					return false;
				}
				if(PinyinMap.dao.add(name, pylist[i])==false) {
					return false;
				}
			}
			return true;
		}catch(Exception ex) {
			return false;
		}
	}
	
	//通过文字找汉字信息
	public String findHanziByName(String name) {
		Hanzi hanzi = Hanzi.dao.findFirst("select * from "+Hanzi.table+" where name=?",name);
		String sql = "select a.pinyin,b.fayin from (select pinyin from "+PinyinMap.table+" where name=?) a left join "+Pinyin.table+" b on a.pinyin=b.name";
		List<PinyinMap> pylist = PinyinMap.dao.find(sql,name);
		return String.format("{\"hanzi\":%s,\"pylist\":%s}", JsonKit.toJson(hanzi),JsonKit.toJson(pylist));
	}
}