package com.example.demo;

public class Request {


    Request() {

    }

    Request(String jsonObject,
            AuthorizeForm authorizeForm)
    {
        this.jsonObject=jsonObject;
        this.authorizeForm=authorizeForm;
    }


    private String jsonObject;
    private AuthorizeForm authorizeForm;

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public AuthorizeForm getAuthorizeForm() {
        return authorizeForm;
    }

    public void setAuthorizeForm(AuthorizeForm authorizeForm) {
        this.authorizeForm = authorizeForm;
    }
}
