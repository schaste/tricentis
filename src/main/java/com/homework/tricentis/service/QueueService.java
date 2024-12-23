package com.homework.tricentis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Log4j2
@Service
@RequiredArgsConstructor
public class QueueService {

    private final Map<String, Queue<String>> queues = new HashMap<>();

    public void enqueue(String queueName, String message) {
        queues.computeIfAbsent(queueName, k -> new LinkedList<>())
                .offer(message);
    }

    public String dequeue(String queueName) {
        Queue<String> queue = queues.get(queueName);
        if (queue != null && !queue.isEmpty()) {
            return queue.poll();
        }
        return null;
    }

    public Queue<String> getSnapshot(String queueName) {
        return queues.getOrDefault(queueName, new LinkedList<>());
    }
}
