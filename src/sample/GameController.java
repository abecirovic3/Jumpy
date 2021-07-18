package sample;

import javafx.collections.ObservableList;
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
import javafx.util.Pair;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class GameController {
    public GridPane gameGrid;

    private Difficulty difficulty;

    private byte activeRow = 0, activeColumn = 0;
    private boolean firstAction = true;
    private boolean gameEnded = false;

    private GameModel model;

    private Button[][] gameGridButtons;
    private byte numberOfColumns = 4, numberOfRows = 5;

    private Button[] confirmButtons;

    public GameController(Difficulty difficulty) {
        this.difficulty = difficulty;

        model = new GameModel();
        for(int i=0; i<4; i++) {
            model.getInputList()[i] = -1;
        }

        if (difficulty == Difficulty.EASY)
            numberOfRows = 7;
        else if (difficulty == Difficulty.NORMAL)
            numberOfRows = 6;

        gameGridButtons = new Button[numberOfRows][numberOfColumns];
        confirmButtons = new Button[numberOfRows];
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

        initializeGameGridButtons();
    }

    private void initializeGameGridButtons() {
        ObservableList<Node> children = gameGrid.getChildren();

        for (Node node : children) {
            Integer row = GridPane.getRowIndex(node);
            Integer column = GridPane.getColumnIndex(node);

            if (row == null)
                row = 0;
            if (column == null)
                column = 0;

            if (row < numberOfRows && column < numberOfColumns)
                gameGridButtons[row][column] = (Button) node;

            if (node instanceof Button && column == numberOfColumns && row < numberOfRows) {
                confirmButtons[row] = (Button) node;
            }
        }
    }

    public void inputAction(ActionEvent actionEvent) {
        Pair<Integer, String> identifier = decodePressedButton(((Button)actionEvent.getSource()).getId());
        model.getInputList()[activeColumn] = identifier.getKey().byteValue();
        gameGridButtons[activeRow][activeColumn].setStyle("-fx-background-image: url(\"" +
                identifier.getValue() + "\");");
        activeColumn++;
        if (activeColumn == numberOfColumns) {
            confirmButtons[activeRow].setVisible(true);
        }
    }

    private Pair<Integer, String> decodePressedButton(String buttonId) {
        Pair<Integer, String> identifier = new Pair<>(1, "/img/cat.png");
        if (buttonId.equals("btnDog"))
            identifier = new Pair<>(2, "/img/dog.png");
        else if (buttonId.equals("btnFox"))
            identifier = new Pair<>(3, "/img/fox.png");
        else if (buttonId.equals("btnLion"))
            identifier = new Pair<>(4, "/img/lion.png");
        else if (buttonId.equals("btnMonkey"))
            identifier = new Pair<>(5, "/img/monkey.png");
        else if (buttonId.equals("btnPanda"))
            identifier = new Pair<>(6, "/img/panda.png");

        return identifier;
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
        grid.setVisible(false);
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
