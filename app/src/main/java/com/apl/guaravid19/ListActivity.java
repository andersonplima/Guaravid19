package com.apl.guaravid19;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private static final int REQUESTCODE_EDIT = 1;
    private ListView listView;
    private ArrayAdapter<Paciente> pacientesAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        refreshData();

        listView = findViewById(R.id.listView);
        listView.setAdapter(pacientesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Paciente paciente = (Paciente) parent.getItemAtPosition(position);
                 Intent detailIntent = new Intent(ListActivity.this, MainActivity.class);
                 detailIntent.putExtra("paciente", paciente);
                 startActivityForResult(detailIntent, REQUESTCODE_EDIT);
            }
        });
    }

    private void refreshData() {
        PacienteDAO pacienteDAO = new PacienteDAO(this);

        ArrayList<Paciente> pacientesList = pacienteDAO.listar();
        if (pacientesAdapter == null)
            pacientesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, pacientesList);
        else {
            pacientesAdapter.clear();
            pacientesAdapter.addAll(pacientesList);
            pacientesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_EDIT)
                refreshData();
        }
    }
}
