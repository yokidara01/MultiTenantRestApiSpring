package   com.ws.patient;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.ws.patient.model.Tenant;
import com.ws.patient.repository.TenantRepo;
import com.ws.patient.routing.TenantAwareRoutingSource;
import com.zaxxer.hikari.HikariDataSource;
@Configuration
public class CommandLineAppStartupRunner implements CommandLineRunner {
	@Autowired 
	TenantRepo tenantRepo;
	AbstractRoutingDataSource dataSource = new TenantAwareRoutingSource();
	List<Tenant> list ;
	DataSource data;
	@Override
	public void run(String... arg0) throws Exception {
		
		list= tenantRepo.findAll();
		
		Map<Object, Object> targetDataSources = new HashMap<>();
		for (final Tenant item : list) {
			System.out.println("here"+item.getName());
			targetDataSources.put(item.getName(), tenant(item));
		}
		PatientServiceApp.dataSource.setTargetDataSources(targetDataSources);

		PatientServiceApp.dataSource.afterPropertiesSet();
		
	}

	public DataSource tenant(Tenant tenant) {
		System.out.println("here");
		DataSourceProperties dataSourceProperties = PatientServiceApp.dataSourceProperties();
		HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create(dataSourceProperties.getClassLoader())
				.driverClassName(dataSourceProperties.getDriverClassName())
				.url(tenant.getUrl()).username("postgres")
				.password("root").type(HikariDataSource.class).build();
		return dataSource;
	}
}
