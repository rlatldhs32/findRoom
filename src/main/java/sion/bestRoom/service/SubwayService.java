package sion.bestRoom.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sion.bestRoom.dto.CreateSubwayDTO;
import sion.bestRoom.model.OneRoom;
import sion.bestRoom.model.Subway;
import sion.bestRoom.repository.SubwayRepository;
import sion.bestRoom.util.CalculateUtil;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class SubwayService {

    private final SubwayRepository subwayRepository;
    public void deleteAllSubway() {
        subwayRepository.deleteAll();
    }
    public List<String> saveSubway(CreateSubwayDTO dto) {
        List<String> subwayNameList = new ArrayList<>();

        List<Subway> subwayList = new ArrayList<>();

        dto.getData().forEach(subwayInfoDTO -> {
            Subway subway = Subway.builder()
                    .name(subwayInfoDTO.getStatn_nm())
                    .line(subwayInfoDTO.getRoute())
                    .code(subwayInfoDTO.getStatn_id())
                    .x(Double.parseDouble(subwayInfoDTO.getCrdnt_x()))
                    .y(Double.parseDouble(subwayInfoDTO.getCrdnt_y()))
                    .location(CalculateUtil.calculatePoint(Double.parseDouble(subwayInfoDTO.getCrdnt_x()), Double.parseDouble(subwayInfoDTO.getCrdnt_y())))
                    .build();
            subwayList.add(subway);
            subwayNameList.add(subwayInfoDTO.getStatn_nm());
        });

        subwayRepository.saveAll(subwayList);

        return subwayNameList;
    }

    public List<Subway> getSubwayList() {
        return subwayRepository.findAll();
    }

    public Double getNearbySubwayDistance(Double x, Double y) {
        return subwayRepository.findClosestSubwayWithDistance(x, y).getDistance();
    }
}
