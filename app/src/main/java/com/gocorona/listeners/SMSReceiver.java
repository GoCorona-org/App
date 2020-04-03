package com.gocorona.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;

public class SMSReceiver extends BroadcastReceiver {
    private static SmsListener mListener;
    Boolean b;
    String abcd,xyz;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            if(smsMessage == null){
                continue;
            }
//            String sender = smsMessage.getDisplayOriginatingAddress();
            // b=sender.endsWith("WNRCRP");  //Just to fetch otp sent from WNRCRP
            String messageBody = smsMessage.getMessageBody();
            if (!TextUtils.isEmpty(messageBody)){
                abcd=messageBody.replaceAll("[^0-9]","");   // here abcd contains otp
//                which is in number format
                //Pass on the text to our listener.
                if(!TextUtils.isEmpty(abcd) && abcd.length()==6){
                    if (mListener !=null){
                        mListener.messageReceived(abcd);
                    }

                }
            }

        }
    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

    public static void unbindListener() {
        mListener = null;
    }

    public interface SmsListener{
        public void messageReceived(String messageText);}
}