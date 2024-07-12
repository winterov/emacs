package ru.emacs.configurations

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableJdbcRepositories(basePackages = ["ru.emacs"])
class DatabaseConfiguration {
    companion object{
        const val USERNAME= "postgres"
        const val PASSWORD= "London8793"
        const val URL= "jdbc:postgresql://localhost:5432/clinic"
        const val DRIVER = "org.postgresql.Driver"
    }

    @Bean("primaryDb")
    @Primary
    fun dataSource(): DataSource {
        return DataSourceBuilder
            .create()
            .username(USERNAME)
            .password(PASSWORD)
            .url(URL)
            .driverClassName(DRIVER)
            .build()
    }
}
