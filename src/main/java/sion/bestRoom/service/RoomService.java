package sion.bestRoom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import sion.bestRoom.dto.DabangRoomDTO;
import sion.bestRoom.dto.RoomDTO;
import sion.bestRoom.feign.DabangFeignClient;
import sion.bestRoom.feign.dto.CityDTO;
import sion.bestRoom.feign.dto.RoomRequestParams;
import sion.bestRoom.feign.response.*;
import sion.bestRoom.model.City;
import sion.bestRoom.model.OneRoom;
import sion.bestRoom.repository.CityRepository;
import sion.bestRoom.repository.OneRoomRepository;
import sion.bestRoom.util.CalculateUtil;
import sion.bestRoom.util.Constants;
import sion.bestRoom.util.RoomConverter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Thread.sleep;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    @PersistenceContext
    private EntityManager em;

    private final DabangFeignClient dabangFeignClient;
    private final OneRoomRepository oneRoomRepository;
    private final CityRepository cityRepository;
    private final SubwayService subwayService;

    private List<OneRoom> convertDabangDtoToOneRoom(List<DabangRoomDTO> rooms,String code) {
        //convert DabangRoomDTO to OneRoomList
        List<OneRoom> oneRoomList = new ArrayList<>();
        for (DabangRoomDTO room : rooms) {
            String room_desc2 = room.getRoom_desc2();
            // , 를 기준으로 3개 나눔. 처음 : 층 , 두번째 : 면적 , 세번째 : 관리비 ex ) 2층, 19.83m², 관리비 7만
            String[] split = room_desc2.split(",");
            String floor = split[0];
            String size = split[1];
            String maintenance_fee = split[2];
            maintenance_fee = maintenance_fee.substring(4);
            maintenance_fee = maintenance_fee.trim();

            if(maintenance_fee.equals("없음"))
                maintenance_fee = "0";
            else
                maintenance_fee = maintenance_fee.substring(0, maintenance_fee.length() - 1);

            Double sizeDouble = Double.parseDouble(size.substring(0, size.length() - 2));

            //소수점 이하 버림
            if(maintenance_fee.contains("."))
                maintenance_fee = maintenance_fee.substring(0, maintenance_fee.indexOf("."));

            Long maintenance_feeLong = 0L;
            try{
//                log.info("maintenance_fee_ParseToLong : {}", maintenance_fee);
                maintenance_feeLong = Long.parseLong(maintenance_fee);
            }
            catch (NumberFormatException e)
            {
                maintenance_feeLong= 999L; //TODO: 이 경우 찾아야함.
                log.error("maintenance_fee : {}", maintenance_fee);
            }

            String price_title = room.getPrice_title();
            Long deposit = 0L;
            Long monthly_rent = 0L;
            if(price_title.contains("/"))
            {
                String[] split1 = price_title.split("/");
                //'억'이 들어가 있을 경우 그 앞에 숫자를 10000 곱해주고 그 뒤에를 더함.
                if(price_title.contains("억")) {
                    String[] split2 = price_title.split("억");
                    deposit = Long.parseLong(split2[0]) * 10000;
                    //만약 억 뒤에 없으면 무시
                    if(split2.length > 1){
                        String[] split3 = split2[1].split("/");
                        if(split3.length>1){
                            if(!split3[0].isEmpty())
                            deposit += Long.parseLong(split3[0].substring(0, split3[0].length()));
                        }
                        else
                            deposit += Long.parseLong(split2[1].substring(0, split2[1].length()));
                    }
                }
                else
                    deposit = Long.parseLong(split1[0].substring(0, split1[0].length()));
                monthly_rent = Long.parseLong(split1[1].substring(0, split1[1].length() ));
            }
            else
            {
                //'억'이 들어가 있을 경우 그 앞에 숫자를 10000 곱해주고 그 뒤에를 더함.
                if(price_title.contains("억")) {
                    String[] split1 = price_title.split("억");
                    deposit = Long.parseLong(split1[0]) * 10000;
                    //만약 억 뒤에 없으면 무시
                    if(split1.length > 1)
                        deposit += Long.parseLong(split1[1].substring(0, split1[1].length() ));
                }
                else
                    deposit = Long.parseLong(price_title.substring(0, price_title.length() ));
            }

            Double cost_divided_size = ((deposit * Constants.ConvertPercent) / 12 + monthly_rent + maintenance_feeLong) / sizeDouble;


            OneRoom oneRoom = OneRoom.builder()
                    .title(room.getTitle())
                    .dabang_id(room.getId())
                    .room_type(room.getRoom_type())
                    .room_type_str(room.getRoom_type_str())
                    .maintenance_fee(Double.valueOf(maintenance_feeLong))
                    .floor(floor)
                    .size(sizeDouble)
                    .deposit(deposit)
                    .monthly_rent(monthly_rent)
                    .img_url(room.getImg_url())
                    .total_price((deposit * Constants.ConvertPercent) / 12 + monthly_rent + maintenance_feeLong)
                    .x(room.getLocation().get(0)) //경도  x : 경도 :
                    .y(room.getLocation().get(1)) //위도  y : 위도 : latitude
                    .selling_type(room.getSelling_type())
                    .selling_type_str(room.getSelling_type_str())
                    .code(code)
                    .location(CalculateUtil.calculatePoint(room.getLocation().get(0), room.getLocation().get(1)))
                    .cost_divided_size(cost_divided_size)
                    .build();
            oneRoomList.add(oneRoom);
        }

        return oneRoomList;

    }



    private List<OneRoom> convertDabangDtoToOneRoomV5(List<DabangRoomV5> rooms,String code) {
        //convert DabangRoomDTO to OneRoomList
        List<OneRoom> oneRoomList = new ArrayList<>();
        for (DabangRoomV5 room : rooms) {
            String room_desc = room.getRoomDesc();
            // , 를 기준으로 3개 나눔. 처음 : 층 , 두번째 : 면적 , 세번째 : 관리비 ex ) 2층, 19.83m², 관리비 7만
            String[] split = room_desc.split(",");
            String floor = split[0];
            String size = split[1];
            String maintenance_fee = split[2];
            maintenance_fee = maintenance_fee.substring(4);
            maintenance_fee = maintenance_fee.trim();

            if(maintenance_fee.equals("없음"))
                maintenance_fee = "0";
            else
                maintenance_fee = maintenance_fee.substring(0, maintenance_fee.length() - 1);

            Double sizeDouble = Double.parseDouble(size.substring(0, size.length() - 2));

            //소수점 이하 버림
            if(maintenance_fee.contains("."))
                maintenance_fee = maintenance_fee.substring(0, maintenance_fee.indexOf("."));

            Long maintenance_feeLong = 0L;
            try{
//                log.info("maintenance_fee_ParseToLong : {}", maintenance_fee);
                maintenance_feeLong = Long.parseLong(maintenance_fee);
            }
            catch (NumberFormatException e)
            {
                maintenance_feeLong= 999L; //TODO: 이 경우 찾아야함.
                log.error("maintenance_fee : {}", maintenance_fee);
            }

            String price_title = room.getPriceTitle();
            Long deposit = 0L;
            Long monthly_rent = 0L;
            if(price_title.contains("/"))
            {
                String[] split1 = price_title.split("/");
                //'억'이 들어가 있을 경우 그 앞에 숫자를 10000 곱해주고 그 뒤에를 더함.
                if(price_title.contains("억")) {
                    String[] split2 = price_title.split("억");
                    deposit = Long.parseLong(split2[0]) * 10000;
                    //만약 억 뒤에 없으면 무시
                    if(split2.length > 1){
                        String[] split3 = split2[1].split("/");
                        if(split3.length>1){
                            if(!split3[0].isEmpty())
                                deposit += Long.parseLong(split3[0].substring(0, split3[0].length()));
                        }
                        else
                            deposit += Long.parseLong(split2[1].substring(0, split2[1].length()));
                    }
                }
                else
                    deposit = Long.parseLong(split1[0].substring(0, split1[0].length()));
                monthly_rent = Long.parseLong(split1[1].substring(0, split1[1].length() ));
            }
            else
            {
                //'억'이 들어가 있을 경우 그 앞에 숫자를 10000 곱해주고 그 뒤에를 더함.
                if(price_title.contains("억")) {
                    String[] split1 = price_title.split("억");
                    deposit = Long.parseLong(split1[0]) * 10000;
                    //만약 억 뒤에 없으면 무시
                    if(split1.length > 1)
                        deposit += Long.parseLong(split1[1].substring(0, split1[1].length() ));
                }
                else
                    deposit = Long.parseLong(price_title.substring(0, price_title.length() ));
            }

            Double cost_divided_size = ((deposit * Constants.ConvertPercent) / 12 + monthly_rent + maintenance_feeLong) / sizeDouble;


            //room_type 넣기.
            //0 : '원'으로 시작
            //1 : '투'로 시작
            //2 : 쓰 로 시작
            //3 : 오 로 시작
            //4 : 빌 로 시작
            //5 : 아 로 시작

            Long roomType = 0L;
//            if(room.getRoomTypeName().startsWith("투"))
//                roomType = 1L;
//            else if(room.getRoomTypeName().startsWith("쓰"))
//                roomType = 2L;
            if(room.getRoomTypeName().startsWith("오"))
                roomType = 1L;
//            else if(room.getRoomTypeName().startsWith("빌")) // << 직방이네
//                roomType = 4L;
            else if(room.getRoomTypeName().startsWith("아"))
                roomType = 2L;



            Integer sellingType = 0;
            if(room.getPriceTypeName().equals("월세"))
                sellingType = 0;
            else if(room.getPriceTypeName().equals("전세"))
                sellingType = 1;
            else if(room.getPriceTypeName().equals("매매"))
                sellingType = 2;
            else sellingType =0;

            List<String> imgUrlList = room.getImgUrlList();
            String imgUrl = "";
            if(!imgUrlList.isEmpty())
                imgUrl = imgUrlList.get(0);

            OneRoom oneRoom = OneRoom.builder()
                    .title(room.getRoomTitle())
                    .dabang_id(room.getId())
                    .room_type(roomType)
                    .room_type_str(room.getRoomTypeName())
                    .maintenance_fee(Double.valueOf(maintenance_feeLong))
                    .floor(floor)
                    .size(sizeDouble)
                    .deposit(deposit)
                    .monthly_rent(monthly_rent)
                    .img_url(imgUrl)
                    .total_price((deposit * Constants.ConvertPercent) / 12 + monthly_rent + maintenance_feeLong)
                    .x(room.getRandomLocation().getLng()) //경도  x : 경도 :
                    .y(room.getRandomLocation().getLat()) //위도  y : 위도 : latitude
                    .selling_type_str(room.getPriceTypeName())
                    .selling_type(sellingType)
                    .code(code)
                    .location(CalculateUtil.calculatePoint(room.getRandomLocation().getLng(), room.getRandomLocation().getLat()))
                    .cost_divided_size(cost_divided_size)
                    .build();
            oneRoomList.add(oneRoom);
        }

        return oneRoomList;

    }


    public List<RoomDTO> getAllRooms(Double x1, Double x2, Double y1, Double y2, Integer sellingType,Long roomType) {
        if(x1>x2){
            Double temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if(y1>y2){
            Double temp = y1;
            y1 = y2;
            y2 = temp;
        }
        List<RoomDTO> oneRoomList;
        oneRoomList = oneRoomRepository.findBetweenXAndYAndTypes(x1, x2, y1, y2,sellingType,roomType);
        return oneRoomList;
    }


    public List<RoomDTO> getBestTopRooms(Double x1, Double x2, Double y1, Double y2, Integer number, Integer sellingType,Long roomType, Double minSize) {
        List<RoomDTO> oneRoomList;
        if(x1>x2){
            Double temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if(y1>y2){
            Double temp = y1;
            y1 = y2;
            y2 = temp;
        }
        oneRoomList = oneRoomRepository.findBetweenXAndYOrderByTotalPriceDividedBySizeDescLimit(x1, x2, y1, y2,number,minSize,sellingType,roomType);
        //이제 월세만 따져보자.
        //관리세 + 월세
        return oneRoomList;
    }


    public List<City> getGeongGiAndSeoulAreaCode() {
        DabangCityResponse geongGiNorthCity = dabangFeignClient.getGeongGiNorthCity();
        DabangCityResponse geongGiSouthCity = dabangFeignClient.getGeongGiSouthCity();
        //겹치는 code는 삭제
        List<CityDTO> geongGiNorth = geongGiNorthCity.getRegions();
        List<CityDTO> geongGiSouth = geongGiSouthCity.getRegions();
        Set<CityDTO> regionSet = new HashSet<>();
        regionSet.addAll(geongGiNorth);
        regionSet.addAll(geongGiSouth);


        List<CityDTO> regions = new ArrayList<>(regionSet);

        List<City> cityList = new ArrayList<>();
        for (CityDTO region : regions) {
            City city = City.builder()
                    .code(region.getCode())
                    .name(region.getName())
                    .x(region.getLocation().get(0))
                    .y(region.getLocation().get(1))
                    .location(CalculateUtil.calculatePoint(region.getLocation().get(0), region.getLocation().get(1)))
                    .build();
            cityList.add(city);
        }
        cityRepository.saveAll(cityList);
        return cityList;
    }


    public List<String> getDabangRoomsInCity()  {

        List<City> cities = cityRepository.findAll();
        List<String> result = new ArrayList<>();
        //cities의 code에서 뒤 101 부터 300까지 처음부터 돌면서 feginClient에서 오류가 나타날경우에는 다음 city찾음.
        for (City city : cities) {
            String code = city.getCode();
            Long plusCode = 101L; //코드 뒤에 자세히 검색할 애들
            Long page = 1L;
            Long roomCnt = 0L;
            while(plusCode<300L) {
                boolean goNextCity = false;
                page = 1L;
                while(page<100L){
                    try {
                        code = city.getCode() + plusCode;
                        log.info("code : {}", code);
                        log.info("page : {}", page); //41131102 에서 에러났었음. 확인해봐야할듯.
                        DabangResponse dabangResponse = dabangFeignClient.getRoomsInCity(code, page);
                        //page가 1인데도 없을 경우에는 flag 설정해야함.
                        if(page==1 && dabangResponse.getRooms().isEmpty())
                        {
                            goNextCity = true;
                            break;
                        }

                        log.info("dabangResponse : {}", dabangResponse.getRooms().size());
                        List<DabangRoomDTO> rooms = dabangResponse.getRooms();
                        List<OneRoom> roomList = convertDabangDtoToOneRoom(rooms,code);

                        oneRoomRepository.saveAll(roomList);
                        roomCnt += rooms.size();

                        if(!dabangResponse.getHas_more() || dabangResponse.getRooms().isEmpty()) //페이지 안넘어가도 된다고 하면
                            page= 100L;
                    }
                    catch (Exception e)
                    {
                        log.error("!! error : {}", e.getMessage());
                        plusCode=300L;
                        break;
                    }
                    page++;
                }
                if(goNextCity)
                    break;
                //여기도 page도 100이면서 저번것도
                plusCode++;

            }
            result.add(city.getName() + " : " + roomCnt +" 개의 방");
        }

        return result;
    }



    public List<String> getDabangRoomsInCityV2() throws InterruptedException {
    //오피스텔 가져옴
        List<City> cities = cityRepository.findAll();
        List<String> result = new ArrayList<>();

        for (City city : cities) {
            String code = city.getCode();
            Long plusCode = 101L; //코드 뒤에 자세히 검색할 애들
            Long page = 1L;
            Long roomCnt = 0L;
            while(plusCode<300L) {
                boolean goNextCity = false;
                page = 1L;
                while(page<100L){
                    try {
                        code = city.getCode() + plusCode;
                        log.info("code : {}", code);
                        log.info("page : {}", page); //41131102 에서 에러났었음. 확인해봐야할듯.
                        //오피스텔만 가져옴.
                        DabangV5Response<DabangV5Result> dabangResponse = dabangFeignClient.getOfficeRoomInCityV5("web","5.0.0",code, page);
                        //page가 1인데도 없을 경우에는 flag 설정해야함.
                        if(page==1 && dabangResponse.getResult().getRoomList().isEmpty())
                        {
                            goNextCity = true;
                            break;
                        }

                        log.info("dabangResponse : {}", dabangResponse.getResult().getRoomList().size());
                        List<DabangRoomV5> rooms = dabangResponse.getResult().getRoomList();
                        List<OneRoom> roomList = convertDabangDtoToOneRoomV5(rooms,code);

                        try {
                            oneRoomRepository.saveAll(roomList);
                        }
                        catch (Exception e)
                        {
                            log.error("sql error : {}", e.getMessage());
                        }
                        roomCnt += rooms.size();
                        Boolean hasMore = dabangResponse.getResult().getHasMore();

                        if(!hasMore || dabangResponse.getResult().getRoomList().isEmpty()) //페이지 안넘어가도 된다고 하면
                            page= 100L;
                    }
                    //중복일시
                    catch (Exception e)
                    {
                        log.error("!! error : {}", e.getMessage());
                        plusCode=300L;
                        sleep(1000);
                        break;
                    }
                    page++;
                }
                if(goNextCity)
                    break;
                //여기도 page도 100이면서 저번것도
                plusCode++;

            }
            result.add(city.getName() + " : " + roomCnt +" 개의 방");

        }

        return result;
    }

    @Transactional(dontRollbackOn = Exception.class)
    public List<String> getAllDabangRoomsInCity() throws InterruptedException, JsonProcessingException {
        List<String> allResults = new ArrayList<>();
        for (RoomType roomType : RoomType.values()) {
            log.info("\n\nroomType !!! : {}\n\n", roomType.getDescription());
            List<String> result = getDabangRoomsInCityV3(roomType);
            allResults.addAll(result);
        }
        getApartInfo();
        return allResults;
    }



    @Transactional(dontRollbackOn = Exception.class)
    public List<String> getDabangRoomsInCityV3(RoomType roomType) throws InterruptedException {
        List<City> cities = cityRepository.findAll();
        List<String> result = new ArrayList<>();
        List<OneRoom> batchList = new ArrayList<>();
        int batchSize = 1000;

        for (City city : cities) {
            String code = city.getCode();
            Long plusCode = 101L;
            Long roomCnt = 0L;

            while (plusCode < 300L) {
                boolean goNextCity = false;
                Long page = 1L;

                while (page < 100L) {
                    try {
                        String fullCode = city.getCode() + plusCode;
                        log.info("code : {}", fullCode);
                        log.info("page : {}", page);

                        RoomRequestParams params = new RoomRequestParams("web", "5.0.0", fullCode, page);
                        DabangV5Response<DabangV5Result> dabangResponse = roomType.getApiCall().apply(dabangFeignClient, params);
//                        DabangV5Response<DabangV5Result> dabangResponse = dabangFeignClient.getOneTwoRoomInCityV5("web", "5.0.0", fullCode, page);

                        if (page == 1 && dabangResponse.getResult().getRoomList().isEmpty()) {
                            goNextCity = true;
                            break;
                        }
                        log.info("dabangResponse : {}", dabangResponse.getResult().getRoomList().size());
                        List<DabangRoomV5> rooms = dabangResponse.getResult().getRoomList();
                        List<OneRoom> roomList = convertDabangDtoToOneRoomV5(rooms, fullCode);
                        batchList.addAll(roomList);
                        if(batchList.size() >= batchSize) {
                            oneRoomRepository.saveAll(batchList);
                            batchList.clear();
                        }
                        roomCnt += rooms.size();
                        Boolean hasMore = dabangResponse.getResult().getHasMore();

                        if (!hasMore || dabangResponse.getResult().getRoomList().isEmpty()) {
                            page = 100L;
                        }
                    } catch (Exception e) {
                        log.error("!! error : {}", e.getMessage());
                        plusCode = 300L;
                        sleep(1000);
                        break;
                    }
                    page++;
                }
                if (goNextCity) break;
                plusCode++;
            }
//            result.add(city.getName() + " : " + roomCnt + " 개의 " + roomType.getDescription());
        }
        try {
            if (!batchList.isEmpty()) {
                oneRoomRepository.saveAll(batchList);
            }
        }
        catch (Exception e) {
            log.error("!! error : {}", e.getMessage());
        }
//        finally {
//            log.info("em.clear()");
//            em.clear();
//        }

        return result;
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void getApartInfo() throws JsonProcessingException {

        // 경도 37 ~ 37.7 , 위도 126 ~ 128 을 각각 20분의1씩 나눠서 넣음

        List<OneRoom> batchList = new ArrayList<>();

        Integer batchSize= 1000;
        double x1 = 37.0;
        double y1 = 126.0;
        for(int i=0;i<20;i++){
            double swLat = x1 + i * 0.035;
            double neLat = x1 + (i+1) * 0.035;
            for(int j=0;j<20;j++){
                double swLng = y1 + j * 0.05;
                double neLng = y1 + (j+1) * 0.05;
                log.info("swLat : {}, swLng : {}, neLat : {}, neLng : {}", swLat, swLng, neLat, neLng);
                String bbox = createBboxParam(swLat, swLng, neLat, neLng);
                log.info("bbox : {}", bbox);
                String filters = createFiltersParam();
                String useMap = "naver";
                int zoom = 13;
                Long page = 1L;
                Long fienErrorCnt = 0L;
                while (page < 10000L) {
                    if(fienErrorCnt>3L)
                        break;
                    try {
                        log.info("page : {}", page);
                        DabangV5Response<DabangV5Result> aparts = dabangFeignClient.getApartmentRoomInCityV6(
                                "web",
                                "5.0.0",
                                bbox,
                                filters,
                                useMap,
                                zoom,
                                page
                        );
                        log.info("dabangResponse : {}", aparts.getResult().getRoomList().size());
                        Boolean hasMore = aparts.getResult().getHasMore();
                        if (!hasMore) {
                        log.info("hasMore is false. break . page : {}", page);
                        break;
                        }
                        List<DabangRoomV5> rooms = aparts.getResult().getRoomList();
                        List<OneRoom> roomList = convertDabangDtoToOneRoomV5(rooms, null);
                        batchList.addAll(roomList);
                        if(batchList.size() >= batchSize) {
                            oneRoomRepository.saveAll(batchList);
                            batchList.clear();
                        }
                    }
                    catch (FeignException e) {
                        log.error("!! Feign error : {}", e.getMessage());
                        log.error("api info : {}", "apartment");
                        fienErrorCnt++;
                    }
                    catch (Exception e) {
                        log.error("!! error : {}", e.getMessage());
                        log.error("api info : {}", "apartment");
                    }
                    page++;
                }
            }
        }
        try{
            if(!batchList.isEmpty())
                oneRoomRepository.saveAll(batchList);
        }
        catch (Exception e)
        {
            log.error("sql error : {}", e.getMessage());
        }
        finally {
            log.info("em.clear()");
            em.clear();
        }
    }


    public List<RoomDTO> getTop10CostEffectivenessRooms() {
        List<OneRoom> allRooms = oneRoomRepository.findAll();
        List<RoomDTO> roomDTOList = new ArrayList<>();
        //convert allRooms to roomDTOList
        for (OneRoom room : allRooms) {
            RoomDTO dto = RoomConverter.toDTO(room);
            roomDTOList.add(dto);
        }
        roomDTOList.sort((o1, o2) -> {
            Double costEffectiveness1 = o1.getTotal_price() / o1.getSize();
            Double costEffectiveness2 = o2.getTotal_price() / o2.getSize();
            return costEffectiveness1.compareTo(costEffectiveness2);
        });
        return roomDTOList.subList(0, 10);
    }

    //각 룸별로, 가장 가까운 subWay와의 거리를 room Entity의 subWayDistance에  저장
    //위도와 경도를 가지고 m단위로 거리를 계산


    public void deleteAllRooms() {
        oneRoomRepository.deleteAll();
    }

    public List<OneRoom> getRoomsWithinDistance(Double targetX, Double targetY, double distance) {
        // WKT (Well-Known Text) 포맷으로 좌표를 표현
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTWriter writer = new WKTWriter();
        Coordinate coordinate = new Coordinate(targetX, targetY);
        String wktPoint = writer.write(geometryFactory.createPoint(coordinate));
        return oneRoomRepository.findRoomsWithinDistance(wktPoint, distance);
    }

    //모든 룸의 cost_divided_size의 크기가 하위 몇 %인지 계산해서 db에 저장
    public String calculateAllRoomsCostDividedSizeRank() {
        List<OneRoom> allRooms = oneRoomRepository.findAll();
        allRooms.sort(Comparator.comparing(OneRoom::getCost_divided_size));
        for (int i = 0; i < allRooms.size(); i++) {
            OneRoom room = allRooms.get(i);
            room.setCostPercent(((double)i / allRooms.size()) * 100);
        }
        oneRoomRepository.saveAll(allRooms);
        return "calculateAllRoomsCostDividedSizeRank Success";
    }

    //input : 하나의 oneRoom, distance
    //그 룸의 좌표를 가지고 주위 distance 미터 만큼의 oneRooms의 cost_divided_size의 크기가 하위 몇 %인지 계산
    public Double getCostDividedSizeRank(OneRoom oneRoom, Double distance) {
        List<OneRoom> roomsWithinDistance = getRoomsWithinDistance(oneRoom.getX(), oneRoom.getY(), distance);
        log.info("roomsWithinDistance : {}", roomsWithinDistance.size());

        roomsWithinDistance.sort(Comparator.comparing(OneRoom::getCost_divided_size));
        System.out.println("roomsWithinDistance = " + roomsWithinDistance);
        int rank = 0;
        for (OneRoom room : roomsWithinDistance) {
            if (room.getId().equals(oneRoom.getId())) {
                break;
            }
            rank++;
        }
        return ((double)rank / roomsWithinDistance.size() )*100;
    }


    //모든 룸별로 가장 가까운 지하철역까지의 거리를 계산해서 db에 저장
    public String calculateAllRoomsSubwayDistance() {
        List<OneRoom> allRooms = oneRoomRepository.findAll();
        for (OneRoom room : allRooms) {
            //가까운 subWay 찾고, 그 location까지의 거리 계산
            Double nearbySubwayDistance = subwayService.getNearbySubwayDistance(room.getX(), room.getY());
            room.setSubway_distance(nearbySubwayDistance);
        }
//        oneRoomRepository.saveAll(allRooms);
        return "calculateAllRoomsCostDividedSizeRank Success";
    }


    String createBboxParam(double swLat, double swLng, double neLat, double neLng) throws JsonProcessingException {
        Map<String, Map<String, Double>> bbox = new HashMap<>();
        bbox.put("sw", Map.of("lat", swLat, "lng", swLng));
        bbox.put("ne", Map.of("lat", neLat, "lng", neLng));
        return URLEncoder.encode(new ObjectMapper().writeValueAsString(bbox), StandardCharsets.UTF_8);
    }

    String createFiltersParam() throws JsonProcessingException {
        Map<String, Object> filters = new HashMap<>();
        filters.put("sellingTypeList", Arrays.asList("MONTHLY_RENT", "LEASE", "SELL"));
        filters.put("tradeRange", Map.of("min", 0, "max", 999999));
        filters.put("depositRange", Map.of("min", 0, "max", 999999));
        filters.put("priceRange", Map.of("min", 0, "max", 999999));
        filters.put("isIncludeMaintenance", false);
        filters.put("pyeongRange", Map.of("min", 0, "max", 999999));
        filters.put("useApprovalDateRange", Map.of("min", 0, "max", 999999));
        filters.put("dealTypeList", Arrays.asList("AGENT", "DIRECT"));
        filters.put("householdNumRange", Map.of("min", 0, "max", 999999));
        filters.put("parkingNumRange", Map.of("min", 0, "max", 999999));
        filters.put("isShortLease", false);
        filters.put("hasTakeTenant", false);
        return URLEncoder.encode(new ObjectMapper().writeValueAsString(filters), StandardCharsets.UTF_8);
    }
}
