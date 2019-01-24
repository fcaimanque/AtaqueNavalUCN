package cl.ucn.disc.dsm.fcaimanque.ataqnaval.model;

/**
 * cada celda del tablero
 *
 */
public class Celda {
    private final int playerNum;
    private Status status;

    public Celda(int playerNum, Status status) {
        this.playerNum = playerNum;
        this.status = status;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public Status getStatus() {
        return status;
    }

    void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        VACIO, OCUPADO, DAÑADO, FALLO
    }
}
