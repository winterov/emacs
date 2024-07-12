package ru.emacs.repositories.extractors




import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.ResultSetExtractor
import ru.emacs.agregators.PasswordChangeToken
import java.sql.ResultSet
import java.sql.SQLException


internal class PasswordChangeTokenExtractor : ResultSetExtractor<PasswordChangeToken?> {
    @Throws(SQLException::class, DataAccessException::class)
    override fun extractData(rs: ResultSet): PasswordChangeToken? {
        if (!rs.next()) {
            return null
        }
        val token= rs.getString("token") ?: return null
        val passwordChangeToken = PasswordChangeToken()
        passwordChangeToken.userId = (rs.getLong("id"))
        passwordChangeToken.token = token
        passwordChangeToken.expired = rs.getTimestamp("token_expired").toLocalDateTime()
        passwordChangeToken.createdAt = rs.getTimestamp("token_created").toLocalDateTime()
        return passwordChangeToken
    }
}
