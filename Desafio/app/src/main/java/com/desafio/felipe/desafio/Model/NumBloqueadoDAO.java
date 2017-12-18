package com.desafio.felipe.desafio.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

/**
 * Created by felipe on 17/12/17.
 */

public class NumBloqueadoDAO {

    private static final String NOME_TABELA = "lista_negra";

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;

    private static final String COL_ID = "id";
    private static final String COL_NOME = "nome";
    private static final String COL_TEL = "telefone";

    private int indexId;
    private int indexNome;
    private int indexTel;


    public NumBloqueadoDAO(Context context) {
        openHelper = new BancoDeDados(context);
        database = openHelper.getWritableDatabase();
    }

    public ArrayList<NumBloqueado> carregarNumIndesejados(){
        ArrayList<NumBloqueado> arrayList = new ArrayList<>();

        try{
            Cursor cursor = database.query(NOME_TABELA, new String[]{COL_ID, COL_NOME, COL_TEL},
                    null, null, null, null, COL_NOME+" ASC");

            indexId = cursor.getColumnIndex(COL_ID);
            indexNome = cursor.getColumnIndex(COL_NOME);
            indexTel = cursor.getColumnIndex(COL_TEL);

            cursor.moveToNext();
            while(cursor != null){
                NumBloqueado num = new NumBloqueado();

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

    public NumBloqueado adicionarNumero(NumBloqueado numBloqueado){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOME, numBloqueado.getNome());
        contentValues.put(COL_TEL, numBloqueado.getTelefone());

        try{
            long id = database.insert(BancoDeDados.NOME_TABELA, null, contentValues);
            numBloqueado.setId(id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return numBloqueado;
    }

    public boolean removerNumero(long id){

        try{
            database.delete(BancoDeDados.NOME_TABELA, "id = '"+id+"'", null);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean editarNumero(NumBloqueado numBloqueado){

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID, numBloqueado.getId());
        contentValues.put(COL_NOME, numBloqueado.getNome());
        contentValues.put(COL_TEL, numBloqueado.getTelefone());

        try{
            database.update(BancoDeDados.NOME_TABELA, contentValues, COL_ID+"="+ numBloqueado.getId(), null);
            return true;
        }catch (Exception e){
            return false;

        }
    }
}
