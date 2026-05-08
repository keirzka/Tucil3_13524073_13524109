package icesolver.gui;

import icesolver.model.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class BoardRenderer {
    public GridPane render(Board papan, State state) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for (int i = 0; i < papan.baris; i++) {
            for (int j = 0; j < papan.kolom; j++) {
                char c = papan.karakterTampilan(i, j, state.pos, state.checkpointBerikutnya);

                Label label = new Label(String.valueOf(c));
                label.getStyleClass().add("board-cell");

                switch(c){
                    case 'X' :
                        label.getStyleClass().add("tembok-cell");
                        break;
                    case 'Z':
                        label.getStyleClass().add("mulai-cell");
                        break;
                    case '*':
                        label.getStyleClass().add("jalan-cell");
                        break;
                    case 'O':
                        label.getStyleClass().add("tujuan-cell");
                        break;
                    case 'L':
                        label.getStyleClass().add("lava-cell");
                        break;
                }

                label.setMinSize(40, 40);
                label.setAlignment(Pos.CENTER);

                StackPane cell = new StackPane(label);

                grid.add(cell, j, i);
            }
        }
        return grid;
    }
}