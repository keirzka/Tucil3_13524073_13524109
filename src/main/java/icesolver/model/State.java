package icesolver.model;
import java.util.Objects;

public final class State {
    public final Position pos;
    public final int checkpointBerikutnya;
    public State(Position pos, int checkpointBerikutnya) {
        this.pos = pos;
        this.checkpointBerikutnya = checkpointBerikutnya;
    }

    public State denganPosisi(Position posBaru, Board papan) {
        int cb = checkpointBerikutnya;
        Cell c = papan.tileAt(posBaru);
        if (c.isCheckpoint() && c.indeksCheckpoint() == cb) {
            cb++;
        }
        return new State(posBaru, cb);
    }

    public boolean semuaCheckpointSelesai(Board papan) {
        return checkpointBerikutnya >= papan.totalCheckpoints();
    }

    public boolean isTujuan(Board papan) {
        return pos.equals(papan.tujuan) && semuaCheckpointSelesai(papan);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State s = (State) o;
        return checkpointBerikutnya == s.checkpointBerikutnya && pos.equals(s.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, checkpointBerikutnya);
    }

    @Override
    public String toString() {
        return "Status{pos=" + pos + ", cpBerikutnya=" + checkpointBerikutnya + "}";
    }
}