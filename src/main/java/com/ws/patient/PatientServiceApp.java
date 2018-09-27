package com.ws.patient;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ws.patient.core.ThreadLocalStorage;
import com.ws.patient.routing.TenantAwareRoutingSource;
import com.zaxxer.hikari.HikariDataSource;

@CrossOrigin(origins = "*")
@SpringBootApplication

public class PatientServiceApp {
	public static AbstractRoutingDataSource dataSource = new TenantAwareRoutingSource();
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "patientService");
		SpringApplication.run(PatientServiceApp.class, args);
	}

	@Bean
	public DataSource dataSource() {

		

		Map<Object, Object> targetDataSources = new HashMap<>();

		targetDataSources.put("base", tenantOne());
	   // targetDataSources.put("etab1", tenantTwo());
		ThreadLocalStorage.setTenantName("base");
		dataSource.setTargetDataSources(targetDataSources);

		dataSource.afterPropertiesSet();

		return dataSource;
	}

	public DataSource tenantOne() {
		DataSourceProperties dataSourceProperties = dataSourceProperties();
		HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create(dataSourceProperties.getClassLoader())
				.driverClassName(dataSourceProperties.getDriverClassName())
				.url("jdbc:postgresql://localhost:5432/db_mmt?currentSchema=sc_mmt").username("postgres")
				.password("root").type(HikariDataSource.class).build();
		return dataSource;

	}

	public DataSource tenantTwo() {
		DataSourceProperties dataSourceProperties = dataSourceProperties();
		HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create(dataSourceProperties.getClassLoader())
				.driverClassName(dataSourceProperties.getDriverClassName())
				.url("jdbc:postgresql://localhost:5432/etab1?currentSchema=sc_mmt").username("postgres")
				.password("root").type(HikariDataSource.class).build();
		return dataSource;

	}

	//
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.jpa.properties")
	public static DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

}
