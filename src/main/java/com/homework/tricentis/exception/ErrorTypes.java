package com.homework.tricentis.exception;

public enum ErrorTypes {

    INTERNAL_SERVER_ERROR("ERR_100", "Internal Server Error"),
    MESSAGE_NOT_VALID("ERR_101", "Message is not valid"),
    TIMESTAMP_NOT_VALID("ERR_102", "Timestamp is not valid");

    private final String internalErrorMessage;
    private final String internalErrorId;

    ErrorTypes(String internalErrorId, String internalErrorMessage) {
        this.internalErrorMessage = internalErrorMessage;
        this.internalErrorId = internalErrorId;
    }

    public String getInternalErrorMessage() {
        return internalErrorMessage;
    }

    public String getInternalErrorId() {
        return internalErrorId;
    }
}