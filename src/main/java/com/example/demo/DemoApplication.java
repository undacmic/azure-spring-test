package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args)
		throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException
	{

		SecurityHandler sh = new SecurityHandler();
		SpringApplication.run(DemoApplication.class, args);

	}

}
