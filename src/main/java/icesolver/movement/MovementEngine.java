package icesolver.movement;
import icesolver.model.*;

public class MovementEngine {
    public static MoveResult geser(Board papan, Position dari, Direction arah, State status) {
        int baris = dari.baris;
        int kolom = dari.kolom;
        int totalBiaya = 0;
        int tileDilalui = 0;
        int cpBerikutnya = status.checkpointBerikutnya;
        Position berikutnya = new Position(baris + arah.dBaris, kolom + arah.dKolom);
        if (!papan.dalamBatas(berikutnya)) {
            return MoveResult.gagal();
        }
        Cell tileBerikutnya = papan.tileAt(berikutnya);
        if (tileBerikutnya == Cell.TEMBOK) {
            return MoveResult.tidakBergerak();
        }
        boolean berhentiKarenaTembok = false;
        while (true) {
            baris += arah.dBaris;
            kolom += arah.dKolom;
            if (!papan.dalamBatas(baris, kolom)) {
                return MoveResult.gagal();
            }
            Cell tile = papan.tileAt(baris, kolom);
            if (tile == Cell.TEMBOK) {
                baris -= arah.dBaris;
                kolom -= arah.dKolom;
                berhentiKarenaTembok = true;
                break;
            }
            if (tile == Cell.LAVA) {
                return MoveResult.gagal();
            }
            totalBiaya += papan.biayaAt(baris, kolom);
            tileDilalui++;
            if (tile.isCheckpoint()) {
                int idxCp = tile.indeksCheckpoint();
                if (idxCp == cpBerikutnya) {
                    cpBerikutnya++;
                }
                Position depan = new Position(baris + arah.dBaris, kolom + arah.dKolom);
                if (!papan.dalamBatas(depan)) {
                    return MoveResult.gagal();
                }
                if (papan.tileAt(depan) == Cell.TEMBOK) {
                    berhentiKarenaTembok = true;
                    break;
                }
                continue;
            }
            Position depan = new Position(baris + arah.dBaris, kolom + arah.dKolom);
            if (!papan.dalamBatas(depan)) {
                return MoveResult.gagal();
            }
            if (papan.tileAt(depan) == Cell.TEMBOK) {
                berhentiKarenaTembok = true;
                break;
            }
        }
        Position landing = new Position(baris, kolom);
        if (!berhentiKarenaTembok) {
            return MoveResult.gagal();
        }
        Cell tileLanding = papan.tileAt(landing);
        if (tileLanding.isCheckpoint()) {
            int idxCp = tileLanding.indeksCheckpoint();
            if (idxCp > status.checkpointBerikutnya) {
                return MoveResult.gagal();
            }
        }
        if (landing.equals(dari)) {
            return MoveResult.tidakBergerak();
        }
        return MoveResult.berhasil(landing, totalBiaya, tileDilalui);
    }

    public static State terapkanGerak(Board papan, State saat, Direction arah) {
        MoveResult hasil = geser(papan, saat.pos, arah, saat);
        if (!hasil.isberhasil()) return null;
        Position landing = hasil.posLanding;
        int cpBaru = hitungUlangCP(papan, saat.pos, arah, saat.checkpointBerikutnya, landing);
        return new State(landing, cpBaru);
    }

    private static int hitungUlangCP(Board papan, Position dari, Direction arah, int cpAwal, Position landing) {
        int cpBerikutnya = cpAwal;
        int baris = dari.baris;
        int kolom = dari.kolom;
        while (true) {
            baris += arah.dBaris;
            kolom += arah.dKolom;
            Position saat = new Position(baris, kolom);
            Cell tile = papan.tileAt(saat);
            if (tile.isCheckpoint()) {
                if (tile.indeksCheckpoint() == cpBerikutnya) {
                    cpBerikutnya++;
                }
            }
            if (saat.equals(landing)) break;
        }
        return cpBerikutnya;
    }
}