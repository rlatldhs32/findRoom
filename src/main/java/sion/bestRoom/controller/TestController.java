package sion.bestRoom.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sion.bestRoom.dto.CreateSubwayDTO;
import sion.bestRoom.model.City;
import sion.bestRoom.service.*;
import sion.bestRoom.util.Constants;

import java.util.List;


@Transactional
@RestController
@RequiredArgsConstructor
@Slf4j

public class TestController {

    //무중단배포 테스트용123


}
