package com.desafio.felipe.desafio.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.desafio.felipe.desafio.R;

import java.util.ArrayList;

/**
 * Created by felipe on 15/12/17.
 */

public class AdapterBlackList extends BaseAdapter{

    private Context context;
    private ArrayList<NumIndesejado> list;
    private LayoutInflater inflater;

    public AdapterBlackList(Context context, ArrayList<NumIndesejado> list) {
        this.context = context;
        this.list = list;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NumIndesejado getItem(int position) {
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

        NumIndesejado ligacao = getItem(position);

        nome.setText(ligacao.getNome());
        tele.setText(ligacao.getTelefone());

        editar.getBackground().setAlpha(0);
        remover.getBackground().setAlpha(0);

        return convertView;
    }
}
