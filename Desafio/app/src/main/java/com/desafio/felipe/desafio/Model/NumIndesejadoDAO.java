package com.desafio.felipe.desafio.Model;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.awt.font.NumericShaper;
import java.util.ArrayList;

/**
 * Created by felipe on 17/12/17.
 */

public class NumIndesejadoDAO {

    private static final String NOME_TABELA = "lista_negra";

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;

    private static final String COL_ID = "id";
    private static final String COL_NOME = "nome";
    private static final String COL_TEL = "telefone";

    private int indexId;
    private int indexNome;
    private int indexTel;


    public NumIndesejadoDAO(Context context) {
        openHelper = new BancoDeDados(context);
        database = openHelper.getWritableDatabase();
    }

    public ArrayList<NumIndesejado> carregarNumIndesejados(){
        ArrayList<NumIndesejado> arrayList = new ArrayList<>();

        try{
            Cursor cursor = database.query(NOME_TABELA, new String[]{COL_ID, COL_NOME, COL_TEL},
                    null, null, null, null, COL_NOME+" ASC");

            indexId = cursor.getColumnIndex(COL_ID);
            indexNome = cursor.getColumnIndex(COL_NOME);
            indexTel = cursor.getColumnIndex(COL_TEL);

            cursor.moveToNext();
            while(cursor != null){
                NumIndesejado num = new NumIndesejado();

                num.setId(cursor.getLong(indexId));
                num.setNome(cursor.getString(indexNome));
                num.setTelefone(cursor.getString(indexTel));

                arrayList.add(num);
                cursor.moveToNext();
            }

        }catch (Exception e ){
            e.printStackTrace();
        }

        return arrayList;
    }

    public NumIndesejado adicionarNumero(NumIndesejado numIndesejado){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOME, numIndesejado.getNome());
        contentValues.put(COL_TEL, numIndesejado.getTelefone());

        try{
            long id = database.insert(BancoDeDados.NOME_TABELA, null, contentValues);
            numIndesejado.setId(id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return numIndesejado;
    }

    public boolean removerNumero(long id){

        try{
            database.delete(BancoDeDados.NOME_TABELA, "id = '"+id+"'", null);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean editarNumero(NumIndesejado numIndesejado){

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID, numIndesejado.getId());
        contentValues.put(COL_NOME, numIndesejado.getNome());
        contentValues.put(COL_TEL, numIndesejado.getTelefone());

        try{
            database.update(BancoDeDados.NOME_TABELA, contentValues, COL_ID+"="+numIndesejado.getId(), null);
            return true;
        }catch (Exception e){
            return false;

        }
    }
}
