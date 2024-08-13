package nbcamp.personalscheduler.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleCreateServiceDto {
    private String content;
    private Long managerId;
    private String password;
}
