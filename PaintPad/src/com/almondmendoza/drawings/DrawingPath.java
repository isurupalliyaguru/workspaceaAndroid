package com.almondmendoza.drawings;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;


public class DrawingPath implements ICanvasCommand{
    public Path path;
    public Paint paint;

    public void draw(Canvas canvas) {
        canvas.drawPath( path, paint );
    }

    public void undo() {
        //Todo this would be changed later
    }
}
