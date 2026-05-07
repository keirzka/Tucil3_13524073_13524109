package icesolver.output;
import icesolver.model.*;

public class BoardPrinter {
    public void printBoard(Board papan){
        for(int i = 0; i < papan.baris; i++){
            for(int j = 0; j < papan.kolom; j++){
                System.out.print(papan.tileAt(i, j));
            }
            System.out.println("");
        }
    }

    public void printBoardDenganState(Board papan, State state){
        for (int b = 0; b < papan.baris; b++) {
            for (int k = 0; k < papan.kolom; k++) {
                System.out.print(papan.karakterTampilan(b, k, state.pos, state.checkpointBerikutnya));
            }
            System.out.println();
        }
    }
}
