package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Creates a connection given the host, database name, username and password.
 * Use getConnection() method to get the created connection.
 */
public class DatabaseConnectionManager {
  private final String url;
  private final Properties properties;

  /**
   * Constructor that sets the states for url and properties.
   * @param host String to host database.
   * @param databaseName String for the specific database within the host.
   * @param username String containing username for database.
   * @param password String containing password for database.
   */
  public DatabaseConnectionManager(String host, String databaseName,
      String username, String password) {

    this.url = "jdbc:postgresql://" + host + "/" + databaseName;
    this.properties = new Properties();
    this.properties.setProperty("user", username);
    this.properties.setProperty("password", password);
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(this.url, this.properties);
  }
}
