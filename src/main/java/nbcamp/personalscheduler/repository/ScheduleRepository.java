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
import java.util.ArrayList;
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

    public List<Schedule> findList(LocalDate updateDate, Long managerId, int pageNum, int pageSize) {
        StringBuilder sqlSb = new StringBuilder("select * from schedule where 1=1");
        List<Object> queryArgs = new ArrayList<>();

        if (updateDate != null) {
            sqlSb.append(" and date(update_at) = ?");
            queryArgs.add(Date.valueOf(updateDate));
        }
        if (managerId != null) {
            sqlSb.append(" and manager_id = ?");
            queryArgs.add(managerId);
        }
        sqlSb.append(" order by update_at desc");
        sqlSb.append(" limit ").append(pageSize).append(" offset ").append(pageNum - 1);

        return jdbcTemplate.query(sqlSb.toString(), (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String content = rs.getString("content");
            Long findManagerId = rs.getLong("manager_id");
            String password = rs.getString("password");
            LocalDateTime createAt = rs.getTimestamp("create_at").toLocalDateTime();
            LocalDateTime updateAt = rs.getTimestamp("update_at").toLocalDateTime();
            return new Schedule(id, content, new Manager(findManagerId), password, createAt, updateAt);
        }, queryArgs.toArray());
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
