package com.example.detectioncar.controller;

import com.example.detectioncar.dto.SensorDataPage;
import com.example.detectioncar.dto.SensorDataRow;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminSensorController {

    private final JdbcTemplate jdbcTemplate;

    public AdminSensorController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @GetMapping("/sensor-data")
    public SensorDataPage querySensorData(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer familyId,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "desc") String order
    ) {

        if (page < 1) page = 1;
        if (size < 1) size = 20;
        int offset = (page - 1) * size;

   
        String orderSql = "desc";
        if ("asc".equalsIgnoreCase(order)) {
            orderSql = "asc";
        }

        StringBuilder from = new StringBuilder();
        from.append(" FROM sensor_data s ");
        from.append(" LEFT JOIN user_info u ON s.user_id = u.user_id ");
        from.append(" WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (userId != null) {
            from.append(" AND s.user_id = ? ");
            params.add(userId);
        }
        if (familyId != null) {
            from.append(" AND u.family_id = ? ");
            params.add(familyId);
        }
        if (startTime != null && !startTime.isBlank()) {
            from.append(" AND s.created_at >= ? ");
            params.add(startTime);
        }
        if (endTime != null && !endTime.isBlank()) {
            from.append(" AND s.created_at <= ? ");
            params.add(endTime);
        }

        // count
        String countSql = "SELECT COUNT(*) " + from;
        Long total = jdbcTemplate.queryForObject(countSql, params.toArray(), Long.class);
        if (total == null) total = 0L;

        // list
        String listSql = "SELECT s.id, s.user_id, s.car_name, s.sensor_type, " +
                "s.ax, s.ay, s.az, s.pressure_value, s.raw_data, s.created_at " +
                from +
                " ORDER BY s.created_at " + orderSql +
                " LIMIT ? OFFSET ? ";

        List<Object> listParams = new ArrayList<>(params);
        listParams.add(size);
        listParams.add(offset);

        List<SensorDataRow> list = jdbcTemplate.query(listSql, listParams.toArray(), (rs, rowNum) -> {
            SensorDataRow r = new SensorDataRow();
            r.setId(rs.getInt("id"));
            r.setUserId(rs.getInt("user_id"));
            r.setCarName(rs.getString("car_name"));
            r.setSensorType(rs.getString("sensor_type"));

            Float ax = (Float) rs.getObject("ax");
            Float ay = (Float) rs.getObject("ay");
            Float az = (Float) rs.getObject("az");
            Float pv = (Float) rs.getObject("pressure_value");

            r.setAx(ax);
            r.setAy(ay);
            r.setAz(az);
            r.setPressureValue(pv);

            r.setRawData(rs.getString("raw_data"));

            Timestamp ts = rs.getTimestamp("created_at");
            if (ts != null) {
                LocalDateTime t = ts.toLocalDateTime();
                r.setCreatedAt(t);
            }
            return r;
        });

        return new SensorDataPage(total, list);
    }
}
