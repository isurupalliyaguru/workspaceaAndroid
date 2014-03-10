package com.ipaintpad.drawings.brush;

import android.graphics.Color;
import android.graphics.Path;


public class TriangleBrush extends Brush{

    @Override
    public void mouseMove(Path path, float x, float y) {
    	path.moveTo(x, y);
    	path.lineTo(x-40,y+60);
    	path.lineTo(x+40, y+60);
    	path.lineTo(x, y);
    	
    }
    
    public void mouseDown(Path path, float x, float y) {
    	path.moveTo(x, y);
    	path.lineTo(x-40,y+60);
    	path.lineTo(x+40, y+60);
    	path.lineTo(x, y);
    }
}
