package com.example.hanzi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanzi.common.DBAutoUtil;
import com.example.hanzi.common.HttpUtil;
import com.example.hanzi.model.Hanzi;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.TableMapping;

@RestController  
@RequestMapping("/hanzi")
public class HanziController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @RequestMapping("/find")
    public String findByName(String name){
    	log.info("findhanzi:"+name);
    	return JsonKit.toJson(Hanzi.dao.findFirst("select * from "+Hanzi.table+" where name=?",name));
    }
    
    @RequestMapping("/insert")
    public String insert(String name, String pinyin, String hanzipic, String unicode, String fayin, String meaning){
    	log.info("insert:"+name);
    	new Hanzi().set("name", name).set("pinyin", pinyin).set("hanzipic", hanzipic)
    	.set("unicode", unicode).set("fayin", fayin).set("meaning", meaning).save();
    	return "success";
    }

    // 如下方法可以在字段增加后，不修改java程序，自动导入新增的字段，只要数据库结构变化了即可
    @RequestMapping("/add")
    public String add(HttpServletRequest request){
    	Map<String,String> wenzi = HttpUtil.getRequestParameters(request);
    	log.info("add:"+wenzi);
    	return DBAutoUtil.insertDataToTable("Hanzi", wenzi)?"success":"failed";
    }
    
    @RequestMapping("/list")
    public String list(){
    	log.info("list");
    	List<Hanzi> hanzi = Hanzi.dao.find("select * from "+Hanzi.table);
    	return hanzi.toString();
    }

    @RequestMapping("/page")
    public String page(int page, int pagesize, @RequestParam(value="keyword", required=false) String keyword, @RequestParam(value="full", required=false) String full){
    	log.info(String.format("page=%d,pagesize=%d",page,pagesize));
    	Map<String, String> keywordmap = new HashMap<String,String>();
    	if(keyword!=null && !keyword.isEmpty()){
    		keywordmap.put("name", keyword);
    	}
    	if(!"y".equals(full)) {
    		keywordmap.put("chang", "y");
    	}
    	Page<Record> hanzi = DBAutoUtil.page("hanzi", page, pagesize, keywordmap);
    	return JsonKit.toJson(hanzi);
    }
    
    @RequestMapping("/desc")
    public String desc(){
    	log.info("desc");
    	Table table = TableMapping.me().getTable(Hanzi.class);
    	return table.getColumnTypeMap().toString();
    }
}
