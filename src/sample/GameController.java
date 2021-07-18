package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class GameController {
    public GridPane gameGrid;
    Difficulty difficulty;

    public GameController(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @FXML
    public void initialize() {
        if (difficulty == Difficulty.NORMAL) {
            addRowToGameGrid();
        }
        else if (difficulty == Difficulty.EASY) {
            addRowToGameGrid();
            addRowToGameGrid();
        }
    }

    private void addRowToGameGrid() {
        Button[] buttons = getButtons(4);
        gameGrid.addRow(gameGrid.getRowCount(), buttons[0], buttons[1], buttons[2], buttons[3], getGameGuideGrid());
        Button confirmBtn = new Button();
        confirmBtn.setVisible(false);
        gameGrid.add(confirmBtn, gameGrid.getColumnCount() - 1,gameGrid.getRowCount() - 1);
    }

    private GridPane getGameGuideGrid() {
        GridPane grid = new GridPane();
        grid.setMinSize(64,64);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(5);
        Circle circ1 = new Circle();
        Circle circ2 = new Circle();
        Circle circ3 = new Circle();
        Circle circ4 = new Circle();
        circ1.setRadius(12);
        circ2.setRadius(12);
        circ3.setRadius(12);
        circ4.setRadius(12);
        grid.add(circ1, 0, 0);
        grid.add(circ2, 1, 0);
        grid.add(circ3, 0, 1);
        grid.add(circ4, 1, 1);
        return grid;
    }

    private Button[] getButtons(int numberOfInstances) {
        Button[] buttons = new Button[numberOfInstances];
        for (int i = 0; i < numberOfInstances; i++) {
            buttons[i] = new Button();
            buttons[i].setPrefWidth(64);
            buttons[i].setPrefHeight(64);
        }
        return buttons;
    }

    public void changeDifficultyAction(ActionEvent actionEvent) {
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
