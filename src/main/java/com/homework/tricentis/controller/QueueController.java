package com.homework.tricentis.controller;

import com.homework.tricentis.exception.ErrorTypes;
import com.homework.tricentis.exception.TricentisException;
import com.homework.tricentis.service.MessageValidationService;
import com.homework.tricentis.service.QueueService;
import com.homework.tricentis.validation.DataValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Queue;

@Log4j2
@RestController
@RequestMapping("/api/queues")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;
    private final MessageValidationService messageValidationService;

    @PostMapping("/{queueName}/enqueue")
    public ResponseEntity<String> enqueue(@PathVariable String queueName, @RequestBody String message) {
        try {
            if(!DataValidation.isValidMessage(message)) {
                throw new TricentisException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorTypes.MESSAGE_NOT_VALID);
            }

            messageValidationService.validateTimestamp(message);

            queueService.enqueue(queueName, message);
            return ResponseEntity.status(HttpStatus.CREATED).body("Message enqueued successfully");
        } catch (TricentisException ipx) {
            throw ipx;
        } catch (Exception e) {
            log.error("General error exception", e);
            throw new TricentisException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorTypes.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{queueName}/dequeue")
    public ResponseEntity<String> dequeue(@PathVariable String queueName) {
        String message = queueService.dequeue(queueName);
        //TODO Maybe we need check message before response to client that timestamp exists
        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/{queueName}/snapshot")
    public ResponseEntity<Queue<String>> snapshot(@PathVariable String queueName) {
        Queue<String> snapshot = queueService.getSnapshot(queueName);
        if (snapshot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(snapshot);
        }
    }
}
