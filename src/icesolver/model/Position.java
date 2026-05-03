package icesolver.model;
import java.util.Objects;

public final class Position {
    public final int baris;
    public final int kolom;
    public Position(int baris, int kolom) {
        this.baris = baris;
        this.kolom = kolom;
    }

    public Position gerak(Direction d) {
        return new Position(baris + d.dBaris, kolom + d.dKolom);
    }

    public int jarakManhattan(Position lain) {
        return Math.abs(baris - lain.baris) + Math.abs(kolom - lain.kolom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position p = (Position) o;
        return baris == p.baris && kolom == p.kolom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(baris, kolom);
    }

    @Override
    public String toString() {
        return "(" + baris + "," + kolom + ")";
    }
}