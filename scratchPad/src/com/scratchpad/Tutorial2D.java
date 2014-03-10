package com.scratchpad;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

import com.scratchpad.Tutorial2D.GraphicObject;

public class Tutorial2D extends Activity {

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tutorial2_d);
//    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new Panel(this));
    }
 
    class Panel extends SurfaceView implements SurfaceHolder.Callback {
        private TutorialThread _thread;
        private ArrayList<GraphicObject> _graphics = new ArrayList<GraphicObject>();
 
        public Panel(Context context) {
            super(context);
            getHolder().addCallback(this);
            _thread = new TutorialThread(getHolder(), this);
            setFocusable(true);
        }
 
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            synchronized (_thread.getSurfaceHolder()) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    GraphicObject graphic = new GraphicObject(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
                    graphic.getCoordinates().setX((int) event.getX() - graphic.getGraphic().getWidth() / 2);
                    graphic.getCoordinates().setY((int) event.getY() - graphic.getGraphic().getHeight() / 2);
                    _graphics.add(graphic);
                }
                return true;
            }
        }
 
        @Override
        public void onDraw(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
            Bitmap bitmap;
            GraphicObject.Coordinates coords;
            for (GraphicObject graphic : _graphics) {
                bitmap = graphic.getGraphic();
                coords = graphic.getCoordinates();
                canvas.drawBitmap(bitmap, coords.getX(), coords.getY(), null);
            }
        }
 
        //@Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // TODO Auto-generated method stub
        }
 
        //@Override
        public void surfaceCreated(SurfaceHolder holder) {
            _thread.setRunning(true);
            _thread.start();
        }
 
        //@Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // simply copied from sample application LunarLander:
            // we have to tell thread to shut down & wait for it to finish, or else
            // it might touch the Surface after we return and explode
            boolean retry = true;
            _thread.setRunning(false);
            while (retry) {
                try {
                    _thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    // we will try it again and again...
                }
            }
        }
    }
 
    class TutorialThread extends Thread {
        private SurfaceHolder _surfaceHolder;
        private Panel _panel;
        private boolean _run = false;
 
        public TutorialThread(SurfaceHolder surfaceHolder, Panel panel) {
            _surfaceHolder = surfaceHolder;
            _panel = panel;
        }
 
        public void setRunning(boolean run) {
            _run = run;
        }
 
        public SurfaceHolder getSurfaceHolder() {
            return _surfaceHolder;
        }
 
        @Override
        public void run() {
            Canvas c;
            while (_run) {
                c = null;
                try {
                    c = _surfaceHolder.lockCanvas(null);
                    synchronized (_surfaceHolder) {
                        _panel.onDraw(c);
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        _surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }
 
    class GraphicObject {
        /**
         * Contains the coordinates of the graphic.
         */
        public class Coordinates {
            private int _x = 100;
            private int _y = 0;
 
            public int getX() {
                return _x + _bitmap.getWidth() / 2;
            }
 
            public void setX(int value) {
                _x = value - _bitmap.getWidth() / 2;
            }
 
            public int getY() {
                return _y + _bitmap.getHeight() / 2;
            }
 
            public void setY(int value) {
                _y = value - _bitmap.getHeight() / 2;
            }
 
            public String toString() {
                return "Coordinates: (" + _x + "/" + _y + ")";
            }
        }
 
        private Bitmap _bitmap;
        private Coordinates _coordinates;
 
        public GraphicObject(Bitmap bitmap) {
            _bitmap = bitmap;
            _coordinates = new Coordinates();
        }
 
        public Bitmap getGraphic() {
            return _bitmap;
        }
 
        public Coordinates getCoordinates() {
            return _coordinates;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tutorial2_d, menu);
        return true;
    }
}
