package ru.emacs.users.repositories.impl

import org.intellij.lang.annotations.Language
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import ru.emacs.users.agregators.EUserStatus
import ru.emacs.users.repositories.UserPhoneRepository

@Repository
internal class UserPhoneRepositoryImpl(private val jdbcTemplate: NamedParameterJdbcTemplate): UserPhoneRepository {
    companion object{
        @Language("SQL")
        private const val COUNT_USAGE_PHONE = "SELECT COUNT(u.phone) " +
                "FROM users as u where u.phone = :phone and u.e_status in (:statuses);"
    }
    override fun countOfUsagePhone(phone: String, statuses: List<EUserStatus>): Long {
        val statusesStrings = statuses.stream().map { it.toString() }.toList()
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("phone", phone)
            .addValue("statuses",statusesStrings)
        return jdbcTemplate
            .queryForObject(
                COUNT_USAGE_PHONE,
                namedParameters, Long::class.java
            )!!
    }
}