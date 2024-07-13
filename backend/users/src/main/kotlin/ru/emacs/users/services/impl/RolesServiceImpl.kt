package ru.emacs.users.services.impl



import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.emacs.users.repositories.RoleRepository
import ru.emacs.users.services.RoleService


@Service
internal class RolesServiceImpl @Autowired constructor(private val roleRepository: RoleRepository) :
    RoleService {
    fun setRole(userId: Long, roleId: Long): Int {
        return roleRepository.setRole(userId, roleId)
    }

    override fun setDefaultRoleForNewUser(userId: Long) {
        setRole(userId, ROLE_ID_FOR_NEW_USER)
    }

   override fun checkRoleForDelete(roleId: Long){
        TODO()
    }

    override fun deleteRole(roleId: Long){
       TODO()
    }

    companion object {
        private const val ROLE_ID_FOR_NEW_USER = 6L
    }
}
