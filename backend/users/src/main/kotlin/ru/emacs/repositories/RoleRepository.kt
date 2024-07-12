package ru.emacs.repositories

import ru.emacs.agregators.ERoleStatus


internal interface RoleRepository {
    fun setRole(userId: Long, roleId: Long): Int
    fun changeRoleStatus(roleIds: List<Long?>, roleStatus: ERoleStatus?): Int
}
