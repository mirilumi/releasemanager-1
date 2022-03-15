package com.tset.demo.releasemanager.exceptions

import java.util.*
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service

@Service
class MessageService(private val messages: MessageSource) {
    operator fun get(key: String, vararg tokens: Any?) =
        messages.getMessage(key, tokens, Locale.ENGLISH)
    operator fun get(messageKey: MessageKey, vararg tokens: Any?) =
        messages.getMessage(messageKey.key, tokens, Locale.ENGLISH)
}