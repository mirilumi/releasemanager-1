package com.tset.demo.releasemanager.exceptions

import org.springframework.http.HttpStatus

enum class ErrorCode(val errorCode: Int, val httpStatus: HttpStatus, val message: String) {
    RESOURCE_NOT_FOUND(4000, HttpStatus.NOT_FOUND, "Resource not found"),
    BAD_REQUEST(4001, HttpStatus.BAD_REQUEST, "Bad Request"),
    NUMBER_FORMAT_EXCEPTION(4002, HttpStatus.BAD_REQUEST, "Number format exception"),
    MESSAGE_CONVERTER(
        4003,
        HttpStatus.BAD_REQUEST,
        "Conversion attempt fails / Are u sending a non nullable property to null?"
    ),
    SERVER_ERROR(5000, HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"),
    ILLEGAL_ARGUMENT_TYPE(4001, HttpStatus.BAD_REQUEST, "Illegal Argument Type"),
    UN_PROCESSABLE(4033, HttpStatus.UNPROCESSABLE_ENTITY, "Un-processable Request"),
}