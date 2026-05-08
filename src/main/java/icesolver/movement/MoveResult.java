package icesolver.movement;
import icesolver.model.Position;

public final class MoveResult {
    public enum Status {
        BERHASIL,
        GAGAL,
        TIDAK_BERGERAK
    }
    public final Status status;
    public final Position posLanding;
    public final int biayaGerak;
    public final int tileDilalui;
    private MoveResult(Status status, Position posLanding, int biayaGerak, int tileDilalui) {
        this.status = status;
        this.posLanding = posLanding;
        this.biayaGerak = biayaGerak;
        this.tileDilalui = tileDilalui;
    }
    public static MoveResult berhasil(Position landing, int biaya, int tile) {
        return new MoveResult(Status.BERHASIL, landing, biaya, tile);
    }
    public static MoveResult gagal() {
        return new MoveResult(Status.GAGAL, null, 0, 0);
    }
    public static MoveResult tidakBergerak() {
        return new MoveResult(Status.TIDAK_BERGERAK, null, 0, 0);
    }
    public boolean isberhasil() { 
        return status == Status.BERHASIL;
    }
    public boolean isgagal() { 
        return status == Status.GAGAL;
    }
    public boolean istidakBergerak()  { 
        return status == Status.TIDAK_BERGERAK;
    }
    @Override
    public String toString() {
        return "HasilGerak{" + status + ", posLanding=" + posLanding + ", biaya=" + biayaGerak + "}";
    }
}