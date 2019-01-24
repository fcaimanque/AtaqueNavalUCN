package cl.ucn.disc.dsm.fcaimanque.ataqnaval.model;

import java.util.ArrayList;
import java.util.List;

/**
 * contiene la flota, estado de ataque
 *
 * @author Vu
 */
public class Jugador {
    private int numShipsArranged = 0;
    private final int numShips = 3;
    private final List<Nave> naves = new ArrayList<>(numShips);

    /**
     * - lancha: usa 2 celdas y 1 ataque
     * - buque: 3 celdas, 2 ataques
     * - portaaviones: 5 celdas, 3 ataques
     */
    public Jugador(int playerNum) {
        int playerNum1 = playerNum;

        Nave nave = new Nave(playerNum, Nave.tipo.LANCHA);
        naves.add(nave);
        nave = new Nave(playerNum, Nave.tipo.BUQUE);
        naves.add(nave);
        nave = new Nave(playerNum, Nave.tipo.PORTAAVIONES);
        naves.add(nave);
    }

   /* public Jugador(BluetoothService.MessageChannel channel) {
        Nave nave = new Nave(playerNum, Nave.tipo.LANCHA);
        naves.add(nave);
        nave = new Nave(playerNum, Nave.tipo.BUQUE);
        naves.add(nave);
        nave = new Nave(playerNum, Nave.tipo.PORTAAVIONES);
        naves.add(nave);
    }*/

    public int getNumShipsArranged() {
        return numShipsArranged;
    }

    public int getNumShips() {
        return numShips;
    }

    public List<Nave> getNaves() {
        return naves;
    }

    private int getNumShipsToArrange() {
        return numShips - numShipsArranged;
    }

    public boolean canAddCell() {
        return getNumShipsToArrange() > 0;
    }


    public void addCell(Celda cell) {
        Nave nave = naves.get(numShipsArranged);

        nave.addCell(cell);
        if (!nave.canAddCells())
            numShipsArranged++;
    }

    public boolean canAttack() {
        for (Nave nave : naves)
            if (nave.canAttack())
                return true;
        return false;
    }


    public void attackCell(Celda celda) {
        Nave nave = getNextShipCanAttack();
        nave.attackCell(celda);
    }


    public Nave getNextShipCanAttack() {
        int index = 0;
        Nave nave;
        do {
            nave = naves.get(index);
            index++;
        } while (!nave.canAttack());
        return nave;
    }


    public void resetNumsAttacksMade() {
        for (Nave nave : naves)
            nave.setNumAttacksMade(0);
    }


    public int getNumShipsAlive() {
        int numShipsAlive = 0;
        for (Nave nave : naves)
            if (nave.isAlive())
                numShipsAlive++;
        return numShipsAlive;
    }

    public boolean isAlive() {
        return getNumShipsAlive() > 0;
    }
}
