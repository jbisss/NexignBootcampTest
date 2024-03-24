package ru.nexigntestovoe.jbisss.generator.udrGenerator;

import ru.nexigntestovoe.jbisss.generator.ApplicationConstants;
import ru.nexigntestovoe.jbisss.generator.cdrGenerator.domain.Cdr;
import ru.nexigntestovoe.jbisss.generator.udrGenerator.domain.Udr;
import ru.nexigntestovoe.jbisss.file.readers.StringReader;
import ru.nexigntestovoe.jbisss.file.writers.FilesWriter;

import java.util.*;

/**
 * Generating UDR-files
 */
public class UdrGenerator implements IUdrGenerator{

    private final StringReader<Cdr> stringReader;
    private final FilesWriter<Udr> filesWriterUdr;

    public UdrGenerator(StringReader<Cdr> stringReader, FilesWriter<Udr> filesWriterUdr) {
        this.stringReader = stringReader;
        this.filesWriterUdr = filesWriterUdr;
    }

    /**
     * Method for generating all reports
     */
    @Override
    public void generateReports() {
        handleCdrsRowsAndGenerateUdrs(null, null);
    }

    /**
     * Method for generating all reports for only phone number
     */
    @Override
    public void generateReports(String msisdn) {
        handleCdrsRowsAndGenerateUdrs(msisdn, null);
    }

    /**
     * Method for generating all reports for only phone number with months
     */
    @Override
    public void generateReports(String msisdn, String month) {
        handleCdrsRowsAndGenerateUdrs(msisdn, month);
    }

    private void handleCdrsRowsAndGenerateUdrs(String exactPhoneNumber, String exactMonth) {
        List<Cdr> cdrs = stringReader.read();
        Map<String, Udr> abonentUdrMap = new HashMap<>();

        for (Cdr cdr : cdrs) {
            Iterator<Cdr.CdrRow> cdrRowsIterator = cdr.getRowsIterator();
            while(cdrRowsIterator.hasNext()) {
                Cdr.CdrRow cdrRow = cdrRowsIterator.next();
                if (Objects.isNull(exactPhoneNumber) && Objects.isNull(exactMonth)) {
                    handleAbonent(cdrRow, abonentUdrMap);
                }
                if (Objects.nonNull(exactPhoneNumber) && Objects.nonNull(exactMonth)) {
                    if (cdrRow.getPhoneNumber().equals(exactPhoneNumber) && cdrRow.getCallMonth().equals(exactMonth)) {
                        handleAbonent(cdrRow, abonentUdrMap);
                    }
                }
                if (Objects.nonNull(exactPhoneNumber) && Objects.isNull(exactMonth)) {
                    if (cdrRow.getPhoneNumber().equals(exactPhoneNumber)) {
                        handleAbonent(cdrRow, abonentUdrMap);
                    }
                }
            }
        }
        Objects.requireNonNull(abonentUdrMap.values().stream().toList()).forEach(filesWriterUdr::write);
    }

    private void handleAbonent(Cdr.CdrRow cdrRow, Map<String, Udr> abonentUdrMap) {
        String key = cdrRow.getPhoneNumber() + cdrRow.getCallMonth();
        if (abonentUdrMap.containsKey(key)) {
            handleExistingAbonent(abonentUdrMap.get(key), cdrRow);
        } else {
            handleNewAbonent(abonentUdrMap, cdrRow, key, cdrRow.getPhoneNumber());
        }
    }

    private void handleNewAbonent(Map<String, Udr> abonentUdrMap, Cdr.CdrRow cdrRow, String key, String phoneNumber) {
        long incomingCallTotalTimeToSet = 0;
        long outcomingCallTotalTimeToSet = 0;
        long callTime = cdrRow.getCallTime();
        if (cdrRow.getCallType().equals(ApplicationConstants.CallType.INCOMING)) {
            incomingCallTotalTimeToSet = callTime;
        } else {
            outcomingCallTotalTimeToSet = callTime;
        }
        abonentUdrMap.put(key, new Udr(phoneNumber, cdrRow.getCallMonth(), new Udr.Call(incomingCallTotalTimeToSet), new Udr.Call(outcomingCallTotalTimeToSet)));
    }

    private void handleExistingAbonent(Udr abonentUdr, Cdr.CdrRow cdrRow) {
        long callTime = cdrRow.getCallTime();
        if (cdrRow.getCallType().equals(ApplicationConstants.CallType.INCOMING)) {
            abonentUdr.addTotalTimeToIncomingCall(callTime);
        } else {
            abonentUdr.addTotalTimeToOutcomingCall(callTime);
        }
    }
}
