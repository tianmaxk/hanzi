package com.example.hanzi.common;

import java.util.HashMap;
import java.util.Map;

public class HanziUtil {
	private static final Map<String, String> pyMap;  
    static  
    {  
    	pyMap = new HashMap<String, String>();
    	pyMap.put("ā", "a1");
    	pyMap.put("á", "a2");
    	pyMap.put("ǎ", "a3");
    	pyMap.put("à", "a4");
    	pyMap.put("ō", "o1");
    	pyMap.put("ó", "o2");
    	pyMap.put("ǒ", "o3");
    	pyMap.put("ò", "o4");
    	pyMap.put("ē", "e1");
    	pyMap.put("é", "e2");
    	pyMap.put("ě", "e3");
    	pyMap.put("è", "e4");
    	pyMap.put("ī", "i1");
    	pyMap.put("í", "i2");
    	pyMap.put("ǐ", "i3");
    	pyMap.put("ì", "i4");
    	pyMap.put("ū", "u1");
    	pyMap.put("ú", "u2");
    	pyMap.put("ǔ", "u3");
    	pyMap.put("ù", "u4");
    	pyMap.put("ǖ", "v1");
    	pyMap.put("ǘ", "v2");
    	pyMap.put("ǚ", "v3");
    	pyMap.put("ǜ", "v4");
    	pyMap.put("ü", "v");
    }

	//生成拼音字符串
	static public String genPinyinStr(String pinyin) {
		String py = "";
		String shengdiao = "";
		for(int j=0;j<pinyin.length();j++) {
			String curchar = String.valueOf(pinyin.charAt(j));
			if(pyMap.containsKey(curchar)) {
				String info = pyMap.get(curchar);
				py += info.substring(0,1);
				if(info.length()>=2) {
					shengdiao = info.substring(1,2);
				}
			} else {
				py += curchar;
			}
		}
		py += shengdiao;
		return py;
	}
}
