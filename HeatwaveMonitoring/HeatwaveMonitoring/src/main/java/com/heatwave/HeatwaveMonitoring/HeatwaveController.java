package com.heatwave.HeatwaveMonitoring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeatwaveController {

    private final JdbcTemplate jdbcTemplate;

    public HeatwaveController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/saveData")
    public String saveData(
            @RequestParam int data_id,
            @RequestParam String city,
            @RequestParam int temp,
            @RequestParam int moisture) {

        String sql = """
                INSERT INTO data
                (data_id, city, temp, moisture)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                data_id,
                city,
                temp,
                moisture
        );

        if (temp >= 40) {
            return """
                    <h2>Data Saved Successfully!</h2>
                    <p>City: %s</p>
                    <p>Temperature: %d°C</p>
                    <p>Moisture: %d%%</p>
                    <h3>⚠️ HEATWAVE ALERT!</h3>
                    """.formatted(city, temp, moisture);
        }

        return """
                <h2>Data Saved Successfully!</h2>
                <p>City: %s</p>
                <p>Temperature: %d°C</p>
                <p>Moisture: %d%%</p>
                <h3>Temperature is Normal.</h3>
                """.formatted(city, temp, moisture);
    }
}