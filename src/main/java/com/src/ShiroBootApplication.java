package com.src;

import com.src.common.shiro.config.RequestJsonHandlerMethodArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Spring-Boot 核心启动器
 */
@SpringBootApplication
public class ShiroBootApplication extends WebMvcConfigurerAdapter {

		public static void main(String[] args) {
		SpringApplication.run(ShiroBootApplication.class, args);
	}


	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
		argumentResolvers.add(new RequestJsonHandlerMethodArgumentResolver());
		super.addArgumentResolvers(argumentResolvers);
	}









}
