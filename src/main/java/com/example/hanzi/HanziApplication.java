package com.example.hanzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.hanzi.model.Hanzi;
import com.example.hanzi.model.Users;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;

@SpringBootApplication
@EnableAsync
public class HanziApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(HanziApplication.class, args);

		Environment environment = context.getBean(Environment.class);
		String localhost = environment.getProperty("spring.datasource.url");
		String username = environment.getProperty("spring.datasource.username");
		String password = environment.getProperty("spring.datasource.password");

		// 数据库设置
		DruidPlugin dp = new DruidPlugin(localhost, username, password);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.addMapping("users", Users.class);
		arp.addMapping("hanzi", Hanzi.class);
		// 与web环境唯一的不同是要手动调用一次相关插件的start()方法
		dp.start();
		arp.start();
	}
}
