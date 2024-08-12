package nbcamp.personalscheduler.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nbcamp.personalscheduler.entity.Schedule;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private ManagerResponseDto manager;
}
