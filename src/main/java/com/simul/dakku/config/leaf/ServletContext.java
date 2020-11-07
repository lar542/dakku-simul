package com.simul.dakku.config.leaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.simul.dakku.modules")
public class ServletContext implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/resources/**")
			.addResourceLocations("/resources/");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}
	
	@Bean
	public SpringResourceTemplateResolver thymeleafTemplateResolver() {
		SpringResourceTemplateResolver resourceTemplateResolver = new SpringResourceTemplateResolver();
		resourceTemplateResolver.setPrefix("/WEB-INF/views/");
		resourceTemplateResolver.setSuffix(".html");
		resourceTemplateResolver.setCharacterEncoding("UTF-8");
		resourceTemplateResolver.setCacheable(false);
		return resourceTemplateResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(thymeleafTemplateResolver());
		return templateEngine;
	}
	
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		thymeleafViewResolver.setCharacterEncoding("UTF-8");
		thymeleafViewResolver.setOrder(1);
		return thymeleafViewResolver;
	}
	
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setLocation(new ClassPathResource("config.properties"));
		return configurer;
	}
	
//	@Configuration
//	@Profile("local")
//	public static class CORSLocalConfig implements WebMvcConfigurer {
//		
//		@Value("${dakku.front.local.url}")
//		private String url;
//		
//		@Override
//		public void addCorsMappings(CorsRegistry registry) {
//			registry.addMapping("/**")		//CORS를 적용할 URL 패턴
//					.allowedOrigins(url)	//자원 공유를 허락할 Origin 지정
//					.allowedMethods("*");	//허용할 HTTP method 지정
//		}
//	}
//	
//	@Configuration
//	@Profile("prod")
//	public static class CORSProdConfig implements WebMvcConfigurer {
//		
//		@Value("${dakku.front.prod.url}")
//		private String url;
//		
//		@Override
//		public void addCorsMappings(CorsRegistry registry) {
//			registry.addMapping("/**")
//					.allowedOrigins(url)
//					.allowedMethods("*");
//		}
//	}
	
}
