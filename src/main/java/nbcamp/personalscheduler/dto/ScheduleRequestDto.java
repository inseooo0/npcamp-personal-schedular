package nbcamp.personalscheduler.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleRequestDto {
    private String content;
    private String name;
    private String email;
    private String password;
}
