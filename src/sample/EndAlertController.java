package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndAlertController {
    public Label labelTitleMessage;
    public Label labelCombinationMessage;

    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    public ImageView img4;

    public Label labelDifficulty;

    public VBox winVBox;
    public Label timeLabel;
    public Label rankLabel;
    public TextField nameField;

    private boolean win;
    private GameModel model = GameModel.getInstance();

    public EndAlertController(boolean win) {
        this.win = win;
    }

    @FXML
    public void initialize() {
        if (!win) {
            labelTitleMessage.setText("Better luck next time!");
            labelCombinationMessage.setText("The correct combination is:");
        }

        initializeCombinationImages();

        labelDifficulty.setText(labelDifficulty.getText() + model.difficulty);

        if (win) {
            winVBox.setVisible(true);
            timeLabel.setText(model.stopwatch.getElapsedTime().toString());
            rankLabel.setText("5"); // temp
        }
    }

    private void initializeCombinationImages() {
        img1.setImage(new Image(getImgSource(model.generatedList[0])));
        img2.setImage(new Image(getImgSource(model.generatedList[1])));
        img3.setImage(new Image(getImgSource(model.generatedList[2])));
        img4.setImage(new Image(getImgSource(model.generatedList[3])));
    }

    private String getImgSource(byte identifier) {
        String result = "/img/cat.png";
        if (identifier == 2)
            result = "/img/dog.png";
        else if (identifier == 3)
            result = "/img/fox.png";
        else if (identifier == 4)
            result = "/img/lion.png";
        else if (identifier == 5)
            result = "/img/monkey.png";
        else if (identifier == 6)
            result = "/img/panda.png";

        return result;
    }

    public void closeAlertAction(ActionEvent actionEvent) {
        Stage currStage = (Stage) labelTitleMessage.getScene().getWindow();
        currStage.close();
    }
}
