package sample;

import java.time.LocalTime;

public class Stopwatch {
    private LocalTime startTime;
    private LocalTime stoppedElapsedTime;
    private boolean stopped;

    public Stopwatch() {
        stopped = true;
        reset();
    }

    public void start() {
        startTime = LocalTime.now();
        stopped = false;
    }

    public void stop() {
        stoppedElapsedTime = getElapsedTime();
        stopped = true;
    }

    public LocalTime getElapsedTime() {
        if (stopped) return stoppedElapsedTime;
        LocalTime currTime = LocalTime.now();
        int hours = currTime.getHour() - startTime.getHour();
        int minutes = currTime.getMinute() - startTime.getMinute();
        int seconds = currTime.getSecond() - startTime.getSecond();
        int nanos = currTime.getNano() - startTime.getNano();

        if (nanos < 0) { seconds--; nanos += 1000000000; }
        if (seconds < 0) { minutes--; seconds += 60; }
        if (minutes < 0) { hours--; minutes += 60; }

        return LocalTime.of(hours, minutes, seconds, nanos);
    }

    public void reset() {
        startTime = LocalTime.of(0, 0, 0, 0);
        stoppedElapsedTime = LocalTime.of(0, 0, 0, 0);
    }
}
