package nbcamp.personalscheduler.service;

import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.entity.Manager;
import nbcamp.personalscheduler.entity.Schedule;
import nbcamp.personalscheduler.exception.ApiException;
import nbcamp.personalscheduler.exception.CommonErrorCode;
import nbcamp.personalscheduler.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repository;
    private final ManagerService managerService;
    private final ModelMapper modelMapper;

    public Schedule save(Schedule schedule) {
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

    public Schedule update(Long scheduleId, Schedule updateSchedule) {
        Schedule schedule = findById(scheduleId);

        if (schedule == null) {
            throw new ApiException(CommonErrorCode.INVALID_PARAMETER);
        } else if (!schedule.getPassword().equals(updateSchedule.getPassword())) {
            throw new ApiException(CommonErrorCode.INVALID_PARAMETER);
        }

        return repository.update(scheduleId, updateSchedule);
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
