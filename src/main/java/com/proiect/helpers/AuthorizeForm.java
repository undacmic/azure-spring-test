package com.proiect.helpers;

public class AuthorizeForm {

    AuthorizeForm() {}
    AuthorizeForm(Long userId,
                  String token,
                  String encodedPublic) {
        this.token=token;
        this.userId=userId;
        this.encodedPublic=encodedPublic;
    }

    private Long userId;
    private String token;
    private String encodedPublic;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEncodedPublic() {
        return encodedPublic;
    }

    public void setEncodedPublic(String encodedPublic) {
        this.encodedPublic = encodedPublic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
