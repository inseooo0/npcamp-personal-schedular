package nbcamp.personalscheduler.service;

import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.entity.Manager;
import nbcamp.personalscheduler.entity.Schedule;
import nbcamp.personalscheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repository;
    private final ManagerService managerService;

    public Schedule save(Schedule schedule) {
        return repository.save(schedule);
    }

    public Schedule findById(Long scheduleId) {
        return repository.findById(scheduleId);
    }

    public List<Schedule> findList(LocalDate updateDate, Long managerId) {
        List<Schedule> scheduleList;
        if (updateDate != null && managerId != null) {
            scheduleList = repository.findListV0(updateDate, managerId);
        } else if (updateDate != null) {
            scheduleList = repository.findListV1(updateDate);
        } else if (managerId != null) {
            scheduleList = repository.findListV2(managerId);
        } else {
            scheduleList = repository.findListV3();
        }

        for (Schedule schedule : scheduleList) {
            Long id = schedule.getManager().getId();
            schedule.setManager(managerService.findById(id));
        }

        return scheduleList;
    }

    public Schedule update(Long scheduleId, Schedule updateSchedule) {
        Schedule schedule = findById(scheduleId);

        if (schedule == null) {
            throw new IllegalArgumentException("해당 ID를 가진 일정은 존재하지 않습니다.");
        } else if (!schedule.getPassword().equals(updateSchedule.getPassword())) {
            throw new IllegalArgumentException("일정의 비밀번호가 일치하지 않습니다.");
        }

        return repository.update(scheduleId, updateSchedule);
    }

    public void removeById(Long scheduleId, String password) {
        Schedule schedule = findById(scheduleId);

        if (schedule == null) {
            throw new IllegalArgumentException("해당 ID를 가진 일정은 존재하지 않습니다.");
        } else if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("일정의 비밀번호가 일치하지 않습니다.");
        }

        repository.removeById(scheduleId);
    }
}
