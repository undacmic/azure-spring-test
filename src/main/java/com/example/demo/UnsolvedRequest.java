package com.example.demo;

import java.time.LocalDate;

public class UnsolvedRequest {

    UnsolvedRequest() {}
    public UnsolvedRequest(Long requestId,
                           LocalDate requestDate,
                           String type,
                           String username,
                           Long objectId)
    {
        this.requestId = requestId;
        this.requestDate = requestDate;
        this.type = type;
        this.username = username;
        this.objectId = objectId;
    }

    private Long requestId;
    private LocalDate requestDate;
    private String type;
    private String username;
    private Long objectId;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
}
