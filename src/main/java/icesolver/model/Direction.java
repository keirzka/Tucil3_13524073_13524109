package icesolver.model;

public enum Direction {
    ATAS('U', -1, 0),
    BAWAH('D', 1, 0),
    KIRI('L', 0, -1),
    KANAN('R', 0, 1);
    public final char simbol;
    public final int dBaris;
    public final int dKolom;

    Direction(char simbol, int dBaris, int dKolom) {
        this.simbol = simbol;
        this.dBaris = dBaris;
        this.dKolom = dKolom;
    }

    public static Direction dariChar(char c) {
        for (Direction d : values()) {
            if (d.simbol == c) return d;
        }
        throw new IllegalArgumentException("Arah tidak dikenal: " + c);
    }

    @Override
    public String toString() {
        return String.valueOf(simbol);
    }
}