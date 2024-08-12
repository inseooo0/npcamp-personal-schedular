package nbcamp.personalscheduler.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import nbcamp.personalscheduler.entity.Manager;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(title = "ManagerResp : 담당자 정보 응답 DTO")
public class ManagerResponseDto {
    @Schema(description = "담당자 ID", example = "1")
    private Long id;
    @Schema(description = "담당자 이름", example = "inseo")
    private String name;
    @Schema(description = "담당자 이메일", example = "example@gmail.com")
    private String email;
    @Schema(description = "담당자 정보 생성일시", example = "2024-08-11T17:15:23")
    private LocalDateTime createAt;
    @Schema(description = "담당자 정보 수정일시", example = "2024-08-11T17:15:23")
    private LocalDateTime updateAt;

    public ManagerResponseDto(Manager manager) {
        this.id = manager.getId();
        this.name = manager.getName();
        this.email = manager.getEmail();
        this.createAt = manager.getCreateAt();
        this.updateAt = manager.getUpdateAt();
    }
}
