package com.ipaintpad.drawings.brush;

import android.graphics.Path;


public class SquareBrush extends Brush{

    @Override
    public void mouseMove(Path path, float x, float y) {
        path.addRect(x, y+10, x+10, y, Path.Direction.CW);
    }

}
