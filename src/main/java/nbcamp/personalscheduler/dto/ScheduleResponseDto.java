package nbcamp.personalscheduler.dto;

import lombok.Getter;
import lombok.Setter;
import nbcamp.personalscheduler.entity.Schedule;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleResponseDto {
    private Long id;
    private String content;
    private String name;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.content = schedule.getContent();
        this.name = schedule.getName();
        this.createAt = schedule.getCreateAt();
        this.updateAt = schedule.getUpdateAt();
    }
}
