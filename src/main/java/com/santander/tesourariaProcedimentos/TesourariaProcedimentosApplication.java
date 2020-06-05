package com.santander.tesourariaProcedimentos;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import javax.sql.DataSource;
import java.io.IOException;

@SpringBootApplication
public class TesourariaProcedimentosApplication implements WebMvcConfigurer {

	public static void main(String[] args) throws IOException {
		new SpringApplicationBuilder(new Class[]{TesourariaProcedimentosApplication.class}).headless(false).run(args);
		//openHomePage();
	}
	
	private static void openHomePage() throws IOException {
		Runtime rt = Runtime.getRuntime();
		rt.exec(new String[]{"cmd", "/c", "start chrome http://localhost:8090/home"});
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUsername("PGT_PRG_EXT");
		dataSource.setPassword("PGT_PRG_EXT");
		dataSource.setUrl("jdbc:oracle:thin:@ORADS109.SANTANDER.COM.BR:1527:ORADS109");
		return dataSource;
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/images/**")
				.addResourceLocations("file://bsbrsp1153/csa_gbm/_Atividades_SGM_diario/ProcedimenosTesouraria/imagens/")
				.setCachePeriod(0);
	}
	
	

}
