package ru.emacs.users.repositories.impl



import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Repository
import ru.emacs.users.agregators.ERoleStatus

import ru.emacs.users.repositories.RoleRepository


@Repository
internal class RoleRepositoryImpl @Autowired constructor(private val jdbcTemplate: NamedParameterJdbcTemplate) :
    RoleRepository {
    companion object {
        private const val SET_ROLE = "INSERT INTO users_roles (role_id,user_id) VALUES (:roleId,:userId)"
        private const val CHANGE_ROLE_STATUS = "UPDATE roles set status=:status WHERE id IN (:roleIds)"
    }
    override fun setRole(userId: Long, roleId: Long): Int {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("roleId", roleId)
        return jdbcTemplate.update(SET_ROLE, namedParameters)
    }


    override fun changeRoleStatus(roleIds: List<Long?>, roleStatus: ERoleStatus?): Int {
        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("status", roleStatus!!.name)
            .addValue("roleIds", roleIds)
        return jdbcTemplate.update(CHANGE_ROLE_STATUS, namedParameters)
    }


}
