package cl.ucn.disc.dsm.fcaimanque.ataqnaval.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import androidx.core.content.ContextCompat;

import java.util.List;

import cl.ucn.disc.dsm.fcaimanque.ataqnaval.R;
import cl.ucn.disc.dsm.fcaimanque.ataqnaval.model.Celda;

/**
 * Infla tableros como gridViews
 *
 */
public class TableroAdapter extends ArrayAdapter<Celda> {
    private LayoutInflater inflater;

    /**
     *
     */
    public TableroAdapter(Context context, List<Celda> objects) {
        super(context, -1, objects);
        inflater = LayoutInflater.from(context);
    }

    /**
     * FIJA EL COLOR DE LA CELDA DE ACUERDO AL STATUS
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.layout_cell, parent, false);
        Celda cell = getItem(position);

        Button button = view.findViewById(R.id.button_board_cell);

        assert cell != null;
        if (cell.getStatus() == Celda.Status.DAÑADO)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorHit));
        else if (cell.getStatus() == Celda.Status.FALLO)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorMissed));

        else if (cell.getPlayerNum() == 2)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorUnknown));

        else if (cell.getStatus() == Celda.Status.VACIO)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorVacant));
        else
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorOccupied));

        return view;
    }

    /**
     * llena con celdas el tablero
     */
    public void addCells(GridView gridView, int playerNum, int numCells) {
        gridView.setAdapter(this);
        for (int i = 0; i < numCells; i++)
            this.add(new Celda(playerNum, Celda.Status.VACIO));
    }
}
