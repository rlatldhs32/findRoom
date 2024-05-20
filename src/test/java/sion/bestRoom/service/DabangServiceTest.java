package sion.bestRoom.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;


class DabangServiceTest {



    @Test
    void test2(){
        String url = "goood123";
        Long abc = 101L;
        String newurl = url + abc;
        System.out.println(newurl);
    }


    @Test
    void test(){
//        String url = "https://www.dabangapp.com/api/3/room/new-list/multi-room/region?api_version=3.0.1&call_type=web&code=41133101&filters=%7B%22multi_room_type%22%3A%5B0%2C1%2C2%5D%2C%22selling_type%22%3A%5B0%2C1%2C2%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B1%2C2%5D%2C%22use_approval_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22is_contract%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D&page=1&version=1&zoom=13";
        String url = "\n" +
                "https://www.dabangapp.com/api/3/room/new-list/multi-room/region?api_version=3.0.1&call_type=web&code=41117105&filters=%7B%22multi_room_type%22%3A%5B0%2C1%2C2%5D%2C%22selling_type%22%3A%5B0%2C1%2C2%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B1%2C2%5D%2C%22use_approval_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22is_contract%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D&page=1&version=1&zoom=13";
//        String url = "6625ff8bafbee50d1a1c33a9";
        // -> https://www.dabangapp.com/api/3/room/new-list/multi-room/region?api_version=3.0.1&call_type=web
        // &code=41117105&filters={"multi_room_type":[0,1,2],"selling_type":[0,1,2],"deposit_range":[0,999999],
        // "price_range":[0,999999],"trade_range":[0,999999],"maintenance_cost_range":[0,999999],"room_size":[0,999999],
        // "supply_space_range":[0,999999],"room_floor_multi":[1,2,3,4,5,6,7,-1,0],"division":false,"duplex":false,
        // "room_type":[1,2],"use_approval_date_range":[0,999999],"parking_average_range":[0,999999],"household_num_range":[0,999999],
        // "parking":false,"short_lease":false,"full_option":false,
        // "elevator":false,"balcony":false,"safety":false,"pano":false,"is_contract":false,"deal_type":[0,1]}&page=1&version=1&zoom=13
        try {
            // 필터 문자열을 디코딩
            String decodedFilters = URLDecoder.decode(url, "UTF-8");
            System.out.println(decodedFilters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void encodingTest(){
        String baseUrl = "https://www.dabangapp.com/api/3/room/new-list/multi-room/region?";
        String parameters = "43578725";

        //180 인코딩하기 43578725
        try {
            // 인코딩된 URL 생성
            String encodedParameters = URLEncoder.encode(parameters, "UTF-8");
            System.out.println(encodedParameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }




    }

    @Test
    void encodingTest2(){
        String input = "43578725";
        String hashedOutput = hashSHA256(input);
        System.out.println(hashedOutput);
        Assertions.assertThat(hashedOutput).isEqualTo("6625ff8bafbee50d1a1c33a9");
        //4504a3075b6a6c344d45a158b5554939eb25c9c0ee80d4be6a34e964ee6cf721
    }

    public static String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}