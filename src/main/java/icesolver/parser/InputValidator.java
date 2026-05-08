package icesolver.parser;
import icesolver.model.*;
import java.util.ArrayList;
import java.util.List;

public class InputValidator {
    public static class HasilValidasi {
        public final boolean valid;
        public final List<String> kesalahan;
        public final List<String> peringatan;
        public HasilValidasi(List<String> kesalahan, List<String> peringatan) {
            this.kesalahan = kesalahan;
            this.peringatan = peringatan;
            this.valid = kesalahan.isEmpty();
        }
    }
    public static HasilValidasi validasi(Board papan) {
        List<String> kesalahan = new ArrayList<>();
        List<String> peringatan = new ArrayList<>();
        Cell tileMulai = papan.tileAt(papan.mulai);
        if (tileMulai == Cell.TEMBOK || tileMulai == Cell.LAVA) {
            kesalahan.add("Posisi MULAI " + papan.mulai + " berada di tile yang tidak bisa dilewati: " + tileMulai);
        }
        Cell tileTujuan = papan.tileAt(papan.tujuan);
        if (tileTujuan == Cell.TEMBOK || tileTujuan == Cell.LAVA) {
            kesalahan.add("Posisi TUJUAN " + papan.tujuan + " berada di tile yang tidak bisa dilewati: " + tileTujuan);
        }
        for (int b = 0; b < papan.baris; b++) {
            for (int k = 0; k < papan.kolom; k++) {
                int biaya = papan.biayaAt(b, k);
                if (biaya < 0) {
                    kesalahan.add("Biaya negatif " + biaya + " pada (" + b + "," + k + ")");
                }
            }
        }
        boolean peringatanBorder = false;
        for (int k = 0; k < papan.kolom; k++) {
            if (papan.tileAt(0, k) != Cell.TEMBOK) peringatanBorder = true;
            if (papan.tileAt(papan.baris - 1, k) != Cell.TEMBOK) peringatanBorder = true;
        }
        for (int b = 0; b < papan.baris; b++) {
            if (papan.tileAt(b, 0) != Cell.TEMBOK) peringatanBorder = true;
            if (papan.tileAt(b, papan.kolom - 1) != Cell.TEMBOK) peringatanBorder = true;
        }
        if (peringatanBorder) {
            peringatan.add("Border papan mengandung tile non-TEMBOK. Aktor bisa jatuh dari tepi papan.");
        }
        for (int i = 0; i < papan.totalCheckpoints(); i++) {
            Position cp = papan.checkpoints[i];
            Cell tileCp = papan.tileAt(cp);
            if (tileCp == Cell.TEMBOK || tileCp == Cell.LAVA) {
                kesalahan.add("Checkpoint " + i + " pada " + cp + " berada di tile yang tidak bisa dilewati");
            }
        }
        if (papan.baris < 3 || papan.kolom < 3) {
            peringatan.add("Papan sangat kecil (" + papan.baris + "x" + papan.kolom + ").");
        }
        return new HasilValidasi(kesalahan, peringatan);
    }
}