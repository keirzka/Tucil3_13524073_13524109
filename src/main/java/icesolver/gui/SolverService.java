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
                case "H1 : Heuristic Manhattan" -> new HeuristicManhattan();
                case "H2 : Heuristic Checkpoint" -> new HeuristicCheckpoint();
                case "H3 : Heuristic Advanced" -> new HeuristicAdvanced();
                default -> h;
            };
        }

        // Pilihan algoritma
        solver = switch (algoritma) {
            case "Uniform Cost Search (UCS)" -> new UCSSolver();
            case "Greedy Best First Searc (GBFS)" -> new GBFSSolver(h);
            case "A Star (A*)" -> new AStarSolver(h);
            case "Breadth First Search (BFS)" -> new BFSSolver();
            default -> null;
        };

        // Jalankan solver
        return solver.selesaikan(papan);
    }

    public Solver getSolver(){
        return solver;
    }
}