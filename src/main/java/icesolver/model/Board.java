package icesolver.model;

public final class Board {
    public final int baris;
    public final int kolom;
    private final Cell[][] grid;
    private final int[][] biaya;
    public final Position mulai;
    public final Position tujuan;
    public final Position[] checkpoints;
    public Board(Cell[][] grid, int[][] biaya, Position mulai, Position tujuan, Position[] checkpoints) {
        this.baris = grid.length;
        this.kolom = grid[0].length;
        this.grid = grid;
        this.biaya = biaya;
        this.mulai = mulai;
        this.tujuan = tujuan;
        this.checkpoints = checkpoints;
    }

    public Cell tileAt(int b, int k) {
        return grid[b][k];
    }

    public Cell tileAt(Position p) {
        return grid[p.baris][p.kolom];
    }

    public int biayaAt(int b, int k) {
        return biaya[b][k];
    }

    public int biayaAt(Position p) {
        return biaya[p.baris][p.kolom];
    }

    public boolean dalamBatas(int b, int k) {
        return b >= 0 && b < baris && k >= 0 && k < kolom;
    }

    public boolean dalamBatas(Position p) {
        return dalamBatas(p.baris, p.kolom);
    }

    public int totalCheckpoints() {
        return checkpoints.length;
    }

    public char karakterTampilan(int b, int k, Position posAktor, int checkpointBerikutnya) {
        Position p = new Position(b, k);
        if (p.equals(posAktor)) return 'Z';
        Cell c = grid[b][k];

        if(c == Cell.MULAI){
            return '*';
        }

        if (c.isCheckpoint() && c.indeksCheckpoint() < checkpointBerikutnya) return '*';
        return c.simbol;
    }

    public void cetak(Position posAktor, int checkpointBerikutnya) {
        for (int b = 0; b < baris; b++) {
            for (int k = 0; k < kolom; k++) {
                System.out.print(karakterTampilan(b, k, posAktor, checkpointBerikutnya));
            }
            System.out.println();
        }
    }
}