package sion.bestRoom.util;

public class EncodingUtil {
    //문자 인코딩
    public static String encode(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            return str;
        }
    }
}
