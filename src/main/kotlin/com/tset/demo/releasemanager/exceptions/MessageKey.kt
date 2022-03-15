package com.tset.demo.releasemanager.exceptions

enum class MessageKey(val key: String, val numTokens: Int) {
    BAD_REQUEST("bad_request", 1),
    NULL_PROPERTY("null_property", 1),
    EMPTY_PROPERTY("empty_property", 1),
    NULL_OR_EMPTY_PROPERTY("null_or_empty_property", 1),
    INVALID_PAYLOAD("invalid_payload", 0),
    WILDCARD_ARGUMENT("wildcard_argument", 1),
    ENTITY_NOT_FOUND("entity_not_found", 1),
}