package sion.bestRoom.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sion.bestRoom.dto.CreateBusDTO;
import sion.bestRoom.feign.BusFeignClient;
import sion.bestRoom.model.Bus;
import sion.bestRoom.repository.BusRepository;
import sion.bestRoom.util.CalculateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BusService {

    private final BusRepository busRepository;
    private final BusFeignClient busFeignClient;
    public void deleteAllBus() {
        busRepository.deleteAll();
    }

    public String saveBusByFeignClient(){
        //1000씩 105까지 호출
        for(int i=1;i<=105;i++){
            CreateBusDTO busInfo = busFeignClient.getBusInfo(i,2000);
            saveBus(busInfo);
        }
        return "BUS_SAVED";
    }

    public List<String> saveBus(CreateBusDTO dto) {
        // 우선 경기/인천/서울로 시작하는 애들만 저장
        List<String> busNameList = new ArrayList<>();

        List<Bus> busList = dto.getData().stream()
                .filter(busInfoDTO -> busInfoDTO.get관리도시명().startsWith("경기") ||
                        busInfoDTO.get관리도시명().startsWith("인천") ||
                        busInfoDTO.get관리도시명().startsWith("서울"))
                .map(busInfoDTO -> {
                    Bus bus = Bus.builder()
                            .city(busInfoDTO.get관리도시명())
                            .name(busInfoDTO.get정류장명())
                            .code(busInfoDTO.get정류장번호())
                            .x(Double.parseDouble(busInfoDTO.get경도()))
                            .y(Double.parseDouble(busInfoDTO.get위도()))
                            .location(CalculateUtil.calculatePoint(Double.parseDouble(busInfoDTO.get경도()), Double.parseDouble(busInfoDTO.get위도())))
                            .build();
                    busNameList.add(busInfoDTO.get정류장명());
                    return bus;
                })
                .collect(Collectors.toList());

        busRepository.saveAll(busList);

        return busNameList;
    }
}
