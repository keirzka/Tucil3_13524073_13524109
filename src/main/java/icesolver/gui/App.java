package icesolver.gui;

import icesolver.model.Board;
import icesolver.parser.InputParser;
import icesolver.solver.Solver;
import icesolver.output.FileOutput;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Header
        Label judul = new Label("Ice Sliding Puzzle Solver ");
        judul.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        judul.setMaxWidth(Double.MAX_VALUE);
        judul.setAlignment(Pos.CENTER);

        // =========== INPUT UI ============

        // INPUT FILE
        Label inputLabel = new Label("Konfigurasi Papan (.txt)");
        inputLabel.setStyle("-fx-font-weight: bold;");
        Button btnFile = new Button("[Choose file]");
        Label fileNameLabel = new Label("Belum ada file terpilih");

        // PILIHAN ALGORITMA
        Label algoritmaLabel = new Label("Jenis Algoritma");
        algoritmaLabel.setStyle("-fx-font-weight: bold;");
        ComboBox<String> pilihanAlgoritma = new ComboBox<>(FXCollections.observableArrayList("UCS", "GBFS", "A*", "BFS"));
        pilihanAlgoritma.setValue("Pilih Algoritma");

        // PILIHAN HEURISTIC untuk GBFS dan A*
        Label heuristicLabel = new Label("Pilih jenis heuristic yang tersedia");
        heuristicLabel.setStyle("-fx-font-weight: bold;");
        ComboBox<String> pilihanHeuristic = new ComboBox<>(FXCollections.observableArrayList("H1", "H2", "H3"));
        pilihanHeuristic.setValue("Pilih Heuristic");


        // Logika Kondisional Pilihan Heuristic
        heuristicLabel.setVisible(false);
        pilihanHeuristic.setVisible(false);
        pilihanAlgoritma.setOnAction(e -> {
            String selected = pilihanAlgoritma.getValue();
            boolean isHeuristicNeeded = "GBFS".equals(selected) || "A*".equals(selected);
            heuristicLabel.setVisible(isHeuristicNeeded);
            pilihanHeuristic.setVisible(isHeuristicNeeded);
        });

        // Logika File Chooser
        final String[] selectedPath = {null};

        btnFile.setOnAction(e -> {
            FileChooser fileInput = new FileChooser();
            fileInput.setTitle("[Choose File]");
            fileInput.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            var file = fileInput.showOpenDialog(stage);
            if (file != null) {
                fileNameLabel.setText(file.getName());
                selectedPath[0] = file.getAbsolutePath();
            }
        });

        // Tombol Solver
        Button btnSolver = new Button("Run Ice Solver");
        btnSolver.setStyle("-fx-font-size: 16px; -fx-font-weight: bold");
        btnSolver.setAlignment(Pos.CENTER);
        HBox tombolBox = new HBox(btnSolver);
        tombolBox.setAlignment(Pos.CENTER);

        // Layout
        VBox InputUI = new VBox(10); // Jarak antar komponen adalah 10 pixel
        InputUI.setPadding(new Insets(20)); // Margin dari tepi jendela
        InputUI.setAlignment(Pos.TOP_LEFT); // Komponen rata tengah atas
        VBox.setMargin(algoritmaLabel, new Insets(20, 0, 0, 0));
        VBox.setMargin(heuristicLabel, new Insets(20, 0, 0, 0));
        VBox.setMargin(btnSolver, new Insets(30, 0, 30, 0));
        Scene sceneInput = new Scene(InputUI, 800, 600);
        InputUI.getChildren().addAll(judul, new Separator(), inputLabel, btnFile, fileNameLabel, algoritmaLabel, pilihanAlgoritma, heuristicLabel, pilihanHeuristic, tombolBox);


        // =========== Output UI ============
        Label labelResult = new Label("Hasil Pencarian Path");
        labelResult.setStyle("-fx-font-size: 16px; -fx-font-weight: bold");

        Label labelVisualisasi = new Label("Visualisasi Pencarian Path");
        labelVisualisasi.setStyle("-fx-font-size: 16px; -fx-font-weight: bold");
        labelVisualisasi.setMaxWidth(Double.MAX_VALUE);
        labelVisualisasi.setAlignment(Pos.CENTER);

        // Footer
        Button btnBack = new Button("Back");
        btnBack.setAlignment(Pos.CENTER);

        Button btnSave = new Button("Save");
        btnSave.setAlignment(Pos.CENTER);
        btnSave.setDisable(true);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox footerBox = new HBox(10);
        footerBox.getChildren().addAll(btnBack, spacer, btnSave);

        Pagination pagination = new Pagination();
        pagination.setMinHeight(300);
        pagination.setMinWidth(400);

        HBox paginationWrapper = new HBox(pagination);
        paginationWrapper.setAlignment(Pos.CENTER);
        paginationWrapper.setMaxWidth(Double.MAX_VALUE);

        Label labelInfo = new Label();
        labelInfo.setAlignment(Pos.BASELINE_LEFT);

        // Layout
        VBox OutputUI = new VBox(10); // Jarak antar komponen adalah 10 pixel
        OutputUI.setPadding(new Insets(20)); // Margin dari tepi jendela
        OutputUI.getChildren().addAll(judul, labelResult, labelInfo, labelVisualisasi, paginationWrapper, footerBox);
        Scene sceneOutput = new Scene(OutputUI, 800, 600);

        // Navigasi Back
        btnBack.setOnAction(e -> stage.setScene(sceneInput));

        // LOGIKA SOLVER
        final Board[] lastBoard = {null};
        final Solver[] lastSolver = {null};
        final Solver.HasilSolusi[] lastResult = {null};

        btnSolver.setOnAction(e -> {
            if(selectedPath[0] == null) return;

            try {
                SolverService service =  new SolverService();
                var result = service.solve(selectedPath[0], pilihanAlgoritma.getValue(), pilihanHeuristic.getValue());
                Board papanAwal = InputParser.baca(selectedPath[0]);

                if(result == null || !result.terpecahkan){
                    labelInfo.setText("Solusi tidak ditemukan");
                }
                else{
                    labelInfo.setText(
                            "Path solusi: " + result.stringJalur() +
                            "\nTotal cost solusi : " + result.totalBiaya +
                            "\nWaktu eksekusi : " + result.waktuEksekusiMs + " ms" +
                            "\nBanyak iterasi yang dilakukan  : " + result.iterasi);

                    BoardRenderer renderer = new BoardRenderer();

                    // Setup Slider
                    pagination.setPageCount(result.riwayatStatus.size());

                    // Show Papan
                    pagination.setPageFactory(pageIndex -> {
                        VBox page = new VBox(10);
                        page.setAlignment(Pos.CENTER);
                        Label stepLabel = new Label();
                        stepLabel.setStyle("-fx-font-weight: bold;");

                        if(pageIndex == 0){
                            stepLabel.setText("Initial");
                        }
                        else{
                            stepLabel.setText("Step " + pageIndex + " : " + result.jalur.get(pageIndex - 1).simbol);
                        }

                        GridPane papan = renderer.render(papanAwal, result.riwayatStatus.get(pageIndex));
                        page.getChildren().addAll(stepLabel, papan);
                        return page;
                    });
                }
                lastBoard[0] = papanAwal;
                lastSolver[0] = service.getSolver();
                lastResult[0] = result;
                btnSave.setDisable(false);

                stage.setScene(sceneOutput);

            } catch (Exception err) {
                err.printStackTrace();
            }
        });

        // Save File
        btnSave.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Simpan Hasil Solusi");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"));
            fileChooser.setInitialFileName("ice-sliding-puzzle-solution.txt");
            var file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try{
                    new FileOutput().simpanHasil(lastBoard[0], lastSolver[0], lastResult[0], file.getAbsolutePath());
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        stage.setTitle("Ice Solver GUI");
        stage.setScene(sceneInput);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}