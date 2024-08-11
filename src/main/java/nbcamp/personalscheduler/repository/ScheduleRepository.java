package nbcamp.personalscheduler.repository;

import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.entity.Manager;
import nbcamp.personalscheduler.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public Schedule save(Schedule schedule) {

        String sql = "insert into schedule(content, manager_id, password) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, schedule.getContent());
            pstmt.setLong(2, schedule.getManager().getId());
            pstmt.setString(3, schedule.getPassword());
            return pstmt;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return findById(id);
    }

    public Schedule findById(Long id) {
        String sql = "select * from schedule where id = ?";

        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(id);
                schedule.setContent(rs.getString("content"));
                schedule.setManager(new Manager(rs.getLong("manager_id")));
                schedule.setPassword(rs.getString("password"));
                schedule.setCreateAt(rs.getTimestamp("create_at").toLocalDateTime());
                schedule.setUpdateAt(rs.getTimestamp("update_at").toLocalDateTime());
                return schedule;
            } else {
                return null;
            }
        }, id);
    }

    public List<Schedule> findListV0(LocalDate updateDate, Long managerId) {
        String sql = "select * from schedule where date(update_at) = ? and manager_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String content = rs.getString("content");
            Long findManagerId = rs.getLong("manager_id");
            String password = rs.getString("password");
            LocalDateTime createAt = rs.getTimestamp("create_at").toLocalDateTime();
            LocalDateTime updateAt = rs.getTimestamp("update_at").toLocalDateTime();
            return new Schedule(id, content, new Manager(findManagerId), password, createAt, updateAt);
        }, Date.valueOf(updateDate), managerId);
    }

    public List<Schedule> findListV1(LocalDate updateDate) {
        String sql = "select * from schedule where date(update_at) = ? order by update_at desc";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String content = rs.getString("content");
            Long findManagerId = rs.getLong("manager_id");
            String password = rs.getString("password");
            LocalDateTime createAt = rs.getTimestamp("create_at").toLocalDateTime();
            LocalDateTime updateAt = rs.getTimestamp("update_at").toLocalDateTime();
            return new Schedule(id, content, new Manager(findManagerId), password, createAt, updateAt);
        }, Date.valueOf(updateDate));
    }

    public List<Schedule> findListV2(Long managerId) {
        String sql = "select * from schedule where manager_id = ? order by update_at desc";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String content = rs.getString("content");
            Long findManagerId = rs.getLong("manager_id");
            String password = rs.getString("password");
            LocalDateTime createAt = rs.getTimestamp("create_at").toLocalDateTime();
            LocalDateTime updateAt = rs.getTimestamp("update_at").toLocalDateTime();
            return new Schedule(id, content, new Manager(findManagerId), password, createAt, updateAt);
        }, managerId);
    }

    public List<Schedule> findListV3() {
        String sql = "select * from schedule order by update_at desc";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String content = rs.getString("content");
            Long findManagerId = rs.getLong("manager_id");
            String password = rs.getString("password");
            LocalDateTime createAt = rs.getTimestamp("create_at").toLocalDateTime();
            LocalDateTime updateAt = rs.getTimestamp("update_at").toLocalDateTime();
            return new Schedule(id, content, new Manager(findManagerId), password, createAt, updateAt);
        });
    }

    public Schedule update(Long scheduleId, Schedule schedule) {
        String sql = "update schedule set content = ? where id = ?";

        jdbcTemplate.update(sql, schedule.getContent(), scheduleId);
        return findById(scheduleId);
    }

    public void removeById(Long scheduleId) {
        String sql = "delete from schedule where id = ?";
        jdbcTemplate.update(sql, scheduleId);
    }

}
