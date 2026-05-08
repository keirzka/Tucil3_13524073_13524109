package icesolver.heuristic;
import icesolver.model.*;

public class HeuristicCheckpoint implements Heuristic {
    @Override
    public double perkiraan(State status, Board papan) {
        double total = 0;
        Position posisiSaat = status.pos;
        for (int i = status.checkpointBerikutnya; i < papan.totalCheckpoints(); i++) {
            Position cp = papan.checkpoints[i];
            total += posisiSaat.jarakManhattan(cp);
            posisiSaat = cp;
        }
        total += posisiSaat.jarakManhattan(papan.tujuan);
        return total;
    }
    @Override public String label() { 
        return "H2"; 
    }
    @Override public String nama()  { 
        return "Checkpoint Chain Manhattan"; 
    }
}