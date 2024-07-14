package ru.emacs.users.utils




import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import ru.emacs.properties.services.SecurityPropertiesService
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

@Component
internal class JwtUtils(private val securityPropertiesService: SecurityPropertiesService) {

    private lateinit var secretKey: SecretKey
    fun generateToken(
        authorities: Collection<GrantedAuthority>, email: String?, issuedDate: Date?,
        expiredDate: Date?, id: Long?
    ): String {
        val claims: MutableMap<String, Any?> = HashMap()
        val authoritiesList = authorities
            .map { obj: GrantedAuthority -> obj.authority }
            .toList()
        claims["authorities"] = authoritiesList
        claims["id"] = id
        return Jwts.builder()
            .claims(claims)
            .subject(email)
            .issuedAt(issuedDate)
            .expiration(expiredDate)
            .signWith(secretKey)
            .compact()
    }

    fun getEmailFromToken(token: String): String {
        return getClaimFromToken(token) { claims: Claims -> claims.subject }
    }

    fun getAuthorities(token: String): List<String> {
        return getClaimFromToken(token) { claims: Claims ->
            claims.get(
                "authorities",
                List::class.java
            ) as List<String>
        }
    }

    fun generateRefreshTokenFromEmail(): String {
        return UUID.randomUUID().toString()
    }

    private fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    @PostConstruct
    fun createKey() {
        val properties = securityPropertiesService.getSecurityProperty().jwtProperties
        secretKey = Keys.hmacShaKeyFor(properties.secret.toByteArray())
    }
}
