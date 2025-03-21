package rip.shuka.testi.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
	private static HikariDataSource dataSource;

	public static void init(String hostname, String user, String password, int port, String database) {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:postgresql://" + hostname + ":" + port + "/" + database);
		config.setUsername(user);
		config.setPassword(password);
		dataSource = new HikariDataSource(config);
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}

