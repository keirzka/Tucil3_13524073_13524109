package icesolver.output;

import icesolver.model.*;
import icesolver.solver.*;
import java.util.List;

public class ResultPrinter {
    private final BoardPrinter boardPrinter = new BoardPrinter();

    public void printSemuaResult(Board papan, Solver.HasilSolusi result){
        List<State> riwayat = result.riwayatStatus;
        List<Direction> jalur = result.jalur;

        System.out.println("Initial");
        boardPrinter.printBoardDenganState(papan, riwayat.get(0));
        System.out.println("");

        int len = jalur.size();
        for(int i = 0; i < len; i++){
            System.out.println("Step " + (i + 1) + " : " + jalur.get(i).simbol);
            boardPrinter.printBoardDenganState(papan, riwayat.get(i + 1));
            System.out.println("");
        }
    }

    public void printStepResult(Board papan, Solver.HasilSolusi result, int step){
        List<State> riwayat = result.riwayatStatus;
        List<Direction> jalur = result.jalur;

        if (step == 0) {
            System.out.println("Initial");
        } else {
            System.out.println("Step " + step + " : " + jalur.get(step - 1).simbol);
        }
        boardPrinter.printBoardDenganState(papan, riwayat.get(step));

    }

}
