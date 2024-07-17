package ru.emacs.users.repositories.impl


import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import ru.emacs.users.agregators.EmailApprovedToken
import ru.emacs.users.repositories.UserEmailRepository
import ru.emacs.users.repositories.extractors.EmailApprovedTokenExtractor
import java.time.LocalDateTime


@Repository
internal class UserEmailRepositoryImpl(private val jdbcTemplate: NamedParameterJdbcTemplate) : UserEmailRepository {
    companion object {
        private const val DELETE_USED_TOKEN = "DELETE FROM email_approved_token as eat WHERE eat.userid=:id"
        private const val INSERT_TOKEN =
            "INSERT INTO email_approved_token (userid, token, expired, createdat) VALUES (:id,:token,:expired,:create) " +
                    "ON CONFLICT (userId) DO UPDATE SET token=:token, expired = :expired, createdat =:create"

        private const val VERIFIED_TOKEN_BY_EMAIL = "SELECT " +
                "eat.token, eat.expired as token_expired, eat.createdat as token_created, " +
                "u.is_email_verified as email_verified, u.e_status as user_status" +
                " FROM users as u left join email_approved_token eat on u.id = eat.userid" +
                " where u.email=:email"

        private const val UPDATE_USER_EMAIL_STATUS =
            "UPDATE users set is_email_verified=:status, updatedat=:updatedAt where id=:id"
        private const val COUNT_USAGE_EMAIL = "SELECT COUNT(u.email) FROM users as u where u.email = :email;"
    }


    override fun saveVerifiedEmailToken(userId: Long, token: String, expired: LocalDateTime): Int {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("create", LocalDateTime.now())
            .addValue("id", userId)
            .addValue("token", token)
            .addValue("expired", expired)
        return jdbcTemplate.update(INSERT_TOKEN, namedParameters)
    }

    override fun deleteUsedEmailApprovedTokenByUserId(id: Long): Int {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("id", id)
        return jdbcTemplate.update(DELETE_USED_TOKEN, namedParameters)
    }

    override fun getVerifiedToken(email: String): EmailApprovedToken? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("email", email)
        return jdbcTemplate.query(VERIFIED_TOKEN_BY_EMAIL, namedParameters, EmailApprovedTokenExtractor())
    }


    override fun updateUserEmailStatusByUserId(userId: Long, status: Boolean): Int {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("id", userId)
            .addValue("status", status)
        return jdbcTemplate.update(UPDATE_USER_EMAIL_STATUS, namedParameters)
    }

    override fun countOfUsageEmail(email: String): Long {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("email", email)
        return jdbcTemplate
            .queryForObject(
                COUNT_USAGE_EMAIL,
                namedParameters, Long::class.java
            )!!
    }
}
