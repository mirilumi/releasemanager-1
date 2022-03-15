package com.tset.demo.releasemanager.exceptions

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class ControllerExceptionHandler(private val messageService: MessageService, private val objectMapper: ObjectMapper) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(value = [(CustomException::class)])
    fun catchedHandledException(e: CustomException): ResponseEntity<CustomErrorDto> {
        logger.error("Throwing handled Exception: ${e::class.qualifiedName}")
        return respond(e)
    }

    @ExceptionHandler(value = [(NumberFormatException::class)])
    fun numberFormatException(e: Throwable): ResponseEntity<CustomErrorDto> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(CustomErrorDto(ErrorCode.NUMBER_FORMAT_EXCEPTION, e.message ?: ""))
    }

    @ExceptionHandler(value = [(Throwable::class)])
    fun handleAnyException(e: Throwable): ResponseEntity<CustomErrorDto> {
        logger.error( "Unhandled Exception: ${e::class.qualifiedName}" )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(CustomErrorDto(ErrorCode.SERVER_ERROR, e.message ?: ""))
    }

    @ExceptionHandler(value = [(MethodArgumentTypeMismatchException::class)])
    fun handle(e: MethodArgumentTypeMismatchException) =
        respond(CustomException(ErrorCode.ILLEGAL_ARGUMENT_TYPE, MessageKey.WILDCARD_ARGUMENT, e.cause?.message ?: ""))

    @ExceptionHandler(value = [(ServletRequestBindingException::class)])
    fun handleServletRequestBindingException(e: ServletRequestBindingException) =
        respondToBadRequest(e)

    @ExceptionHandler(value = [(MissingKotlinParameterException::class)])
    fun handle(e: MissingKotlinParameterException) =
        respond(CustomException(ErrorCode.UN_PROCESSABLE, MessageKey.NULL_PROPERTY, e.parameter.name as Any))

    @ExceptionHandler(value = [(HttpMessageNotReadableException::class)])
    fun handleMessageNotReadable(e: HttpMessageNotReadableException): ResponseEntity<CustomErrorDto> {
        if (e.cause is MissingKotlinParameterException) {
            return handle(e.cause as MissingKotlinParameterException)
        }
        return respond(CustomException(ErrorCode.BAD_REQUEST, MessageKey.WILDCARD_ARGUMENT, e.message ?: ""))
    }

    @ExceptionHandler(value = [(HttpMessageConversionException::class)])
    fun handlePayloadIssues(e: HttpMessageConversionException): ResponseEntity<CustomErrorDto> {
        return respond(CustomException(ErrorCode.MESSAGE_CONVERTER, MessageKey.WILDCARD_ARGUMENT, e.message ?: ""))
    }

    @ExceptionHandler(value = [(HttpRequestMethodNotSupportedException::class)])
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException) =
        respondToBadRequest(e)

    private fun respondToBadRequest(e: Throwable) =
        respond(CustomException(ErrorCode.BAD_REQUEST, MessageKey.WILDCARD_ARGUMENT, e.message ?: ""))


    private fun respond(e: CustomException): ResponseEntity<CustomErrorDto> {
        logger.info(e.message)
        logger.debug(e::class.simpleName)
        val detailedMessage = messageService.get(e.messageKey.key, *e.messageTokens)
        val customErrorDto = CustomErrorDto(
            e.errorCode,
            detailedMessage,
            e.errors
        )
        logger.error("Error Body =>" + objectMapper.writeValueAsString(customErrorDto))
        return ResponseEntity
            .status(e.errorCode.httpStatus)
            .body(
                customErrorDto
            )
    }
}