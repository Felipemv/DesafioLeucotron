package com.desafio.felipe.desafio.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by felipe on 15/12/17.
 */

public class BancoDeDados extends SQLiteOpenHelper{

    public static final String NOME_TABELA = "lista_negra";
    private static final String NOME_BANCO = "banco_desafio";
    private static final int VERSAO = 1;

    private static final String CRIAR_TABELA = "CREATE TABLE IF NOT EXISTS "
            +NOME_TABELA+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, " +
            "telefone VARCHAR)";

    private static final String DELETAR_TABELA = "DELETE TABLE IF EXISTS "+ NOME_TABELA;

    public BancoDeDados(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETAR_TABELA);
        onCreate(db);
    }

}
