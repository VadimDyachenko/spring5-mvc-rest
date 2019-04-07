package ua.vadym;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "ua.vadym.spring5mvcrest")
@PropertySource({"classpath:application.properties"})
public class TestConfiguration {

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource source = new DriverManagerDataSource();
        source.setUrl(env.getProperty("spring.datasource.url"));
        source.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
        source.setUsername(env.getProperty("spring.datasource.username"));
        source.setPassword(env.getProperty("spring.datasource.password"));
        return source;
    }

    @Bean
    public DatabaseConfigBean databaseConfig() {
        DatabaseConfigBean config = new DatabaseConfigBean();
        config.setAllowEmptyFields(true);
        return config;
    }

    @Bean(name = "databaseConnection")
    public DatabaseDataSourceConnectionFactoryBean databaseConnection(DataSource dataSource, DatabaseConfigBean config) {
        final DatabaseDataSourceConnectionFactoryBean factory = new DatabaseDataSourceConnectionFactoryBean(dataSource);
        factory.setDatabaseConfig(config);
        return factory;
    }
}
