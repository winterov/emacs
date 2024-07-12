package ru.emacs.repositories.extractors


import org.springframework.jdbc.core.RowMapper
import ru.emacs.agregators.AppRole
import java.sql.ResultSet

internal class RoleExtractor:RowMapper<AppRole> {
    override fun mapRow(rs: ResultSet, rowNum: Int): AppRole? {
        val roleId = rs.getLong("r_id")
        if(roleId==0L) return null
        val title = rs.getString("r_title")
        val description = rs.getString("r_description")
        val role = AppRole(roleId,title,description)
        return role
    }
}