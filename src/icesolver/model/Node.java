package icesolver.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Node implements Comparable<Node> {
    public final State status;
    public final int biayaG;
    public final double biayaF;
    public final Node induk;
    public final Direction arahDariInduk;
    public Node(State status) {
        this.status = status;
        this.biayaG = 0;
        this.biayaF = 0;
        this.induk = null;
        this.arahDariInduk = null;
    }

    public Node(State status, int biayaG, double biayaF, Node induk, Direction arahDariInduk) {
        this.status = status;
        this.biayaG = biayaG;
        this.biayaF = biayaF;
        this.induk = induk;
        this.arahDariInduk = arahDariInduk;
    }

    public List<Direction> rekonstruksiJalur() {
        List<Direction> jalur = new ArrayList<>();
        Node saat = this;
        while (saat.arahDariInduk != null) {
            jalur.add(saat.arahDariInduk);
            saat = saat.induk;
        }
        Collections.reverse(jalur);
        return jalur;
    }

    public List<State> rekonstruksiStatus() {
        List<State> daftarStatus = new ArrayList<>();
        Node saat = this;
        while (saat != null) {
            daftarStatus.add(saat.status);
            saat = saat.induk;
        }
        Collections.reverse(daftarStatus);
        return daftarStatus;
    }

    @Override
    public int compareTo(Node lain) {
        return Double.compare(this.biayaF, lain.biayaF);
    }

    @Override
    public String toString() {
        return "Node{status=" + status + ", g=" + biayaG + ", f=" + biayaF + "}";
    }
}