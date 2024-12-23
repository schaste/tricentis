package com.homework.tricentis.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.homework.tricentis.exception.ErrorTypes;
import com.homework.tricentis.exception.TricentisException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MessageValidationServiceTest {

    @Autowired
    private MessageValidationService messageValidationService;

    private String validMessage;
    private String invalidMessageMissingTimestamp;
    private String invalidMessageTimestampFormat;

    @BeforeEach
    public void setUp() {
        // Create a valid message with timestamp
        JsonObject validJson = new JsonObject();
        validJson.addProperty("timestamp", "2024-12-23T12:45:30.123Z");
        validMessage = validJson.toString();

        // Create an invalid message without timestamp
        JsonObject invalidJsonMissingTimestamp = new JsonObject();
        invalidJsonMissingTimestamp.addProperty("data", "some data");
        invalidMessageMissingTimestamp = invalidJsonMissingTimestamp.toString();

        // Create an invalid message with wrong timestamp format
        JsonObject invalidJsonWrongTimestamp = new JsonObject();
        invalidJsonWrongTimestamp.addProperty("timestamp", "12/23/2024 12:45:30");
        invalidMessageTimestampFormat = invalidJsonWrongTimestamp.toString();
    }

    @Test
    public void testValidTimestamp() {
        String timestamp = messageValidationService.validateTimestamp(validMessage);
        assertNotNull(timestamp);
        assertEquals("2024-12-23T12:45:30.123Z", timestamp);
    }

    @Test
    public void testInvalidTimestampMissing() {
        TricentisException exception = assertThrows(TricentisException.class, () -> {
            messageValidationService.validateTimestamp(invalidMessageMissingTimestamp);
        });
        assertTrue(exception.getErrorTypes().equals(ErrorTypes.TIMESTAMP_NOT_VALID));
    }

    @Test
    public void testInvalidTimestampFormat() {
        TricentisException exception = assertThrows(TricentisException.class, () -> {
            messageValidationService.validateTimestamp(invalidMessageTimestampFormat);
        });
        assertTrue(exception.getErrorTypes().equals(ErrorTypes.TIMESTAMP_NOT_VALID));
    }

    @Test
    public void testInvalidMessageFormat() {
        String invalidMessage = "This is an invalid JSON string without timestamp.";
        TricentisException exception = assertThrows(TricentisException.class, () -> {
            messageValidationService.validateTimestamp(invalidMessage);
        });
        assertTrue(exception.getErrorTypes().equals(ErrorTypes.TIMESTAMP_NOT_VALID));
    }
}

