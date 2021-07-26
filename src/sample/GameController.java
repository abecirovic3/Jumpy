package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class GameController {
    public GridPane gameGrid;
    public Button btnRestart;
    public Label timeLabel;

    private GameModel model;

    private Button[][] gameGridButtons;
    private byte numberOfColumns = 4, numberOfRows = 5;

    private Button[] confirmButtons;
    private GridPane[] guideGrids;

    public GameController(Difficulty difficulty) {
        model = GameModel.getInstance();
        model.startGame(difficulty);

        if (difficulty == Difficulty.EASY)
            numberOfRows = 7;
        else if (difficulty == Difficulty.NORMAL)
            numberOfRows = 6;

        gameGridButtons = new Button[numberOfRows][numberOfColumns];
        confirmButtons = new Button[numberOfRows];
        guideGrids = new GridPane[numberOfRows];
    }

    @FXML
    public void initialize() {
        if (model.difficulty == Difficulty.NORMAL) {
            addRowToGameGrid();
        }
        else if (model.difficulty == Difficulty.EASY) {
            addRowToGameGrid();
            addRowToGameGrid();
        }

        initializeGameGridNodes();
    }

    private void initializeGameGridNodes() {
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

            if (node instanceof GridPane && column == numberOfColumns && row < numberOfRows) {
                guideGrids[row] = (GridPane) node;
            }
        }
    }

    public void inputAction(ActionEvent actionEvent) {
        if (model.gameEnded || model.activeColumn == numberOfColumns) return;

        if (model.firstAction) {
            model.firstAction = false;
            model.stopwatch.start();
            startStopwatchThread();
        }

        Pair<Integer, String> identifier = decodePressedButton(((Button)actionEvent.getSource()).getId());
        model.inputList[model.activeColumn] = identifier.getKey().byteValue();
        gameGridButtons[model.activeRow][model.activeColumn].setStyle("-fx-background-image: url(\"" +
                identifier.getValue() + "\");");

        while (model.activeColumn < numberOfColumns && model.inputList[model.activeColumn] != -1)
            model.activeColumn++;

        if (model.activeColumn == numberOfColumns) {
            confirmButtons[model.activeRow].setVisible(true);
        }
    }

    private void startStopwatchThread() {
        Thread timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!model.gameEnded) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            timeLabel.setText("Time: "
                                    + model.stopwatch.getElapsedTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//                            System.out.println(timeLabel.getText());
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        timeThread.start();
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
        Button confirmBtn = getConfirmButton();
        gameGrid.add(confirmBtn, gameGrid.getColumnCount() - 1,gameGrid.getRowCount() - 1);
    }

    private Button getConfirmButton() {
        Button confirmBtn = new Button();
        confirmBtn.setVisible(false);
        confirmBtn.setPrefHeight(32);
        confirmBtn.setPrefWidth(32);
        confirmBtn.setStyle("-fx-background-image: url(\"/img/confirmation.png\");\n" +
                "-fx-background-color: Transparent;");
        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                validateInputAction(e);
            }
        });
        return confirmBtn;
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
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    recoverInputAction(e);
                }
            });
        }
        return buttons;
    }

    public void goBackToMainMenu(ActionEvent actionEvent) {

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

        closeCurrentStage();
    }

    private void closeCurrentStage() {
        //model.resetGameParameters();
        model.gameEnded = true;
        Stage currStage = (Stage) gameGrid.getScene().getWindow();
        currStage.close();
    }

    public void validateInputAction(ActionEvent actionEvent) {
        byte[] result = model.validateInput();
        byte fullHits = result[0];
        byte halfHits = result[1];

        guideGrids[model.activeRow].setVisible(true);
        confirmButtons[model.activeRow].setVisible(false);

        fillGameGuideGridCircles(fullHits, halfHits);

        model.clearInputList();

        if (fullHits == 4) {
//            model.stopwatch.stop();
            model.gameEnded = true;
            showEndAlert(true);
            btnRestart.setText("Play again");
            return;
        }

        model.activeRow++;
        model.activeColumn = 0;

        if (model.activeRow == numberOfRows) {
//            model.stopwatch.stop();
            model.gameEnded = true;
            showEndAlert(false);
            btnRestart.setText("Play again");
        }
    }

    private void showEndAlert(boolean win) {
        EndAlertController ctrl = new EndAlertController(win, model);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/endGameAlert.fxml"));
        loader.setController(ctrl);

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("End of game");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private void fillGameGuideGridCircles(byte fullHits, byte halfHits) {
        ObservableList<Node> children = guideGrids[model.activeRow].getChildren();
        Circle[][] circles = new Circle[2][2];

        for (Node node : children) {
            Integer row = GridPane.getRowIndex(node);
            Integer column = GridPane.getColumnIndex(node);

            if (row == null)
                row = 0;
            if (column == null)
                column = 0;

            circles[row][column] = (Circle) node;
        }

        int row = 0, column = 0;
        for (byte i = 0; i < fullHits; i++) {
            circles[row][column].setFill(Paint.valueOf("FF0000"));
            column++;
            if (column == 2) {
                row++;
                column = 0;
            }
        }
        for (byte i = 0; i < halfHits; i++) {
            circles[row][column].setFill(Paint.valueOf("00FF00"));
            column++;
            if (column == 2) {
                row++;
                column = 0;
            }
        }
    }

    public void recoverInputAction(ActionEvent actionEvent) {
        if (model.gameEnded) return;
        Button pressedButton = (Button) actionEvent.getSource();
        for (int j = 0; j < numberOfColumns; j++) {
            if (gameGridButtons[model.activeRow][j] == pressedButton) {
                pressedButton.setStyle(null);
                model.inputList[j] = -1;
                if (j < model.activeColumn)
                    model.activeColumn = (byte) j;
                if (confirmButtons[model.activeRow].isVisible())
                    confirmButtons[model.activeRow].setVisible(false);
                break;
            }
        }
    }

    public void restartAction(ActionEvent actionEvent) {
        closeCurrentStage();
        // need to find better way of stopping the stopwatch thread ^ˇ
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GameController ctrl = new GameController(model.difficulty);

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
    }

//    public void setTimeLabelText(String text) {
//        timeLabel.setText(text);
//    }
}
