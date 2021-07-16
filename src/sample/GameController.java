package sample;

import javafx.fxml.FXML;

public class GameController {
    Difficulty difficulty;

    public GameController(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @FXML
    public void initialize() {
    }
}
