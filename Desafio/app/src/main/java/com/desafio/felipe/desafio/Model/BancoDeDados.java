package com.desafio.felipe.desafio.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by felipe on 15/12/17.
 */

public class BancoDeDados {

    private static final String NOME_TABELA = "lista_negra";
    private Context context;

    private SQLiteDatabase database;

    public BancoDeDados(Context context){
        this.context = context;
    }

    public boolean conexao(){
        try{
            database = context.openOrCreateDatabase("app", Context.MODE_PRIVATE, null);

            database.execSQL("CREATE TABLE IF NOT EXISTS "+NOME_TABELA+"(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, telefone VARCHAR)");
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public ArrayList<NumIndesejado> carregarNumIndesejados(){
        ArrayList<NumIndesejado> lista = new ArrayList<>();

        if(conexao()){
            try{
                Cursor cursor = database.rawQuery("SELECT id, nome, telefone FROM "+NOME_TABELA, null);

                int nome = cursor.getColumnIndex("nome");
                int tel = cursor.getColumnIndex("telefone");
                cursor.moveToFirst();

                NumIndesejado ligacao;
                while (cursor != null){
                    ligacao = new NumIndesejado();

                    ligacao.setNome(cursor.getString(nome));
                    ligacao.setTelefone(cursor.getString(tel));

                    lista.add(ligacao);

                    cursor.moveToNext();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return lista;
    }

    public boolean adicionarNumero(NumIndesejado numIndesejado){

        if(conexao()){
            try{
                database.execSQL("INSERT INTO "+NOME_TABELA+"(nome, telefone) VALUES('"
                        +numIndesejado.getNome()+"', '"+numIndesejado.getTelefone()+"')");
            }catch (Exception e){
                return false;
            }
            return true;
        }
        return false;
    }
}
