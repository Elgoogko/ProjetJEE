package TP4.src.main.java.com.example.TP4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class userService {

    @Autowired
    private JdbcTemplate jdbc;

    // SELECT *
    public List<Map<String, Object>> getAllUsers() {
        return jdbc.queryForList("SELECT * FROM users");
    }

    // SELECT avec paramètre
    public Map<String, Object> getUserById(Long id) {
        return jdbc.queryForMap("SELECT * FROM users WHERE id = ?", id);
    }

    // INSERT
    public void createUser(String name) {
        jdbc.update("INSERT INTO users (name) VALUES (?)", name);
    }

    // UPDATE
    public void updateUser(Long id, String name) {
        jdbc.update("UPDATE users SET name = ? WHERE id = ?", name, id);
    }

    // DELETE
    public void deleteUser(Long id) {
        jdbc.update("DELETE FROM users WHERE id = ?", id);
    }
}

