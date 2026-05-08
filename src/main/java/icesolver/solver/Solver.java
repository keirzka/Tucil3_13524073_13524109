package icesolver.solver;
import icesolver.model.*;
import java.util.List;

public abstract class Solver {
    public static final class HasilSolusi {
        public final boolean terpecahkan;
        public final List<Direction> jalur;
        public final List<State> riwayatStatus;
        public final int totalBiaya;
        public final int iterasi;
        public long waktuEksekusiMs;
        public HasilSolusi(boolean terpecahkan, List<Direction> jalur, List<State> riwayatStatus, int totalBiaya, int iterasi) {
            this.terpecahkan = terpecahkan;
            this.jalur = jalur;
            this.riwayatStatus = riwayatStatus;
            this.totalBiaya = totalBiaya;
            this.iterasi = iterasi;
        }
        public String stringJalur() {
            if (jalur == null || jalur.isEmpty()) return "";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < jalur.size(); i++) {
                sb.append(jalur.get(i).simbol);
                if(i < jalur.size() - 1){
                    sb.append("-");
                }
            }
            return sb.toString();
        }
        public static HasilSolusi tidakAdaSolusi(int iterasi) {
            return new HasilSolusi(false, null, null, 0, iterasi);
        }
    }
    public abstract HasilSolusi selesaikan(Board papan);
    public abstract String namaAlgoritma();
}