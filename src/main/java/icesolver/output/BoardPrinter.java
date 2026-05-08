package icesolver.output;
import icesolver.model.Board;
import icesolver.model.State;

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
        for (int i = 0; i < papan.baris; i++) {
            for (int j = 0; j < papan.kolom; j++) {
                System.out.print(papan.karakterTampilan(i, j, state.pos, state.checkpointBerikutnya));
            }
            System.out.println();
        }
    }

    public String boardToString(Board papan, State state){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < papan.baris; i++){
            for(int j= 0; j < papan.kolom; j++){
                sb.append(papan.karakterTampilan(i, j, state.pos, state.checkpointBerikutnya));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
