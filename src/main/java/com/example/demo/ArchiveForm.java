package com.example.demo;

import java.time.LocalDate;

public class ArchiveForm {

    ArchiveForm() {}
    ArchiveForm(Long userId,
                String readAccess,
                LocalDate expirationDate){
        this.userId = userId;
        this.readAccess = readAccess;
        this.expirationDate = expirationDate;
    }
    private Long userId;
    private String readAccess;
    private LocalDate expirationDate;

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReadAccess() {
        return readAccess;
    }

    public void setReadAccess(String readAccess) {
        this.readAccess = readAccess;
    }
}
