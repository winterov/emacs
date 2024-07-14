package ru.emacs.users.repositories.impl


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import ru.emacs.users.repositories.UserPasswordRepository
import java.time.LocalDateTime


@Repository
class UserPasswordRepositoryImpl @Autowired constructor(private val jdbcTemplate: NamedParameterJdbcTemplate) :
    UserPasswordRepository {
    companion object {
        private const val UPDATE_PASSWORD_BY_ID = "UPDATE users  set credential_expired =:credentialExpired," +
                "password = :password, updatedat=:updatedAt where id=:userId"
    }

   override fun updatePassword(
        userId: Long,
        passwordHash: String,
        credentialExpired: LocalDateTime,
        updatedAt: LocalDateTime
    ): Int {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("password", passwordHash)
            .addValue("credentialExpired", credentialExpired)
        return jdbcTemplate.update(UPDATE_PASSWORD_BY_ID, namedParameters)
    }



}
