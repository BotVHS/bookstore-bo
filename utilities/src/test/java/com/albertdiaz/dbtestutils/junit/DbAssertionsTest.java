package com.albertdiaz.dbtestutils.junit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ExtendWith({MockitoExtension.class})
public class DbAssertionsTest {
    @BeforeAll
    static void loadDb() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:test");
        var statement = connection.createStatement();

        String sql = "Create table student (ID int primary key, name varchar(50))";
 
        statement.execute(sql);
         
        sql = "Insert into student (ID, name) values (1, 'Nam Ha Minh 1')";
        statement.executeUpdate(sql);

        sql = "Insert into student (ID, name) values (2, 'Nam Ha Minh 2')";
        statement.executeUpdate(sql);
    }

    private static Connection connection;

    @Test
    void testAssertThatHasOneLineTrue() {
        DbAssertions.assertThat(connection)
            .table("student")
            .where("id", 1)
            .hasOneLine();
    }

    @Test
    void testAssertThatHasOneLineWithCompositeWhwre() {
        DbAssertions.assertThat(connection)
            .table("student")
            .where("id", 1)
            .and("name", "Nam Ha Minh 1")
            .hasOneLine();
    }

    @Test
    void testAssertDoesNotExist() {
        DbAssertions.assertThat(connection)
            .table("student")
            .where("id", 3333)
            .doesNotExist();
    }

    @Test
    void testAssertThatHasValueEqual() {
        DbAssertions.assertThat(connection)
            .table("student")
            .column("name")
            .where("id", 1)
            .valueEqual("Nam Ha Minh 1");
    }

    @Test
    void testAssertThatRowCount() {
        DbAssertions.assertThat(connection)
            .table("student")
            .hasLines(2);
    }
}
