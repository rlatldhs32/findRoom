//package sion.bestRoom.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import sion.bestRoom.dto.CreateBusDTO;
//import sion.bestRoom.dto.CreateSubwayDTO;
//import sion.bestRoom.model.Bus;
//import sion.bestRoom.model.Subway;
//import sion.bestRoom.repository.BusRepository;
//import sion.bestRoom.repository.SubwayRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class BusService {
//
//    private final BusRepository busRepository;
//    public void deleteAllBus() {
//        busRepository.deleteAll();
//    }
//
//
//
//
//    public List<String> saveBus(CreateBusDTO dto) {
//        List<String> busNameList = new ArrayList<>();
//
//        List<Bus> busList = new ArrayList<>();
//
//        dto.getData().forEach(busInfoDTO -> {
//            Bus bus = Bus.builder()
//                    .city(busInfoDTO.get관리도시명())
//                    .name(busInfoDTO.get정류장명())
//                    .code(busInfoDTO.get정류장번호())
//                    .x(Double.parseDouble(busInfoDTO.get경도()))
//                    .y(Double.parseDouble(busInfoDTO.get위도()))
//                    .build();
//            busList.add(bus);
//            busNameList.add(busInfoDTO.get정류장명());
//        });
//
////        busRepository.saveAll(busList);
//
//        return busNameList;
//    }
//
//    public List<String> saveSubway(CreateSubwayDTO dto) {
//        List<String> subwayNameList = new ArrayList<>();
//
//        List<Subway> subwayList = new ArrayList<>();
//
//        dto.getData().forEach(subwayInfoDTO -> {
//            Subway subway = Subway.builder()
//                    .name(subwayInfoDTO.getStatn_nm())
//                    .line(subwayInfoDTO.getRoute())
//                    .code(subwayInfoDTO.getStatn_id())
//                    .x(Double.parseDouble(subwayInfoDTO.getCrdnt_x()))
//                    .y(Double.parseDouble(subwayInfoDTO.getCrdnt_y()))
//                    .build();
//            subwayList.add(subway);
//            subwayNameList.add(subwayInfoDTO.getStatn_nm());
//        });
//
////        subwayRepository.saveAll(subwayList);
//
//        return subwayNameList;
//    }
//}
