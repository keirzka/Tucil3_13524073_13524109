package icesolver;

import icesolver.heuristic.*;
import icesolver.model.*;
import icesolver.output.*;
import icesolver.parser.*;
import icesolver.solver.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String args[]){
        Scanner input = new Scanner(System.in);

        System.out.print(">>> Masukkan file input (.txt) : ");
        String fileName = input.nextLine();
        
        Path path;

        if(!fileName.endsWith(".txt")){
            path = Paths.get("test/" + fileName + ".txt");
        }
        else{
            path = Paths.get("test/" + fileName);
        }

        if(!Files.exists(path)){
            System.out.println("File " + path + " tidak ditemukan!!");
            return;
        }

        System.out.println("Pilihan Algoritma yang Tersedia:");
        System.out.println("- UCS");
        System.out.println("- GBFS");
        System.out.println("- A*");
        System.out.println("- BFS");

        System.out.print(">>> Masukkan pilihan algoritma : ");
        String pilihanAlgoritma = input.nextLine();

        // Validation untuk pilihan algoritma
        while(!pilihanAlgoritma.equals("UCS") && !pilihanAlgoritma.equals("A*") && !pilihanAlgoritma.equals("GBFS") && !pilihanAlgoritma.equals("BFS")){
            System.out.println("Piliha algoritma tidak valid.");
            System.out.print(">>> Masukkan pilihan algoritma : ");
            pilihanAlgoritma = input.nextLine();
        }

        String pilihanHeuristic = null;
        if(pilihanAlgoritma.equals("GBFS") || pilihanAlgoritma.equals("A*")){
            System.out.println("Pilihan Heuristic yang Tersedia :");
            System.out.println("- H1");
            System.out.println("- H2");
            System.out.println("- H3");

            System.out.print(">>> Masukkan pilihan heuristic : ");
            pilihanHeuristic = input.nextLine();
            // Validation untuk pilihan heuristic
            while(!pilihanHeuristic.equals("H1") && !pilihanHeuristic.equals("H2") && !pilihanHeuristic.equals("H3")){
                System.out.println("Piliha heuristic tidak valid.");
                System.out.print(">>> Masukkan pilihan heuristic : ");
                pilihanHeuristic = input.nextLine();
            }
        }
        

        System.out.println("\n========= Visualisasi Papan Awal =========");

        Board papanInput = null;
        BoardPrinter printer = new BoardPrinter();

        try{
            papanInput = InputParser.baca(path.toString());
        } catch(IOException e){
            System.out.println("Papan tidak ditemukan!");
            return;
        }

        if(papanInput != null){
            printer.printBoard(papanInput);
        }

        System.out.println("Memulai proses pencarian jalur Ice Sliding dengan algoritma " + pilihanAlgoritma + "...");

        Solver solver = null;
        Heuristic h = null;

        // Pilihan Heuristic
        if(pilihanHeuristic != null){
            if(pilihanHeuristic.equals("H1")) {
                h = new HeuristicManhattan();
            }
            else if(pilihanHeuristic.equals("H2")){
                h = new HeuristicCheckpoint();
            }
            else if(pilihanHeuristic.equals("H3")){
                h = new HeuristicAdvanced();
            }
        }

        // Pilihan Algoritma
        if(pilihanAlgoritma.equals("UCS")){
            solver = new UCSSolver();
        }
        else if(pilihanAlgoritma.equals("GBFS")){
            solver = new GBFSSolver(h);
        }
        else if(pilihanAlgoritma.equals("A*")){
            solver = new AStarSolver(h);
        }
        else if(pilihanAlgoritma.equals("BFS")){
            solver = new BFSSolver();
        }

        Solver.HasilSolusi result = solver.selesaikan(papanInput);

        if(result == null || !result.terpecahkan){
            System.out.println("Tidak ada solusi yang ditemukan.");
            return;
        }
        
        System.out.println("Solusi yang ditemukan : " + result.stringJalur());
        System.out.println("Cost dari solusi : " + result.totalBiaya);
        System.out.println("\n========= Visualisasi Path Finding =========\n");

        ResultPrinter printResult = new ResultPrinter();
        printResult.printSemuaResult(papanInput, result);

        System.out.println("Waktu eksekusi : " + String.valueOf(result.waktuEksekusiMs) + " ms");
        System.out.println("Banyak iterasi yang dilakukan : " + result.iterasi + " iterasi");

        while(true){
            System.out.print("Apakah Anda ingin melakukan playback? (Ya / Tidak) : ");
            String pilihanPlayBack = input.nextLine();

            // Validasi pilihan
            while(!pilihanPlayBack.equals("Ya") && !pilihanPlayBack.equals("Tidak")){
                System.out.println("Masukkan tidak valid");
                System.out.print("Apakah Anda ingin melakukan playback? (Ya / Tidak) : ");
                pilihanPlayBack = input.nextLine();
            }

            if(pilihanPlayBack.equals("Tidak")){
                break;
            }
            if(pilihanPlayBack.equals("Ya")){
                System.out.print("Pada step berapa Anda ingin melakukan playback? (0 - " + result.jalur.size() + ") : ");
                int index = input.nextInt();
                input.nextLine();
                printResult.printStepResult(papanInput, result, index);
            }
        }
        
        System.out.print("Apakah Anda ingin menyimpan hasil pencarian program? (Ya / Tidak) : ");
        String save = input.nextLine();

        while(!save.equals("Ya") && !save.equals("Tidak")){
            System.out.println("Masukkan tidak valid");
            System.out.print("Apakah Anda ingin menyimpan hasil pencarian program? (Ya / Tidak) : ");
            save = input.nextLine();
        }

        if(save.equals("Ya")){
            System.out.print("Masukkan nama file format txt : ");
            String pathSave = input.nextLine();
            if(!pathSave.endsWith(".txt")){
                pathSave += ".txt";
            }

            pathSave = "output/" + pathSave;
            try{
                FileOutput fileOutput = new FileOutput();
                fileOutput.simpanHasil(solver, result, pathSave);
            } catch(IOException e) {
                System.out.println("Gagal menyimpan file hasil pencarian");
                e.printStackTrace();
            }

            System.out.println("File hasil pencarian berhasil disimpan pada " + pathSave);
        }
        else{
            System.out.println("Program selesai. Solusi tidak disimpan.");
        }
    }
}
