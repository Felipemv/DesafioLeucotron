package com.android.internal.telephony;

// Need for Android Telephony
// Please keep the package, class and method names as it is.
// Arquivo de controle de serviço (estado do telefone)

  interface ITelephony {

    boolean endCall();

    void answerRingingCall();

    void silenceRinger();

  }