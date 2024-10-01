package sion.bestRoom.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sion.bestRoom.dto.RoomDTO;
import sion.bestRoom.dto.ShowDabang;
import sion.bestRoom.service.RoomService;

import java.util.ArrayList;
import java.util.List;

//@RestController
@Controller
@RequiredArgsConstructor
public class RoomFrontController {

    private final RoomService roomService;

    //test

    @GetMapping("/good/effective")
    public String  getEffectiveRooms(Model model) { //sungName 우선
        List<RoomDTO> dabangRooms = roomService.getTop10CostEffectivenessRooms();
        List<ShowDabang> showDabangs = convertToShowDabang(dabangRooms);
        model.addAttribute("dabangRooms", showDabangs);
        return "dabangTest";
    }

    @GetMapping("/good/effective/exceptBack")
    public String  getEffectiveRoomsexceptBack(Model model) { //sungName 우선
        List<RoomDTO> bestTopRooms = roomService.getBestTopRooms(126.93, 127.2, 37.412603, 37.5171, 20, null,null,0D);
        List<ShowDabang> showDabangs = convertToShowDabang(bestTopRooms);
        model.addAttribute("dabangRooms", showDabangs);
        return "dabangTest";
    }


    private List<ShowDabang> convertToShowDabang(List<RoomDTO> dabangRooms) {
        List<ShowDabang> showDabangs = new ArrayList<>();
        for (RoomDTO dabangRoom : dabangRooms) {
            String redirectUrl = "";
            String imageUrl = dabangRoom.getImg_url();
            if(dabangRoom.getZigbang_id()!=null)
                imageUrl+="?w=300&h=400";
            if(dabangRoom.getZigbang_id() != null)
                redirectUrl = "https://www.zigbang.com/home/villa/items/" + dabangRoom.getZigbang_id();
            else if(dabangRoom.getDabang_id() != null)
                redirectUrl ="https://www.dabangapp.com/room/" + dabangRoom.getDabang_id();
            ShowDabang showDabang = new ShowDabang();
            showDabang.setDeposit(dabangRoom.getDeposit());
            showDabang.setMonthlyRent(dabangRoom.getMonthly_rent());
            showDabang.setSize(dabangRoom.getSize());
            showDabang.setImageUrl(imageUrl);
            showDabang.setRedirectUrl(redirectUrl);
            showDabang.setX(dabangRoom.getX());
            showDabang.setY(dabangRoom.getY());
            showDabangs.add(showDabang);
            showDabang.setId(dabangRoom.getId());
            showDabang.setDabangId(dabangRoom.getDabang_id());
            showDabang.setZigbangId(dabangRoom.getZigbang_id());
        }
        return showDabangs;
    }
}
