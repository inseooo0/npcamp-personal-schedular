package nbcamp.personalscheduler.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    private Long id;
    private String content;
    private Manager manager;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Schedule(String content, Manager manager, String password) {
        this.content = content;
        this.manager = manager;
        this.password = password;
    }

    public Schedule(Long id, String content, Manager manager, String password, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.content = content;
        this.manager = manager;
        this.password = password;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
