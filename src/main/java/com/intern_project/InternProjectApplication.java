package com.intern_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class InternProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternProjectApplication.class, args);
	}

}
