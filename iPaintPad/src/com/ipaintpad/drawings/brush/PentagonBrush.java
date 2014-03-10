package com.ipaintpad.drawings.brush;

import android.graphics.Color;
import android.graphics.Path;


public class PentagonBrush extends Brush{

    @Override
    public void mouseMove(Path path, float x, float y) {
    	path.moveTo(x, y);
    	path.lineTo(x,y+30);
    	path.lineTo(x+20, y+40);
    	path.lineTo(x+40, y+30);
    	path.lineTo(x+40, y);
    	path.lineTo(x, y);
    }
    
    public void mouseDown(Path path, float x, float y) {
    	path.moveTo(x, y);
    	path.lineTo(x,y+30);
    	path.lineTo(x+20, y+40);
    	path.lineTo(x+40, y+30);
    	path.lineTo(x+40, y);
    	path.lineTo(x, y);
    }
}
