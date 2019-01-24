package cl.ucn.disc.dsm.fcaimanque.ataqnaval;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cl.ucn.disc.dsm.fcaimanque.ataqnaval.adapters.TableroAdapter;
import cl.ucn.disc.dsm.fcaimanque.ataqnaval.model.Celda;
import cl.ucn.disc.dsm.fcaimanque.ataqnaval.model.Jugador;

/**
 *
 */
public class Tablero {
    static private Jugador jugador;

    private static Tablero ourInstance = new Tablero();
    private static TableroAdapter tableroAdapter;
    private static int x, y, cols;
    private static Random random = new Random();

    private Tablero() {
        x = 0;
        y = 0;
        cols = 1;
    }

    public static Tablero getInstance() {
        if (ourInstance == null)
            ourInstance = new Tablero();
        return ourInstance;
    }

    private static void setXYFromPos(int cols, int pos) {
        x = pos % cols;
        y = pos / cols;
    }

    private static int getPosFromXY() {
        return x * cols + y;
    }

    private static int getPosFromXY(int x, int y) {
        return x * cols + y;
    }

    private static void getEmptyCell() {
        do {
            x = random.nextInt(cols);
            y = random.nextInt(cols);
        }
        while (tableroAdapter.getItem(getPosFromXY()).getStatus() == Celda.Status.OCUPADO);
    }

    private static boolean isNorthValid(int size) {
        if (y - size < 0)
            return false;

        int source = getPosFromXY(x, y);
        for (int i = 0; i < size; i++)
            if (tableroAdapter.getItem(source - i).getStatus() == Celda.Status.OCUPADO)
                return false;

        return true;
    }

    private static void setNorthPlacement(int size) {
        for (int i = 0; i < size; i++)
            jugador.addCell(tableroAdapter.getItem(getPosFromXY(x, y - i)));
    }

    private static boolean isEastValid(int size) {
        if (x + size > cols)
            return false;

        for (int i = 0; i < size; i++)
            if (tableroAdapter.getItem(getPosFromXY(x + i, y)).getStatus() == Celda.Status.OCUPADO)
                return false;

        return true;
    }

    private static void setEastPlacement(int size) {
        for (int i = 0; i < size; i++)
            jugador.addCell(tableroAdapter.getItem(getPosFromXY(x + i, y)));
    }

    private static boolean isSouthValid(int size) {
        if (y + size > cols)
            return false;

        for (int i = 0; i < size; i++)
            if (tableroAdapter.getItem(getPosFromXY(x, y + i)).getStatus() == Celda.Status.OCUPADO)
                return false;

        return true;
    }

    private static void setSouthPlacement(int size) {
        for (int i = 0; i < size; i++)
            jugador.addCell(tableroAdapter.getItem(getPosFromXY(x, y + i)));
    }

    private static boolean isWestValid(int size) {
        if (x - size < 0)
            return false;

        for (int i = 0; i < size; i++) {
            int nextX = x - i;
            if (nextX < 0 || tableroAdapter.getItem(getPosFromXY(nextX, y)).getStatus() == Celda.Status.OCUPADO)
                return false;
        }

        return true;
    }

    private static void setWestPlacement(int size) {
        for (int i = 0; i < size; i++)
            jugador.addCell(tableroAdapter.getItem(getPosFromXY(x - i, y)));
    }


    private static void setPlacement(int size) {
        boolean notPlaced = true;

        while (notPlaced) {
            List<Integer> sample = new ArrayList<>(4);
            sample.add(0);
            sample.add(1);
            sample.add(2);
            sample.add(3);

            getEmptyCell();

            while (!sample.isEmpty()) {
                int i = random.nextInt(sample.size());
                switch (sample.get(i)) {
                    case 0:
                        if (isNorthValid(size)) {
                            setNorthPlacement(size);
                            notPlaced = false;
                        } else
                            sample.remove(i);
                        break;
                    case 1:
                        if (isEastValid(size)) {
                            setEastPlacement(size);
                            notPlaced = false;
                        } else
                            sample.remove(i);
                        break;
                    case 2:
                        if (isSouthValid(size)) {
                            setSouthPlacement(size);
                            notPlaced = false;
                        } else
                            sample.remove(i);
                        break;
                    case 3:
                        if (isWestValid(size)) {
                            setWestPlacement(size);
                            notPlaced = false;
                        } else
                            sample.remove(i);
                        break;
                    default:
                        break;
                }
            }
        }
    }


    public static void generateShipPlacement(Jugador jugador2, TableroAdapter tableroAdapter, int dim) {
        jugador = jugador2;

        Tablero.tableroAdapter = tableroAdapter;
        cols = dim;

        setPlacement(2); //lancha
        setPlacement(3); //buque
        setPlacement(5); //portaviones


        Tablero.tableroAdapter.notifyDataSetChanged();
    }
}
