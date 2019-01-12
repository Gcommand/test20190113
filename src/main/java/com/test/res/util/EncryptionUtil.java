package com.test.res.util;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class EncryptionUtil {

    public static String sha1(String toHash)
    {
        String hashStr = null;
//        String password = "password";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(toHash.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            hashStr = hash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return hashStr;

//        return hash.toLowerCase(Locale.ENGLISH);
    }
}
