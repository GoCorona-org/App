package simplifii.framework.asyncmanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.util.HashMap;

public class ThumbnailExtractAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private final String videoUrl;
    private final ImageView mThumbnail;
    private final boolean mIsVideo;
//    private BaseActivity baseActivity;
//    private boolean isActivity;
//    private BaseFragment baseFragment;
    private MediaMetadataRetriever mmr;

    public ThumbnailExtractAsyncTask(String videoLocalUrl, ImageView thumbnail, boolean isVideo) {
        videoUrl = videoLocalUrl;
        mThumbnail = thumbnail;
        mIsVideo = isVideo;
//        this.isActivity = isActivity;
//        this.baseActivity = baseActivity;
        if (isVideo) {
            mmr = new MediaMetadataRetriever();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        if (isActivity) {
//            baseActivity.showProgressDialog();
//        } else {
//            baseFragment.showProgressBar();
//        }
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        if (!mIsVideo) {
            return getBitmap(videoUrl);
        } else {
            mmr.setDataSource(this.videoUrl, new HashMap<String, String>());
            return mmr.getFrameAtTime(5000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        }
    }

    @Override
    protected void onPostExecute(Bitmap thumb) {
//        if (isActivity) {
//            baseActivity.hideProgressBar();
//        } else {
//            baseFragment.hideProgressBar();
//        }
        if (thumb != null) {
            mThumbnail.setImageBitmap(thumb);
        }
    }

    private Bitmap getBitmap(String fileUrl) {
        mmr.setDataSource(fileUrl);
        byte[] data = mmr.getEmbeddedPicture();
        Bitmap bitmap = null;
        // convert the byte array to a bitmap
        if (data != null) {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        }
        return bitmap;
    }
}
