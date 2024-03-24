package ru.nexigntestovoe.jbisss.h2db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSqlAdapterH2 {

    @Test
    public void test() {
        SqlAdapterH2 sqlAdapterH2 = new SqlAdapterH2();

        Assertions.assertNotNull(sqlAdapterH2.getAllPhoneNumbers());
    }
}
