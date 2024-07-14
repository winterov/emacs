package ru.emacs.users


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import ru.emacs.users.services.UserAccountService


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
internal class SecurityConfiguration(private val jwtRequestFilter: JwtRequestFilter, private val userAccountDetailsService: UserAccountService) {
    @Bean
    @Throws(Exception::class)
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type")
        corsConfiguration.allowedOrigins = listOf("http://localhost:4200","http://localhost:80")
        corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE")
        corsConfiguration.allowCredentials = true
        corsConfiguration.exposedHeaders = listOf("Authorization")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**",corsConfiguration)
        http
            .csrf { csrf -> csrf.disable() }
            .cors { corsCustomizer -> corsCustomizer.configurationSource(source) }
            .headers { headerCustomizer -> headerCustomizer.frameOptions { customizer -> customizer.disable() } }
            .sessionManagement{sessionManagement->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
            .authorizeHttpRequests { request->request.anyRequest().permitAll() }
            .formLogin { customizer -> customizer.disable() }
            .exceptionHandling { handling-> handling.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) }
            .logout { logoutCustomizer->logoutCustomizer.disable() }
            .rememberMe {rememberMeCustomizer->rememberMeCustomizer.disable()  }
            .httpBasic { basicCustomizer -> basicCustomizer.disable() }
            .passwordManagement { passwordManagementCustomizer-> passwordManagementCustomizer.disable() }
            .userDetailsService(userAccountDetailsService)
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

}
