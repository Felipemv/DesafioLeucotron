package com.desafio.felipe.desafio.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by felipe on 16/12/17.
 */

public class InterceptarChamada extends BroadcastReceiver {

    private NumBloqueadoDAO numBloqueadoDAO;

    @Override
    public void onReceive(final Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.PHONE_STATE")){
            String num = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            //Verifica se o número que está ligando está no banco de dados
            numBloqueadoDAO = new NumBloqueadoDAO(context);
            ArrayList<NumBloqueado> arrayList = numBloqueadoDAO.carregarNumIndesejados();

            for (int i = 0; i < arrayList.size(); i++) {
                if(num.contains(arrayList.get(i).getTelefone().replaceAll("-", ""))){
                    bloquearChamada(context);
                    break;
                }
            }

        }

    }

    //Bloqueia a chamada se o número estiver no banco de dados
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void bloquearChamada(Context context) {
        ITelephony iTelephony;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try{
            //Invoca o método para finalizar a chamada
            Class classe = Class.forName(telephonyManager.getClass().getName());
            Method method= classe.getDeclaredMethod("getITelephony");
            method.setAccessible(true);
            iTelephony = (ITelephony) method.invoke(telephonyManager);
            iTelephony.endCall();
            Toast.makeText(context, "Ligação Bloqueada!", Toast.LENGTH_SHORT).show();

        }catch (Exception e ){
            e.printStackTrace();
        }
    }
}
