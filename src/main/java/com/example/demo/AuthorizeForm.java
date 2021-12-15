package com.example.demo;

public class AuthorizeForm {

    AuthorizeForm() {}
    AuthorizeForm(String signature,
                  String token,
                  String encodedPublic,
                  String algorithm) {
        this.token=token;
        this.signature = signature;
        this.encodedPublic=encodedPublic;
        this.algorithm = algorithm;
    }

    private String token;
    private String encodedPublic;
    private String signature;
    private String algorithm;


    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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
