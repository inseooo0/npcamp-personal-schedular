package nbcamp.personalscheduler.repository;

import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public Schedule save(Schedule schedule) {

        String sql = "insert into schedule(content, name, password) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, schedule.getContent());
            pstmt.setString(2, schedule.getName());
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
                schedule.setName(rs.getString("name"));
                schedule.setPassword(rs.getString("password"));
                schedule.setCreateAt(rs.getTimestamp("create_at").toLocalDateTime());
                schedule.setUpdateAt(rs.getTimestamp("update_at").toLocalDateTime());
                return schedule;
            } else {
                return null;
            }
        }, id);
    }
}
