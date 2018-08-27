package com.example.hanzi.common;

import java.util.Map;
import java.util.Set;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

public class DBAutoUtil {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static public Class<Model> getTableClass(String name) throws ClassNotFoundException{
		return (Class<Model>) Class.forName("com.example.hanzi.model."+name);
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	static public Page<Model> page(String tablename, int page, int pagesize, String keywords) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
//    	Model model = getTableClass(tablename).newInstance();
//    	if(keywords==null || keywords.isEmpty()) {
//    		return model.dao().paginate(page, pagesize, "select * ", " from "+tablename.toLowerCase());
//    	} else {
//    		return model.dao().paginate(page, pagesize, "select * ", " from "+tablename.toLowerCase()+" where name=?", keywords);
//    	}
//    }

	static public Page<Record> page(String tablename, int page, int pagesize, String keywords) {
    	if(keywords==null || keywords.isEmpty()) {
    		return Db.paginate(page, pagesize, "select * ", " from "+tablename.toLowerCase());
    	} else {
    		return Db.paginate(page, pagesize, "select * ", " from "+tablename.toLowerCase()+" where name=?", keywords);
    	}
    }
	
	static public Page<Record> page(String tablename, int page, int pagesize, Map<String, String> keywordmap) {
    	if(keywordmap==null || keywordmap.size()<=0) {
    		return Db.paginate(page, pagesize, "select * ", " from "+tablename.toLowerCase());
    	} else {
    		String keywords = "";
    		for(Map.Entry<String, String> entry : keywordmap.entrySet()) {
    			if(!keywords.isEmpty()) {
    				keywords += " and ";
    			}
    			keywords += String.format("%s='%s'", entry.getKey(), entry.getValue());
			}
    		return Db.paginate(page, pagesize, "select * ", " from "+tablename.toLowerCase()+" where " + keywords);
    	}
    }

	static public Set<String> getTableColumns(String tablename) throws ClassNotFoundException{
		Table table = TableMapping.me().getTable(getTableClass(tablename));
    	return table.getColumnTypeMap().keySet();
	}

	static public boolean insertDataToTable(String tablename, Map<String, String> data) {
		try {
			String name = data.get("name");
			String tbname = tablename.toLowerCase();
			boolean bExist = true;
			Record record = Db.findFirst("select id from "+tbname+" where name=?",name);
			if(record==null) {
				bExist = false;
				record = new Record();
			}
			for(Map.Entry<String, String> entry : data.entrySet()) {
				record.set(entry.getKey(), entry.getValue());
			}
			if(bExist) {
				Db.update(tbname, record);
			} else {
				Db.save(tbname, record);
			}
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
}