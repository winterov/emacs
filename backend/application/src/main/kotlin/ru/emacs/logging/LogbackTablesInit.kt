package ru.emacs.logging


import org.intellij.lang.annotations.Language
import ru.emacs.configurations.DatabaseConfiguration
import java.sql.DriverManager



class LogbackTablesInit{
    companion object{
        @Language("SQL")
        private const val CREATE_LOGGING_EVENT_TABLE="CREATE TABLE IF NOT EXISTS logging_event " +
                "  (" +
                "    timestmp         BIGINT NOT NULL," +
                "    formatted_message  TEXT NOT NULL," +
                "    logger_name       VARCHAR(254) NOT NULL," +
                "    level_string      VARCHAR(254) NOT NULL," +
                "    thread_name       VARCHAR(254)," +
                "    reference_flag    SMALLINT," +
                "    arg0              VARCHAR(254)," +
                "    arg1              VARCHAR(254)," +
                "    arg2              VARCHAR(254)," +
                "    arg3              VARCHAR(254)," +
                "    caller_filename   VARCHAR(254) NOT NULL," +
                "    caller_class      VARCHAR(254) NOT NULL," +
                "    caller_method     VARCHAR(254) NOT NULL," +
                "    caller_line       CHAR(4) NOT NULL," +
                "    event_id          BIGINT DEFAULT nextval('logging_event_id_seq') PRIMARY KEY" +
                "  );"
        @Language("SQL")
        private const val CREATE_SEQUENCE="CREATE SEQUENCE IF NOT EXISTS logging_event_id_seq MINVALUE 1 START 1;"
        @Language("SQL")
        private const val CREATE_LOGGING_EVENT_PROPERTY ="CREATE TABLE IF NOT EXISTS logging_event_property" +
                "  (" +
                "    event_id\t      BIGINT NOT NULL," +
                "    mapped_key        VARCHAR(254) NOT NULL," +
                "    mapped_value      VARCHAR(1024)," +
                "    PRIMARY KEY(event_id, mapped_key)," +
                "    FOREIGN KEY (event_id) REFERENCES logging_event(event_id) " +
                "  );"
        @Language("SQL")
        private const val CREATE_LOGGING_EVENT_EXCEPTION="CREATE TABLE IF NOT EXISTS logging_event_exception\n" +
                "  (" +
                "    event_id         BIGINT NOT NULL," +
                "    i                SMALLINT NOT NULL," +
                "    trace_line       VARCHAR(254) NOT NULL," +
                "    PRIMARY KEY(event_id, i)," +
                "    FOREIGN KEY (event_id) REFERENCES logging_event(event_id)" +
                "  );"
    }
    fun createTables(){
        val connection = DriverManager.getConnection(
            DatabaseConfiguration.URL,
            DatabaseConfiguration.USERNAME,
            DatabaseConfiguration.PASSWORD)
        connection.prepareStatement(CREATE_SEQUENCE).execute()
        connection.prepareStatement(CREATE_LOGGING_EVENT_TABLE).execute()
        connection.prepareStatement(CREATE_LOGGING_EVENT_PROPERTY).execute()
        connection.prepareStatement(CREATE_LOGGING_EVENT_EXCEPTION).execute()
        connection.close()

    }
}