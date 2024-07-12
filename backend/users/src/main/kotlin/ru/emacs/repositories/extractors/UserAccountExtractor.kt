package ru.emacs.repositories.extractors



import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.ResultSetExtractor
import ru.emacs.agregators.EUserStatus
import ru.emacs.agregators.UserAccount
import java.sql.ResultSet
import java.sql.SQLException


internal class UserAccountExtractor : ResultSetExtractor<UserAccount> {
    @Throws(DataAccessException::class, SQLException::class)
    override fun extractData(rs: ResultSet): UserAccount? {
        if (!rs.next()) {
            return null
        }
        val userAccount = extractUserAccount(rs)
        val roleExtractor = RoleExtractor()
        val role = roleExtractor.mapRow(rs,1)?: return userAccount
        val authorityExtractor = AuthorityExtractor()
        val authority = authorityExtractor.mapRow(rs,1)?:return userAccount
        role.authorities.add(authority)
        userAccount.roles.add(role)
        while (rs.next()){
            val r = roleExtractor.mapRow(rs,1)?: return userAccount
            val a = authorityExtractor.mapRow(rs,1)?:return userAccount
            r.authorities.add(a)
            userAccount.roles.add(r)
        }
        return userAccount
    }


    private fun extractUserAccount(rs: ResultSet):UserAccount{
        val userAccount = UserAccount()
        userAccount.id = rs.getLong("id")
        userAccount.email = rs.getString("email")
        userAccount.isEmailVerified=rs.getBoolean("is_email_verified")
        userAccount.status = EUserStatus.valueOf(rs.getString("e_status"))
        userAccount.phone = rs.getString("phone")
        userAccount.psword = rs.getString("password")
        userAccount.isPhoneVerified=rs.getBoolean("is_phone_verified")
        userAccount.credentialExpiredTime = rs.getTimestamp("credential_expired").toLocalDateTime()
        userAccount.createdAt = rs.getTimestamp("createdat").toLocalDateTime()
        userAccount.updatedAt = rs.getTimestamp("updatedat").toLocalDateTime()
        return userAccount
    }



}
