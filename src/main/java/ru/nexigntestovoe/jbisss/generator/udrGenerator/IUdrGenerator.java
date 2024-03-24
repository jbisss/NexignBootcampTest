package ru.nexigntestovoe.jbisss.generator.udrGenerator;

public interface IUdrGenerator {

    void generateReports();
    void generateReports(String msisdn);
    void generateReports(String msisdn, String month);
}
