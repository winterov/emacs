package ru.emacs.services





interface RoleService {
    fun setDefaultRoleForNewUser(userId: Long)
    fun checkRoleForDelete(roleId: Long)
    fun deleteRole(roleId: Long)
}
