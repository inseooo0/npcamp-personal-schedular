package nbcamp.personalscheduler.service;

import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.entity.Manager;
import nbcamp.personalscheduler.repository.ManagerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository repository;

    public Manager save(Manager manager) {
        return repository.save(manager);
    }

    public Manager findById(Long id) {
        return repository.findById(id);
    }

    public Manager update(Manager manager) {
        return repository.update(manager);
    }
}
