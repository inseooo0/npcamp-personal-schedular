package nbcamp.personalscheduler.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.dto.*;
import nbcamp.personalscheduler.entity.Manager;
import nbcamp.personalscheduler.entity.Schedule;
import nbcamp.personalscheduler.service.ManagerService;
import nbcamp.personalscheduler.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Schedule Controller", description = "Schedule API")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ManagerService managerService;
    private final ModelMapper modelMapper;

    @PostMapping("/schedule")
    @Operation(summary = "schedule 등록", description = "일정을 등록할 때 사용하는 API")
    public ScheduleResponseDto createSchedule(@Valid @RequestBody ScheduleCreateDto requestDto) {

        ManagerCreateServiceDto managerDto = modelMapper.map(requestDto, ManagerCreateServiceDto.class);
        Manager savedManager = managerService.save(managerDto);

        ScheduleCreateServiceDto scheduleDto = modelMapper.map(requestDto, ScheduleCreateServiceDto.class);
        scheduleDto.setManagerId(savedManager.getId());

        Schedule savedSchedule = scheduleService.save(scheduleDto);

        // response dto 생성
        ManagerResponseDto managerResponseDto = modelMapper.map(savedManager, ManagerResponseDto.class);
        ScheduleResponseDto scheduleResponseDto = modelMapper.map(savedSchedule, ScheduleResponseDto.class);
        scheduleResponseDto.setManager(managerResponseDto);

        return scheduleResponseDto;
    }

    @GetMapping("/schedule/{scheduleId}")
    @Operation(summary = "schedule 단건 조회", description = "일정 ID로 일정을 조회할 때 사용하는 API")
    public ScheduleResponseDto getSchedule(@PathVariable Long scheduleId) {
        Schedule findSchedule = scheduleService.findById(scheduleId);
        Manager findManager = managerService.findById(findSchedule.getManager().getId());

        // response dto 생성
        ManagerResponseDto managerResponseDto = modelMapper.map(findManager, ManagerResponseDto.class);
        ScheduleResponseDto scheduleResponseDto = modelMapper.map(findSchedule, ScheduleResponseDto.class);
        scheduleResponseDto.setManager(managerResponseDto);

        return scheduleResponseDto;
    }

    @GetMapping("/schedule")
    @Operation(summary = "schedule 목록 조회", description = "수정일 또는 담당자 ID로 일정 목록을 조회할 때 사용하는 API")
    public List<ScheduleResponseDto> getScheduleList(@RequestParam(required = false) String updateDate,
                                                     @RequestParam(required = false) Long managerId,
                                                     @RequestParam int pageNum,
                                                     @RequestParam int pageSize) {
        LocalDate date = null;
        if (updateDate != null) {
            date = LocalDate.parse(updateDate, DateTimeFormatter.ISO_DATE);
        }

        List<Schedule> scheduleList = scheduleService.findList(date, managerId, pageNum, pageSize);
        List<ScheduleResponseDto> responseData = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            Manager manager = schedule.getManager();
            ManagerResponseDto managerResponseDto = modelMapper.map(manager, ManagerResponseDto.class);
            ScheduleResponseDto scheduleResponseDto = modelMapper.map(schedule, ScheduleResponseDto.class);
            scheduleResponseDto.setManager(managerResponseDto);
            responseData.add(scheduleResponseDto);
        }

        return responseData;
    }

    @PutMapping("/schedule/{scheduleId}")
    @Operation(summary = "schedule 수정", description = "일정을 수정할 때 사용하는 API")
    public ScheduleResponseDto updateSchedule(@PathVariable Long scheduleId,
                                              @Valid @RequestBody ScheduleUpdateDto requestDto) {
        Schedule originSchedule = scheduleService.findById(scheduleId);
        Long managerId = originSchedule.getManager().getId();

        ManagerUpdateServiceDto managerDto = new ManagerUpdateServiceDto();
        managerDto.setId(managerId);
        managerDto.setName(requestDto.getName());

        ScheduleUpdateServiceDto scheduleDto = new ScheduleUpdateServiceDto();
        scheduleDto.setId(scheduleId);
        scheduleDto.setContent(requestDto.getContent());
        scheduleDto.setPassword(requestDto.getPassword());

        Schedule schedule = scheduleService.update(scheduleDto);
        Manager manager = managerService.update(managerDto);
        schedule.setManager(manager);

        // response dto 생성
        ManagerResponseDto managerResponseDto = modelMapper.map(manager, ManagerResponseDto.class);
        ScheduleResponseDto scheduleResponseDto = modelMapper.map(schedule, ScheduleResponseDto.class);
        scheduleResponseDto.setManager(managerResponseDto);

        return scheduleResponseDto;
    }

    @DeleteMapping("/schedule/{scheduleId}")
    @Operation(summary = "schedule 삭제", description = "일정 ID로 일정을 삭제할 때 사용하는 API")
    public String deleteSchedule(@PathVariable Long scheduleId,
                                 @Valid @RequestBody ScheduleDeleteDto requestDto) {
        String password = requestDto.getPassword();
        scheduleService.removeById(scheduleId, password);
        return "ok";
    }
}
