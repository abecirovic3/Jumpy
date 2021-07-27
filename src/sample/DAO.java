package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalTime;
import java.util.Scanner;

public class DAO {
    private static DAO instance;
    private Connection conn;
    private ObservableList<Highscore> highscores = FXCollections.observableArrayList();
    private PreparedStatement getAllHighscoresQuery, addHighscoreQuery, deleteAllHighscoresQuery, getNewUserIdQuery;

    public static DAO getInstance() {
        if (instance == null) instance = new DAO();
        return instance;
    }

    public Connection getConn() {
        return conn;
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:jumpyScores.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            getAllHighscoresQuery = conn.prepareStatement("SELECT username, time, difficulty FROM highscores");
        } catch (SQLException e) {
            regenerateDatabase();
            try {
                getAllHighscoresQuery = conn.prepareStatement("SELECT username, time, difficulty FROM highscores");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try {
            addHighscoreQuery = conn.prepareStatement("INSERT INTO highscores VALUES(?, ?, ?, ?)");
            getNewUserIdQuery = conn.prepareStatement("SELECT MAX(id) + 1 FROM highscores");
            deleteAllHighscoresQuery = conn.prepareStatement("DELETE from highscores");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = getAllHighscoresQuery.executeQuery();
            while (rs.next()) {
                Difficulty diff = getDifficultyFromString(rs.getString(3));
                highscores.add(new Highscore(rs.getString(1), LocalTime.parse(rs.getString(2)), diff));
            }
            FXCollections.sort(highscores);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerateDatabase() {
        try {
            String query = "";
            Scanner scanner = new Scanner(new FileInputStream(System.getProperty("user.dir") + "/resources/sql/DBDefault.sql"));
            while (scanner.hasNext()) {
                query += scanner.nextLine();
                if (query.length() > 1 && query.charAt(query.length() - 1) == ';') {
                    Statement statement = conn.createStatement();
                    statement.execute(query);
                    query = "";
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addHighscore(String username, String time, String difficulty) {
        int id = 1;
        try {
            ResultSet rs = getNewUserIdQuery.executeQuery();
            if (rs.next())
                id = rs.getInt(1);
            addHighscoreQuery.setInt(1, id);
            addHighscoreQuery.setString(2, username);
            addHighscoreQuery.setString(3, time);
            addHighscoreQuery.setString(4, difficulty);
            addHighscoreQuery.executeUpdate();
            Difficulty diff = getDifficultyFromString(difficulty);
            highscores.add(new Highscore(username, LocalTime.parse(time), diff));
            FXCollections.sort(highscores);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Difficulty getDifficultyFromString(String d) {
        Difficulty diff = Difficulty.EASY;
        if (d.equals("NORMAL"))
            diff = Difficulty.NORMAL;
        else if (d.equals("HARD"))
            diff = Difficulty.HARD;
        return diff;
    }

    public void deleteAllHighscores() {
        try {
            deleteAllHighscoresQuery.executeUpdate();
            highscores.removeAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Highscore> getHighscores() {
        return highscores;
    }

    public ObservableList<Highscore> getHighscoresByDifficulty(Difficulty difficulty) {
        ObservableList<Highscore> result = FXCollections.observableArrayList();
        for (Highscore h : highscores)
            if (h.getDifficulty() == difficulty)
                result.add(h);

        return result;
    }
}
