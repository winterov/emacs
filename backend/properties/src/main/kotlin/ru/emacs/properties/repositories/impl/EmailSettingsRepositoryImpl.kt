package ru.emacs.properties.repositories.impl

import org.intellij.lang.annotations.Language
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import ru.emacs.properties.models.EmailSettings
import ru.emacs.properties.repositories.EmailSettingsRepository
import ru.emacs.properties.repositories.extractors.EmailRowMapper

@Repository
class EmailSettingsRepositoryImpl(private val jdbcTemplate: NamedParameterJdbcTemplate): EmailSettingsRepository {
    companion object{
        @Language("PostgreSQL")
        private const val GET_EMAIL_SETTINGS = "SELECT * FROM email_properties WHERE is_enabled=:enabled"
    }
    override fun getSettings(isEnabled: Boolean): List<EmailSettings> {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("enabled", isEnabled)
        return jdbcTemplate.query(GET_EMAIL_SETTINGS, namedParameters, EmailRowMapper())
    }
}