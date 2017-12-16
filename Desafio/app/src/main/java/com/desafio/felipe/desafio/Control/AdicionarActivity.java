package com.desafio.felipe.desafio.Control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.desafio.felipe.desafio.Model.BancoDeDados;
import com.desafio.felipe.desafio.Model.NumIndesejado;
import com.desafio.felipe.desafio.R;

public class AdicionarActivity extends AppCompatActivity {

    private EditText nome;
    private EditText telefone;

    private Button adicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        referencias();
        listeners();
    }

    //Adiciona a referencia dos componentes da Activity
    public void referencias(){

        nome = (EditText) findViewById(R.id.addNome);
        telefone = (findViewById(R.id.addTelefone));

        adicionar = (Button) findViewById(R.id.addBlackList);
    }

    //Adiciona os listeners aos componentes da Activity
    public void listeners(){
        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NumIndesejado numIndesejado = new NumIndesejado();
                numIndesejado.setNome(nome.getText().toString());
                numIndesejado.setTelefone(telefone.getText().toString());

                if(new BancoDeDados(getApplicationContext()).adicionarNumero(numIndesejado)){
                    Intent proximaPagina = new Intent(AdicionarActivity.this, DesafioActivity.class);
                    startActivity(proximaPagina);
                    finish();
                }
            }
        });
    }
}
