package sion.bestRoom.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import sion.bestRoom.feign.dto.zigbang.ReqZigbangRoomInfoDTO;
import sion.bestRoom.feign.dto.zigbang.ResZigbangListDTO;
import sion.bestRoom.feign.response.ZigbangResponse;

@FeignClient(name = "zigBangEstate", url = "https://api.zigbang.com/v2/items")
public interface ZigbangFeignClient {


    @GetMapping(value = "/villa?&depositMin=0&rentMin=0&salesTypes%5B0%5D=%EC%A0%84%EC%84%B8&salesTypes%5B1%5D=%EC%9B%94%EC%84%B8&salesTypes%5B2%5D=%EB%A7%A4%EB%A7%A4&salesPriceMin=0&domain=zigbang&checkAnyItemWithoutFilter=true"
            , produces = "application/json")
    ZigbangResponse getVilla(@RequestParam("geohash") String geohash);
//                             @RequestParam("depositMin") Long depositMin,
//                             @RequestParam("rentMin") Long rentMin,
//                             @RequestParam("salesPriceMin") Long salesPriceMin,
//                             @RequestParam("checkAnyItemWithoutFilter") Boolean filter);

    @PostMapping(value = "/list" , produces = "application/json")
    ResZigbangListDTO getRoomInfo(@RequestBody ReqZigbangRoomInfoDTO getZigbangRoomInfoDTO);

}