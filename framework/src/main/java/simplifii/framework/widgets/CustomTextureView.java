package simplifii.framework.widgets;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import java.io.IOException;

public class CustomTextureView extends TextureView implements TextureView.SurfaceTextureListener {
    private boolean isMpPrepared;
    private String url;
    private MediaPlayer mp;
    private Surface surface;
    private OnVideoPreparedListener videoPreparedListener;
    private OnVideoCompleteListener videoCompleteListener;
    private OnSeekCompleteListener seekCompleteListener;
    private OnInfoListener infoListener;

    public CustomTextureView(Context context) {
        super(context);
    }

    public CustomTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void loadVideo(String localPath) {
        this.url = localPath;
        if (this.isAvailable()) {
            prepareVideo(getSurfaceTexture());
        }

        setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, int width, int height) {
        isMpPrepared = false;
        prepareVideo(surface);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mp != null) {
            if (mp.isPlaying())
                mp.stop();
            mp.reset();
            mp.release();
            mp = null;
            isMpPrepared = false;
        }

        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    public void prepareVideo(SurfaceTexture t) {
        try {
            this.surface = new Surface(t);
            mp = new MediaPlayer();
            mp.setSurface(this.surface);

            mp.setDataSource(url);
            mp.prepareAsync();

            mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mediaPlayer) {
                    mp = mediaPlayer;
                    if (seekCompleteListener != null) {
                        seekCompleteListener.onSeekCompleted(mediaPlayer);
                    }
                   /* if (isMute)
                        mp.setVolume(0f, 0f);
                    if (isAutoPlay)
                        mp.start();
                    else
                        mp.pause();*/
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mp = mediaPlayer;
                    if (videoCompleteListener != null) {
                        videoCompleteListener.onVideoCompleted(mediaPlayer);
                    }
                }
            });
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    isMpPrepared = true;
                    mp = mediaPlayer;
                    mp.setLooping(true);
                    /*if (isMute)
                        mp.setVolume(0f, 0f);
                    if (position > 0)
                        mp.seekTo(position);*/
//                    else {
                    if (videoPreparedListener != null) {
                        videoPreparedListener.onVideoPrepared(mediaPlayer);
                    }
                        /*if (isAutoPlay)
                            mp.start();
                        else
                            mp.pause();*/
//                    }
                }

            });
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    public void startPlay() {
        if (mp != null && !mp.isPlaying()) {
            mp.start();
        }

    }

    public void startPlayingAt(int i) {
        if (mp != null && isMpPrepared) {
            mp.start();
            if (i > 0)
                mp.seekTo(i);
        }
    }

    public int getPosition() {
        if (mp != null)
            return mp.getCurrentPosition();
        else
            return 0;

    }

    public void pausePlay() {
        if (mp != null && isMpPrepared)
            mp.pause();
    }

    public boolean isPlaying() {
        if (mp != null && mp.isPlaying()) {
            return true;
        }
        return false;
    }

    public boolean isMpPrepared() {
        if (mp != null && isMpPrepared) {
            return true;
        }
        return false;
    }

    public void stopPlay() {
        if (mp != null)
            mp.stop();
    }

    public void changePlayState() {
        if (mp != null) {
            if (mp.isPlaying())
                mp.pause();
            else
                mp.start();
        }
    }

    public void setOnVideoPreparedListener(OnVideoPreparedListener videoPreparedListener) {
        this.videoPreparedListener = videoPreparedListener;

    }

    public void setOnVideoCompleteListener(OnVideoCompleteListener videoCompleteListener) {
        this.videoCompleteListener = videoCompleteListener;

    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener seekCompleteListener) {
        this.seekCompleteListener = seekCompleteListener;

    }

    public void setOnInfoListener(OnInfoListener infoListener) {
        this.infoListener = infoListener;

    }


    public interface OnVideoPreparedListener {
        void onVideoPrepared(MediaPlayer mp);
    }

    public interface OnVideoCompleteListener {
        void onVideoCompleted(MediaPlayer mp);
    }

    public interface OnSeekCompleteListener {
        void onSeekCompleted(MediaPlayer mp);
    }

    public interface OnInfoListener {
        void onInfo(MediaPlayer mp);
    }
}