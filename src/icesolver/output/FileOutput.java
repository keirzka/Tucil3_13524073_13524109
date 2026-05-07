package icesolver.output;

import icesolver.solver.Solver;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutput {
    public void simpanHasil(Solver solver, Solver.HasilSolusi result, String fileName) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        writer.write("=========== HASIL PENCARIAN ===========");
        writer.newLine();

        writer.write("Algoritma : " + solver.namaAlgoritma());
        writer.newLine();

        writer.write("Hasil pencarian jalur : " + result.stringJalur());
        writer.newLine();

        writer.write("Total biaya : " + result.totalBiaya);
        writer.newLine();

        writer.write("Jumlahi terasi : " + result.iterasi);
        writer.newLine();

        writer.write("Waktu eksekusi : " + result.waktuEksekusiMs);
        writer.newLine();

        writer.close();

    }
}
