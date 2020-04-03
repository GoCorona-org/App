package simplifii.framework.utility;


import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

public class CustomContentObserver extends ContentObserver {
	private int observerType;
	private ObserverListener listener;

	public CustomContentObserver(Handler handler, int observerType,
			ObserverListener listener) {
		super(handler);
		this.observerType = observerType;

	}


	@Override
	public void onChange(boolean selfChange) {
		this.onChange(selfChange, null);
		if (listener != null)
			listener.onChange(selfChange, null, observerType);
	}

	@Override
	public void onChange(boolean selfChange, Uri uri) {
		if (listener != null)
			listener.onChange(selfChange, null, observerType);
	}

	public static interface ObserverListener {
		public void onChange(boolean selfChange, Uri uri, int observerType);
	}
}