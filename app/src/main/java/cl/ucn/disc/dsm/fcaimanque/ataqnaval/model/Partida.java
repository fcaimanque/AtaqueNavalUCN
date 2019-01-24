package cl.ucn.disc.dsm.fcaimanque.ataqnaval.model;


import android.content.Context;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Random;

import cl.ucn.disc.dsm.fcaimanque.ataqnaval.adapters.TableroAdapter;
import cl.ucn.disc.dsm.fcaimanque.ataqnaval.DespliegueNaves;
import cl.ucn.disc.dsm.fcaimanque.ataqnaval.Tablero;

/**
 * controla la partida
 *
 */
public class Partida {
    private static Partida instance;
    private Stage stage;
    private int numCells1side;
    private TextView textViewGameStage, textViewMessage;
    private Button buttonAttack, buttonRestart;
    private GridView gridViewBoard1, gridViewBoard2;
    private TableroAdapter tableroAdapter1, tableroAdapter2;
    private Jugador jugador1, jugador2;

    private Partida() {
    }

    /**
     *
     */
    public static Partida getInstance() {
        if (instance == null)
            instance = new Partida();
        return instance;
    }

    /**
     *
     */
    public void setFields(Context context, int numCells1side,
                          TextView textViewGameStage, TextView textViewMessage,
                          Button buttonAttack, Button buttonRestart,
                          GridView gridViewBoard1, GridView gridViewBoard2,
                          TableroAdapter tableroAdapter1, TableroAdapter tableroAdapter2) {
        Context context1 = context;
        this.numCells1side = numCells1side;
        this.textViewGameStage = textViewGameStage;
        this.textViewMessage = textViewMessage;
        this.buttonAttack = buttonAttack;
        this.buttonRestart = buttonRestart;
        this.gridViewBoard1 = gridViewBoard1;
        this.gridViewBoard2 = gridViewBoard2;
        this.tableroAdapter1 = tableroAdapter1;
        this.tableroAdapter2 = tableroAdapter2;
    }

    /**
     * [re]starts game by clearing boards and letting bot secretly arrange its fleet
     */
    public void initialize() {
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
            }
        });
        disableClicking();

        tableroAdapter1.clear();
        tableroAdapter2.clear();
        tableroAdapter1.addCells(gridViewBoard1, 1, getNumCellsBoardArea());
        tableroAdapter2.addCells(gridViewBoard2, 2, getNumCellsBoardArea());

        jugador1 = new Jugador(1);
        jugador2 = new Jugador(2);

        letP2arrange();
    }

    private void letP2arrange() {
        Tablero.generateShipPlacement(jugador2, tableroAdapter2, numCells1side);
        enableGameStageArranging();
    }

    private void enableGameStageArranging() {
        putGameStage(Stage.COLOCACION);
        letP1arrange();
    }

    private void letP1arrange() {
        gridViewBoard1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Celda cell = (Celda) parent.getAdapter().getItem(position);
                jugador1.addCell(cell);
                tableroAdapter1.notifyDataSetChanged();

                if (jugador1.canAddCell())
                    setMessage(jugador1.getNaves().get(jugador1.getNumShipsArranged()).getNumCellsToAdd() +
                            " cell(s) left to add to your ship " +
                            (jugador1.getNumShipsArranged() + 1));
                else {
                    gridViewBoard1.setOnItemClickListener(null);

                    if (checkArrange())
                        enableGameStageBattling();
                    else
                        setMessage("Invalid arrangement; click RESTART");
                }
            }
        });
    }

    private boolean checkArrange() {
        DespliegueNaves shipArr = new DespliegueNaves();
        TableroAdapter tableroAdapter = tableroAdapter1;
        int c = 0;
        for (int i = 0; i < tableroAdapter.getCount(); i++) {
            Celda cell = tableroAdapter1.getItem(i);
            assert cell != null;
            if (cell.getStatus() == Celda.Status.OCUPADO) {
                c = c + 1;
                if (c == 10) {
                    if (((shipArr.checkArrangeLH(tableroAdapter)) || (shipArr.checkArrangeLV(tableroAdapter)))) {
                        if (((shipArr.checkArrangeMH(tableroAdapter)) || (shipArr.checkArrangeMV(tableroAdapter)))) {
                            if (((shipArr.checkArrangeSH(tableroAdapter)) || (shipArr.checkArrangeSV(tableroAdapter)))) {
                                return true;
                            }
                        }
                    }

                }
            }
        }
        return false;
    }

    private void enableGameStageBattling() {
        putGameStage(Stage.BATALLANDO);
        enableGameStageAttacking();
    }

    private void enableGameStageAttacking() {
        buttonAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putGameStage(Stage.ATACANDO);
                buttonAttack.setOnClickListener(null);
                letP1attack();
            }
        });
    }

    private void letP1attack() {
        gridViewBoard2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Celda cell = (Celda) parent.getAdapter().getItem(position);
                jugador1.attackCell(cell);
                tableroAdapter2.notifyDataSetChanged();

                if (!jugador2.isAlive()) {
                    disableClicking();
                    setMessage("Ganaste! Reinicia la Partida");
                } else if (jugador1.canAttack()) {
                    Nave nave = jugador1.getNextShipCanAttack();
                    setMessage("Ataques restantes de la nave " + (jugador1.getNaves().indexOf(nave) + 1) + ": " +nave.getNumAttacksLeft());
                } else {
                    gridViewBoard2.setOnItemClickListener(null);
                    jugador1.resetNumsAttacksMade();
                    letP2attack();
                }
            }
        });
    }

    private void letP2attack() {
        Random random = new Random();
        while (jugador2.canAttack()) {
            Celda cell;
            do {
                cell = tableroAdapter1.getItem(random.nextInt(getNumCellsBoardArea()));
            }
            while (cell.getStatus() == Celda.Status.DAÑADO ||
                    cell.getStatus() == Celda.Status.FALLO);
            jugador2.attackCell(cell);
            tableroAdapter1.notifyDataSetChanged();

            if (!jugador1.isAlive()) {
                disableClicking();
                setMessage("Perdiste.... Reinicia la Partida");
                break;
            }
        }
        if (jugador1.isAlive()) {
            jugador2.resetNumsAttacksMade();
            enableGameStageBattling();
        }
    }

    private void putGameStage(Stage stage) {
        this.stage = stage;
        String msg = "Etapa: " + stage;
        textViewGameStage.setText(msg);
        describeGameStage();
    }

    private void describeGameStage() {
        String msg;
        if (stage == Stage.COLOCACION)
            msg = "presiona una CELDA para colocar tus " +
                    jugador1.getNumShips() + " naves";
        else if (stage == Stage.BATALLANDO)
            msg = "presiona ATAQUE";
        else
            msg = "presiona una CELDA del ENEMIGO" +
                    jugador2.getNumShipsAlive() + " naves sobrevivientes";
        setMessage(msg);
    }

    private void setMessage(String msg) {
        textViewMessage.setText("Message: " + msg);
    }

    private void disableClicking() {
        buttonAttack.setOnClickListener(null);
        gridViewBoard1.setOnItemClickListener(null);
        gridViewBoard2.setOnItemClickListener(null);
    }

    private int getNumCellsBoardArea() {
        return (int) Math.pow(numCells1side, 2);
    }

    private enum Stage {
        COLOCACION, BATALLANDO, ATACANDO
    }
}
