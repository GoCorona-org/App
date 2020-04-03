package simplifii.framework.exceptionhandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class Builder {

	public static void showAlert(Context callingPageRef, String title) {
		AlertDialog.Builder alertbox = new AlertDialog.Builder(callingPageRef);
		// Set the message to display
		alertbox.setCancelable(false);
		alertbox.setMessage(title);
		alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				// Toast.makeText(callingPageRef.getApplicationContext(),
				// message2, Toast.LENGTH_LONG).show();
				arg0.dismiss();
			}
		});
		alertbox.show();
	}

	public static void showAlertWithActivityCall(final Context callingPageRef,
			String title, String message, final Intent classIntent)

	{
		final AlertDialog.Builder alert = new AlertDialog.Builder(
				callingPageRef);
		// Set the message to display
		alert.setCancelable(false);
		alert.setTitle(title);
		alert.setMessage(message);

		alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// cancelled
				// new OpenActivity(callingPageRef, classIntent);

				callingPageRef.startActivity(classIntent);
				Activity ac = (Activity) callingPageRef;
				ac.finish();
			}
		});
		alert.show();

	}

	public static void showAlertWithNegativeButton(Context callingPageRef,
			String title, String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(callingPageRef);
		alert.setCancelable(false);
		alert.setTitle(title);
		alert.setMessage(message);

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// cancelled
					}
				});
		alert.show();
	}

	public static void showAlertWithPositiveButton(Context callingPageRef,
			String title, String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(callingPageRef);
		alert.setCancelable(false);
		alert.setTitle(title);
		alert.setMessage(message);

		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// cancelled
			}
		});
		alert.show();
	}

}
