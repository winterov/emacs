package ru.emacs.users.repositories.impl




import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import ru.emacs.users.agregators.UserAccount
import ru.emacs.users.repositories.UserDetailsRepository
import ru.emacs.users.repositories.extractors.UserAccountExtractor


@Repository
internal class UserDetailsRepositoryImpl(private val jdbcTemplate: NamedParameterJdbcTemplate) : UserDetailsRepository {
    companion object {
        private const val LOAD_BY_EMAIL =
            "SELECT  u.id, u.email, u.is_email_verified, u.e_status, u.phone, u.password, " +
                    "u.is_phone_verified, u.credential_expired, u.createdat, u.updatedat, " +
                    "r.title as r_title, r.description as r_description,r.id as r_id,a.id," +
                    "a.e_authorities, a.id as a_id " +
                    "FROM users as u " +
                    "left join users_roles ur on u.id = ur.user_id " +
                    "left join roles r on ur.role_id = r.id " +
                    "left join roles_authorities ra on r.id = ra.role_id " +
                    "left join authorities a on ra.authority_id = a.id " +
                    "where u.email=:email"+
                    " and(u.e_status='ACTIVE' or u.e_status='NEW_USER')"
        private const val LOAD_BY_PHONE =
            "SELECT  u.id, u.email, u.is_email_verified, u.e_status, u.phone, u.password," +
                    "u.is_phone_verified, u.credential_expired, u.createdat, u.updatedat, " +
                    "r.title as r_title, r.description as r_description,r.id as r_id,a.id," +
                    "a.e_authorities, a.id as a_id " +
                    "FROM users as u " +
                    "left join users_roles ur on u.id = ur.user_id " +
                    "left join roles r on ur.role_id = r.id " +
                    "left join roles_authorities ra on r.id = ra.role_id " +
                    "left join authorities a on ra.authority_id = a.id " +
                    "where u.phone=:phone"+
                    " and(u.e_status='ACTIVE' or u.e_status='NEW_USER')"
    }
    override fun loadByEmail(email: String): UserAccount? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource().addValue("email", email)
        return jdbcTemplate.query(
        LOAD_BY_EMAIL, namedParameters, UserAccountExtractor()
        )
    }

    override fun loadByPhone(phone: String):UserAccount? {
        val namedParameters: SqlParameterSource = MapSqlParameterSource().addValue("phone", phone)
        return jdbcTemplate.query(
        LOAD_BY_PHONE, namedParameters, UserAccountExtractor()
        )
    }


}
