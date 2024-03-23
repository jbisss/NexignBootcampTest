package ru.nexigntestovoe.jbisss.generator.h2db;

import ru.nexigntestovoe.jbisss.generator.cdrGenerator.subGenerators.PhoneGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    private final PhoneGenerator phoneGenerator;

    public SqlAdapterH2(PhoneGenerator phoneGenerator) {
        this.phoneGenerator = phoneGenerator;

        instantiateAdapter();
    }

    private void instantiateAdapter() {
        try (Statement statement = connection.createStatement()) {
            String createTableQuery = """
            CREATE TABLE
            IF NOT EXISTS abonents
            (id INT AUTO_INCREMENT PRIMARY KEY, phone_number VARCHAR(255))
            """;
            statement.executeUpdate(createTableQuery);

            String insertQuery = "INSERT INTO abonents (phone_number) VALUES ";
            StringBuilder generatedPhoneNumbersBuilder = new StringBuilder();
            for (int i = 0; i < PHONE_NUMBERS_TO_GENERATE; i++) {
                generatedPhoneNumbersBuilder.append(String.format("(%s), ", phoneGenerator.generateRandomPhoneNumber()));
            }
            generatedPhoneNumbersBuilder.delete(generatedPhoneNumbersBuilder.length() - 2, generatedPhoneNumbersBuilder.length());
            insertQuery += generatedPhoneNumbersBuilder.toString();
            statement.executeUpdate(insertQuery);
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
