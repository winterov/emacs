package ru.emacs.users.repositories

import ru.emacs.users.agregators.ERoleStatus


internal interface RoleRepository {
    fun setRole(userId: Long, roleId: Long): Int
    fun changeRoleStatus(roleIds: List<Long?>, roleStatus: ERoleStatus?): Int
}
