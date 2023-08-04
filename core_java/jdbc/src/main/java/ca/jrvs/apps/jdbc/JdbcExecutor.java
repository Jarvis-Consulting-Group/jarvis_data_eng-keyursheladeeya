package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JdbcExecutor is the entrypoint for the program.
 * main() method uses the DatabaseConnectionManager to create a connection and uses that
 * connection to execute statements that returns a ResultSet.
 */
public class JdbcExecutor {

  final Logger logger = LoggerFactory.getLogger(JdbcExecutor.class);

  /**
   * main() method creates a connection using the DatabaseConnectionManager and executes
   * a statement to get the count of all customers in the customers table.
   * @param args string array args.
   */
  public static void main(String[] args) {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
        "hplussport", "postgres", "password");

    BasicConfigurator.configure();

    JdbcExecutor jdbcExecutor = new JdbcExecutor();

    try {
      Connection connection = dcm.getConnection();
      OrderDao orderDao = new OrderDao(connection);
      Order dbOrder = orderDao.findById(1000);

      jdbcExecutor.logger.info(dbOrder.toString());

      /*

      CustomerDao customerDao = new CustomerDao(connection);
      Customer customer = new Customer();
      customer.setFirstName("John");
      customer.setLastName("Adams");
      customer.setEmail("jadams.wh.gov");
      customer.setAddress("1234 Main St");
      customer.setCity("Arlington");
      customer.setState("VA");
      customer.setPhone("(555) 555-9845");
      customer.setZipCode("01234");

      Customer dbCustomer = customerDao.create(customer);
      jdbcExecutor.logger.info(dbCustomer.toString());
      dbCustomer = customerDao.findById(dbCustomer.getId());
      jdbcExecutor.logger.info(dbCustomer.toString());
      dbCustomer.setEmail("john.adams@wh.gov");
      dbCustomer = customerDao.update(dbCustomer);
      jdbcExecutor.logger.info(dbCustomer.toString());
      customerDao.delete(dbCustomer.getId());

       */
    } catch (SQLException e) {
      jdbcExecutor.logger.error("Error: SQLException: ", e);
    }

  }
}
