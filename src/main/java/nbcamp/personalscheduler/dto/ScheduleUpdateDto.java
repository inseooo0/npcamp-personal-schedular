package nbcamp.personalscheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleUpdateDto {
    @NotBlank
    @Size(max = 200)
    private String content;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
}
