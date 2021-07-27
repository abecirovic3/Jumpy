package sample;

import java.time.LocalTime;

public class Highscore implements Comparable {
    private String username;
    private LocalTime time;
    private Difficulty difficulty;

    public Highscore(String username, LocalTime time, Difficulty difficulty) {
        this.username = username;
        this.time = time;
        this.difficulty = difficulty;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return username + " - " + time.toString();
    }

    @Override
    public int compareTo(Object o) {
        Highscore hs = (Highscore) o;
        if (time.isBefore(hs.time)) return -1;
        if (time.isAfter(hs.time)) return 1;
        return username.compareTo(hs.username);
    }
}
