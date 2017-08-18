package com.mawic.homepage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.mawic.homepage.domain.mapper")
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	@Bean
    public CommandLineRunner onBoot() {
	    return args -> {
//            System.out.println(System.getProperty("java.class.path"));//系统的classpaht路径
//            System.out.println(System.getProperty("user.dir"));//用户的当前路径
        };
    }
}
