package com.example.nenguou.meizhiday.Utils;

/**
 * Created by binguner on 2017/12/2.
 */

public enum TimeUnit {
    DAYS,
    HOURS,
    MICROSECONDS,
    MILLISECONDS,
    MINUTES,
    NANOSECONDS,
    SECONDS;

    private TimeUnit() {
    }

    public long convert(long sourceDuration, java.util.concurrent.TimeUnit sourceUnit) {
        throw new RuntimeException("Stub!");
    }

    public long toNanos(long duration) {
        throw new RuntimeException("Stub!");
    }

    public long toMicros(long duration) {
        throw new RuntimeException("Stub!");
    }

    public long toMillis(long duration) {
        throw new RuntimeException("Stub!");
    }

    public long toSeconds(long duration) {
        throw new RuntimeException("Stub!");
    }

    public long toMinutes(long duration) {
        throw new RuntimeException("Stub!");
    }

    public long toHours(long duration) {
        throw new RuntimeException("Stub!");
    }

    public long toDays(long duration) {
        throw new RuntimeException("Stub!");
    }

    public void timedWait(Object obj, long timeout) throws InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public void timedJoin(Thread thread, long timeout) throws InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public void sleep(long timeout) throws InterruptedException {
        throw new RuntimeException("Stub!");
    }
    public static String toNormal(String string){
        String s = string.split("T")[0];
        return s;
    }
}
