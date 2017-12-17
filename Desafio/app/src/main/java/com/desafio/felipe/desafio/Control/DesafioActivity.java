package com.desafio.felipe.desafio.Control;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.desafio.felipe.desafio.Model.BancoDeDados;
import com.desafio.felipe.desafio.Model.NumIndesejado;
import com.desafio.felipe.desafio.Model.NumIndesejadoDAO;
import com.desafio.felipe.desafio.R;

import java.util.ArrayList;

public class DesafioActivity extends AppCompatActivity {

    private ImageButton adicionar;
    private ImageButton carregar;

    private ListView listView;

    private ArrayList<NumIndesejado> list;

    private NumIndesejadoDAO numIndesejadoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio);

        permissoes();


        referencias();
        listeners();

        numIndesejadoDAO = new NumIndesejadoDAO(this);

        carregarLista();

    }

    private void permissoes() {
        if(ContextCompat.checkSelfPermission(DesafioActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(DesafioActivity.this,
                    Manifest.permission.READ_PHONE_STATE)){
                ActivityCompat.requestPermissions(DesafioActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }else{
                ActivityCompat.requestPermissions(DesafioActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
        }
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
        list = numIndesejadoDAO.carregarNumIndesejados();

        AdapterBlackList adapter = new AdapterBlackList(this, list);
        listView.setAdapter(adapter);
    }
}
