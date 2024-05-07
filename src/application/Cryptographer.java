package application;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cryptographer {

    public String encrypt(String stringForEncrypt){
        String saltForEncrypt = "itmo";
        String hexString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(saltForEncrypt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(stringForEncrypt.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length ; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hexString = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            System.out.println("No such algorithm for encrypting.");
        }
        return hexString;
    }
}