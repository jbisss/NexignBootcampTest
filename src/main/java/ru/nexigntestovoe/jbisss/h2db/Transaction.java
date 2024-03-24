package ru.nexigntestovoe.jbisss.h2db;

public class Transaction {

    private final String callType;
    private final String startCall;
    private final String endCall;
    private final int abonentId;

    public Transaction(String callType, String startCall, String endCall, int abonentId) {
        this.callType = callType;
        this.startCall = startCall;
        this.endCall = endCall;
        this.abonentId = abonentId;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s, %s)", callType, startCall, endCall, abonentId);
    }
}
