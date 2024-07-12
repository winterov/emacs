package ru.emacs.repositories.extractors


import org.springframework.jdbc.core.RowMapper
import ru.emacs.agregators.AppAuthority
import ru.emacs.agregators.EAuthorities
import java.sql.ResultSet

internal class AuthorityExtractor: RowMapper<AppAuthority> {
    override fun mapRow(rs: ResultSet, rowNum: Int): AppAuthority? {
        val authorityId = rs.getLong("a_id")
        if(authorityId==0L) return null
        val authority = AppAuthority()
        authority.id = authorityId
        authority.authority = EAuthorities.valueOf(rs.getString("e_authorities"))
        return authority
    }

}