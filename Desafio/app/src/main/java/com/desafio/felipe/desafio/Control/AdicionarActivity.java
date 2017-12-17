package com.desafio.felipe.desafio.Control;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.desafio.felipe.desafio.Model.BancoDeDados;
import com.desafio.felipe.desafio.Model.NumIndesejado;
import com.desafio.felipe.desafio.Model.NumIndesejadoDAO;
import com.desafio.felipe.desafio.R;

public class AdicionarActivity extends AppCompatActivity {

    private Bundle bundle;

    private EditText nome;
    private EditText telefone;

    private Button adicionar;

    private long id;
    private String editNome;
    private String editTel;

    private NumIndesejadoDAO numIndesejadoDAO;
    private boolean editar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        referencias();
        listeners();

        numIndesejadoDAO = new NumIndesejadoDAO(this);

        bundle = new Bundle();
        bundle = getIntent().getExtras();

        if(bundle != null){
            editar();
        }
    }

    private void editar() {
        id = bundle.getLong("id");
        editNome = bundle.getString("nome");
        editTel = bundle.getString("telefone");

        nome.setText(editNome);
        telefone.setText(editTel);
        adicionar.setText("Editar");

        editar = true;
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

                final NumIndesejado numIndesejado = new NumIndesejado();
                numIndesejado.setNome(nome.getText().toString());
                numIndesejado.setTelefone(telefone.getText().toString());

                if(editar){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdicionarActivity.this);
                    builder.setTitle("Atenção");
                    builder.setMessage("Confirmar edição?");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            numIndesejado.setId(id);
                            if(numIndesejadoDAO.editarNumero(numIndesejado)){
                                Intent proximaPagina = new Intent(AdicionarActivity.this, DesafioActivity.class);
                                startActivity(proximaPagina);
                                finish();
                            }
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.create();
                    builder.show();


                }else{
                    if(numIndesejadoDAO.adicionarNumero(numIndesejado).getId() > 0){

                        Intent proximaPagina = new Intent(AdicionarActivity.this, DesafioActivity.class);
                        startActivity(proximaPagina);
                        finish();
                    }else{
                        Toast.makeText(AdicionarActivity.this, "Erro ao adicionar número!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
