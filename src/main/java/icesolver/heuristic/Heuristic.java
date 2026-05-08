package icesolver.heuristic;
import icesolver.model.*;

public interface Heuristic {
    double perkiraan(State status, Board papan);
    String label();
    String nama();
}