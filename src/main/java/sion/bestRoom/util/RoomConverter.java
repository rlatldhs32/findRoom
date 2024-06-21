package sion.bestRoom.util;

import sion.bestRoom.dto.RoomDTO;
import sion.bestRoom.model.OneRoom;

public class RoomConverter {

    public static RoomDTO toDTO(OneRoom oneRoom){
        return RoomDTO.builder()
                .id(oneRoom.getId())
                .dabang_id(oneRoom.getDabang_id())
                .zigbang_id(oneRoom.getZigbang_id())
                .title(oneRoom.getTitle())
                .floor(oneRoom.getFloor())
                .maintenance_fee(oneRoom.getMaintenance_fee())
                .deposit(oneRoom.getDeposit())
                .monthly_rent(oneRoom.getMonthly_rent())
                .room_type(oneRoom.getRoom_type())
                .room_type_str(oneRoom.getRoom_type_str())
                .size(oneRoom.getSize())
                .img_url(oneRoom.getImg_url())
                .total_price(oneRoom.getTotal_price())
                .x(oneRoom.getX())
                .y(oneRoom.getY())
                .code(oneRoom.getCode())
                .selling_type(oneRoom.getSelling_type())
                .selling_type_str(oneRoom.getSelling_type_str())
                .cost_divided_size(oneRoom.getCost_divided_size())
                .cost_divided_size_percent(oneRoom.getCost_divided_size_percent())
                .subway_distance(oneRoom.getSubway_distance())
                .build();
    }
}
