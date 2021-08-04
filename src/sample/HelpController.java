package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class HelpController {
    public TextArea textArea;
    public Hyperlink gameRulesLink;
    public Hyperlink newGameLink;
    public Hyperlink highScoresLink;
    public Hyperlink watchTheTimeLink;
    public Hyperlink reportBugsLink;
    public Hyperlink helpDevelopLink;

    private final String gameRules = "The aim of the game is to guess the right combination of symbols. When you start a game, a sequence of four symbols is generated and your goal is to guess that combination.\n\nYou have six symbols to choose from. As soon as you select the first symbol the stopwatch starts. Depending on the difficulty level you have five, six or seven possible attempts to guess the right combination.\n\nWhen you choose your four symbols a confirmation button will appear and if you are happy with your selection you click the confirm button. At any point if you are unhappy with a symbol in its position you can undo the selection just by pressing on the desired location.\n\nAfter you click on the confirm button, the game guide grid shows up. It consists of four circles. The color of the circles indicates how close you are to guessing the right combination. When a circle is red it means that you have put a symbol in the right place, if the circle is green it means that you have guessed the right symbol but it is not in the right position, and black suggests that the symbol is completely wrong. Keep in mind that the order of circles isn't related to the order in which you have chosen your symbols. The circles just show how many of your symbols are correct and in their places and how many are just in the sequence.\n\nThe game is finished when you guess the right combination, or when you run out of attempts.\n\nAt any point you can choose to restart the game, or change the difficulty.\n\nGood luck and have fun.";
    private final String startANewGame = "To start a new game go to Home screen and choose one of the difficulty levels. If you choose EASY you will have 7 attempts to guess the right combination, NORMAL offers you 6 attempts and HARD means that you only get 5 attempts.";
    private final String highScores = "As soon as you make your first selection in a game the stopwatch starts. When you guess the right combination an alert shows up which shows your exact time and your rank. You then enter your name and click the button Done, your score is then saved.\n\nThe game keeps separate track of scores for every difficulty level.\n\nTo check your scores, click on the dropdown menu in the up left corner and choose the option Scores.";
    private final String watchTheTime = "Keep in mind that when you start choosing your combination that you shouldn't open other windows such as the Help, About and Scores view, because the stopwatch won't stop, and you are loosing precious time.\n\nThis is done in order to prevent the player to start the game then open another window just to stop the stopwatch so he/she can examine the combination more briefly.";
    private final String reportBugs = "Feel free to report any bugs and unwanted behavior of the game to my email address: abecirovic3@etf.unsa.ba";
    private final String helpDevelop = "Feel free to head out to my Github and start improving this game if you like.\nMy github: https://github.com/abecirovic3.\nOr you can go to About and click the Github icon.";

    @FXML
    public void initialize() {
        textArea.setText(gameRules);
    }

    public void setTextAreaAction(ActionEvent actionEvent) {
        Hyperlink link = (Hyperlink) actionEvent.getSource();
        String id = link.getId();
        if (id.equals("gameRulesLink"))
            textArea.setText(gameRules);
        else if (id.equals("newGameLink"))
            textArea.setText(startANewGame);
        else if (id.equals("highScoresLink"))
            textArea.setText(highScores);
        else if (id.equals("watchTheTimeLink"))
            textArea.setText(watchTheTime);
        else if (id.equals("reportBugsLink"))
            textArea.setText(reportBugs);
        else if (id.equals("helpDevelopLink"))
            textArea.setText(helpDevelop);
    }

    public void closeAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage currStage = (Stage) node.getScene().getWindow();
        currStage.close();
    }
}
