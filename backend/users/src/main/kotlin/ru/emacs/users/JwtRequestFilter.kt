package ru.emacs.users




import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.emacs.users.utils.JwtUtils
import java.io.IOException
import java.util.stream.Collectors


@Component
internal class JwtRequestFilter @Autowired constructor(private val jwtTokenUtil: JwtUtils) :
    OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        var email: String? = null
        var jwt: String? = null
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7)
            try {
                email = jwtTokenUtil.getEmailFromToken(jwt)
            } catch (e: ExpiredJwtException) {
                this.logger.error("The token is expired")
                throw org.springframework.security.access.AccessDeniedException("Срок действия токена авторизации истек")
            } catch (e: MalformedJwtException) {
                logger.error("RefreshToken not valid")
                 throw org.springframework.security.access.AccessDeniedException("Токен авторизации не валиден")
            }catch (e: JwtException){
                logger.error(e.message+"token: $jwt")
                throw org.springframework.security.access.AccessDeniedException("Токен авторизации не валиден")
            }
        }

        if (email != null && SecurityContextHolder.getContext().authentication == null) {
            println(jwtTokenUtil.getAuthorities(jwt!!))
            val token = UsernamePasswordAuthenticationToken(email, null,
                jwtTokenUtil.getAuthorities(jwt).stream().map { role: String? -> SimpleGrantedAuthority(role) }
                    .collect(Collectors.toList()))
            SecurityContextHolder.getContext().authentication = token
        }

        filterChain.doFilter(request, response)
    }
}
