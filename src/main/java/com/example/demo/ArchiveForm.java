package com.example.demo;

public class ArchiveForm {

    ArchiveForm() {}
    ArchiveForm(Long userId,
                String readAccess){
        this.userId = userId;
        this.readAccess = readAccess;
    }
    private Long userId;
    private String readAccess;

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
