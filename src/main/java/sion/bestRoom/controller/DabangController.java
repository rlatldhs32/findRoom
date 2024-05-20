package sion.bestRoom.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sion.bestRoom.dto.DabangRoomDTO;
import sion.bestRoom.dto.ShowDabang;
import sion.bestRoom.model.OneRoom;
import sion.bestRoom.service.DabangService;

import java.util.ArrayList;
import java.util.List;

//@RestController
@Controller
@RequiredArgsConstructor
public class DabangController {

    private final DabangService dabangService;

    @GetMapping("/dabang")
    public String getDabangRooms() throws InterruptedException {
        List<DabangRoomDTO> dabangRooms = dabangService.getDabangRooms(); //걸어놓은 모든 곳을 다방에서 가져옴.
        return "index";
//        return dabangRooms;
    }

    @GetMapping("/dabang/effective")
    public String  getEffectiveRooms(Model model) { //sungName 우선
        List<OneRoom> dabangRooms = dabangService.getTop10CostEffectivenessRooms();
        List<ShowDabang> showDabangs = convertToShowDabang(dabangRooms);
        model.addAttribute("dabangRooms", showDabangs);
        return "dabangTest";
    }

    @GetMapping("/dabang/effective/exceptBack")
    public String  getEffectiveRoomsexceptBack(Model model) { //sungName 우선
        List<OneRoom> dabangRooms = dabangService.getTop10CostEffectivenessRoomsExceptSemiBaseMent();
        List<ShowDabang> showDabangs = convertToShowDabang(dabangRooms);
        model.addAttribute("dabangRooms", showDabangs);
        return "dabangTest";
    }




    private List<ShowDabang> convertToShowDabang(List<OneRoom> dabangRooms) {
        List<ShowDabang> showDabangs = new ArrayList<>();
        for (OneRoom dabangRoom : dabangRooms) {
            String redirectUrl = "https://www.dabangapp.com/room/" + dabangRoom.getDabang_id();
            ShowDabang showDabang = new ShowDabang();
            showDabang.setDeposit(dabangRoom.getDeposit());
            showDabang.setMonthlyRent(dabangRoom.getMonthly_rent());
            showDabang.setSize(dabangRoom.getSize());
            showDabang.setImageUrl(dabangRoom.getImg_url());
            showDabang.setRedirectUrl(redirectUrl);
            showDabang.setX(dabangRoom.getX());
            showDabang.setY(dabangRoom.getY());
            showDabangs.add(showDabang);
        }
        return showDabangs;
    }
}
