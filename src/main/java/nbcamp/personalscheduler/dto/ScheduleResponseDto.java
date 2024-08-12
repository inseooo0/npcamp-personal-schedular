package nbcamp.personalscheduler.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import nbcamp.personalscheduler.entity.Schedule;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(title = "ScheduleResp : 일정 정보 응답 DTO")
public class ScheduleResponseDto {
    @Schema(description = "일정 ID", example = "1")
    private Long id;
    @Schema(description = "일정 내용", example = "미용실 가기")
    private String content;
    @Schema(description = "일정 생성일시", example = "2024-08-11T17:15:23")
    private LocalDateTime createAt;
    @Schema(description = "일정 수정일시", example = "2024-08-11T17:15:23")
    private LocalDateTime updateAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.content = schedule.getContent();
        this.createAt = schedule.getCreateAt();
        this.updateAt = schedule.getUpdateAt();
    }
}
