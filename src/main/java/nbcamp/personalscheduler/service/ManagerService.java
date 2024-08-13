package nbcamp.personalscheduler.service;

import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.dto.ManagerCreateServiceDto;
import nbcamp.personalscheduler.entity.Manager;
import nbcamp.personalscheduler.repository.ManagerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository repository;
    private final ModelMapper modelMapper;

    public Manager save(ManagerCreateServiceDto managerDto) {
        Manager manager;
        if (managerDto.getEmail() != null) {
            manager = new Manager(managerDto.getName(), managerDto.getEmail());
        } else {
            manager = new Manager(managerDto.getName());
        }
        return repository.save(manager);
    }

    public Manager findById(Long id) {
        return repository.findById(id);
    }

    public Manager update(Manager manager) {
        return repository.update(manager);
    }
}
