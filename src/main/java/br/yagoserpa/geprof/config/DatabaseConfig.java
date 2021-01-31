package br.yagoserpa.geprof.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    private final Environment environment;

    public DatabaseConfig(
        @Autowired Environment environment
    ) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        final HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setPoolName("DataSourceConfig");
        hikariConfig.setJdbcUrl(environment.getProperty("spring.datasource.url"));
        hikariConfig.setUsername(environment.getProperty("spring.datasource.username"));
        hikariConfig.setPassword(environment.getProperty("spring.datasource.password"));
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setMaximumPoolSize(Integer.parseInt(environment.getProperty("spring.datasource.maximum-pool-size")));

        return new HikariDataSource(hikariConfig);
    }

}
