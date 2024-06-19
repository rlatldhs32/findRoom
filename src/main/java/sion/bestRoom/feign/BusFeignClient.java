package sion.bestRoom.feign;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sion.bestRoom.dto.CreateBusDTO;
import sion.bestRoom.feign.response.DabangCityResponse;
import sion.bestRoom.feign.response.DabangResponse;

@FeignClient(name = "busApi", url = "https://api.odcloud.kr/api")
public interface BusFeignClient {
    //공공데이터 api에 쓰임.

    @GetMapping(value="/15067528/v1/uddi:eb02ec03-6edd-4cb0-88b8-eda22ca55e80?serviceKey=AIEfEhriYKJeSHRKrXFbnGBNiNMTebhMI%2F%2BUxtMBxA43IwPXaJBMlW7lH1Xolrpvt84uATZiVoxmKI0tRQV%2F5w%3D%3D"
            ,produces = "application/json")
    CreateBusDTO getBusInfo(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage); // 2000개씩 105개정도 하면 됨. 202310년꺼 기준

}
