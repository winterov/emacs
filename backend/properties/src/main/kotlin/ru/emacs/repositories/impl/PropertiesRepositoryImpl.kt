package ru.emacs.repositories.impl


import org.intellij.lang.annotations.Language
import ru.emacs.repositories.PropertiesRepository
import ru.emacs.repositories.extractors.PropertyExtractor
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import ru.emacs.models.PropertiesModel


@Repository
internal class PropertiesRepositoryImpl(private val jdbcTemplate: NamedParameterJdbcTemplate): PropertiesRepository {
    companion object{
        @Language("SQL")
        private const val QUERY = "SELECT pr.clazz, pr.property FROM properties as pr where pr.clazz=:clazz"
    }
    override fun getProperty(clazz: Class<*>): PropertiesModel? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("clazz", clazz.name)
       return jdbcTemplate.query(QUERY,namedParameters, PropertyExtractor())
    }
}