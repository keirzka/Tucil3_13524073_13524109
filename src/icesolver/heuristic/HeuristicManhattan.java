package icesolver.heuristic;
import icesolver.model.*;

public class HeuristicManhattan implements Heuristic {
    @Override
    public double perkiraan(State status, Board papan) {
        if (!status.semuaCheckpointSelesai(papan)) {
            Position cpSelanjutnya = papan.checkpoints[status.checkpointBerikutnya];
            double keCp = status.pos.jarakManhattan(cpSelanjutnya);
            double cpKeTujuan = cpSelanjutnya.jarakManhattan(papan.tujuan);
            return keCp + cpKeTujuan;
        }
        return status.pos.jarakManhattan(papan.tujuan);
    }
    @Override public String label() { 
        return "H1"; 
    }
    @Override public String nama()  { 
        return "Manhattan Distance"; 
    }
}