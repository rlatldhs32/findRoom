//package sion.bestRoom.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvValidationException;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import sion.bestRoom.model.OneRoom;
//
//import java.io.*;
//import java.math.BigInteger;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@Slf4j
//@SpringBootTest
//class DabangServiceTest {
//
//
//    @Autowired
//    private DabangService dabangService;
//
//    @Test
//    @DisplayName("query 시간테스트")
//    void testTimeQuery(){
//
//        long start2 = System.currentTimeMillis();
//        List<OneRoom> bestTopRooms2 = dabangService.getAllRoomTest(127.052258761841, 127.072258761841, 37.2549398021063, 37.5549398021063);
//        bestTopRooms2.sort((o1, o2) -> {
//            Double costEffectiveness1 = o1.getTotal_price() / o1.getSize();
//            Double costEffectiveness2 = o2.getTotal_price() / o2.getSize();
//            return costEffectiveness1.compareTo(costEffectiveness2);
//        });
//        long end2 = System.currentTimeMillis();
//
//        //쿼리 시간 측정
//        long start = System.currentTimeMillis();
//        List<OneRoom> bestTopRooms1 = dabangService.getBestTop10Rooms(127.052258761841, 127.072258761841, 37.2549398021063, 37.5549398021063);
//        long end = System.currentTimeMillis();
//        System.out.println("쿼리 시간 : " + (end - start) + "ms");
//
//        //하고 정렬 시간
////        long start2 = System.currentTimeMillis();
////        List<OneRoom> bestTopRooms2 = dabangService.getAllRoomTest(127.052258761841, 127.072258761841, 37.2549398021063, 37.5549398021063);
////        bestTopRooms2.sort((o1, o2) -> {
////            Double costEffectiveness1 = o1.getTotal_price() / o1.getSize();
////            Double costEffectiveness2 = o2.getTotal_price() / o2.getSize();
////            return costEffectiveness1.compareTo(costEffectiveness2);
////        });
////        long end2 = System.currentTimeMillis();
//        System.out.println("쿼리 시간 : " + (end - start) + "ms");
//        System.out.println("정렬 시간 : " + (end2 - start2) + "ms");
//
//        Assertions.assertThat(end-start).isLessThan(end2-start);
//
//    }
//
//
//    @Test
//    @DisplayName("query로 하기 vs 밖에서 정렬하기 차이 확인")
//    void test5(){
//
//        //시간차이 테스트
//
//        List<OneRoom> bestTopRooms1 = dabangService.getBestTop10Rooms(127.052258761841, 127.072258761841, 37.2549398021063, 37.5549398021063);
//        List<OneRoom> bestTopRooms2 = dabangService.getAllRoomTest(127.052258761841, 127.072258761841, 37.2549398021063, 37.5549398021063);
//
//        //정렬
//        bestTopRooms2.sort((o1, o2) -> {
//            Double costEffectiveness1 = o1.getTotal_price() / o1.getSize();
//            Double costEffectiveness2 = o2.getTotal_price() / o2.getSize();
//            return costEffectiveness1.compareTo(costEffectiveness2);
//        });
//
//        List<OneRoom> bestTop20Rooms1 = bestTopRooms1.subList(0, 20);
//        List<OneRoom> bestTop20Rooms2 = bestTopRooms2.subList(0, 20);
//
//        List<Long> bestTop20RoomIds1 = new ArrayList<>();
//        List<Long> bestTop20RoomIds2 = new ArrayList<>();
//
//        for (OneRoom oneRoom : bestTop20Rooms1) {
//            bestTop20RoomIds1.add(oneRoom.getId());
//        }
//        for (OneRoom oneRoom : bestTop20Rooms2) {
//            bestTop20RoomIds2.add(oneRoom.getId());
//        }
//
//        Assertions.assertThat(bestTop20RoomIds1).isEqualTo(bestTop20RoomIds2);
//
//
//    }
//
//    @Test
//    void test2(){
//        String url = "goood123";
//        Long abc = 101L;
//        String newurl = url + abc;
//        System.out.println(newurl);
//    }
//
//    @Test
//    void csvTest(){
//
//
//        String csvFile = "C:\\Users\\sionkim\\Downloads\\bus_info.csv";
//
//        String jsonFile = "C:\\Users\\sionkim\\Downloads\\bus_info2.json"; // JSON 파일 경로
//
//
//        BufferedReader br = null;
//        String line;
//        String csvSplitBy = ","; // CSV 파일에서 사용하는 구분자
//
//        ObjectMapper mapper = new ObjectMapper();
//        ArrayNode arrayNode = mapper.createArrayNode();
//
//        try {
//            br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8));
//            String[] headers = br.readLine().split(csvSplitBy); // 첫 번째 줄에서 헤더를 읽어옴
//
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(csvSplitBy);
//                ObjectNode objNode = mapper.createObjectNode();
//
//                for (int i = 0; i < headers.length; i++) {
//                    objNode.put(headers[i], values[i]);
//                }
//
//                arrayNode.add(objNode);
//            }
//
//            // JSON 파일로 쓰기
//            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonFile), arrayNode);
//            System.out.println("CSV 파일이 JSON으로 성공적으로 변환되었습니다!");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//
//    public static List<String[]> filterCSV(String filePath) {
//        List<String[]> filteredRecords = new ArrayList<>();
//
//        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
//            String[] nextLine;
//            String[] header = reader.readNext();  // 헤더 읽기
//
//            if (header != null) {
//                filteredRecords.add(header);  // 필터된 결과에 헤더 추가
//            }
//
//            while ((nextLine = reader.readNext()) != null) {
//                System.out.println(" nextLine : = " + nextLine);
//                String cityName = nextLine[7];  // "관리도시명" 컬럼 인덱스 (여기서는 7번째)
//
//                System.out.println("cityName = " + cityName);
//                if ("안동".equals(cityName)) {
//                    filteredRecords.add(nextLine);
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (CsvValidationException e) {
//            throw new RuntimeException(e);
//        }
//
//        return filteredRecords;
//    }
//
//
//    @Test
//    void test(){
////        String url = "https://www.dabangapp.com/api/3/room/new-list/multi-room/region?api_version=3.0.1&call_type=web&code=41133101&filters=%7B%22multi_room_type%22%3A%5B0%2C1%2C2%5D%2C%22selling_type%22%3A%5B0%2C1%2C2%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B1%2C2%5D%2C%22use_approval_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22is_contract%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D&page=1&version=1&zoom=13";
//        String url = "\n" +
//                "https://www.dabangapp.com/api/3/room/new-list/multi-room/region?api_version=3.0.1&call_type=web&code=41117105&filters=%7B%22multi_room_type%22%3A%5B0%2C1%2C2%5D%2C%22selling_type%22%3A%5B0%2C1%2C2%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B1%2C2%5D%2C%22use_approval_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22is_contract%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D&page=1&version=1&zoom=13";
////        String url = "6625ff8bafbee50d1a1c33a9";
//        // -> https://www.dabangapp.com/api/3/room/new-list/multi-room/region?api_version=3.0.1&call_type=web
//        // &code=41117105&filters={"multi_room_type":[0,1,2],"selling_type":[0,1,2],"deposit_range":[0,999999],
//        // "price_range":[0,999999],"trade_range":[0,999999],"maintenance_cost_range":[0,999999],"room_size":[0,999999],
//        // "supply_space_range":[0,999999],"room_floor_multi":[1,2,3,4,5,6,7,-1,0],"division":false,"duplex":false,
//        // "room_type":[1,2],"use_approval_date_range":[0,999999],"parking_average_range":[0,999999],"household_num_range":[0,999999],
//        // "parking":false,"short_lease":false,"full_option":false,
//        // "elevator":false,"balcony":false,"safety":false,"pano":false,"is_contract":false,"deal_type":[0,1]}&page=1&version=1&zoom=13
//        try {
//            // 필터 문자열을 디코딩
//            String decodedFilters = URLDecoder.decode(url, "UTF-8");
//            System.out.println(decodedFilters);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void encodingTest(){
//        String baseUrl = "https://www.dabangapp.com/api/3/room/new-list/multi-room/region?";
//        String parameters = "43578725";
//
//        //180 인코딩하기 43578725
//        try {
//            // 인코딩된 URL 생성
//            String encodedParameters = URLEncoder.encode(parameters, "UTF-8");
//            System.out.println(encodedParameters);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//
//
//
//    }
//
//    @Test
//    void encodingTest2(){
//        String input = "43578725";
//        String hashedOutput = hashSHA256(input);
//        System.out.println(hashedOutput);
//        Assertions.assertThat(hashedOutput).isEqualTo("6625ff8bafbee50d1a1c33a9");
//        //4504a3075b6a6c344d45a158b5554939eb25c9c0ee80d4be6a34e964ee6cf721
//    }
//
//    public static String hashSHA256(String input) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA-512");
//            byte[] hash = digest.digest(input.getBytes());
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hash) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) hexString.append('0');
//                hexString.append(hex);
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static String hashMD5(String input) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] messageDigest = md.digest(input.getBytes());
//            BigInteger no = new BigInteger(1, messageDigest);
//            StringBuilder hashtext = new StringBuilder(no.toString(16));
//            while (hashtext.length() < 32) {
//                hashtext.insert(0, "0");
//            }
//            return hashtext.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}