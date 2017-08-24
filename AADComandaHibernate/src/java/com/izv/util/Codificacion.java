package com.izv.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Codificacion {

    private Codificacion() {
    }

    private static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuilder buf = new StringBuilder();
        for (int j = 0; j < b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static String sha1(String entrada) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(entrada.getBytes());
            byte[] salida = md.digest();
            return bytesToHex(salida);
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    public static String md5(String entrada) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(entrada.getBytes());
            byte[] salida = md.digest();
            return bytesToHex(salida);
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }
}