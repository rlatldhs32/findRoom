package sion.bestRoom.controller;//package sion.bestRoom.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestCDController {

    @GetMapping("/test")
    public String getDabangRooms() throws InterruptedException {
        System.out.println("test");
        return "test";
    }
}
