package sion.bestRoom.feign;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sion.bestRoom.feign.response.NaverResponse;

@FeignClient(name = "naverEstate", url = "https://new.land.naver.com/api")

public interface NaverFeignClient {

        @Cacheable("NAVER_ESTATE")
        @GetMapping("/articles/")
        NaverResponse getEstate();
}
