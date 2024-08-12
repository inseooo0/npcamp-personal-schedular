package nbcamp.personalscheduler.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.dto.ManagerResponseDto;
import nbcamp.personalscheduler.dto.ScheduleRequestDto;
import nbcamp.personalscheduler.dto.ScheduleResponseDto;
import nbcamp.personalscheduler.entity.Manager;
import nbcamp.personalscheduler.entity.Schedule;
import nbcamp.personalscheduler.service.ManagerService;
import nbcamp.personalscheduler.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Schedule Controller", description = "Schedule API")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ManagerService managerService;

    @PostMapping("/schedule")
    @Operation(summary = "schedule 등록", description = "일정을 등록할 때 사용하는 API")
    public ResponseEntity<Map<String, Object>> createSchedule(@Valid @RequestBody ScheduleRequestDto requestDto) {
        // dto -> entity
        Manager manager;
        if (requestDto.getEmail() != null) {
            manager = new Manager(requestDto.getName(), requestDto.getEmail());
        } else {
            manager = new Manager(requestDto.getName());
        }

        // manager db 저장
        Manager savedManager = managerService.save(manager);

        // schedule entity 생성
        Schedule schedule = new Schedule(requestDto.getContent(), savedManager, requestDto.getPassword());

        // schedule db 저장
        Schedule savedSchedule = scheduleService.save(schedule);

        // response dto 생성

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("schedule", new ScheduleResponseDto(savedSchedule));
        responseData.put("manager", new ManagerResponseDto(savedManager));

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/schedule/{scheduleId}")
    @Operation(summary = "schedule 단건 조회", description = "일정 ID로 일정을 조회할 때 사용하는 API")
    public ResponseEntity<Map<String, Object>> getSchedule(@PathVariable Long scheduleId) {
        Schedule findSchedule = scheduleService.findById(scheduleId);
        Manager findManager = managerService.findById(findSchedule.getManager().getId());

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("schedule", new ScheduleResponseDto(findSchedule));
        responseData.put("manager", new ManagerResponseDto(findManager));

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/schedule")
    @Operation(summary = "schedule 목록 조회", description = "수정일 또는 담당자 ID로 일정 목록을 조회할 때 사용하는 API")
    public ResponseEntity<List<Map<String, Object>>> getScheduleList(@RequestParam(required = false) String updateDate,
                                                                     @RequestParam(required = false) Long managerId,
                                                                     @RequestParam int pageNum,
                                                                     @RequestParam int pageSize) {
        LocalDate date = null;
        if (updateDate != null) {
            date = LocalDate.parse(updateDate, DateTimeFormatter.ISO_DATE);
        }

        List<Schedule> scheduleList = scheduleService.findList(date, managerId, pageNum, pageSize);
        List<Map<String, Object>> responseData = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("schedule", new ScheduleResponseDto(schedule));
            responseMap.put("manager", new ManagerResponseDto(schedule.getManager()));
            responseData.add(responseMap);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/schedule/{scheduleId}")
    @Operation(summary = "schedule 수정", description = "일정을 수정할 때 사용하는 API")
    public ResponseEntity<Map<String, Object>> updateSchedule(@PathVariable Long scheduleId,
                                                              @Valid @RequestBody ScheduleRequestDto requestDto) {
        Schedule originSchedule = scheduleService.findById(scheduleId);
        Long managerId = originSchedule.getManager().getId();

        Manager updateManager = new Manager(requestDto.getName());
        updateManager.setId(managerId);

        Schedule updateSchedule = new Schedule(requestDto.getContent(), updateManager, requestDto.getPassword());
        Schedule result = scheduleService.update(scheduleId, updateSchedule);
        Manager manager = managerService.update(updateManager);
        result.setManager(manager);

        // response dto 생성

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("schedule", new ScheduleResponseDto(result));
        responseData.put("manager", new ManagerResponseDto(manager));

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @DeleteMapping("/schedule/{scheduleId}")
    @Operation(summary = "schedule 삭제", description = "일정 ID로 일정을 삭제할 때 사용하는 API")
    public String deleteSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto requestDto) {
        String password = requestDto.getPassword();
        scheduleService.removeById(scheduleId, password);
        return "ok";
    }
}
