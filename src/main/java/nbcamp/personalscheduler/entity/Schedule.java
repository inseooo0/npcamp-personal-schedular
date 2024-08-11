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
    private String name;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Schedule(String content, String name, String password) {
        this.content = content;
        this.name = name;
        this.password = password;
    }

    public Schedule(Long id, String content, String name, String password, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.content = content;
        this.name = name;
        this.password = password;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
