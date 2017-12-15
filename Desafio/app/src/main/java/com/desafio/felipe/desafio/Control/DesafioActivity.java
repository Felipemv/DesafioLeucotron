package com.desafio.felipe.desafio.Control;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import com.desafio.felipe.desafio.Model.AdapterBlackList;
import com.desafio.felipe.desafio.Model.LigacaoBloqueada;
import com.desafio.felipe.desafio.R;

import java.util.ArrayList;

public class DesafioActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<LigacaoBloqueada> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio);

        listView = (ListView) findViewById(R.id.listView);

        try{
            SQLiteDatabase database = openOrCreateDatabase("app", MODE_PRIVATE, null);

            database.execSQL("DROP TABLE IF EXISTS teste");
            database.execSQL("CREATE TABLE IF NOT EXISTS teste(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, telefone VARCHAR)");

            database.execSQL("INSERT INTO teste(nome, telefone) VALUES('Felipe', '997577551')");
            database.execSQL("INSERT INTO teste(nome, telefone) VALUES('Tayná', '998287454')");

            Cursor cursor = database.rawQuery("SELECT id, nome, telefone FROM teste", null);

            int nome = cursor.getColumnIndex("nome");
            int tel = cursor.getColumnIndex("telefone");
            cursor.moveToFirst();

            LigacaoBloqueada ligacao;
            while (cursor != null){
                ligacao = new LigacaoBloqueada();

                ligacao.setNome(cursor.getString(nome));
                ligacao.setTelefone(cursor.getString(tel));

                list.add(ligacao);

                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        AdapterBlackList adapter = new AdapterBlackList(this, list);
        listView.setAdapter(adapter);
    }
}
