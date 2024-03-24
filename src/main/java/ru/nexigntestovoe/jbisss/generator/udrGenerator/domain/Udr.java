package ru.nexigntestovoe.jbisss.generator.udrGenerator.domain;

import java.time.Duration;
import java.time.LocalTime;

public class Udr {

    public static class Call {

        private long totalTime;

        public Call(long totalTime) {
            this.totalTime = totalTime;
        }

        @Override
        public String toString() {
            return String.format("{\n\t\t\"totalTime\": \"%s\"\n\t}", millisToSimpleTime(totalTime));
        }

        private String millisToSimpleTime(long millis) {
            Duration duration = Duration.ofMillis(millis);
            LocalTime time = LocalTime.MIDNIGHT.plus(duration);
            return String.format("%02d:%02d:%02d",
                    time.getHour(), time.getMinute(), time.getSecond());
        }
    }

    private final String msisdn;
    private final String month;
    private final Call incomingCall;
    private final Call outcomingCall;

    public Udr(String msisdn, String month, Call incomingCall, Call outcomingCall) {
        this.msisdn = msisdn;
        this.month = month;
        this.incomingCall = incomingCall;
        this.outcomingCall = outcomingCall;
    }

    public void addTotalTimeToIncomingCall(long timeToAdd) {
        this.incomingCall.totalTime += timeToAdd;
    }

    public void addTotalTimeToOutcomingCall(long timeToAdd) {
        this.outcomingCall.totalTime += timeToAdd;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getMonth() {
        return month;
    }

    @Override
    public String toString() {
        return String.format("{\n\t\"msisdn\": \"%s\",\n\t\"incomingCall\": %s,\n\t \"outcomingCall\": %s\n}", msisdn, incomingCall, outcomingCall);
    }
}
