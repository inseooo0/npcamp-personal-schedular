package nbcamp.personalscheduler.service;

import lombok.RequiredArgsConstructor;
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

    public Schedule save(Schedule schedule) {
        return repository.save(schedule);
    }

    public Schedule findById(Long scheduleId) {
        return repository.findById(scheduleId);
    }

    public List<Schedule> findList(LocalDate updateDate, String name) {
        if (updateDate != null && name != null) {
            return repository.findListV0(updateDate, name);
        } else if (updateDate != null) {
            return repository.findListV1(updateDate);
        } else if (name != null) {
            return repository.findListV2(name);
        } else {
            return repository.findListV3();
        }
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
