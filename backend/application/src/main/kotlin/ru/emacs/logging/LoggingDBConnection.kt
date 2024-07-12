package ru.emacs.logging


import ch.qos.logback.core.db.ConnectionSource
import ch.qos.logback.core.db.dialect.SQLDialectCode
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import ru.emacs.configurations.DatabaseConfiguration
import java.sql.Connection

class LoggingDBConnection: ConnectionSource {
    private val hikariDataSource: HikariDataSource
    private var isStarted: Boolean=false
    private val isSupportsGetGeneratedKeys=true
    private val isSupportsBatchUpdates=true
    init{
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = DatabaseConfiguration.URL
            driverClassName = DatabaseConfiguration.DRIVER
            username = DatabaseConfiguration.USERNAME
            password = DatabaseConfiguration.PASSWORD
        }
        this.hikariDataSource = HikariDataSource(hikariConfig)
    }

    override fun start() {
        this.isStarted=true
    }

    override fun stop() {
        this.isStarted=false
    }

    override fun isStarted(): Boolean {
        return this.isStarted
    }

    override fun getConnection(): Connection {
        return hikariDataSource.connection
    }

    override fun getSQLDialectCode(): SQLDialectCode {
        return SQLDialectCode.POSTGRES_DIALECT
    }

    override fun supportsGetGeneratedKeys(): Boolean {
        return this.isSupportsGetGeneratedKeys
    }

    override fun supportsBatchUpdates(): Boolean {
        return this.isSupportsBatchUpdates
    }


}