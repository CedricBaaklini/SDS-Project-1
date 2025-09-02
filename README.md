# SQL Injection Demo (Java + MySQL)

A small,
educational project that demonstrates how SQL Injection (SQLi) works and how to prevent it using parameterized queries.
It contains two console-based login flows:

- A deliberately vulnerable example that builds SQL with string concatenation.
- A secure example that uses prepared statements to safely bind user input.

Use this project to learn by doing: see an injection succeed in the vulnerable flow, then see how the secure flow blocks
the same attack.
Important: Educational use only. Do not use the vulnerable approach in any real application.

## What you’ll learn

- How SQL injection arises from concatenating untrusted input into SQL.
- How prepared statements prevent injection by separating code from data.
- Practical steps to harden Java database code.

## Prerequisites

- Java 17 or newer installed (JDK).
- MySQL Server running locally.
- Maven (for dependency management and building).

The project uses the MySQL JDBC driver via Maven.

## Quick start

1. Create the demo database and table

- You can keep the schema name with spaces (as shown) or choose a simpler one. If you prefer to avoid spaces, see the
  note below.

``` sql
-- Create a schema (with spaces)
CREATE DATABASE IF NOT EXISTS `SDS Project 1`;
USE `SDS Project 1`;

-- Create a demo Users table
CREATE TABLE IF NOT EXISTS Users (
  Id INT AUTO_INCREMENT PRIMARY KEY,
  Username VARCHAR(100) NOT NULL UNIQUE,
  Password VARCHAR(100) NOT NULL
);

-- Insert some sample users
INSERT INTO Users (Username, Password) VALUES
  ('alice', 'wonderland'),
  ('bob', 'builder'),
  ('charlie_k', 'cheese');
```

1. Configure credentials

- The code assumes:
    - JDBC URL: jdbc:mysql://localhost:3306/SDS%20Project%201
    - User: root
    - Password: opensesame

- Adjust these to match your local setup if needed.

1. Build

- From the project root:

``` 
mvn -q -e -DskipTests package
```

1. Run

- From your IDE: run the “main” method of either class to test.
    - Vulnerable demo: Injection.main
    - Secure demo: SafeCode.main

- From the command-line (example, using your IDE or any runner that resolves Maven dependencies on the classpath):
    - Ensure the compiled classes and MySQL connector are on the classpath, then run the desired main class.

Tip: Easiest is running directly from your IDE, which will automatically provide the dependency classpath.

## Demonstration

- Vulnerable flow
    - When prompted:
        - Username: charlie_k' OR '1'='1
        - Password: anything

    - You’ll see how the login bypasses authentication due to the injected condition.

- Secure flow
    - Use the same input as above; the login should be rejected because prepared statements treat the input as data, not
      SQL code.

For convenience, the example injection payload is also noted in sample.txt.

## How it works (high level)

- Vulnerable approach
    - Concatenates user input into the SQL query string.
    - The database can’t distinguish user input from SQL syntax, enabling attackers to alter query logic.

- Secure approach
    - Uses PreparedStatement with parameter placeholders (?) and binds values separately.
    - The driver sends the SQL structure and data independently, preventing input from being interpreted as executable
      SQL.

## Troubleshooting

- Access denied/connection issues
    - Verify MySQL is running and accessible on localhost:3306.
    - Confirm username/password and database name in the JDBC URL.
    - If you renamed the database, update both the JDBC URL and the SQL that references the schema name.

- Schema name with spaces
    - JDBC URL must URL-encode spaces (e.g., SDS%20Project%201).
    - SQL identifiers with spaces must be quoted with backticks (e.g., ). `SDS Project 1`
    - Alternatively, avoid spaces: use a schema such as sds_project_1 and update the code/JDBC URL accordingly.

- Driver not found
    - Ensure you run via your IDE (which uses Maven dependencies) or otherwise include MySQL Connector/J on the
      classpath.

## Security and ethics

- This repository intentionally contains insecure code for demonstration purposes.
- Do not deploy the vulnerable example anywhere.
- Use the secure pattern (prepared statements) for any real database interactions.

## License

Use, modify, and learn freely. If you share or teach with this repo, a link back is appreciated.
