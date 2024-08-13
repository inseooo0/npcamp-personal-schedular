package nbcamp.personalscheduler.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleUpdateServiceDto {
    private Long id;
    private String content;
    private String password;
}
