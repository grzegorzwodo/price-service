package com.shoppingplatform.price;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class URLShortener {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = BASE62.length();

    public static String shortenURL(String longUrl) throws NoSuchAlgorithmException {
        // Generate SHA-1 hash
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = md.digest(longUrl.getBytes());

        // Encode the hash using Base62
        return base62Encode(hashBytes);
    }

    private static String base62Encode(byte[] hashBytes) {
        StringBuilder encoded = new StringBuilder();
        long hashValue = 0;
        for (int i = 0; i < 6; i++) { // Use only the first 6 bytes to keep it short
            hashValue = (hashValue << 8) | (hashBytes[i] & 0xFF);
        }
        while (hashValue > 0) {
            int index = (int) (hashValue % BASE);
            encoded.append(BASE62.charAt(index));
            hashValue /= BASE;
        }
        return encoded.reverse().toString();
    }

    public static void main(String[] args) {
        try {
            String longUrl = "https://www.example.com/very/long/url/that/needs/to/be/shortened";
            String shortUrl = shortenURL(longUrl);
            System.out.println("Short URL: " + shortUrl);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
