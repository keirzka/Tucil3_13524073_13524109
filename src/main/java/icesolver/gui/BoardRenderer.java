package icesolver.gui;

import icesolver.model.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class BoardRenderer {
    public GridPane render(Board papan, State state) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for (int i = 0; i < papan.baris; i++) {
            for (int j = 0; j < papan.kolom; j++) {
                char c = papan.karakterTampilan(i, j, state.pos, state.checkpointBerikutnya);

                Label label = new Label(String.valueOf(c));

                label.setMinSize(40, 40);
                label.setAlignment(Pos.CENTER);

                label.setStyle("-fx-border-color: black;" + "-fx-font-size: 18px;" + "-fx-font-weight: bold;");

                StackPane cell = new StackPane(label);

                grid.add(cell, j, i);
            }
        }
        return grid;
    }
}