package simplifii.framework.utility;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nitin on 30/09/15.
 */
public class SMSUtil {
    Context ctx;

    public SMSUtil(Context ctx) {
        this.ctx = ctx;
    }

    public void sendSMS(final String phoneNumber, final String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
        Log.d("SMS", phoneNumber + "," + message);
        PendingIntent sentPI = PendingIntent.getBroadcast(ctx, 0, new Intent(
                SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(ctx, 0,
                new Intent(DELIVERED), 0);

        // ---when the SMS has been sent---
//        ctx.registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode()) {
//                    case Activity.RESULT_OK:
//                        ContentValues values = new ContentValues();
////                        for (int i = 0; i < MobNumber.size() - 1; i++) {
//                        values.put("address", phoneNumber);
//                        // txtPhoneNo.getText().toString());
//                        values.put("body", message);
////                        }
//                        ctx.getContentResolver().insert(
//                                Uri.parse("content://sms/sent"), values);
////                        Toast.makeText(getBaseContext(), "SMS sent",
////                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
////                        Toast.makeText(getBaseContext(), "Generic failure",
////                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NO_SERVICE:
////                        Toast.makeText(getBaseContext(), "No service",
////                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
////                        Toast.makeText(getBaseContext(), "Null PDU",
////                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_RADIO_OFF:
////                        Toast.makeText(getBaseContext(), "Radio off",
////                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(SENT));
//
//        // ---when the SMS has been delivered---
//        ctx.registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode()) {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(getBaseContext(), "SMS delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Toast.makeText(getBaseContext(), "SMS not delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    private Context getBaseContext() {
        return ctx;
    }
}