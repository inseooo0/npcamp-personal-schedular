package nbcamp.personalscheduler.controller;

import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.dto.ScheduleRequestDto;
import nbcamp.personalscheduler.dto.ScheduleResponseDto;
import nbcamp.personalscheduler.entity.Schedule;
import nbcamp.personalscheduler.service.ScheduleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // dto -> entity
        Schedule schedule = new Schedule(requestDto.getContent(), requestDto.getName(), requestDto.getPassword());

        Schedule savedSchedule = scheduleService.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }
}
