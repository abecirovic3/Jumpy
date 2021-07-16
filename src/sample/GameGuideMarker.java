package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

public class GameGuideMarker {
    GridPane grid;

    public GameGuideMarker() {
        grid = new GridPane();
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
    }

    public GridPane getGrid() {
        return grid;
    }
}
