package com.apl.guaravid19;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PacienteDAO {
    private Conexao conexao;
    private SQLiteDatabase database;

    public PacienteDAO(Context context) {
        conexao = new Conexao(context);
        database = conexao.getWritableDatabase();
    }

    public void incluir(Paciente paciente) {
        ContentValues values = new ContentValues();
        values.put("nome", paciente.getNome());
        values.put("cpf", paciente.getCpf());
        values.put("telefone", paciente.getTelefone());

        database.insert(Conexao.PACIENTE_TABLE, null, values);
    }

    public void alterar(Paciente paciente) {
        ContentValues values = new ContentValues();
        values.put(Conexao.PACIENTE_FIELD_CPF, paciente.getCpf());
        values.put(Conexao.PACIENTE_FIELD_NOME, paciente.getNome());
        values.put(Conexao.PACIENTE_FIELD_TELEFONE, paciente.getTelefone());

        database.update(Conexao.PACIENTE_TABLE, values, Conexao.PACIENTE_FIELD_ID + "=?", new String[] {String.valueOf(paciente.getId())});
    }

    public Paciente pegar(int id) {
        Cursor cursor = database.query(Conexao.PACIENTE_TABLE,
                new String[] {
                        Conexao.PACIENTE_FIELD_ID,
                        Conexao.PACIENTE_FIELD_NOME,
                        Conexao.PACIENTE_FIELD_CPF,
                        Conexao.PACIENTE_FIELD_TELEFONE
                },
                Conexao.PACIENTE_FIELD_ID + "=?", new String[]{String.valueOf(id)},
                null, null,Conexao.PACIENTE_FIELD_NOME, "1");

        Paciente paciente = null;
        if (cursor.moveToNext())
        {
            paciente = new Paciente();
            paciente.setTelefone(cursor.getString(3));
            paciente.setNome(cursor.getString(1));
            paciente.setCpf(cursor.getString(2));
            paciente.setId(cursor.getInt(0));
        }

        cursor.close();
        return paciente;
    }

    public ArrayList<Paciente> listar() {
        Cursor cursor = database.query(Conexao.PACIENTE_TABLE,
                new String[] {
                        Conexao.PACIENTE_FIELD_ID,
                        Conexao.PACIENTE_FIELD_NOME,
                        Conexao.PACIENTE_FIELD_CPF,
                        Conexao.PACIENTE_FIELD_TELEFONE
                },
                null, null, null, null,Conexao.PACIENTE_FIELD_NOME);

        ArrayList<Paciente> pacientes = new ArrayList<>();

        while(cursor.moveToNext()) {
            Paciente paciente = new Paciente();
            paciente.setTelefone(cursor.getString(3));
            paciente.setNome(cursor.getString(1));
            paciente.setCpf(cursor.getString(2));
            paciente.setId(cursor.getInt(0));

            pacientes.add(paciente);
        }

        cursor.close();

        return pacientes;
    }

    public void excluir(int pacienteId) {
        database.delete(Conexao.PACIENTE_TABLE, Conexao.PACIENTE_FIELD_ID + "=?", new String[] {String.valueOf(pacienteId)});
    }
}
