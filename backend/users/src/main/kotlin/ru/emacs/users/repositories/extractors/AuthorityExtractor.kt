package ru.emacs.users.repositories.extractors


import org.springframework.jdbc.core.RowMapper
import ru.emacs.users.agregators.EAuthorities
import java.sql.ResultSet

internal class AuthorityExtractor: RowMapper<ru.emacs.users.agregators.AppAuthority> {
    override fun mapRow(rs: ResultSet, rowNum: Int): ru.emacs.users.agregators.AppAuthority? {
        val authorityId = rs.getLong("a_id")
        if(authorityId==0L) return null
        val authority = ru.emacs.users.agregators.AppAuthority()
        authority.id = authorityId
        authority.authority = EAuthorities.valueOf(rs.getString("e_authorities"))
        return authority
    }

}