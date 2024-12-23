package com.homework.tricentis.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QueueServiceTest {

    @Autowired
    private QueueService queueService;

    @Test
    void testEnqueueAndDequeue() {
        String queueName = "testQueue";
        String message = "Hello World";

        queueService.enqueue(queueName, message);
        String dequeuedMessage = queueService.dequeue(queueName);

        assertNotNull(dequeuedMessage);
        assertEquals(message, dequeuedMessage);
    }
}

