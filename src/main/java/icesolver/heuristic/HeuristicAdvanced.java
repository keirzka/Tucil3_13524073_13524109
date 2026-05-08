package icesolver.heuristic;
import icesolver.model.*;

public class HeuristicAdvanced implements Heuristic {
    @Override
    public double perkiraan(State status, Board papan) {
        int biayaMin = hitungBiayaTileMin(papan);
        double total = 0;
        Position posisiSaat = status.pos;
        for (int i = status.checkpointBerikutnya; i < papan.totalCheckpoints(); i++) {
            Position cp = papan.checkpoints[i];
            total += posisiSaat.jarakManhattan(cp);
            posisiSaat = cp;
        }
        total += posisiSaat.jarakManhattan(papan.tujuan);
        return total * biayaMin;
    }

    private int hitungBiayaTileMin(Board papan) {
        int min = Integer.MAX_VALUE;
        for (int b = 0; b < papan.baris; b++) {
            for (int k = 0; k < papan.kolom; k++) {
                Cell tile = papan.tileAt(b, k);
                if (tile != Cell.TEMBOK && tile != Cell.LAVA) {
                    int biaya = papan.biayaAt(b, k);
                    if (biaya > 0 && biaya < min) min = biaya;
                }
            }
        }
        return (min == Integer.MAX_VALUE) ? 1 : min;
    }

    @Override public String label() { 
        return "H3"; 
    }
    @Override public String nama()  { 
        return "Cost-Weighted Chain Manhattan"; 
    }
}