package com.desafio.felipe.desafio.Control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.desafio.felipe.desafio.Model.NumBloqueado;
import com.desafio.felipe.desafio.Model.NumBloqueadoDAO;
import com.desafio.felipe.desafio.R;

import java.util.ArrayList;

/**
 * Created by felipe on 15/12/17.
 */

public class ListaNegraAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<NumBloqueado> list;
    private LayoutInflater inflater;
    private NumBloqueadoDAO numBloqueadoDAO;

    public ListaNegraAdapter(Context context, ArrayList<NumBloqueado> list) {
        this.context = context;
        this.list = list;

        inflater = LayoutInflater.from(context);
        numBloqueadoDAO = new NumBloqueadoDAO(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NumBloqueado getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(R.layout.agenda, null);
        }

        TextView nome = (TextView) convertView.findViewById(R.id.nome);
        TextView tele = (TextView) convertView.findViewById(R.id.telefone);

        ImageButton editar = (ImageButton) convertView.findViewById(R.id.editar);
        ImageButton remover = (ImageButton) convertView.findViewById(R.id.remover);

        NumBloqueado ligacao = getItem(position);

        nome.setText(ligacao.getNome());
        tele.setText(ligacao.getTelefone());

        //Remove a cor de fundo do componente
        editar.getBackground().setAlpha(0);
        remover.getBackground().setAlpha(0);

        listeners(remover, editar, ligacao);

        return convertView;
    }

    public void listeners(ImageButton remover, ImageButton editar, final NumBloqueado num){
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent proximaPagina = new Intent(context, AdicionarActivity.class);

                proximaPagina.putExtra("id", num.getId());
                proximaPagina.putExtra("nome", num.getNome());
                proximaPagina.putExtra("telefone", num.getTelefone());

                context.startActivity(proximaPagina);
                ((Activity)context).finish();
            }
        });

        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Confirmar exclusão?");
                builder.setTitle("Atenção");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        numBloqueadoDAO.removerNumero(num.getId());
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create();
                builder.show();
            }
        });
    }


}
