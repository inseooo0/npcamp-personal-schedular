package nbcamp.personalscheduler.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDeleteDto {
    @NotBlank
    private String password;
}
