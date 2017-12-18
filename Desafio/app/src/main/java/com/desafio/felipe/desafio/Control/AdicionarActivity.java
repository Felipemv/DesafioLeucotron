package com.desafio.felipe.desafio.Control;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.desafio.felipe.desafio.Model.NumBloqueado;
import com.desafio.felipe.desafio.Model.NumBloqueadoDAO;
import com.desafio.felipe.desafio.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class AdicionarActivity extends AppCompatActivity {

    private Bundle bundle;

    private EditText nome;
    private EditText telefone;

    private Button adicionar;

    private long id;
    private String editNome;
    private String editTel;

    private NumBloqueadoDAO numBloqueadoDAO;
    private boolean editar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        //Habilita o botão de up do toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        referencias();
        listeners();

    }

    @Override
    protected void onResume() {
        super.onResume();

        numBloqueadoDAO = new NumBloqueadoDAO(this);

        bundle = new Bundle();
        bundle = getIntent().getExtras();

        if(bundle != null){
            editar();
        }
    }

    //Recolhe as informações de edição passadas pelo Bundle
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

        //Criando máscara para o número do telefone
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(telefone, smf);
        telefone.addTextChangedListener(mtw);
    }

    //Adiciona os listeners aos componentes da Activity
    public void listeners(){
        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NumBloqueado numBloqueado = new NumBloqueado();
                numBloqueado.setNome(nome.getText().toString());
                numBloqueado.setTelefone(telefone.getText().toString());

                if(nome.getText().toString().trim().equals("") ||
                        telefone.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos",
                            Toast.LENGTH_SHORT).show();

                }else if(telefone.getText().toString().length() < 10){
                    Toast.makeText(getApplicationContext(), "Número de telefone incorreto",
                            Toast.LENGTH_SHORT).show();

                }else{
                    if(editar){
                        confirmarEdição(numBloqueado);
                    }else{
                        if(numBloqueadoDAO.adicionarNumero(numBloqueado).getId() > 0){

                            Intent proximaPagina = new Intent(AdicionarActivity.this, DesafioActivity.class);
                            startActivity(proximaPagina);
                            finish();
                        }else{
                            Toast.makeText(AdicionarActivity.this, "Erro ao adicionar número!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }




            }
        });
    }

    //Apresenta um alerta para confirmação de edição ao usuário
    public void confirmarEdição(final NumBloqueado numBloqueado){

        AlertDialog.Builder builder = new AlertDialog.Builder(AdicionarActivity.this);
        builder.setTitle("Atenção");
        builder.setMessage("Confirmar edição?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                numBloqueado.setId(id);
                if(numBloqueadoDAO.editarNumero(numBloqueado)){
                    Intent proximaPagina = new Intent(AdicionarActivity.this, DesafioActivity.class);
                    startActivity(proximaPagina);
                    finish();
                }
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Cancela a edição
            }
        });

        builder.create();
        builder.show();
    }
}
