package icesolver.parser;
import icesolver.model.*;
import java.io.*;
import java.util.*;

public class InputParser {
    public static Board baca(String jalurFile) throws IOException {
        List<String> baris = bacaBaris(jalurFile);
        if (baris.isEmpty()) throw new IOException("File kosong: " + jalurFile);
        String[] dimensi = baris.get(0).trim().split("\\s+");
        if (dimensi.length < 2) throw new IOException("Baris pertama harus berupa 'N M'");
        int N, M;
        try {
            N = Integer.parseInt(dimensi[0]);
            M = Integer.parseInt(dimensi[1]);
        } catch (NumberFormatException e) {
            throw new IOException("Dimensi tidak valid pada baris 1: " + baris.get(0));
        }
        if (N <= 0 || M <= 0) throw new IOException("Dimensi harus positif");
        int jumlahBarisExpected = 1 + N + N;
        if (baris.size() < jumlahBarisExpected) {
            throw new IOException("File terlalu pendek: diharapkan " + jumlahBarisExpected + " baris, ditemukan " + baris.size());
        }
        Cell[][] grid = new Cell[N][M];
        Position mulai = null, tujuan = null;
        Map<Integer, Position> petaCheckpoint = new TreeMap<>();
        for (int b = 0; b < N; b++) {
            String barisGrid = baris.get(1 + b);
            if (barisGrid.length() < M) {
                throw new IOException("Baris " + b + " memiliki " + barisGrid.length() + " karakter, diharapkan " + M);
            }
            for (int k = 0; k < M; k++) {
                char ch = barisGrid.charAt(k);
                Cell tile;
                try {
                    tile = Cell.dariChar(ch);
                } catch (IllegalArgumentException e) {
                    throw new IOException("Karakter tidak dikenal '" + ch + "' pada baris=" + b + " kolom=" + k);
                }
                grid[b][k] = tile;
                if (tile == Cell.MULAI) {
                    if (mulai != null) throw new IOException("Ditemukan lebih dari satu tile MULAI (Z)");
                    mulai = new Position(b, k);
                } else if (tile == Cell.TUJUAN) {
                    if (tujuan != null) throw new IOException("Ditemukan lebih dari satu tile TUJUAN (O)");
                    tujuan = new Position(b, k);
                } else if (tile.isCheckpoint()) {
                    int idx = tile.indeksCheckpoint();
                    if (petaCheckpoint.containsKey(idx)) throw new IOException("Checkpoint duplikat '" + ch + "' pada papan");
                    petaCheckpoint.put(idx, new Position(b, k));
                }
            }
        }
        int[][] biaya = new int[N][M];
        for (int b = 0; b < N; b++) {
            String[] token = baris.get(1 + N + b).trim().split("\\s+");
            if (token.length < M) { 
                throw new IOException("Baris biaya " + b + " memiliki " + token.length + " nilai, diharapkan " + M);
            }
            for (int k = 0; k < M; k++) {
                try {
                    biaya[b][k] = Integer.parseInt(token[k]);
                } catch (NumberFormatException e) {
                    throw new IOException("Biaya tidak valid pada baris=" + b + " kolom=" + k);
                }
            }
        }
        Position[] checkpoints = buatArrayCheckpoint(petaCheckpoint);
        if (mulai == null) throw new IOException("Tile MULAI (Z) tidak ditemukan");
        if (tujuan == null) throw new IOException("Tile TUJUAN (O) tidak ditemukan");
        return new Board(grid, biaya, mulai, tujuan, checkpoints);
    }

    private static Position[] buatArrayCheckpoint(Map<Integer, Position> peta) throws IOException {
        if (peta.isEmpty()) return new Position[0];
        int idxMaks = Collections.max(peta.keySet());
        for (int i = 0; i <= idxMaks; i++) {
            if (!peta.containsKey(i)) {
                throw new IOException("Checkpoint '" + i + "' tidak ditemukan — indeks harus berurutan dari 0");
            }
        }
        Position[] arr = new Position[idxMaks + 1];
        for (Map.Entry<Integer, Position> e : peta.entrySet()) {
            arr[e.getKey()] = e.getValue();
        }
        return arr;
    }

    private static List<String> bacaBaris(String jalur) throws IOException {
        List<String> baris = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(jalur))) {
            String baris1;
            while ((baris1 = br.readLine()) != null) {
                baris.add(baris1);
            }
        }
        return baris;
    }
}