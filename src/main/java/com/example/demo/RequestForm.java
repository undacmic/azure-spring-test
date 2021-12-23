package com.example.demo;

public class RequestForm {

    RequestForm() {}
    RequestForm(Long idUser,
            Long idBook,
                String type){
        this.idUser = idUser;
        this.idBook = idBook;
        this.type = type;
    }

    private Long idUser;
    private Long idBook;
    private String type;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
