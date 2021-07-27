package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

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

    public void goBackToMainMenuAction(ActionEvent actionEvent) {
        MainController ctrl = new MainController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        loader.setController(ctrl);

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Main");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();

        Node node = (Node) actionEvent.getSource();
        Stage currStage = (Stage) node.getScene().getWindow();
        currStage.close();
    }
}
