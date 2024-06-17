package sion.bestRoom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sion.bestRoom.dto.DabangRoomDTO;
import sion.bestRoom.feign.DabangFeignClient;
import sion.bestRoom.feign.ZigbangFeignClient;
import sion.bestRoom.feign.dto.CityDTO;
import sion.bestRoom.feign.response.DabangCityResponse;
import sion.bestRoom.feign.response.DabangResponse;
import sion.bestRoom.model.City;
import sion.bestRoom.model.OneRoom;
import sion.bestRoom.repository.CityRepository;
import sion.bestRoom.repository.OneRoomRepository;
import sion.bestRoom.util.Constants;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

@Service
@Slf4j
@RequiredArgsConstructor
public class DabangService {

    private final DabangFeignClient dabangFeignClient;
    private final OneRoomRepository oneRoomRepository;
    private final CityRepository cityRepository;

    public List<OneRoom> getTop10CostEffectivenessRooms() {
        //모든 DabangRoom에서, total
        //total_price / size 가 가장 작은 탑 10
        List<OneRoom> allRooms = oneRoomRepository.findAll();
        allRooms.sort((o1, o2) -> {
            Double costEffectiveness1 = o1.getTotal_price() / o1.getSize();
            Double costEffectiveness2 = o2.getTotal_price() / o2.getSize();
            return costEffectiveness1.compareTo(costEffectiveness2);
        });
        return allRooms.subList(0, 10);
    }


    public List<OneRoom> getTop10CostEffectivenessRoomsExceptSemiBaseMent() {
        //모든 DabangRoom에서, total
        //total_price / size 가 가장 작은 탑 10
        List<OneRoom> allRooms = oneRoomRepository.findAll();
        //allRooms의 floor가 반지층 이면 삭제
        List<OneRoom> allRoomsExceptUnder = new ArrayList<>();
        for (OneRoom allRoom : allRooms) {
            if(!allRoom.getFloor().startsWith("반"))
                allRoomsExceptUnder.add(allRoom);
        }

        allRoomsExceptUnder.sort((o1, o2) -> {
            Double costEffectiveness1 = o1.getTotal_price() / o1.getSize();
            Double costEffectiveness2 = o2.getTotal_price() / o2.getSize();
            return costEffectiveness1.compareTo(costEffectiveness2);
        });
        return allRoomsExceptUnder.subList(0, 10);
    }


    public List<DabangRoomDTO> getDabangRooms() throws InterruptedException {

        Long cnt = 1L ;
        //지역을 파람으로 받아야함. TODO.
        while(cnt<100L) {
            DabangResponse sungnamEstate = dabangFeignClient.getSungNamEstate(cnt); //TODO: 파람으로 지역도 바꿔야함.
            List<DabangRoomDTO> rooms = sungnamEstate.getRooms();
            log.info("rooms : {}", rooms.size());
            List<OneRoom> roomList = convertToOneRoom(rooms,"0");

            oneRoomRepository.saveAll(roomList);

            cnt++;
            if(!sungnamEstate.getHas_more())
                break;
        }

        cnt = 1L;
        while(cnt<100L) {
            DabangResponse eonTongEstate = dabangFeignClient.getEongTongEstate(cnt);
            List<DabangRoomDTO> rooms = eonTongEstate.getRooms();
            log.info("rooms : {}", rooms.size());
            List<OneRoom> roomList = convertToOneRoom(rooms,"0");

            oneRoomRepository.saveAll(roomList);

            cnt++;
            if(!eonTongEstate.getHas_more())
                break;
        }

        cnt = 1L;

        while(cnt<100L) {
            DabangResponse meTanEstate = dabangFeignClient.getMatanEstate(cnt); //TODO: 파람으로 지역도 바꿔야함.
            List<DabangRoomDTO> rooms = meTanEstate.getRooms();
            log.info("rooms : {}", rooms.size());
            List<OneRoom> roomList = convertToOneRoom(rooms,"0");

            oneRoomRepository.saveAll(roomList);

            cnt++;
            if(!meTanEstate.getHas_more())
                break;
        }
        return null;
    }

    private List<OneRoom> convertToOneRoom(List<DabangRoomDTO> rooms,String code) throws InterruptedException {

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

//            Long floorLong = Long.parseLong(floor.substring(0, floor.length() - 1));
            Double sizeDouble = Double.parseDouble(size.substring(0, size.length() - 2));

            //소수점 이하 버림
            if(maintenance_fee.contains("."))
                maintenance_fee = maintenance_fee.substring(0, maintenance_fee.indexOf("."));

            Long maintenance_feeLong = 0L;
            try{
                log.info("maintenance_fee_ParseToLong : {}", maintenance_fee);
                maintenance_feeLong = Long.parseLong(maintenance_fee);
            }
            catch (NumberFormatException e)
            {
                maintenance_feeLong= 99L;
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

            if(deposit==800)
            {
                log.info("dabang_id : {}", room.getId());
//                sleep(1000000000);
            }

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
                    .total_price((deposit * Constants.ConvertPercent) / 12 + monthly_rent)
                    .x(room.getLocation().get(0)) //경도  x : 경도 :
                    .y(room.getLocation().get(1)) //위도  y : 위도 : latitude
                    .selling_type(room.getSelling_type())
                    .selling_type_str(room.getSelling_type_str())
                    .code(code)
                    .build();
            oneRoomList.add(oneRoom);
        }

        return oneRoomList;

    }

    public List<OneRoom> getAllRooms(Double x1, Double x2, Double y1, Double y2) {

        //x1, x2, y1, y2 사이에 있는 방들을 가져옴.
        List<OneRoom> oneRoomList = oneRoomRepository.findBetweenXAndY(x1, x2, y1, y2);

        return oneRoomList;
    }

    public List<OneRoom> getBestTop10Rooms(Double x1, Double x2, Double y1, Double y2) {

        //x1, x2, y1, y2 사이에 있는 방들을 가져옴.
        List<OneRoom> oneRoomList = oneRoomRepository.findBetweenXAndYOrderByTotalPriceDividedBySizeDesc(x1, x2, y1, y2);

        return oneRoomList;
    }

    public List<City> getSeoulAreaCode() {
        DabangCityResponse seoulCity = dabangFeignClient.getSeoulCity();
        //저장함.
        List<CityDTO> regions = seoulCity.getRegions();
        //City Model에 저장함.

        List<City> cityList = new ArrayList<>();

        for (CityDTO region : regions) {
            City city = City.builder()
                    .code(region.getCode())
                    .name(region.getName())
                    .x(region.getLocation().get(0))
                    .y(region.getLocation().get(1))
                    .build();
            cityList.add(city);
        }
        cityRepository.saveAll(cityList);
        return cityList;
    }


    public List<String> getAllRoomsInCity() throws InterruptedException {

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
                        List<OneRoom> roomList = convertToOneRoom(rooms,code);

                        oneRoomRepository.saveAll(roomList);
                        roomCnt += rooms.size();

                        if(!dabangResponse.getHas_more() || dabangResponse.getRooms().isEmpty()) //페이지 안넘어가도 된다고 하면
                            page= 100L;
                    }
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

    public void deleteAllAreas() {
        cityRepository.deleteAll();
    }

    public void deleteAllRooms() {
        oneRoomRepository.deleteAll();
    }
}
