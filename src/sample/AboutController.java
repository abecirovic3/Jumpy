package sample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class AboutController {
    private final GameModel model = GameModel.getInstance();

    public void openGithubAction(ActionEvent actionEvent) {
        model.getHostServices().showDocument("https://github.com/abecirovic3");
    }

    public void openLinkAction(ActionEvent actionEvent) {
        Hyperlink link = (Hyperlink) actionEvent.getSource();
        model.getHostServices().showDocument(link.getText());
    }

    public void closeAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage currStage = (Stage) node.getScene().getWindow();
        currStage.close();
    }
}
