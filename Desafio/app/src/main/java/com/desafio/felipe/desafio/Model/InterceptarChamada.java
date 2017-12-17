package com.desafio.felipe.desafio.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by felipe on 16/12/17.
 */

public class InterceptarChamada extends BroadcastReceiver {

    private NumIndesejadoDAO numIndesejadoDAO;

    @Override
    public void onReceive(final Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.PHONE_STATE")){
            String num = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            //Se o numero estiver no banco chama a função de bloqueio

            numIndesejadoDAO = new NumIndesejadoDAO(context);
            ArrayList<NumIndesejado> arrayList = numIndesejadoDAO.carregarNumIndesejados();


            for (int i = 0; i < arrayList.size(); i++) {
                if(num.contains(arrayList.get(i).getTelefone())){
                    bloquearChamada(context);
                    break;
                }
            }

        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void bloquearChamada(Context context) {
        ITelephony iTelephony;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try{
            Class classe = Class.forName(telephonyManager.getClass().getName());
            Method method= classe.getDeclaredMethod("getITelephony");
            method.setAccessible(true);
            iTelephony = (ITelephony) method.invoke(telephonyManager);
            iTelephony.endCall();

        }catch (Exception e ){
            e.printStackTrace();
        }
    }


}
