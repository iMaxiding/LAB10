package com.example.project1.repository;

import com.example.project1.model.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public NoteJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Note> noteRowMapper = (rs, rowNum) -> {
        Note n = new Note();
        n.setId(rs.getLong("id"));
        n.setTitle(rs.getString("title"));
        n.setContent(rs.getString("content"));
        n.setUserId(rs.getLong("user_id"));
        return n;
    };

    public List<Note> findAllByUserIdPrepared(Long userId) {
        // Prepared statement: parametr wstawiany bezpiecznie pod "?"
        String sql = "SELECT id, title, content, user_id FROM notes WHERE user_id = ? ORDER BY id DESC";
        return jdbcTemplate.query(sql, noteRowMapper, userId);
    }
}

