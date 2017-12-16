package com.desafio.felipe.desafio.Control;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.desafio.felipe.desafio.Model.AdapterBlackList;
import com.desafio.felipe.desafio.Model.BancoDeDados;
import com.desafio.felipe.desafio.Model.NumIndesejado;
import com.desafio.felipe.desafio.R;

import java.util.ArrayList;

public class DesafioActivity extends AppCompatActivity {

    private ImageButton adicionar;
    private ImageButton carregar;

    private ListView listView;

    private ArrayList<NumIndesejado> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio);

        SQLiteDatabase database = openOrCreateDatabase("app", MODE_PRIVATE, null);

        referencias();
        listeners();
        carregarLista();

    }

    //Adiciona a referencia dos componentes da Activity
    public void referencias(){

        adicionar = (ImageButton) findViewById(R.id.adicionar);
        carregar = (ImageButton) findViewById(R.id.carregar);

        listView = (ListView) findViewById(R.id.listView);

        //Remover o fundo dos ImageButtons
        adicionar.getBackground().setAlpha(0);
        carregar.getBackground().setAlpha(0);
    }

    //Adiciona os listeners aos componentes da Activity
    public void listeners(){
        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent proximaPagina = new Intent(DesafioActivity.this, AdicionarActivity.class);
                startActivity(proximaPagina);
                finish();
            }
        });

        carregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarLista();
            }
        });

    }

    //Carrega a agenda do banco de dados e coloca na ListViews
    public void carregarLista(){
        list = new BancoDeDados(this).carregarNumIndesejados();

        AdapterBlackList adapter = new AdapterBlackList(this, list);
        listView.setAdapter(adapter);
    }
}
