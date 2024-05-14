package sion.bestRoom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sion.bestRoom.dto.DabangRoomDTO;
import sion.bestRoom.feign.DabangFeignClient;
import sion.bestRoom.feign.response.DabangResponse;
import sion.bestRoom.model.OneRoom;
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
        while(cnt<100L) {
            DabangResponse sungnamEstate = dabangFeignClient.getSungNamEstate(cnt);

            List<DabangRoomDTO> rooms = sungnamEstate.getRooms();
            log.info("rooms : {}", rooms.size());
            List<OneRoom> roomList = convertToOneRoom(rooms);

            oneRoomRepository.saveAll(roomList);

            log.info("estateTest : {}", sungnamEstate);
            cnt++;
            if(!sungnamEstate.getHas_more())
                break;
        }
        return dabangFeignClient.getEstateTest().getRooms();
    }

    private List<OneRoom> convertToOneRoom(List<DabangRoomDTO> rooms) throws InterruptedException {

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
            log.info("maintenance_fee : {}", maintenance_fee);
            if(maintenance_fee.contains("."))
                maintenance_fee = maintenance_fee.substring(0, maintenance_fee.indexOf("."));

            Long maintenance_feeLong = 0L;
            try{
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
            log.info("price_title : {}", price_title);
            if(price_title.contains("/"))
            {
                log.info("price_title !! : {}", price_title);
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
                    .maintenance_fee(maintenance_feeLong)
                    .floor(floor)
                    .size(sizeDouble)
                    .deposit(deposit)
                    .monthly_rent(monthly_rent)
                    .img_url(room.getImg_url())
                    .total_price((deposit * Constants.ConvertPercent) / 12 + monthly_rent)
                    .build();
            oneRoomList.add(oneRoom);
        }

        return oneRoomList;

    }
}
