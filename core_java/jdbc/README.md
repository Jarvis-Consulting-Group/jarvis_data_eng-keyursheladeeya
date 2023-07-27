# JDBC App
The Core Java JDBC app created a basic Java app to perform CRUD operations on a dataset in a database using the JDBC API. It utilized PostgresSQL in a Docker container and Git/GitHub for code management. Maven was used to manage the project, while the JDBC driver acted as a mediator between the app's JDBC methods and the database. Finally, the app was tested with the psql command in the terminal to query the database.

## Design Patterns
I utilized the Data Access Object (DAO) pattern in this project. The DAO pattern is beneficial for normalized databases, as it simplifies joins and reduces database resource usage, making it vertically scalable. The DAO consists of a Data Transfer Object (DTO) representing a single record returned from a query or table, and a DAO acting as an abstraction layer for the CRUD operations. In contrast, the repository pattern is suitable for single-table CRUD operations and performs joins within the code instead of the database. It is horizontally scalable and allows for distribution across different databases.

# Test
To manually test the application, I used terminal queries to verify whether data had been created, updated or deleted from the PostgresSQL database. While reading from the database, I utilized the SLF4J logger to display the DTOs. The database was set up and deployed in a docker container, and the test data and database objects were initialized
