package com.homework.tricentis.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.homework.tricentis.exception.ErrorTypes;
import com.homework.tricentis.exception.TricentisException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.format.DateTimeParseException;

@Log4j2
@Service
@RequiredArgsConstructor
public class MessageValidationService {

    public String validateTimestamp(String message) throws TricentisException {
        try {
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
            String timestamp = jsonObject.get("timestamp").getAsString();

            if (timestamp == null || timestamp.isEmpty()) {
                log.error("Timestamp is missing.");
                throw new TricentisException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorTypes.TIMESTAMP_NOT_VALID);
            }

            if (!isValidTimestamp(timestamp)) {
                log.error("Invalid timestamp format.");
                throw new TricentisException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorTypes.TIMESTAMP_NOT_VALID);
            }

            return timestamp;

        } catch (Exception e) {
            log.error("Invalid message format or missing timestamp.");
            throw new TricentisException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorTypes.TIMESTAMP_NOT_VALID);
        }
    }

    private boolean isValidTimestamp(String timestamp) {
        try {
            // Try to parse the timestamp as an Instant (UTC)
            Instant.parse(timestamp);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

