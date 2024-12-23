package com.homework.tricentis.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QueueControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testEnqueue() {
        String queueName = "testQueue";
        String message = "{\"timestamp\":\"2024-12-23T15:26:24.756+00:00\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(message, headers);
        ResponseEntity<String> response = restTemplate.exchange("/api/queues/" + queueName + "/enqueue", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Message enqueued successfully"));
    }

    @Test
    void testDequeue() {
        String queueName = "testQueue";
        String message = "{\"timestamp\":\"2024-12-23T15:26:24.756+00:00\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(message, headers);
        ResponseEntity<String> responsePost = restTemplate.exchange("/api/queues/" + queueName + "/enqueue", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());
        assertTrue(responsePost.getBody().contains("Message enqueued successfully"));

        ResponseEntity<String> response = restTemplate.getForEntity("/api/queues/" + queueName + "/dequeue", String.class, "");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEmptyDequeue() {
        String queueName = "testQueue";
        while(true) {
            ResponseEntity<String> response = restTemplate.getForEntity("/api/queues/" + queueName + "/dequeue", String.class, "");
            if(response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
                break;
            } else {
                assertEquals(HttpStatus.OK, response.getStatusCode());
            }
        }
    }

    @Test
    void testSnapshot() throws Exception {
        String queueName = "testQueue";
        String message = "{\"timestamp\":\"2024-12-23T15:26:24.756+00:00\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(message, headers);
        ResponseEntity<String> responsePost = restTemplate.exchange("/api/queues/" + queueName + "/enqueue", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());
        assertTrue(responsePost.getBody().contains("Message enqueued successfully"));

        ResponseEntity<String> response = restTemplate.getForEntity("/api/queues/" + queueName + "/snapshot", String.class, "");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().indexOf("timestamp") > 0);
    }

    @Test
    void testEmptySnapshot() throws Exception {
        String queueName = "testQueue";
        ResponseEntity<String> response = restTemplate.getForEntity("/api/queues/" + queueName + "/snapshot", String.class, "");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testTimestampWrong() throws Exception {
        String queueName = "testQueue";
        String message = "{\"timestamp\":\"2024-12-23T15:26756+00:00\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(message, headers);
        ResponseEntity<String> response = restTemplate.exchange("/api/queues/" + queueName + "/enqueue", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().indexOf("ERR_102") > 0);
    }
}

