package cl.ucn.disc.dsm.fcaimanque.ataqnaval.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cl.ucn.disc.dsm.fcaimanque.ataqnaval.R;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        button1 = findViewById(R.id.button2);
        button1.setOnClickListener(this);
    }

    private void button2Click() {
        startActivity(new Intent("cl.ucn.disc.dsm.fcaimanque.ataqnaval.activities.MainActivity"));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button2) {
            button2Click();
        }
    }
}
