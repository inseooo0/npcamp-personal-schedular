package nbcamp.personalscheduler.service;

import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.entity.Schedule;
import nbcamp.personalscheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

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
}
