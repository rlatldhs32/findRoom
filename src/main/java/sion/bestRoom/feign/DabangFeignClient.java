//package sion.bestRoom.feign;
//
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import sion.bestRoom.feign.response.DabangCityResponse;
//import sion.bestRoom.feign.response.DabangResponse;
//import sion.bestRoom.feign.response.NaverResponse;
//
//@FeignClient(name = "dabangEstate", url = "https://www.dabangapp.com/api/3")
//public interface DabangFeignClient {
//
//    @Cacheable("DABANG_ESTATE")
////    @GetMapping("/room/new-list/multi-room/region")
//    @GetMapping(value = "/room/new-list/multi-room/region?api_version=3.0.1&call_type=web&code=127.1290510001917_37.43286899999194_0.0028970_0.0024129&filters=%7B%22multi_room_type%22%3A%5B0%2C1%2C2%5D%2C%22selling_type%22%3A%5B0%2C1%2C2%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B1%2C2%5D%2C%22use_approval_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22is_contract%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D&page=1&version=1&zoom=16"
//    ,produces = "application/json")
//    DabangResponse getEstateTest();
////    Object getEstateTest();
//
//    @GetMapping(value = "/room/new-list/multi-room/region?api_version=3.0.1&call_type=web&code=41133101&filters=%7B%22multi_room_type%22%3A%5B0%2C1%2C2%5D%2C%22selling_type%22%3A%5B0%2C1%2C2%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B1%2C2%5D%2C%22use_approval_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22is_contract%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D\n" +
//            "&version=1&zoom=14" , produces = "application/json")
//    DabangResponse getSungNamEstate(@RequestParam("page") Long page);
//
////    @GetMapping(value = "/room/new-list/multi-room/region?api_version=3.0.1&call_type=web&code=41133101&filters=%7B%22multi_room_type%22%3A%5B0%2C1%2C2%5D%2C%22selling_type%22%3A%5B0%2C1%2C2%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B1%2C2%5D%2C%22use_approval_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22is_contract%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D\n" +
////            "&version=1&zoom=14" , produces = "application/json")
//
//
////    https://www.dabangapp.com/api/3/room/new-list/multi-room/region?api_version=3.0.1&call_type=web&code=41117105&filters=%7B%22multi_room_type%22%3A%5B0%2C1%2C2%5D%2C%22selling_type%22%3A%5B0%2C1%2C2%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B1%2C2%5D%2C%22use_approval_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22is_contract%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D&page=1&version=1&zoom=13
////    DabangResponse getSungNamEstate(@RequestParam("page") Long page);
//
//
//    @GetMapping(value ="/room/new-list/multi-room/region?api_version=3.0.1&call_type=web&code=41117105&filters=%7B%22multi_room_type%22%3A%5B0%2C1%2C2%5D%2C%22selling_type%22%3A%5B0%2C1%2C2%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B1%2C2%5D%2C%22use_approval_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22is_contract%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D&version=1&zoom=13"
//            ,produces = "application/json")
//    DabangResponse getEongTongEstate(@RequestParam("page") Long page);
//
//    @GetMapping(value="/room/new-list/multi-room/region?api_version=3.0.1&call_type=web&code=41117101&filters=%7B%22multi_room_type%22%3A%5B0%2C1%2C2%5D%2C%22selling_type%22%3A%5B0%2C1%2C2%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B1%2C2%5D%2C%22use_approval_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22is_contract%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D&version=1&zoom=13"
//        ,produces = "application/json")
//    DabangResponse getMatanEstate(@RequestParam("page") Long page);
//
//
//    //서울시 클릭 후에 눌렀을때 city정보 가져오기
//
//    @GetMapping(value="/marker/new-multi-room?api_version=3.0.1&call_type=web&filters=%7B%22multi_room_type%22%3A%5B0%2C1%2C2%5D%2C%22selling_type%22%3A%5B0%2C1%2C2%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B1%2C2%5D%2C%22use_approval_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22is_contract%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D&location=%5B%5B126.4958582%2C37.3863848%5D%2C%5B127.4523554%2C37.7428874%5D%5D&version=1&zoom=11",
//            produces = "application/json")
//    DabangCityResponse getSeoulCity();
//
//
//    @GetMapping(value="/room/new-list/multi-room/region?api_version=3.0.1&call_type=web&filters=%7B%22multi_room_type%22%3A%5B0%2C1%2C2%5D%2C%22selling_type%22%3A%5B0%2C1%2C2%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B1%2C2%5D%2C%22use_approval_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22is_contract%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D&version=1&zoom=13"
//            ,produces = "application/json")
//    // &code=41117101
//    DabangResponse getRoomsInCity(@RequestParam("code") String code, @RequestParam("page")Long page);
//}
