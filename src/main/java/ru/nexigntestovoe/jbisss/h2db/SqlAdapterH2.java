package ru.nexigntestovoe.jbisss.h2db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for managing H2-database
 */
public class SqlAdapterH2 implements PhoneNumbersSource {

    private static final int PHONE_NUMBERS_TO_GENERATE = 10;

    private static Connection connection = null;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdb");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SqlAdapterH2() {
        instantiateAdapter();
    }

    private void instantiateAdapter() {
        try (Statement statement = connection.createStatement()) {
            createTableTransactions(statement);
            createTableAbonents(statement);
            fillTableAbonents(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableTransactions(Statement statement) throws SQLException {
        String createTableQuery = """
            CREATE TABLE
            IF NOT EXISTS transactions
            (id INT AUTO_INCREMENT PRIMARY KEY, call_type VARCHAR(2), start_call VARCHAR(20), end_call VARCHAR(20), abonent_id INT)
            """;
        statement.executeUpdate(createTableQuery);
    }

    private void createTableAbonents(Statement statement) throws SQLException {
        String createTableQuery = """
            CREATE TABLE
            IF NOT EXISTS abonents
            (id INT AUTO_INCREMENT PRIMARY KEY, phone_number VARCHAR(255))
            """;
        statement.executeUpdate(createTableQuery);
    }

    private void fillTableAbonents(Statement statement) throws SQLException {
        String insertQuery = "INSERT INTO abonents (phone_number) VALUES ";
        StringBuilder generatedPhoneNumbersBuilder = new StringBuilder();
        for (int i = 0; i < PHONE_NUMBERS_TO_GENERATE; i++) {
            generatedPhoneNumbersBuilder.append(String.format("(7921756535%s), ", i));
        }
        generatedPhoneNumbersBuilder.delete(generatedPhoneNumbersBuilder.length() - 2, generatedPhoneNumbersBuilder.length());
        insertQuery += generatedPhoneNumbersBuilder.toString();
        statement.executeUpdate(insertQuery);
    }

    public int getIdByPhoneNumber(String chosenNumber) {
        String selectQuery = "SELECT abonents.id FROM abonents WHERE abonents.phone_number = " +  chosenNumber;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectQuery);
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addTransaction(Transaction transaction) {
        String insertQuery = "INSERT INTO transactions (call_type, start_call, end_call, abonent_id) VALUES ";
        insertQuery += transaction;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAllTransactions() {
        try (Statement statement = connection.createStatement()) {
            String selectQuery = "SELECT * FROM transactions";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            System.out.println("Transactions from H2");
            while(resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " +
                        resultSet.getString(2) + " " +
                        resultSet.getString(3) + " " +
                        resultSet.getString(4) + " " +
                        resultSet.getString(5) + " ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getAllPhoneNumbers() {
        List<String> result = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            String selectQuery = "SELECT abonents.phone_number FROM abonents";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
