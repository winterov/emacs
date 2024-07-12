package ru.emacs.agregators

import java.time.LocalDateTime
import java.util.*

internal data class AppRole (
    var id :Long,
    var title: String? = null,
    var description: String? = null,
    var authorities: MutableSet<AppAuthority> = HashSet(),
    var isCatalog: Boolean = false,
    var parentId: Long? = null,
    var status: ERoleStatus? = null,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    var path: String? = null,
    var modifyBy: String? = null, //email редактора
    var children: MutableList<AppRole> = LinkedList()
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppRole

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}