# SurveyHub Desktop Application

![Java](https://img.shields.io/badge/Java-21.0.5-orange?style=for-the-badge&logo=oracle)
![Hibernate](https://img.shields.io/badge/Hibernate-6.5.1.Final-brightgreen?style=for-the-badge&logo=hibernate)
![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-10-blue?style=for-the-badge&logo=jakartaee)
![MySQL](https://img.shields.io/badge/MySQL-8.x-orange?style=for-the-badge&logo=mysql)
![JasperReports](https://img.shields.io/badge/JasperReports-6.21.3-lightgrey?style=for-the-badge)
![License](https://img.shields.io/badge/License-Apache%202.0-blue?style=for-the-badge)
![Build Status](https://img.shields.io/github/actions/workflow/status/manuelalejandrojimeneztorres/surveyhub-desktop/desktop-ci.yml?style=for-the-badge)
![Dependencies](https://img.shields.io/badge/Dependencies-Up%20to%20date-brightgreen?style=for-the-badge)

## Description

The **SurveyHub Desktop Application** is an enterprise-grade software solution meticulously designed for desktop environments to facilitate efficient creation, management, and analysis of online surveys. Built on robust technologies such as **Java Swing**, **Hibernate ORM**, and **Jakarta EE**, it ensures high performance, scalability, and seamless user experiences. This application integrates directly with the **SurveyHub Backend**, enabling secure data synchronization, dynamic reporting, and comprehensive role-based access control.

---

## Table of Contents

1. [Key Features](#key-features)
2. [Architecture Overview](#architecture-overview)
3. [Prerequisites](#prerequisites)
4. [Installation](#installation)
   - [Project Setup](#project-setup)
   - [Environment Configuration](#environment-configuration)
   - [Running the Application](#running-the-application)
5. [Technologies and Tools Used](#technologies-and-tools-used)
6. [Dependencies](#dependencies)
7. [Troubleshooting](#troubleshooting)
8. [Contributing Guidelines](#contributing-guidelines)
9. [Support](#support)
10. [License](#license)
11. [Acknowledgments](#acknowledgments)

---

## Key Features

- **Comprehensive Survey Management**: Tools for creating, editing, deleting, and analyzing surveys.
- **Role-Based Access Control (RBAC)**: Secure operations with predefined roles (System Administrator, Survey Manager, and Respondent).
- **Dynamic Reporting**:
  - Generate detailed survey reports using **JasperReports**.
  - Export reports in multiple formats, including PDF and Excel.
- **User Personalization**:
  - Update profile information, including secure password changes with **BCrypt** encryption.
  - Enable or disable dark mode for improved accessibility.
- **Intuitive Interface**:
  - Organized tabbed layout with keyboard shortcuts for efficient navigation.
  - Customizable settings to optimize user workflows.
- **Data Integrity and Validation**: Ensures data consistency with clear feedback for success or error messages.
- **Search and Filter Capabilities**: Quickly locate users, surveys, or data entries.

[🔼 Back to Top](#table-of-contents)

---

## Architecture Overview

The SurveyHub Desktop Application follows a modular architecture, ensuring maintainability and scalability. Key components include:

- **MVC Pattern**: Separates business logic, data access, and presentation layers for cleaner code.
- **Persistence Layer**: Managed using **Hibernate ORM**, providing seamless interaction with the MySQL database.
- **User Interface**: Built with **Java Swing**, enhanced by **FlatLaf** for a modern look-and-feel.
- **Reporting Layer**: Dynamic report generation powered by **JasperReports**.

[🔼 Back to Top](#table-of-contents)

---

## Prerequisites

Before setting up the application, ensure the following tools and software are installed:

- [Java Development Kit (JDK) 21](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [MySQL (8.x)](https://dev.mysql.com/downloads/)
- **Optional**:
  - [Apache NetBeans 24](https://netbeans.apache.org/) or another Java IDE
  - [MySQL Workbench](https://www.mysql.com/products/workbench/) for database management

[🔼 Back to Top](#table-of-contents)

---

## Installation

### Project Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/manuelalejandrojimeneztorres/surveyhub-desktop.git
   ```

2. Navigate to the project directory:

   ```bash
   cd surveyhub-desktop
   ```

3. Build the project using Maven:

   ```bash
   mvn clean install
   ```

[🔼 Back to Top](#table-of-contents)

### Environment Configuration

To ensure flexibility and security, the application relies on environment variables for database configuration. Follow the steps below to set these variables:

#### Required Environment Variables:

- `DB_URL`: Database URL (e.g., `jdbc:mysql://localhost:3306/surveyhub`)
- `DB_USERNAME`: Database username (e.g., `root`)
- `DB_PASSWORD`: Database password (e.g., `YourStrongDatabasePassword`)

#### Setting Environment Variables

##### On Windows:

1. Open the Command Prompt with administrative privileges.
2. Use the `setx` command to add each variable:

   ```cmd
   setx DB_URL "jdbc:mysql://localhost:3306/surveyhub"
   setx DB_USERNAME "root"
   setx DB_PASSWORD "YourStrongDatabasePassword"
   ```

3. Restart the Command Prompt to ensure the variables are applied.

##### On Linux/macOS:

1. Open the terminal.
2. Edit your shell configuration file (e.g., `.bashrc` or `.zshrc`) and add the following lines:

   ```bash
   export DB_URL="jdbc:mysql://localhost:3306/surveyhub"
   export DB_USERNAME="root"
   export DB_PASSWORD="YourStrongDatabasePassword"
   ```

3. Save the file and reload the shell configuration:

   ```bash
   source ~/.bashrc  # or ~/.zshrc
   ```

#### Default Values

The application uses the following default values if no environment variables are detected. These defaults are defined in the utility class `EnvironmentConfig`:

```java
public static void loadEnvironmentVariables() {
    setSystemProperty("DB_URL", "jdbc:mysql://localhost:3306/surveyhub");
    setSystemProperty("DB_USERNAME", "root");
    setSystemProperty("DB_PASSWORD", "YourStrongDefaultDatabasePassword");
}
```

> [!NOTE]
> Modify these defaults as needed to align with your deployment.

### Running the Application

1. Launch the application:

   ```bash
   java -jar target/surveyhub-desktop-1.0-SNAPSHOT.jar
   ```

2. Log in with your credentials to access the main dashboard.

[🔼 Back to Top](#table-of-contents)

---

## Technologies and Tools Used

- **Backend**:
  - Hibernate ORM: For seamless database operations
  - Jakarta EE: Enterprise-grade development framework
  - MySQL Connector/J: JDBC driver for MySQL connectivity

- **Frontend**:
  - Java Swing: GUI framework
  - FlatLaf: Look-and-feel library for modern desktop interfaces

- **Reporting**:
  - JasperReports: Advanced report generation

- **Security**:
  - BCrypt: Secure password hashing

- **Development Tools**:
  - Apache Maven: For build automation
  - Apache NetBeans: IDE for Java development

[🔼 Back to Top](#table-of-contents)

---

## Dependencies

Key dependencies include:

- `hibernate-core` (6.5.1.Final): ORM for database management
- `jakarta.persistence-api` (3.1.0): JPA API
- `mysql-connector-java` (8.2.0): JDBC driver
- `jasperreports` (6.21.3): Reporting library
- `flatlaf` (3.4.1): Modern look-and-feel for GUIs
- `logback-classic` (1.5.6): Logging framework

> [!NOTE]
> Refer to the `pom.xml` file for a complete list of dependencies.

[🔼 Back to Top](#table-of-contents)

---

## Troubleshooting

### Common Issues

- **Database Connection Errors**:
  - Ensure the database credentials in the environment variables are correct.
  - Verify that the MySQL server is running and accessible.

- **Build Errors**:
  - Check for missing dependencies and run `mvn clean install` to resolve them.

- **Application Launch Issues**:
  - Verify the JDK version and ensure the environment variables are correctly set.

[🔼 Back to Top](#table-of-contents)

---

## Contributing Guidelines

We welcome contributions to enhance the SurveyHub Desktop Application. To contribute:

1. Fork the repository.
2. Create a new branch for your feature or bug fix:

   ```bash
   git checkout -b feature/your-feature-name
   ```

3. Commit your changes with detailed messages:

   ```bash
   git commit -m "Add: Description of your feature"
   ```

4. Push your branch and open a pull request.

> [!NOTE]
> Refer to the [CONTRIBUTING.md](CONTRIBUTING.md) file for more details.

[🔼 Back to Top](#table-of-contents)

---

## Support

For support, contact:

- **Email**: support@surveyhub.com
- **Documentation**: [SurveyHub Desktop Wiki](https://github.com/manuelalejandrojimeneztorres/surveyhub-desktop/wiki)

[🔼 Back to Top](#table-of-contents)

---

## License

This project is licensed under the **Apache License 2.0**. See the [LICENSE.txt](LICENSE.txt) file for more details.

[🔼 Back to Top](#table-of-contents)

---

## Acknowledgments

Special thanks to:

- The [Hibernate](https://hibernate.org/) team for their ORM framework.
- The [Jakarta EE](https://jakarta.ee/) community for their enterprise tools.
- The [JasperReports](https://community.jaspersoft.com/) community for reporting solutions.
- The open-source community for providing invaluable resources.

[🔼 Back to Top](#table-of-contents)

---

For more information, visit [SurveyHub Backend](https://github.com/manuelalejandrojimeneztorres/surveyhub-server) or [SurveyHub Mobile](https://github.com/manuelalejandrojimeneztorres/surveyhub-mobile).

Enjoy building with the **SurveyHub Desktop Application** and feel free to contribute to its development! 🚀

[🔼 Back to Top](#table-of-contents)
