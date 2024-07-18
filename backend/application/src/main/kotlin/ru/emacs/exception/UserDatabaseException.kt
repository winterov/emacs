package ru.emacs.exception


import org.postgresql.util.PSQLException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.emacs.dtos.AppResponseErrorDto


@ControllerAdvice
class UserDatabaseException(private val psqlErrorsTranslator: PSQLErrorsTranslator) {
    private val log = LoggerFactory.getLogger(UserDatabaseException::class.java)
    @ExceptionHandler(value = [PSQLException::class])
    fun badSQLRequestCatch(exception: PSQLException): ResponseEntity<AppResponseErrorDto> {
        val serverErrorMessage = exception.serverErrorMessage
        val errorDto: AppResponseErrorDto = if (serverErrorMessage == null) {
            AppResponseErrorDto(
                HttpStatus.BAD_REQUEST,
                exception.message?:"Что то пошло не так"
            )
        } else {
            AppResponseErrorDto(
                HttpStatus.BAD_REQUEST,
                psqlErrorsTranslator.getMessage(serverErrorMessage)
            )
        }
        log.error(exception.message)
        return ResponseEntity<AppResponseErrorDto>(errorDto, HttpStatus.BAD_REQUEST)
    }
}