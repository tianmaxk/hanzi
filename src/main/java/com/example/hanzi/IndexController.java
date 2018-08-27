package com.example.hanzi;

import java.util.Map;
import java.util.Set;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.hanzi.common.DBAutoUtil;
import com.example.hanzi.common.ImageUtil;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Controller
public class IndexController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${img.location}")
    private String location;
	
	@Value("${tesseract.tessdata}")
    private String tessdata;
	
	@RequestMapping("/")
	public String renderIndex(Map<String,Object> result){
		result.put("name", "taomin");
		List<String> list = new ArrayList<String>();
		list.add("aa");
		list.add("bb");
		list.add("c");
		list.add("d");
		result.put("users",list);
		return "index";
	}
	
	@RequestMapping("/tablepage")
	public String renderHanzi(
			@RequestParam(name = "tablename",required=false) String tbname,
			@RequestParam(name = "pageno",required=false) String pageno,
			@RequestParam(name = "pagesize",required=false) String pagesize,
			@RequestParam(name = "type",required=false) String type,
			@RequestParam(name = "keyword",required=false) String keyword,
			@RequestParam(name = "full",required=false) String full,
			Map<String,Object> result){
		if(tbname==null) {
			tbname = "Hanzi";
		}
		if(pageno==null) {
			pageno = "1";
		}
		if(pagesize==null) {
			pagesize = "20";
		}

		result.put("tablename", tbname);
		result.put("pagesize", Integer.parseInt(pagesize));
		if(keyword!=null || "search".equals(type)) {
			result.put("pageno", 1);
		} else {
			result.put("pageno", Integer.parseInt(pageno));
		}

		try {
			List<String> cols = new ArrayList<String>();
			Set<String> columns = DBAutoUtil.getTableColumns(tbname);
			for (String col : columns) {  
			   cols.add(0,col);
			}
			result.put("columns", cols);
//			Page<Record> dbret = DBAutoUtil.page(tbname, Integer.parseInt(pageno), Integer.parseInt(pagesize), keyword);
			Map<String, String> keywordmap = new HashMap<String,String>();
	    	if(keyword!=null && !keyword.isEmpty()){
	    		keywordmap.put("name", keyword);
	    	}
	    	if(!"y".equals(full)) {
	    		keywordmap.put("chang", "y");
	    	}
			Page<Record> dbret = DBAutoUtil.page(tbname, Integer.parseInt(pageno), Integer.parseInt(pagesize), keywordmap);
			result.put("totalpage", dbret.getTotalPage());
			result.put("totalcount", dbret.getTotalRow());
			result.put("contents", dbret.getList());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "tablepage";
	}
	
	@RequestMapping("/file/upload")
	@ResponseBody
    public String uploadImg(@RequestParam("image-file") MultipartFile multipartFile)  {
        if (multipartFile.isEmpty() || multipartFile.getOriginalFilename().isEmpty()) {
           return "IMG_NOT_EMPTY";
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            return "Format Error";
        }
        String root_fileName = multipartFile.getOriginalFilename();
        log.info("上传图片:name={},type={}", root_fileName, contentType);
        
        //获取路径
        String filePath = location;
        log.info("图片保存路径={}", filePath);
        String file_name = null;
        try {
            file_name = ImageUtil.saveImg(multipartFile, filePath);
            if(file_name!=null && !file_name.isEmpty()){
                return "上传成功，文件名："+file_name;
            }
            return "上传失败";
        } catch (Exception e) {
            return "SAVE_IMG_ERROE";
        }
    }

	@RequestMapping("/file/recognizeImg")
	@ResponseBody
    public String recognizeImg(@RequestParam("image-file") MultipartFile multipartFile)  {
        if (multipartFile.isEmpty() || multipartFile.getOriginalFilename().isEmpty()) {
           return "IMG_NOT_EMPTY";
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            return "Format Error";
        }
        String root_fileName = multipartFile.getOriginalFilename();
        log.info("上传图片:name={},type={}", root_fileName, contentType);
        
        //获取路径
        String filePath = location;
        log.info("图片保存路径={}", filePath);
        String file_name = null;
        try {
            file_name = ImageUtil.saveImg(multipartFile, filePath);
            if(file_name!=null && !file_name.isEmpty()){
            	 File imageFile = new File(filePath+"/"+file_name);
                 ITesseract instance = new Tesseract();     
//                 instance.setDatapath("/Users/dev_ios/Downloads/tesseract-master/tessdata");
                 instance.setDatapath(tessdata);
                 // 默认是英文（识别字母和数字），如果要识别中文(数字 + 中文），需要制定语言包
                 instance.setLanguage("chi_sim");
                 try{
                     String result = instance.doOCR(imageFile);
                     return "上传文件解析成功："+result;
                 }catch(TesseractException e){
                     return "上传文件解析失败："+e.getMessage();
                 }
            }
            return "上传失败";
        } catch (Exception e) {
            return "SAVE_IMG_ERROE";
        }
    }
}
