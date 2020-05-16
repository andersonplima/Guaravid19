package com.apl.guaravid19;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextCpf;
    private EditText editTextTelefone;
    private int pacienteId;
    private boolean hasChangedData;
    private boolean cameFromList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextTelefone = findViewById(R.id.editTextTelefone);

        hasChangedData = false;
        Intent callerIntent = getIntent();
        if (callerIntent != null && callerIntent.hasExtra("paciente")) {
            Paciente paciente = (Paciente) callerIntent.getSerializableExtra("paciente");

            editTextTelefone.setText(paciente.getTelefone());
            editTextNome.setText(paciente.getNome());
            editTextCpf.setText(paciente.getCpf());

            cameFromList = true;
            pacienteId = paciente.getId();
        }
        else {
            cameFromList = false;
            pacienteId = 0;
        }

        Button buttonIncluir = findViewById(R.id.buttonIncluir);
        Button buttonAlterar = findViewById(R.id.buttonAlterar);
        Button buttonExcluir = findViewById(R.id.buttonExcluir);
        Button buttonListar = findViewById(R.id.buttonListar);

        buttonIncluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paciente paciente = new Paciente();
                paciente.setNome(editTextNome.getText().toString());
                paciente.setCpf(editTextCpf.getText().toString());
                paciente.setTelefone(editTextTelefone.getText().toString());

                PacienteDAO pacienteDAO = new PacienteDAO(MainActivity.this);
                pacienteDAO.incluir(paciente);

                hasChangedData = true;
                Toast.makeText(getApplicationContext(), "Paciente salvo com sucesso!", Toast.LENGTH_LONG).show();
                limparCampos();
            }
        });

        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pacienteId > 0) {
                    Paciente paciente = new Paciente();
                    paciente.setId(pacienteId);
                    paciente.setCpf(editTextCpf.getText().toString());
                    paciente.setNome(editTextNome.getText().toString());
                    paciente.setTelefone(editTextTelefone.getText().toString());

                    PacienteDAO pacienteDAO = new PacienteDAO(MainActivity.this);
                    pacienteDAO.alterar(paciente);
                    hasChangedData = true;

                    Toast.makeText(getApplicationContext(), "Paciente alterado com sucesso!", Toast.LENGTH_LONG).show();
                    limparCampos();
                } else
                    Toast.makeText(getApplicationContext(), "Não há paciente selecionado para alterar!", Toast.LENGTH_LONG).show();
            }
        });

        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pacienteId > 0) {
                    PacienteDAO pacienteDAO = new PacienteDAO(MainActivity.this);
                    pacienteDAO.excluir(pacienteId);
                    hasChangedData = true;

                    Toast.makeText(getApplicationContext(), "Paciente excluído com sucesso!", Toast.LENGTH_LONG).show();
                    limparCampos();
                } else
                    Toast.makeText(getApplicationContext(), "Não há paciente selecionado para excluir!", Toast.LENGTH_LONG).show();
            }
        });

        buttonListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasChangedData && cameFromList) {
                    setResult(RESULT_OK);
                    finish();
                } else
                    startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
    }

    private void limparCampos() {
        pacienteId = 0;
        editTextCpf.setText("");
        editTextNome.setText("");
        editTextTelefone.setText("");
    }
}
