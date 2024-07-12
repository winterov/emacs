package ru.emacs.repositories.extractors


import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.util.StringUtils
import ru.emacs.agregators.AppRole
import ru.emacs.agregators.ERoleStatus
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*


internal class RolesTreeExtractor : ResultSetExtractor<List<AppRole>> {
    @Throws(SQLException::class, DataAccessException::class)
    override fun extractData(rs: ResultSet): List<AppRole> {
        val rolesLeafs: Queue<AppRole> = LinkedList()
        val rolesNodes: MutableMap<Long?, AppRole> = HashMap()
        var startNode: AppRole? = null
        while (rs.next()) {
            val role = mapRow(rs)
            if (role.parentId == 0L) {
                startNode = role
                startNode.children = LinkedList()
            } else {
                if (role.isCatalog) {
                    role.children = LinkedList()
                    rolesNodes[role.id] = role
                } else {
                    rolesLeafs.add(role)
                }
            }
        }
        /* Раскидали листья*/
        while (!rolesLeafs.isEmpty()) {
            val role = rolesLeafs.poll()
            val parent = rolesNodes[role.parentId]
            parent!!.children.add(role)
        }
        /*раскидали каталоги*/
        for ((_, roleNode) in rolesNodes) {
            if (roleNode.parentId == startNode!!.id) {
                startNode.children.add(roleNode)
            } else {
                rolesNodes[roleNode.parentId]!!.children.add(roleNode)
            }
        }
        return startNode!!.children
    }

    @Throws(SQLException::class)
    private fun mapRow(rs: ResultSet): AppRole {
        val  id = rs.getLong("id")
        val title = rs.getString("title")
        val description = rs.getString("description")
        val role = AppRole(id,title,description)
        role.isCatalog = rs.getBoolean("is_catalog")
        role.status = ERoleStatus.valueOf(rs.getString("status"))
        role.createdAt = rs.getTimestamp("createdat").toLocalDateTime()
        role.updatedAt = rs.getTimestamp("updatedat").toLocalDateTime()
        val rawPath = rs.getString("path")
        val pathArray = StringUtils.delimitedListToStringArray(rawPath, ".")
        if (pathArray.size > 1) {
            role.parentId = java.lang.Long.getLong(pathArray[pathArray.size - 2])
        } else {
            role.parentId = 0L
        }
        role.modifyBy = rs.getString("email")

        return role
    }
}
