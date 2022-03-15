package com.tset.demo.releasemanager.exceptions

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import javax.servlet.http.HttpServletResponse

class CustomErrorDto(
    val errorCode: Int,
    val message: String,
    val detailedMessage: String,
//    val traceId: String, //If we want to enable tracing
    val errors: List<Any> = listOf()
) {
    constructor (errorCode: ErrorCode, detailedMessage: String) : this(
        errorCode.errorCode,
        errorCode.message,
        detailedMessage
    )

    constructor (
        errorCode: ErrorCode,
        detailedMessage: String,
        errors: List<Any>
    ) : this(errorCode.errorCode, errorCode.message, detailedMessage, errors)

    fun writeAsResponse(response: HttpServletResponse, objectMapper: ObjectMapper) {
        // response.status = httpStatus.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(this))
    }
}
