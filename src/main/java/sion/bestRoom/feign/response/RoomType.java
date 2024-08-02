package sion.bestRoom.feign.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sion.bestRoom.feign.DabangFeignClient;
import sion.bestRoom.feign.dto.RoomRequestParams;

import java.util.function.BiFunction;

public enum RoomType {
    ONE_TWO("원룸/투룸", (client, params) -> client.getOneTwoRoomInCityV5(params.getPlatform(), params.getVersion(), params.getCode(), params.getPage())),

    VILLA("빌라", (client, params) -> client.getVillaRoomInCityV5(params.getPlatform(), params.getVersion(), params.getCode(), params.getPage())),

    OFFICE("오피스텔", (client, params) -> client.getOfficeRoomInCityV5(params.getPlatform(), params.getVersion(), params.getCode(), params.getPage()));

//    APARTMENT("아파트", (client, params) -> client.getApartmentRoomInCityV5(params.getPlatform(), params.getVersion(), params.getPage()));


    private final String description;
    private final BiFunction<DabangFeignClient, RoomRequestParams, DabangV5Response<DabangV5Result>> apiCall;

    RoomType(String description, BiFunction<DabangFeignClient, RoomRequestParams, DabangV5Response<DabangV5Result>> apiCall) {
        this.description = description;
        this.apiCall = apiCall;
    }

    public String getDescription() {
        return description;
    }

    public BiFunction<DabangFeignClient, RoomRequestParams, DabangV5Response<DabangV5Result>> getApiCall() {
        return apiCall;
    }

}