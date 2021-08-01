package sample;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class AboutController {
    private HostServices hostServices;

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    public void openGithubAction(ActionEvent actionEvent) {
        hostServices.showDocument("https://github.com/abecirovic3");
    }

    public void openLinkAction(ActionEvent actionEvent) {
        Hyperlink link = (Hyperlink) actionEvent.getSource();
        hostServices.showDocument(link.getText());
    }

    public void closeAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage currStage = (Stage) node.getScene().getWindow();
        currStage.close();
    }

}
