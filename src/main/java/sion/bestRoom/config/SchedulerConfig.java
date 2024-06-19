package sion.bestRoom.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sion.bestRoom.model.City;
import sion.bestRoom.service.CityService;
import sion.bestRoom.service.RoomService;
import sion.bestRoom.service.ZigbangService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Component
@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {

    private final RoomService roomService;
    private final CityService cityService;
    private final ZigbangService zigbangService;
    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(); //single threaded by default
    }

    @Bean
    public ScheduledTaskRegistrar scheduledTaskRegistrar() {
        ScheduledTaskRegistrar registrar = new ScheduledTaskRegistrar();
        registrar.setTaskScheduler(taskScheduler());//스케줄러 등록
        registrar.addTriggerTask( //스케줄러 실행할 메소드 등록
                task(),
                triggerContext -> {
                    CronTask cronTask = new CronTask(
                            () -> System.out.println("Executing Cron Task at " + new Date()),
                            "0 0 0 * * *"); //매 00시마다 리셋
                    return cronTask.getTrigger().nextExecution(triggerContext);
                });
        return registrar;
    }

    @Bean
    public Runnable task() { //스케줄러 실행할 메소드
        return () -> {
            Instant now = Instant.now();
            log.info("Schedule TIME = " + LocalDateTime.ofInstant(now, ZoneOffset.UTC));
            roomService.deleteAllRooms();
            cityService.deleteAllCities();
            roomService.getSeoulAreaCode();
            zigbangService.getZigbangRooms();
            try {
                roomService.getDabangRoomsInCity();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
