package icesolver.solver;
import icesolver.heuristic.*;
import icesolver.model.*;
import icesolver.movement.*;
import java.util.*;

public class AStarSolver extends Solver {
    private final Heuristic heuristic;

    public AStarSolver(Heuristic heuristic){
        this.heuristic = heuristic;
    }

    @Override
    public HasilSolusi selesaikan(Board papan) {
        PriorityQueue<Node> antrian = new PriorityQueue<>();
        Map<State, Integer> dikunjungi = new HashMap<>();
        State statusAwal = new State(papan.mulai, 0);
        Node nodeAwal = new Node(statusAwal, 0, this.heuristic.perkiraan(statusAwal, papan), null, null);
        antrian.add(nodeAwal);
        int iterasi = 0;

        long start = System.currentTimeMillis();

        while (!antrian.isEmpty()) {
            Node saat = antrian.poll();
            State status = saat.status;
            if (dikunjungi.containsKey(status) && dikunjungi.get(status) <= saat.biayaG) {
                continue;
            }
            dikunjungi.put(status, saat.biayaG);
            iterasi++;
            if (status.isTujuan(papan)) {
                List<Direction> jalur = saat.rekonstruksiJalur();
                List<State> riwayat = saat.rekonstruksiStatus();

                long end = System.currentTimeMillis();
                long duration = end - start;
                HasilSolusi result = new HasilSolusi(true, jalur, riwayat, saat.biayaG, iterasi);
                result.waktuEksekusiMs = duration;
                return result;
            }
            for (Direction arah : Direction.values()) {
                MoveResult hasilGerak = MovementEngine.geser(papan, status.pos, arah, status);
                if (!hasilGerak.isberhasil()) continue;
                State statusBaru = MovementEngine.terapkanGerak(papan, status, arah);
                if (statusBaru == null) continue;
                int biayaBaru = saat.biayaG + hasilGerak.biayaGerak;
                if (dikunjungi.containsKey(statusBaru) && dikunjungi.get(statusBaru) <= biayaBaru) {
                    continue;
                }
                double biayaF = biayaBaru + this.heuristic.perkiraan(statusBaru, papan);
                Node anak = new Node(statusBaru, biayaBaru, biayaF, saat, arah);
                antrian.add(anak);
            }
        }

        long end = System.currentTimeMillis();
        HasilSolusi gagal = HasilSolusi.tidakAdaSolusi(iterasi);
        gagal.waktuEksekusiMs = end - start;
        
        return gagal;
    }

    @Override
    public String namaAlgoritma() { 
        return "A*"; 
    }
}