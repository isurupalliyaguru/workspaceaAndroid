package com.almondmendoza.drawings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.almondmendoza.R;
import com.almondmendoza.drawings.brush.Brush;
import com.almondmendoza.drawings.brush.CircleBrush;
import com.almondmendoza.drawings.brush.PenBrush;

import java.io.File;
import java.io.FileOutputStream;


/**
 * Created by IntelliJ IDEA.
 * User: almondmendoza
 * Date: 07/11/2010
 * Time: 2:14 AM
 * Link: http://www.tutorialforandroid.com/
 */
@SuppressLint("NewApi")
public class DrawingActivity extends Activity implements View.OnTouchListener{
    private DrawingSurface drawingSurface;
    private DrawingPath currentDrawingPath;
    private Paint currentPaint;

    private Button redoBtn;
    private Button undoBtn;

    private Brush currentBrush;

    private File APP_FILE_PATH = new File("/sdcard/TutorialForAndroidDrawings");


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing_activity);

        setCurrentPaint();
        currentBrush = new PenBrush();
        
        drawingSurface = (DrawingSurface) findViewById(R.id.drawingSurface);
        drawingSurface.setOnTouchListener(this);

        redoBtn = (Button) findViewById(R.id.redoBtn);
        undoBtn = (Button) findViewById(R.id.undoBtn);

        redoBtn.setEnabled(false);
        undoBtn.setEnabled(false);
    }

    private void setCurrentPaint(){
        currentPaint = new Paint();
        currentPaint.setDither(true);
        currentPaint.setColor(0xFFFFFF00);
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);
        currentPaint.setStrokeCap(Paint.Cap.ROUND);
        currentPaint.setStrokeWidth(3);

    }




    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            currentDrawingPath = new DrawingPath();
            currentDrawingPath.paint = currentPaint;
            currentDrawingPath.path = new Path();
            currentBrush.mouseDown(currentDrawingPath.path, motionEvent.getX(), motionEvent.getY());


        }else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
            currentBrush.mouseMove( currentDrawingPath.path, motionEvent.getX(), motionEvent.getY() );

        }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            currentBrush.mouseUp( currentDrawingPath.path, motionEvent.getX(), motionEvent.getY() );
            

            drawingSurface.addDrawingPath(currentDrawingPath);
            drawingSurface.isDrawing = true;
            undoBtn.setEnabled(true);
            redoBtn.setEnabled(false);
        }

        return true;
    }


//    public void onClick(View view){
//        switch (view.getId()){
//            case R.id.colorRedBtn:
//                currentPaint = new Paint();
//                currentPaint.setDither(true);
//                currentPaint.setColor(0xFFFF0000);
//                currentPaint.setStyle(Paint.Style.STROKE);
//                currentPaint.setStrokeJoin(Paint.Join.ROUND);
//                currentPaint.setStrokeCap(Paint.Cap.ROUND);
//                currentPaint.setStrokeWidth(3);
//            break;
//            case R.id.colorBlueBtn:
//                currentPaint = new Paint();
//                currentPaint.setDither(true);
//                currentPaint.setColor(0xFF00FF00);
//                currentPaint.setStyle(Paint.Style.STROKE);
//                currentPaint.setStrokeJoin(Paint.Join.ROUND);
//                currentPaint.setStrokeCap(Paint.Cap.ROUND);
//                currentPaint.setStrokeWidth(3);
//            break;
//            case R.id.colorGreenBtn:
//                currentPaint = new Paint();
//                currentPaint.setDither(true);
//                currentPaint.setColor(0xFF0000FF);
//                currentPaint.setStyle(Paint.Style.STROKE);
//                currentPaint.setStrokeJoin(Paint.Join.ROUND);
//                currentPaint.setStrokeCap(Paint.Cap.ROUND);
//                currentPaint.setStrokeWidth(3);
//            break;
//
//            case R.id.undoBtn:
//                drawingSurface.undo();
//                if( drawingSurface.hasMoreUndo() == false ){
//                    undoBtn.setEnabled( false );
//                }
//                redoBtn.setEnabled( true );
//            break;
//
//            case R.id.redoBtn:
//                drawingSurface.redo();
//                if( drawingSurface.hasMoreRedo() == false ){
//                    redoBtn.setEnabled( false );
//                }
//
//                undoBtn.setEnabled( true );
//            break;
//            case R.id.saveBtn:
//                final Activity currentActivity  = this;
//                Handler saveHandler = new Handler(){
//                    @Override
//                    public void handleMessage(Message msg) {
//                        final AlertDialog alertDialog = new AlertDialog.Builder(currentActivity).create();
//                        alertDialog.setTitle("Saved 1");
//                        alertDialog.setMessage("Your drawing had been saved :)");
//                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                return;
//                            }
//                        });
//                        alertDialog.show();
//                    }
//                } ;
//               new ExportBitmapToFile(this,saveHandler, drawingSurface.getBitmap()).execute();
//            break;
//            case R.id.circleBtn:
//                currentBrush = new CircleBrush();
//            break;
//            case R.id.pathBtn:
//                currentBrush = new PenBrush();
//            break;
//        }
//    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
         case R.id.colorRedBtn:
             currentPaint = new Paint();
             currentPaint.setDither(true);
             currentPaint.setColor(0xFFFF0000);
             currentPaint.setStyle(Paint.Style.STROKE);
             currentPaint.setStrokeJoin(Paint.Join.ROUND);
             currentPaint.setStrokeCap(Paint.Cap.ROUND);
             currentPaint.setStrokeWidth(3);
         break;
         case R.id.colorBlueBtn:
             currentPaint = new Paint();
             currentPaint.setDither(true);
             currentPaint.setColor(0xFF00FF00);
             currentPaint.setStyle(Paint.Style.STROKE);
             currentPaint.setStrokeJoin(Paint.Join.ROUND);
             currentPaint.setStrokeCap(Paint.Cap.ROUND);
             currentPaint.setStrokeWidth(3);
         break;
         case R.id.colorGreenBtn:
             currentPaint = new Paint();
             currentPaint.setDither(true);
             currentPaint.setColor(0xFF0000FF);
             currentPaint.setStyle(Paint.Style.STROKE);
             currentPaint.setStrokeJoin(Paint.Join.ROUND);
             currentPaint.setStrokeCap(Paint.Cap.ROUND);
             currentPaint.setStrokeWidth(3);
         break;

         case R.id.undoBtn:
             drawingSurface.undo();
             if( drawingSurface.hasMoreUndo() == false ){
                 undoBtn.setEnabled( false );
             }
             redoBtn.setEnabled( true );
         break;

         case R.id.redoBtn:
             drawingSurface.redo();
             if( drawingSurface.hasMoreRedo() == false ){
                 redoBtn.setEnabled( false );
             }

             undoBtn.setEnabled( true );
         break;
         case R.id.saveBtn:
             final Activity currentActivity  = this;
             Handler saveHandler = new Handler(){
                 @Override
                 public void handleMessage(Message msg) {
                     final AlertDialog alertDialog = new AlertDialog.Builder(currentActivity).create();
                     alertDialog.setTitle("Saved 1");
                     alertDialog.setMessage("Your drawing had been saved :)");
                     alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int which) {
                             return;
                         }
                     });
                     alertDialog.show();
                 }
             } ;
            new ExportBitmapToFile(this,saveHandler, drawingSurface.getBitmap()).execute();
         break;
         case R.id.circleBtn:
             currentBrush = new CircleBrush();
         break;
         case R.id.pathBtn:
             currentBrush = new PenBrush();
         break;
     }
		return true;
	}


    private class ExportBitmapToFile extends AsyncTask<Intent,Void,Boolean> {
        private Context mContext;
        private Handler mHandler;
        private Bitmap nBitmap;

        public ExportBitmapToFile(Context context,Handler handler,Bitmap bitmap) {
            mContext = context;
            nBitmap = bitmap;
            mHandler = handler;
        }

        @Override
        protected Boolean doInBackground(Intent... arg0) {
            try {
                if (!APP_FILE_PATH.exists()) {
                    APP_FILE_PATH.mkdirs();
                }

                final FileOutputStream out = new FileOutputStream(new File(APP_FILE_PATH + "/myAwesomeDrawing.png"));
                nBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
                return true;
            }catch (Exception e) {
                e.printStackTrace();
            }
            //mHandler.post(completeRunnable);
            return false;
        }


        @Override
        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if ( bool ){
                mHandler.sendEmptyMessage(1);
            }
        }
    }
}