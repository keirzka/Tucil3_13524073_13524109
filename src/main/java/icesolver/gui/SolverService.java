package icesolver.gui;

import icesolver.heuristic.*;
import icesolver.model.Board;
import icesolver.parser.InputParser;
import icesolver.solver.*;

import java.io.IOException;

public class SolverService {
    private Solver solver;

    public Solver.HasilSolusi solve(String filePath, String algoritma, String heuristic) throws IOException {
        // Baca papan
        Board papan = InputParser.baca(filePath);

        // Pilihan heuristic
        Heuristic h = null;

        if (heuristic != null) {
            h = switch (heuristic) {
                case "H1" -> new HeuristicManhattan();
                case "H2" -> new HeuristicCheckpoint();
                case "H3" -> new HeuristicAdvanced();
                default -> h;
            };
        }

        // Pilihan algoritma
        solver = switch (algoritma) {
            case "UCS" -> new UCSSolver();
            case "GBFS" -> new GBFSSolver(h);
            case "A*" -> new AStarSolver(h);
            case "BFS" -> new BFSSolver();
            default -> null;
        };

        // Jalankan solver
        return solver.selesaikan(papan);
    }

    public Solver getSolver(){
        return solver;
    }
}