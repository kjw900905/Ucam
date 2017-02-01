package com.example.Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class DrawTimeTable extends View {
    public DrawTimeTable(Context context) {
        super(context);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float scaleX = canvas.getWidth() / 1080f;
        float scaleY = canvas.getHeight() / 1920f;
        canvas.scale(scaleX, scaleY);

        Paint paint = new Paint();
    }
}
