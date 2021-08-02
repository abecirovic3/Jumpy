package sample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class HelpController {
    
    public void closeAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage currStage = (Stage) node.getScene().getWindow();
        currStage.close();
    }
}
