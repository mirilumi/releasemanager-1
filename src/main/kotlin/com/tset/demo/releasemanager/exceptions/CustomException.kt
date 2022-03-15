package com.tset.demo.releasemanager.exceptions

class CustomException(
    val errorCode: ErrorCode,
    val errors: List<Any>,
    val messageKey: MessageKey,
    vararg messageTokens: Any?
) : RuntimeException(errorCode.message) {
    val messageTokens: Array<out Any?>

    constructor(errorCode: ErrorCode, messageKey: MessageKey, vararg messageTokens: Any?) : this(
        errorCode,
        emptyList<Any>(),
        messageKey,
        *messageTokens
    )

    init {
        if (messageKey.numTokens > 0) {
            assert(messageTokens.size == messageKey.numTokens)
        }
        this.messageTokens = messageTokens // .map { it.toString() }.toTypedArray()
    }
}
