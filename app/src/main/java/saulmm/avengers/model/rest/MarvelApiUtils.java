package saulmm.avengers.model.rest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MarvelApiUtils {

    public static String generateMarvelHash (String publicKey, String privateKey) {

        String marvelHash = "";

        try {

            String timeStamp    = getUnixTimeStamp();
            String marvelData   = timeStamp + privateKey + publicKey;


            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] hash = messageDigest.digest(marvelData.getBytes());

            StringBuilder stringBuilder = new StringBuilder(2 * hash.length);

            for (byte b : hash)
                stringBuilder.append(String.format("%02x", b&0xff));

            marvelHash = stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {

            System.out.println("[DEBUG]" + " MarvelApiUtils generateMarvelHash - " +
                "NoSuchAlgorithmException");
        }

        return marvelHash;
    }

    public static String getUnixTimeStamp () {

        return String.valueOf(System.currentTimeMillis() / 1000L);
    }
}
