package icesolver.output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import icesolver.model.Board;
import icesolver.solver.Solver;


public class FileOutput {
    public void simpanHasil(Board papan, Solver solver, Solver.HasilSolusi result, String fileName) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        BoardPrinter printer = new BoardPrinter();

        writer.write("=========== HASIL PENCARIAN ===========");
        writer.newLine();

        writer.write("Algoritma : " + solver.namaAlgoritma());
        writer.newLine();

        writer.write("Hasil pencarian jalur : " + result.stringJalur());
        writer.newLine();

        writer.write("Total biaya : " + result.totalBiaya);
        writer.newLine();

        writer.write("Jumlah iterasi : " + result.iterasi);
        writer.newLine();

        writer.write("Waktu eksekusi : " + result.waktuEksekusiMs);
        writer.newLine();
        writer.newLine();

        writer.write("=========== VISUALISASI PENCARIAN ===========");
        writer.newLine();
        writer.newLine();

        for(int i = 0; i < result.riwayatStatus.size(); i++){
            if(i == 0){
                writer.write("Initial");
            }
            else{
                writer.write("Step " + i + " : " + result.jalur.get(i - 1).simbol);
            }

            writer.newLine();
            writer.write(printer.boardToString(papan, result.riwayatStatus.get(i)));
            writer.newLine();

        }

        writer.close();

    }
}
