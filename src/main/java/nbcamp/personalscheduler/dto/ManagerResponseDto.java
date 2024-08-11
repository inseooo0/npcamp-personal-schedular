package nbcamp.personalscheduler.dto;

import lombok.Getter;
import lombok.Setter;
import nbcamp.personalscheduler.entity.Manager;

import java.time.LocalDateTime;

@Getter
@Setter
public class ManagerResponseDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public ManagerResponseDto(Manager manager) {
        this.id = manager.getId();
        this.name = manager.getName();
        this.email = manager.getEmail();
        this.createAt = manager.getCreateAt();
        this.updateAt = manager.getUpdateAt();
    }
}
