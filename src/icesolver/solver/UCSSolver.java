package icesolver.solver;
import icesolver.model.*;
import icesolver.movement.*;
import java.util.*;

public class UCSSolver extends Solver {
    @Override
    public HasilSolusi selesaikan(Board papan) {
        PriorityQueue<Node> antrian = new PriorityQueue<>();
        Map<State, Integer> dikunjungi = new HashMap<>();
        State statusAwal = new State(papan.mulai, 0);
        Node nodeAwal = new Node(statusAwal);
        antrian.add(nodeAwal);
        int iterasi = 0;
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
                return new HasilSolusi(true, jalur, riwayat, saat.biayaG, iterasi);
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
                Node anak = new Node(statusBaru, biayaBaru, biayaBaru, saat, arah);
                antrian.add(anak);
            }
        }
        return HasilSolusi.tidakAdaSolusi(iterasi);
    }

    @Override
    public String namaAlgoritma() { 
        return "UCS"; 
    }
}