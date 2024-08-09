package ru.emacs.properties.repositories.extractors

import org.springframework.jdbc.core.RowMapper
import ru.emacs.properties.models.EEmailType
import ru.emacs.properties.models.EmailSettings
import java.sql.ResultSet

internal class EmailRowMapper : RowMapper<EmailSettings> {
    override fun mapRow(rs: ResultSet, rowNum: Int): EmailSettings {
        val emailSettings = EmailSettings()
        emailSettings.id = rs.getLong("id")
        emailSettings.type = EEmailType.valueOf(rs.getString("type"))
        emailSettings.isEnabled = rs.getBoolean("is_enabled")
        emailSettings.description = rs.getString("description")
        emailSettings.email = rs.getString("email")
        emailSettings.password = rs.getString("password")
        emailSettings.smtpServer.host = rs.getString("smtp_host")
        emailSettings.smtpServer.requireAuth = rs.getBoolean("smtp_require_auth")
        val smtpProtocol = rs.getString("smtp_protocol")
        if (smtpProtocol != null) {
            emailSettings.smtpServer.protocol =
                EmailSettings.SmtpServerProtocol.valueOf(smtpProtocol)
        }
        emailSettings.smtpServer.portSSL = rs.getInt("smtp_port_ssl")
        emailSettings.smtpServer.portTLS = rs.getInt("smtp_port_tls")
        emailSettings.smtpServer.connectionTimeout = rs.getInt("smtp_connection_timeout")
        emailSettings.smtpServer.timeout = rs.getInt("smtp_timeout")
        val serverType = rs.getString("incoming_server_type")
        if (serverType != null) {
            emailSettings.incomingServer.serverType =
                EmailSettings.IncomingServerType.valueOf(serverType)
        }
        emailSettings.incomingServer.imapServer = rs.getString("incoming_imap_server")
        emailSettings.incomingServer.enabledSSL = rs.getBoolean("incoming_enabled_ssl")
        emailSettings.incomingServer.portSSL = rs.getInt("incoming_port_ssl")
        emailSettings.incomingServer.connectionTimeout = rs.getInt("incoming_server_connection_timeout")
        emailSettings.incomingServer.timeout = rs.getInt("incoming_server_timeout")
        return emailSettings
    }
}