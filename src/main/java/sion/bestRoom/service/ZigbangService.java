package sion.bestRoom.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sion.bestRoom.feign.ZigbangFeignClient;
import sion.bestRoom.feign.dto.zigbang.ReqZigbangRoomInfoDTO;
import sion.bestRoom.feign.dto.zigbang.ResZigbangListDTO;
import sion.bestRoom.feign.dto.zigbang.ZigbangItemDTO;
import sion.bestRoom.feign.dto.zigbang.ZigbangItemDetailDTO;
import sion.bestRoom.feign.response.ZigbangResponse;
import sion.bestRoom.model.OneRoom;
import sion.bestRoom.repository.OneRoomRepository;
import sion.bestRoom.util.CalculateUtil;
import sion.bestRoom.util.Constants;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class ZigbangService {

    private final ZigbangFeignClient zigbangFeignClient;

    private final OneRoomRepository oneRoomRepository;

    public String getZigbangRooms() {
        ZigbangResponse res = zigbangFeignClient.getVilla("wyd");
        List<ZigbangItemDTO> items = res.getItems();

        List<Long> itemIds = new ArrayList<>();
        for (ZigbangItemDTO item : items)
            itemIds.add(item.getItemId());

        //itemId 1000개씩 짤라서 saveZigbangVilla 호출
        for (int i = 0; i < itemIds.size(); i += 1000) {
            List<Long> subList = itemIds.subList(i, Math.min(i + 1000, itemIds.size()));
            ReqZigbangRoomInfoDTO reqZigbangRoomInfoDTO = new ReqZigbangRoomInfoDTO();
            reqZigbangRoomInfoDTO.setItem_ids(subList);
            saveZigbangVilla(reqZigbangRoomInfoDTO);
        }

        return "zigBangRooms saved!";
    }

    private void saveZigbangVilla(ReqZigbangRoomInfoDTO reqZigbangRoomInfoDTO) {
        ResZigbangListDTO roomInfo = zigbangFeignClient.getRoomInfo(reqZigbangRoomInfoDTO);

        List<OneRoom> oneRooms = new ArrayList<>();

        List<ZigbangItemDetailDTO> zigbangVillaList = roomInfo.getItems();

        //convert zigbangVillaList to OneRoomList
        for (ZigbangItemDetailDTO zigbangItemDetailDTO : zigbangVillaList) {

            Integer selling_type = 0;
            if (zigbangItemDetailDTO.getSales_type().equals("전세")) {
                selling_type = 1;
            } else if (zigbangItemDetailDTO.getSales_type().equals("매매")) {
                selling_type = 2;
            }

            Double manage_cost = 0.0;
            if (zigbangItemDetailDTO.getManage_cost() != null && !zigbangItemDetailDTO.getManage_cost().isEmpty()) {
                manage_cost = Double.parseDouble(zigbangItemDetailDTO.getManage_cost());
            }

            Double cost_divided_size = ((zigbangItemDetailDTO.getDeposit() * Constants.ConvertPercent) / 12 + zigbangItemDetailDTO.getRent() + manage_cost)/zigbangItemDetailDTO.getSize_m2();

            OneRoom oneRoom = OneRoom.builder()
                    .title(zigbangItemDetailDTO.getTitle())
                    .zigbang_id(zigbangItemDetailDTO.getItem_id())
                    .room_type_str(zigbangItemDetailDTO.getService_type())
                    .maintenance_fee(manage_cost)
                    .floor(zigbangItemDetailDTO.getFloor())
                    .size(zigbangItemDetailDTO.getSize_m2())
                    .deposit(zigbangItemDetailDTO.getDeposit())
                    .monthly_rent(zigbangItemDetailDTO.getRent())
                    .img_url(zigbangItemDetailDTO.getImages_thumbnail())
                    .total_price((zigbangItemDetailDTO.getDeposit() * Constants.ConvertPercent) / 12 + zigbangItemDetailDTO.getRent() + manage_cost)
                    .x(zigbangItemDetailDTO.getLocation().getLng()) //경도  x : 경도 :
                    .y(zigbangItemDetailDTO.getLocation().getLat()) //위도  y : 위도 : latitude
                    .selling_type(selling_type)
                    .selling_type_str(zigbangItemDetailDTO.getSales_type())
                    .code("zigbang")
                    .location(CalculateUtil.calculatePoint(zigbangItemDetailDTO.getLocation().getLng(), zigbangItemDetailDTO.getLocation().getLat()))
                    .cost_divided_size(cost_divided_size)
                    .build();
            oneRooms.add(oneRoom);
        }

        //oneRoom 저장
        oneRoomRepository.saveAll(oneRooms);
    }

    public void deleteZigbangRooms() {
        oneRoomRepository.deleteByZigbangIdIsNotNull();
    }
}
