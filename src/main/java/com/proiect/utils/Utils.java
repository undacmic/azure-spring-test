package com.proiect.utils;

import com.auth0.jwt.algorithms.Algorithm;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


public class Utils {

    public static byte[] getRandomBytes(int size)
        throws NoSuchAlgorithmException
    {
        byte[] b = new byte[size];
        SecureRandom random = SecureRandom.getInstance("DRBG",
                DrbgParameters.instantiation(256, DrbgParameters.Capability.PR_AND_RESEED, null));
        byte[] seed = random.generateSeed(64);
        random.nextBytes(b);
        return b;
    }
    public static String toHex (byte[] input)
            throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, input);
        String hex = bi.toString(16);

        int paddingLength = (input.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
    public static byte[] fromHex(String hex)
            throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    public static String getHash(String password,byte[] salt)
        throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 512);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Utils.toHex(salt)+":"+Utils.toHex(hash);
    }
    public static boolean verifyHash(String originalPassword, String storedPassword)
        throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        String[] parts = storedPassword.split(":");
        byte[] salt = fromHex(parts[0]);
        byte[] passwordHash = fromHex(parts[1]);
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
                salt, 65536, 512);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] verificationHash = skf.generateSecret(spec).getEncoded();

        int diff = passwordHash.length ^ verificationHash.length;
        int i;
        for(i=0;i<passwordHash.length;i++) {
            diff |= passwordHash[i] ^ verificationHash[i];
        }

        return diff == 0;


    }

    public static KeyPair generateKeyPair()
            throws NoSuchAlgorithmException, InvalidAlgorithmParameterException
    {
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
        kpg.initialize(ecSpec, new SecureRandom());
        KeyPair keypair = kpg.generateKeyPair();
        return keypair;
    }




}
