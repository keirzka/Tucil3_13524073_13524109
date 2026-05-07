package icesolver.solver;
import icesolver.model.*;
import icesolver.movement.*;
import java.util.*;

public class BFSSolver extends Solver {
    @Override
    public HasilSolusi selesaikan(Board papan) {
        Queue<Node> antrian = new LinkedList<>();
        Set<State> dikunjungi = new HashSet<>();
        State statusAwal = new State(papan.mulai, 0);
        Node nodeAwal = new Node(statusAwal);
        antrian.add(nodeAwal);
        int iterasi = 0;

        long start = System.currentTimeMillis();

        while (!antrian.isEmpty()) {
            Node saat = antrian.poll();
            State status = saat.status;
            if (dikunjungi.contains(status)) {
                continue;
            }
            dikunjungi.add(status);
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
                if (dikunjungi.contains(statusBaru)) {
                    continue;
                }
                Node anak = new Node(statusBaru, biayaBaru, 0, saat, arah);
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
        return "BFS"; 
    }
}