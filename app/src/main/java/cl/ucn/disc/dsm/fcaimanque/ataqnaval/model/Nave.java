package cl.ucn.disc.dsm.fcaimanque.ataqnaval.model;

import java.util.ArrayList;
import java.util.List;

/**
 * each ship: how many cells left to arrange, how many attacks left,...
 *
 * @author Vu
 */
public class Nave {
    private int numCellsAdded = 0;
    private int numAttacksMade = 0;
    private static tipo tipo;
    private int numCells;
    private int numAttacksAllowed;
    private final List<Celda> cells;

    /**
     * creates ship according to ship type
     */
    public Nave(int playerNum, tipo tipo) {
        int playerNum1 = playerNum;
        this.tipo = tipo;
        setShipTypeDependentFields();
        cells = new ArrayList<>(numCells);
    }

    public void setNumAttacksMade(int numAttacksMade) {
        this.numAttacksMade = numAttacksMade;
    }

    public int getNumCells() {
        return numCells;
    }

    private void setShipTypeDependentFields() {
        if (tipo == Nave.tipo.LANCHA) {
            numCells = 2;
            numAttacksAllowed = 1;
        } else if (tipo == Nave.tipo.BUQUE) {
            numCells = 3;
            numAttacksAllowed = 2;
        } else {
            numCells = 5;
            numAttacksAllowed = 3;
        }
    }

    /**
     * how many cells left to arrange this ship
     */
    public int getNumCellsToAdd() {
        return numCells - numCellsAdded;
    }

    /**
     * not done arranging this ship yet
     */
    public boolean canAddCells() {
        return getNumCellsToAdd() > 0;
    }

    /**
     * add 1 cell to ship during arrangement
     */
    public void addCell(Celda cell) {
        cell.setStatus(Celda.Status.OCUPADO);
        cells.add(cell);
        numCellsAdded++;
    }

    /**
     * @return how many attacks left for this ship
     */
    public int getNumAttacksLeft() {
        return numAttacksAllowed - numAttacksMade;
    }

    /**
     * @return can ship still attack?
     */
    public boolean canAttack() {
        return isAlive() && getNumAttacksLeft() > 0;
    }

    /**
     * this ship now attacks 1 celda
     */
    public void attackCell(Celda celda) {
        if (celda.getStatus() == Celda.Status.VACIO)
            celda.setStatus(Celda.Status.FALLO);
        if (celda.getStatus() == Celda.Status.OCUPADO)
            celda.setStatus(Celda.Status.DAÑADO);
        numAttacksMade++;
    }
    /**
     * @return las celdas que no se dañaron
     */
    public boolean isAlive() {
        for (Celda cell : cells)
            if (cell.getStatus() != Celda.Status.DAÑADO)
                return true;
        return false;
    }

    public enum tipo {
        LANCHA, BUQUE, PORTAAVIONES
    }
}
