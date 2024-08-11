package nbcamp.personalscheduler.repository;

import lombok.RequiredArgsConstructor;
import nbcamp.personalscheduler.entity.Manager;
import nbcamp.personalscheduler.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
@RequiredArgsConstructor
public class ManagerRepository {

    private final JdbcTemplate jdbcTemplate;

    public Manager save(Manager manager) {
        String sql = "insert into manager(name, email) values (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, manager.getName());
            pstmt.setString(2, manager.getEmail());
            return pstmt;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return findById(id);
    }

    public Manager findById(Long id) {
        String sql = "select * from manager where id = ?";

        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                Manager manager = new Manager();
                manager.setId(id);
                manager.setName(rs.getString("name"));
                manager.setEmail(rs.getString("email"));
                manager.setCreateAt(rs.getTimestamp("create_at").toLocalDateTime());
                manager.setUpdateAt(rs.getTimestamp("update_at").toLocalDateTime());
                return manager;
            } else {
                return null;
            }
        }, id);
    }

    public Manager update(Manager manager) {
        String sql = "update manager set name = ? where id = ?";

        jdbcTemplate.update(sql, manager.getName(), manager.getId());
        return findById(manager.getId());
    }
}
