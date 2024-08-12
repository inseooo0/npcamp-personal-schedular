package nbcamp.personalscheduler.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nbcamp.personalscheduler.entity.Manager;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ManagerResponseDto {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
