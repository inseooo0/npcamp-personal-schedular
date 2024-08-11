package nbcamp.personalscheduler.service;

import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.entity.Schedule;
import nbcamp.personalscheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
}
