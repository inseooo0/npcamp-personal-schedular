package nbcamp.personalscheduler.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(title = "ScheduleReq : 일정 생성 요청 DTO")
public class ScheduleRequestDto {
    @NotBlank
    @Size(max = 200)
    @Schema(description = "일정 내용", example = "미용실 가기")
    private String content;
    @NotBlank
    @Schema(description = "담당자 이름", example = "inseo")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @Schema(description = "담당자 이메일", example = "example@gmail.com")
    private String email;
    @NotBlank
    @Schema(description = "비밀번호", example = "0000")
    private String password;
}
