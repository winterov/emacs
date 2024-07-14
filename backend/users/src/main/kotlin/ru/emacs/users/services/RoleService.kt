package ru.emacs.users.services





interface RoleService {
    fun setDefaultRoleForNewUser(userId: Long)
    fun checkRoleForDelete(roleId: Long)
    fun deleteRole(roleId: Long)
}
