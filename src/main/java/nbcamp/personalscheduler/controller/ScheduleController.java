package nbcamp.personalscheduler.controller;

import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.dto.ScheduleRequestDto;
import nbcamp.personalscheduler.dto.ScheduleResponseDto;
import nbcamp.personalscheduler.entity.Schedule;
import nbcamp.personalscheduler.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @GetMapping("/schedule/{scheduleId}")
    public ScheduleResponseDto getSchedule(@PathVariable Long scheduleId) {
        Schedule findSchedule = scheduleService.findById(scheduleId);
        return new ScheduleResponseDto(findSchedule);
    }

    @GetMapping("/schedule")
    public List<ScheduleResponseDto> getScheduleList(@RequestParam(required = false) String updateDate,
                                                     @RequestParam(required = false) String name) {
        LocalDate date = null;
        if (updateDate != null) {
            date = LocalDate.parse(updateDate, DateTimeFormatter.ISO_DATE);
        }

        List<Schedule> scheduleList = scheduleService.findList(date, name);
        return scheduleList.stream().map(ScheduleResponseDto::new).toList();
    }

    @PutMapping("/schedule/{scheduleId}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto requestDto) {
        Schedule updateSchedule = new Schedule(requestDto.getContent(), requestDto.getName(), requestDto.getPassword());
        return new ScheduleResponseDto(scheduleService.update(scheduleId, updateSchedule));
    }

    @DeleteMapping("/schedule/{scheduleId}")
    public String deleteSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto requestDto) {
        String password = requestDto.getPassword();
        scheduleService.removeById(scheduleId, password);
        return "ok";
    }
}
