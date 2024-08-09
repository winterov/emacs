package ru.emacs.properties.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name="email_settings")
class EmailSettings {
    @field:Id
    var id: Long =0
    lateinit var type: EEmailType
    @field:Column("is_enabled")
    var isEnabled: Boolean=false
    lateinit var description: String
    lateinit var email: String
    lateinit var password: String
    var smtpServer: OutgoingSmtpServer = OutgoingSmtpServer()
    var incomingServer: IncomingServer = IncomingServer()


    class OutgoingSmtpServer {
        lateinit var host: String
        var requireAuth: Boolean = true
        lateinit var protocol: SmtpServerProtocol
        var portSSL: Int? = null
        var portTLS: Int? = null
        var connectionTimeout:Int=5000
        var timeout:Int=5000
    }


    class IncomingServer {
        lateinit var serverType: IncomingServerType
        lateinit var imapServer: String
        var enabledSSL: Boolean = false
        var portSSL: Int? = null
        var connectionTimeout:Int=5000
        var timeout:Int=5000
    }

    enum class IncomingServerType {
        IMAP, POP3
    }

    enum class SmtpServerProtocol {
        SSL, TLS
    }
}
