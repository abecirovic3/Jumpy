package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class HighscoreController {
    public ListView scoresList;
    public ChoiceBox diffChoiceBox;

    private GameModel model = GameModel.getInstance();

    public HighscoreController() {
    }

    @FXML
    public void initialize() {
        diffChoiceBox.getItems().addAll(Difficulty.EASY, Difficulty.NORMAL, Difficulty.HARD);
        diffChoiceBox.setValue(Difficulty.EASY);

        diffChoiceBox.setOnAction((event) -> {
            scoresList.setItems(model.dao.getHighscoresByDifficulty((Difficulty) diffChoiceBox.getValue()));
        });

        scoresList.setItems(model.dao.getHighscoresByDifficulty(Difficulty.EASY));
    }

    public void closeAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage currStage = (Stage) node.getScene().getWindow();
        currStage.close();
    }

    public void deleteScoresAction(ActionEvent actionEvent) {
        if (scoresList.getItems() != null)
            model.dao.deleteAllHighscoresByDifficulty((Difficulty) diffChoiceBox.getValue());
        scoresList.setItems(null);
    }
}
