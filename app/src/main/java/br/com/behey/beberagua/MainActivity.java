package br.com.behey.beberagua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnAplicar;
    private EditText editMinutos;
    private TimePicker timePicker;

    private int hora;
    private int minuto;
    private int intervalo;

    private boolean ativo;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAplicar = findViewById(R.id.btn_acao);
        editMinutos = findViewById(R.id.edit_interval);
        timePicker = findViewById(R.id.time_picker);

        timePicker.setIs24HourView(true);

        preferences = getSharedPreferences("db", Context.MODE_PRIVATE);

        ativo = preferences.getBoolean("ativado", false);

        if(ativo) {
            btnAplicar.setText("Pausar");
            btnAplicar.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.black));

            int h = preferences.getInt("hr", timePicker.getCurrentHour());
            int m = preferences.getInt("min", timePicker.getCurrentMinute());
            int i = preferences.getInt("inter", 0);

            timePicker.setCurrentHour(h);
            timePicker.setCurrentMinute(m);
            editMinutos.setText(String.valueOf(i));
        }

        btnAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sIntervalo = editMinutos.getText().toString();

                if(sIntervalo.isEmpty()) {
                    Toast.makeText(MainActivity.this, "O intervalo deve ser preechido", Toast.LENGTH_LONG).show();
                    return;
                }

                hora = timePicker.getCurrentHour();
                minuto = timePicker.getCurrentMinute();
                intervalo = Integer.parseInt(sIntervalo);

                if (!ativo) {
                    btnAplicar.setText("Pausar");
                    btnAplicar.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.black));
                    ativo = !ativo;

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("ativado", true);
                    editor.putInt("hr", hora);
                    editor.putInt("min", minuto);
                    editor.putInt("inter", intervalo);
                    editor.apply();

                } else {
                    btnAplicar.setText("Aplicar");
                    btnAplicar.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.teal_700));
                    ativo = !ativo;

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("ativado", false);
                    editor.remove("hr");
                    editor.remove("min");
                    editor.remove("inter");
                    editor.apply();

                }

                Log.d("Teste", "hora: " + hora + " minuto: " + minuto + " intervalo: " + intervalo);

            }
        });

    }
}