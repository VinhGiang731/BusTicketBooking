package com.project.busticket.config;

import java.sql.Connection;
import java.sql.DriverManager;
import org.springframework.stereotype.Component;

@Component
public class DatabaseStatusProvider {

    // Bạn nên inject các thuộc tính này từ file config
    private final String mysqlUrl = "jdbc:mysql://localhost:3306/busticket?connectTimeout=1000";
    private final String mysqlUser = "root";
    private final String mysqlPass = "31072004";

    public boolean isMySqlAvailable() {
        try (Connection conn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPass)) {
            return true; // Kết nối thành công, MySQL đang chạy
        } catch (Exception e) {
            return false; // Kết nối thất bại, MySQL đã chết
        }
    }
}