package com.starcord.main.utils;

import com.starcord.main.exceptions.InternalServerException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class HashingUtils {
    public String convertToSha256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return HexUtils.toHexString(hash);
        } catch(Exception ex) {
            System.out.println("HashingUtils error: " + ex.getMessage());
            ex.printStackTrace();
            throw new InternalServerException();
        }
    }
}
