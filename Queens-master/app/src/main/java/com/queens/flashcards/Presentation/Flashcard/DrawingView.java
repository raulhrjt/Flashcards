package com.queens.flashcards.Presentation.Flashcard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {
    private Bitmap bitmap;
    private Paint paint;
    private Paint bitmapPaint;
    private Path path;
    private Canvas canvas;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    public DrawingView(Context context) {
        super(context);

        path = new Path();
        bitmapPaint = new Paint(Paint.DITHER_FLAG);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(5);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        path = new Path();
        bitmapPaint = new Paint(Paint.DITHER_FLAG);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }

        return true;
    }

    /** Handles the initial touch down
     *
     * @param x - Width position
     * @param y - Height position
     */
    private void touchStart(float x, float y) {
        path.reset();
        path.moveTo(x, y);
        mX = x;
        mY = y;
    }

    /** Handles the touch being moved
     *
     * @param x - Width position
     * @param y - Height position
     */
    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    /** Handles the initial touch up, applies the Path to the canvas */
    private void touchUp() {
        path.lineTo(mX, mY);
        canvas.drawPath(path, paint);
        path.reset();
    }

    /** Switches the current paint to the passed in color
     *
     * @param color - color ID to switch to
     */
    public void switchColorTo(int color) {
        paint.setColor(color);
    }

    /** Decreases the paints stroke with by 0.5 */
    public void decreaseStrokeWidth() {
        float currWidth = paint.getStrokeWidth();
        if(currWidth >= 0.5f) {
            currWidth -= 0.5f;

            paint.setStrokeWidth(currWidth);
        }
    }

    /** Increases the paints stroke with by 0.5 */
    public void increaseStrokeWidth() {
        float currWidth = paint.getStrokeWidth();
        currWidth += 0.5f;

        paint.setStrokeWidth(currWidth);
    }

    public void setBitmap(Bitmap bitmap) {
        if(bitmap != null) {
            this.bitmap = bitmap;
            Drawable d = new BitmapDrawable(getResources(), bitmap);
            this.setBackground(d);
        }
    }
}
