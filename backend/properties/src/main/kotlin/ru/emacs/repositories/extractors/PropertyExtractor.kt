package ru.emacs.repositories.extractors


import org.springframework.jdbc.core.ResultSetExtractor
import ru.emacs.models.PropertiesModel
import java.sql.ResultSet

internal class PropertyExtractor:ResultSetExtractor<PropertiesModel> {
    override fun extractData(rs: ResultSet): PropertiesModel? {
        if (!rs.next()) return null
        val clazz = Class.forName(rs.getString("clazz"))
        val property = rs.getString("property")
        return PropertiesModel(clazz,property)
    }
}