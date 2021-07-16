package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class MainController {
    public void startGameAction(ActionEvent actionEvent) {
        Difficulty difficulty = determineGameDifficulty(((Button) actionEvent.getSource()).getId());

        GameController ctrl = new GameController(difficulty);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
        loader.setController(ctrl);

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Jumpy");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();

        Node node = (Node) actionEvent.getSource();
        Stage currStage = (Stage) node.getScene().getWindow();
        currStage.close();
    }

    private Difficulty determineGameDifficulty(String btnId) {
        Difficulty difficulty = Difficulty.EASY;
        if (btnId.equals("btnNormal"))
            difficulty = Difficulty.NORMAL;
        else if (btnId.equals("btnHard"))
            difficulty = Difficulty.HARD;

        return difficulty;
    }
}
