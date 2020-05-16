package com.apl.guaravid19;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {

    private static final String GUARAVID_19_DB = "guaravid19db";
    private static final int VERSION = 1;
    public static final String PACIENTE_TABLE = "Paciente";
    public static final String PACIENTE_FIELD_ID = "id";
    public static final String PACIENTE_FIELD_NOME = "nome";
    public static final String PACIENTE_FIELD_CPF = "cpf";
    public static final String PACIENTE_FIELD_TELEFONE = "telefone";

    public Conexao(@Nullable Context context) {
        super(context, GUARAVID_19_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTablePaciente = "CREATE TABLE " + PACIENTE_TABLE + "(" +
                PACIENTE_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PACIENTE_FIELD_NOME + " VARCHAR(50), " +
                PACIENTE_FIELD_CPF + " VARCHAR(14), " +
                PACIENTE_FIELD_TELEFONE + " VARCHAR(50))";
        db.execSQL(createTablePaciente);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
