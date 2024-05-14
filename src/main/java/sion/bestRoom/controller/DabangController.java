package sion.bestRoom.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sion.bestRoom.dto.DabangRoomDTO;
import sion.bestRoom.service.DabangService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DabangController {

    private final DabangService dabangService;

    @GetMapping("/dabang")
    public List<DabangRoomDTO> getDabangRooms() {
        List<DabangRoomDTO> dabangRooms = dabangService.getDabangRooms();
        return dabangRooms;
    }
}
