package ru.emacs.repositories.impl



import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import ru.emacs.agregators.UserAccount
import ru.emacs.repositories.RefreshTokenRepository
import ru.emacs.repositories.extractors.UserAccountExtractor
import java.util.*


@Repository
internal class RefreshTokenRepositoryImpl @Autowired constructor(private val jdbcTemplate: NamedParameterJdbcTemplate) :
    RefreshTokenRepository {
    companion object {
        private const val SAVE_REFRESH_TOKEN =
            "INSERT INTO refresh_tokens (id,expired,token) VALUES (:id,:expired,:token) " +
                    "ON CONFLICT (id) DO UPDATE set expired=:expired, token=:token"
        private const val GET_USER_BY_REFRESH_TOKEN =
            "SELECT  u.id, u.email, u.is_email_verified, u.e_status, u.phone, u.password, " +
                    "u.is_phone_verified, u.credential_expired, u.createdat, u.updatedat, " +
                    "r.title as r_title, r.description as r_description,r.id as r_id,a.id," +
                    "a.e_authorities, a.id as a_id " +
                    "FROM refresh_tokens rf" +
                    " LEFT JOIN users u on u.id = rf.id" +
                    " LEFT JOIN users_roles ur on u.id = ur.user_id" +
                    " LEFT JOIN roles r on ur.role_id = r.id" +
                    " LEFT JOIN roles_authorities ra on r.id = ra.role_id" +
                    " LEFT JOIN authorities a on ra.authority_id = a.id" +
                    "   where rf.token=:token and rf.expired>CURRENT_TIMESTAMP" +
                    "   and(u.e_status='ACTIVE' or u.e_status='NEW_USER')"
    }
    override fun saveRefreshToken(id: Long, expired: Date, token: String) {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("id", id)
            .addValue("expired", expired)
            .addValue("token", token)
        jdbcTemplate.update(
            SAVE_REFRESH_TOKEN,
            namedParameters
        )
    }

    override fun checkRefreshToken(refreshToken: String): UserAccount? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("token", refreshToken)
        return jdbcTemplate.query(
            GET_USER_BY_REFRESH_TOKEN,
            namedParameters,
            UserAccountExtractor()
        )
    }


}
