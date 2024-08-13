package nbcamp.personalscheduler.service;

import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.dto.ScheduleCreateServiceDto;
import nbcamp.personalscheduler.dto.ScheduleUpdateServiceDto;
import nbcamp.personalscheduler.entity.Manager;
import nbcamp.personalscheduler.entity.Schedule;
import nbcamp.personalscheduler.exception.ApiException;
import nbcamp.personalscheduler.exception.CommonErrorCode;
import nbcamp.personalscheduler.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repository;
    private final ManagerService managerService;
    private final ModelMapper modelMapper;

    public Schedule save(ScheduleCreateServiceDto scheduleDto) {
        Manager manager = new Manager();
        manager.setId(scheduleDto.getManagerId());
        Schedule schedule = new Schedule(scheduleDto.getContent(), manager, scheduleDto.getPassword());
        return repository.save(schedule);
    }

    public Schedule findById(Long scheduleId) {
        Schedule schedule = repository.findById(scheduleId);
        if (schedule == null) throw new ApiException(CommonErrorCode.INVALID_PARAMETER);
        return schedule;
    }

    public List<Schedule> findList(LocalDate updateDate, Long managerId, int pageNum, int pageSize) {
        List<Schedule> scheduleList = repository.findList(updateDate, managerId, pageNum, pageSize);

        for (Schedule schedule : scheduleList) {
            Long id = schedule.getManager().getId();
            schedule.setManager(managerService.findById(id));
        }

        return scheduleList;
    }

    public Schedule update(ScheduleUpdateServiceDto scheduleDto) {
        Schedule schedule = findById(scheduleDto.getId());

        // password 일치 여부 확인
        if (schedule == null) {
            throw new ApiException(CommonErrorCode.INVALID_PARAMETER);
        } else if (!schedule.getPassword().equals(scheduleDto.getPassword())) {
            throw new ApiException(CommonErrorCode.INVALID_PARAMETER);
        }

        Schedule updateSchedule = new Schedule();
        updateSchedule.setId(scheduleDto.getId());
        updateSchedule.setContent(scheduleDto.getContent());
        return repository.update(schedule);
    }

    public void removeById(Long scheduleId, String password) {
        Schedule schedule = findById(scheduleId);

        if (schedule == null) {
            throw new ApiException(CommonErrorCode.INVALID_PARAMETER);
        } else if (!schedule.getPassword().equals(password)) {
            throw new ApiException(CommonErrorCode.INVALID_PARAMETER);
        }

        repository.removeById(scheduleId);
    }
}
