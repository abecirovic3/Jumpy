package sample;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class MainController {
    public Menu gameMenu;

    public void startGameAction(ActionEvent actionEvent) {
        Difficulty difficulty = determineGameDifficulty(((Button) actionEvent.getSource()).getId());

        GameController ctrl = new GameController(difficulty, gameMenu);

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

        GameModel m = GameModel.getInstance();
        stage.setOnCloseRequest(e -> {
            m.gameEnded = true; // we do this to stop the stopwatch thread in GameController
        });
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

    public void openHighscoresViewAction(ActionEvent actionEvent) {
        HighscoreController ctrl = new HighscoreController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/highscores.fxml"));
        loader.setController(ctrl);

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Highscores");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void openAboutViewAction(ActionEvent actionEvent) {
        AboutController ctrl = new AboutController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
        loader.setController(ctrl);

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("About Jumpy");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void quitAction(ActionEvent actionEvent) {
        GameModel m = GameModel.getInstance();
        m.gameEnded = true; // again we need this to end the stopwatch thread if needed
        Platform.exit();
    }
}
