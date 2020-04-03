package simplifii.framework.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.util.LruCache;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import simplifii.framework.R;

public class VideoThumbnailUrlLoader {

    private WeakReference<Context> contextWeakReference;
    private WeakReference<ImageView> imageViewWeakReference;
    private String videoUrl;
    private static LruCache<String, Bitmap> imageCache;
    private static VideoThumbnailUrlLoader loader;

    public static VideoThumbnailUrlLoader getInstance() {
        if (loader == null) {
            loader = new VideoThumbnailUrlLoader();
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;
            imageCache = new LruCache<>(cacheSize);
        }
        return loader;
    }

    public void display(ImageView imageView, String videoUrl) {
        if (imageCache.get(videoUrl) != null) {
            Logger.debug("VideoUrL", "IW:" + imageView.getWidth() + ", IH:" + imageView.getHeight());
            Logger.debug("VideoUrL", "BW:" + imageCache.get(videoUrl).getWidth() + ", BH:" + imageCache.get(videoUrl).getHeight());
            setImage(imageView, imageCache.get(videoUrl));
        } else {
            new ThumbLoader(imageView, videoUrl).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private void setImage(ImageView imageView, Bitmap bitmap) {
//        RoundedBitmapDrawable roundDrawable = RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), bitmap);
//        roundDrawable.setCornerRadius(imageView.getContext().getResources().getDimensionPixelOffset(R.dimen.card_corner_radius_media));
////            imageView.setImageBitmap(imageCache.get(videoUrl));
//        imageView.setImageDrawable(roundDrawable);
//        imageView.requestLayout();
        imageView.setImageBitmap(bitmap);
    }

    private class ThumbLoader extends AsyncTask<Void, Void, Bitmap> {

        private WeakReference<ImageView> imageViewWeakReference;
        private String videoUrl;
        private int width;
        private int height;

        public ThumbLoader(ImageView imageView, String videoUrl) {
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
            width = imageView.getContext().getResources().getDimensionPixelOffset(R.dimen.wt_media_tile) / 2;
            height = imageView.getContext().getResources().getDimensionPixelOffset(R.dimen.ht_media_tile);
            this.videoUrl = videoUrl;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap thumb = null;
            try {
                thumb = retriveVideoFrameFromVideo(videoUrl, width, height);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return thumb;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageCache.put(videoUrl, result);
                if (imageViewWeakReference.get() != null) {
                    setImage(imageViewWeakReference.get(), result);
                }
            } else {

            }
        }
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath, int width, int height)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            return mediaMetadataRetriever.getFrameAtTime();

//            return getResizedBitmap(bitmap, width, height);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
    }

    private static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Logger.debug("VideoUrL", "OBW:" + bm.getWidth() + ", OBH:" + bm.getHeight());
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, newWidth, newHeight);
        Logger.debug("VideoUrL", "NBW:" + resizedBitmap.getWidth() + ", NBH:" + resizedBitmap.getHeight());
        bm.recycle();
        return resizedBitmap;
    }
}
