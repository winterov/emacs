package ru.emacs.users.repositories.impl



import org.intellij.lang.annotations.Language
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import ru.emacs.users.agregators.PasswordChangeToken
import ru.emacs.users.repositories.ChangePasswordTokenRepository
import ru.emacs.users.repositories.extractors.PasswordChangeTokenExtractor
import java.time.LocalDateTime


@Repository
internal class ChangePasswordTokenRepositoryImpl @Autowired constructor(private val jdbcTemplate: NamedParameterJdbcTemplate) :
    ChangePasswordTokenRepository {
    companion object {
        @Language("Sql")
        private const val SAVE_CHANGE_PASSWORD_TOKEN =
            "INSERT INTO change_psw_token(id, token, expired) VALUES (:id,:token,:expired) " +
                    "ON CONFLICT (id) DO UPDATE SET token = :token, expired = :expired;"
        @Language("Sql")
        private const val CHECK_TOKEN =
            "SELECT u.id, rpt.token , rpt.expired as token_expired, rpt.createdat as token_created " +
                    " FROM users as u left join change_psw_token rpt on u.id = rpt.id" +
                    " where u.email=:email"
        @Language("Sql")
        private const val DELETE_USED_TOKEN = "DELETE FROM change_psw_token as rpt WHERE rpt.id=:id"
    }


    override fun saveChangePasswordToken(userId: Long, expireDate: LocalDateTime, token: String): Int {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("id", userId)
            .addValue("expired", expireDate)
            .addValue("token", token)
        return jdbcTemplate.update(SAVE_CHANGE_PASSWORD_TOKEN, namedParameters)
    }

    override fun getTokenByUserEmail(email: String): PasswordChangeToken? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("email", email)
        return jdbcTemplate.query(CHECK_TOKEN, namedParameters, PasswordChangeTokenExtractor())
    }



    override fun deleteUsedToken(id: Long) {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("id", id)
        jdbcTemplate.update(DELETE_USED_TOKEN, namedParameters)
    }


}
