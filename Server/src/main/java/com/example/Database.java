package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
        private static final HikariDataSource dataSource;
        //Se initializeaza o singura data
        static {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://142.132.227.144:3306/Messenger");
            config.setUsername("remote");
            config.setPassword("Dacia2025!");
            config.setMaximumPoolSize(10);
            config.setAutoCommit(false);

            dataSource = new HikariDataSource(config);
        }
        public static Connection getConnection() throws SQLException {
            return dataSource.getConnection();
        }
        public static void closeConnection() {
            if (dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
            }
        }

        public static void rollback(Connection connection) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.rollback();
                }
            }
            catch (SQLException e) {
                System.err.println("Error ar rollback "+e.getMessage());
            }
        }
}
