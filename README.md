Tricentis homework multiple queue application
---
Multiple queue implementation

## Technologies Used
* Spring Boot (Java framework for building web applications)
* Lombok (To simplify the code for Java bean classes)

## Setup:
## Prerequisites
* Java 17 or later
* Maven for building the project
## Steps to Set Up the Application
1. Copy and extract project locally
2. Build the project using Maven:
```
mvn clean install
```
3. Run the Spring Boot Application:
```
mvn spring-boot:run
```
By default, the application will run on http://localhost:8080.

## Endpoints
1. POST /api/queues/{queues_name}/enqueue
*  Add message to queue
```
Response:
Message enqueued successfully
```
2. GET /api/queues/{queues_name}/enqueue
* Fetches message from specific queue
```
Response:
{
    ...
    "timestamp": "2024-12-23T15:2656+00:00",
    ...    
}
```
3GET /api/queues/{queues_name}/snapshot
* Fetches all data from specific queue
```
Response:
[{
    ...
    "timestamp": "2024-11-23T15:2656+00:00",
    ...    
},
{
    ...
    "timestamp": "2024-10-23T15:2656+00:00",
    ...    
}
...]
```
## Error Handling
The application uses custom error codes for different error scenarios:
* ERR_100: Internal Server Error (e.g., when something goes wrong).
* ERR_101: Message is not valid (e.g., when trying to fetch a message).
* ERR_102: Timestamp is not valid (e.g., timestamp broken or missing).
```
Example of an error response:
{
    "message": "Timestamp is not valid",
    "timestamp": "2024-12-23T17:09:40.541+00:00",
    "internalCode": "ERR_102"
}
```
## Application Logs
You can check the logs for any issues.