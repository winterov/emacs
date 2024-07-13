package ru.emacs.users.utils


import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import ru.emacs.properties.services.SecurityPropertiesService
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


@Component
class PBFDK2Encoder(private val securityPropertiesService: SecurityPropertiesService) : PasswordEncoder {
    companion object{
        private const val SECRET_KEY_INSTANCE = "PBKDF2WithHmacSHA512"
    }

    override fun encode(rawPassword: CharSequence): String {
        val properties = securityPropertiesService.getSecurityProperty().passwordProperties
        try {
            val result = SecretKeyFactory.getInstance(SECRET_KEY_INSTANCE)
                .generateSecret(
                    PBEKeySpec(
                        rawPassword.toString().toCharArray(),
                        properties.secret.toByteArray(), properties.iteration, properties.keyLength
                    )
                )
                .encoded
            return Base64.getEncoder()
                .encodeToString(result)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidKeySpecException) {
            throw RuntimeException(e)
        }

    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        if (rawPassword == null || encodedPassword == null) return false
        return encode(rawPassword) == encodedPassword
    }

}