package icesolver.model;

public enum Cell {
    JALAN('*'),
    TEMBOK('X'),
    LAVA('L'),
    MULAI('Z'),
    TUJUAN('O'),
    NUM_0('0'), NUM_1('1'), NUM_2('2'), NUM_3('3'), NUM_4('4'),
    NUM_5('5'), NUM_6('6'), NUM_7('7'), NUM_8('8'), NUM_9('9');
    public final char simbol;

    Cell(char simbol) {
        this.simbol = simbol;
    }

    public static Cell dariChar(char c) {
        for (Cell tile : values()) {
            if (tile.simbol == c) return tile;
        }
        throw new IllegalArgumentException("Simbol tile tidak dikenal: '" + c + "'");
    }

    public boolean isCheckpoint() {
        return this == NUM_0 || this == NUM_1 || this == NUM_2 || this == NUM_3 || this == NUM_4 || this == NUM_5 || this == NUM_6 || this == NUM_7 || this == NUM_8 || this == NUM_9;
    }

    public int indeksCheckpoint() {
        if (!isCheckpoint()) return -1;
        return simbol - '0';
    }

    public boolean bisaDilewati() {
        return this != TEMBOK;
    }

    @Override
    public String toString() {
        return String.valueOf(simbol);
    }
}