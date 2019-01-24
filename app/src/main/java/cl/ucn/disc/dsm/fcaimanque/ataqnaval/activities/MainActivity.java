package cl.ucn.disc.dsm.fcaimanque.ataqnaval.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import cl.ucn.disc.dsm.fcaimanque.ataqnaval.model.Celda;
import cl.ucn.disc.dsm.fcaimanque.ataqnaval.model.Partida;
import cl.ucn.disc.dsm.fcaimanque.ataqnaval.R;
import cl.ucn.disc.dsm.fcaimanque.ataqnaval.adapters.TableroAdapter;

/**
 * Actividad para jugar
 */
public class MainActivity extends AppCompatActivity {
    private int numCellsBoardSide;
    private TextView textViewGameStage, textViewMessage;
    private Button buttonAttack, buttonRestart;
    private GridView gridViewBoard1, gridViewBoard2;
    private TableroAdapter tableroAdapter1, tableroAdapter2;

    /**
     * inicializa la partida
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setFields();
        enableGame();
    }

    private void enableGame() {
        Partida partida = Partida.getInstance();
        partida.setFields(this, numCellsBoardSide, textViewGameStage, textViewMessage,
                buttonAttack, buttonRestart,
                gridViewBoard1, gridViewBoard2, tableroAdapter1, tableroAdapter2);
        partida.initialize();
    }

    private void setFields() {
        numCellsBoardSide = getResources().getInteger(R.integer.num_cells_board_side);
        textViewGameStage = findViewById(R.id.text_view_game_stage);
        textViewMessage = findViewById(R.id.text_view_message);
        buttonRestart = findViewById(R.id.button_initialize);
        buttonAttack = findViewById(R.id.button_attack);
        gridViewBoard1 = findViewById(R.id.gridViewBoard1);
        gridViewBoard2 = findViewById(R.id.gridViewBoard2);
        tableroAdapter1 = new TableroAdapter(this, new ArrayList<Celda>());
        tableroAdapter2 = new TableroAdapter(this, new ArrayList<Celda>());
    }
}
