package org.example;

import java.sql.*;
import java.util.NoSuchElementException;

public class DateBase {
    private static final String URL = "jdbc:mysql://localhost:8081";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createSchema(connection);
            createTablePerson(connection);
            createTableDepartment(connection);
            insertPerson(connection);
            insertDepartment(connection);
            getPersonDepartmentName(connection, 1);
        } catch (SQLException e) {
            System.err.println("Подключение не удалось." + e.getMessage());
        }
    }

    private static void createSchema(Connection connection) {
        try(Statement statement = connection.createStatement()){
            statement.execute("DROP SCHEMA `table`");
            statement.execute("CREATE SCHEMA `table`");
        } catch (SQLException e) {
            System.err.println("Не удалось создать схему: " + e.getMessage());
        }
    }

    private static void createTablePerson(Connection connection) {
        try(Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE `table`.`person` (`id` INT NOT NULL PRIMARY KEY, `department_id` INT, `name` VARCHAR(256), `age` INT, `active` BOOL);");
        } catch (SQLException e) {
            System.err.println("Не удалось создать таблицу person: " + e.getMessage());
        }
    }

    private static void createTableDepartment(Connection connection) {
        try(Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE `table`.`depatment` (`id` INT PRIMARY KEY, `name` VARCHAR(128) NOT NULL);");
        } catch (SQLException e) {
            System.err.println("Не удалость создать таблицу department: " + e.getMessage());
        }
    }

    private static void insertPerson(Connection connection) {
        try(Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO `table`.`person` (`id`, `name`, `age`, `active`)\n" +
                    "VALUES (1,'Иван', 21, true );");
            statement.execute("INSERT INTO `table`.`person` (`id`, `name`, `age`, `active`)\n" +
                    "VALUES (2,'Руслан', 19, false );");
            statement.execute("INSERT INTO `table`.`person` (`id`, `name`, `age`, `active`)\n" +
                    "VALUES (3,'Игорь', 25, true );");
            statement.execute("INSERT INTO `table`.`person` (`id`, `name`, `age`, `active`)\n" +
                    "VALUES (4,'Алексей', 30, false );");
        }catch (SQLException e) {
            System.err.println("Не удалость добавить данные в таблицу Person: " + e.getMessage());
        }
    }

    private static void insertDepartment(Connection connection) {
        try(Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO `table`.`depatment` (`id`, `name`)\n" +
                    "VALUES (1,'depatment #1' );");
            statement.execute("INSERT INTO `table`.`depatment` (`id`, `name`)\n" +
                    "VALUES (2,'depatment #2' );");
            statement.execute("INSERT INTO `table`.`depatment` (`id`, `name`)\n" +
                    "VALUES (3,'depatment #3' );");
            statement.execute("INSERT INTO `table`.`depatment` (`id`, `name`)\n" +
                    "VALUES (4,'depatment #4' );");
        } catch (SQLException e) {
            System.err.println("Не удалость добавить данные в таблицу Department: " + e.getMessage());
        }
    }

    /**
     * Пункт 4
     */
    private static String getPersonDepartmentName(Connection connection, long personId) throws SQLException  {
        // Написать метод, который загружает Имя department по Идентификатору person
        try(Statement statement = connection.createStatement()) {
       ResultSet resultSet =  statement.executeQuery("SELECT `depatment`.`name` where `person`.`id` " + personId);
        if (!resultSet.next()) {
            throw new NoSuchElementException("Не найдена строка с идентификатором " + personId);
        }
            return resultSet.getString("name");
        }
    }

}
