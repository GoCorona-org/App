package simplifii.framework.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.appcompat.widget.AppCompatImageView;

import simplifii.framework.R;

public class RoundRectCornerImageView extends AppCompatImageView {

    private float radius = 12.0f;
    private Path path;
    private RectF rect;

    public RoundRectCornerImageView(Context context) {
        this(context, null);
    }

    public RoundRectCornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        path = new Path();
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundRectCornerImageView, defStyle, 0);
        radius = attributes.getFloat(R.styleable.RoundRectCornerImageView_riv_radius, 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}