package nbcamp.personalscheduler.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Manager {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Manager(String name) {
        this.name = name;
    }

    public Manager(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Manager(Long id) {
        this.id = id;
    }
}
