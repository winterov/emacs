package ru.emacs.users.repositories.impl



import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import ru.emacs.users.agregators.EUserStatus
import ru.emacs.users.repositories.UserRegistrationRepository
import java.time.LocalDateTime
import java.util.*


@Repository
internal class UserRegistrationRepositoryImpl(private val jdbcTemplate: NamedParameterJdbcTemplate) :
    UserRegistrationRepository {
    companion object {
        private const val CREATE_NEW_USER =
            "INSERT INTO users (email, phone, password, credential_expired, e_status, createdat) " +
                    "VALUES (:email,:phone,:password,:credentialExpired, :status, :createdAt);"
    }

    override fun saveNewUser(
        email: String,
        phone: String?,
        passwordHash: String,
        credentialExpired: LocalDateTime,
        status: EUserStatus,
        createdAt: LocalDateTime
    ): Long? {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("email", email.lowercase(Locale.getDefault()))
            .addValue("phone", phone)
            .addValue("password", passwordHash)
            .addValue("credentialExpired", credentialExpired)
            .addValue("status", status.toString())
            .addValue("createdAt", createdAt)
        jdbcTemplate.update(CREATE_NEW_USER, namedParameters, keyHolder, arrayOf("id"))
        val id = keyHolder.key
        return id?.toLong()
    }


}
