package com.gh.myblog_backend.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * myblog_backend
 * 2022/8/15 12:55
 * Origin
 */
@SuppressWarnings("all")
public class MD5UItils {

    public static String code(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if(i<0)
                    i+=256;
                if (i<16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(code("19990320"));
    }
}
