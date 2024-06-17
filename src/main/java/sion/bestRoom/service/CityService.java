package sion.bestRoom.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sion.bestRoom.repository.CityRepository;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public void deleteAllCities() {
        cityRepository.deleteAll();
    }
}
