
package com.imarslander;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.imarslander.IMarsView.MarsThread;
import com.imarslander.R;


public class IMarsLander extends Activity { 
    private static final int MENU_EASY = 1;

    private static final int MENU_HARD = 2;

    private static final int MENU_MEDIUM = 3;

    private static final int MENU_PAUSE = 4;

    private static final int MENU_RESUME = 5;

    private static final int MENU_START = 6;

    private static final int MENU_STOP = 7;

    // this is the thread that handles the animation of the Mars lanader
    private MarsThread mMarsThread;

    /** A handle to the View in which the game is running. */
    private IMarsView mMarsView;
    
    MediaPlayer mp1;

    //creating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, MENU_START, 0, R.string.menu_start);
        menu.add(0, MENU_STOP, 0, R.string.menu_stop);
        menu.add(0, MENU_PAUSE, 0, R.string.menu_pause);
        menu.add(0, MENU_RESUME, 0, R.string.menu_resume);
        menu.add(0, MENU_EASY, 0, R.string.menu_easy);
        menu.add(0, MENU_MEDIUM, 0, R.string.menu_medium);
        menu.add(0, MENU_HARD, 0, R.string.menu_hard);

        return true;
    }
    

    //when the menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_START:
                mMarsThread.doStart();
                return true;
            case MENU_STOP:
                mMarsThread.setState(MarsThread.STATE_LOSE,
                        getText(R.string.message_stopped));
                return true;
            case MENU_PAUSE:
                mMarsThread.pause();
                return true;
            case MENU_RESUME:
                mMarsThread.unpause();
                return true;
            case MENU_EASY:
                mMarsThread.setDifficulty(MarsThread.DIFFICULTY_EASY);
                return true;
            case MENU_MEDIUM:
                mMarsThread.setDifficulty(MarsThread.DIFFICULTY_MEDIUM);
                return true;
            case MENU_HARD:
                mMarsThread.setDifficulty(MarsThread.DIFFICULTY_HARD);
                return true;
        }

        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // tell system to use the layout defined in our XML file
        setContentView(R.layout.mars_layout);

        // get handles to the IMarsView from XML, and its MarsThread
        mMarsView = (IMarsView) findViewById(R.id.mars);
        mMarsThread = mMarsView.getThread();
       
        // give the IMarsView a handle to the TextView used for messages
        mMarsView.setTextView((TextView) findViewById(R.id.text));

        if (savedInstanceState == null) {
            // we were just launched: set up a new game
            mMarsThread.setState(MarsThread.STATE_READY);
            Log.w(this.getClass().getName(), "SIS is null");
        } else {
            // we are being restored: resume a previous game
            mMarsThread.restoreState(savedInstanceState);
            Log.w(this.getClass().getName(), "SIS is nonnull");
        }
        
        //initiate the firing sound
        mp1 = MediaPlayer.create(this, R.raw.firing);
        
        //When touch the button thrusters on
        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                	mMarsThread.setFiring(true);
                	//play the rockets firing sounds
                	mp1.seekTo(0);
                    mp1.start();
                   
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP ) {
                	mMarsThread.setFiring(false);
                	if(mp1.isPlaying())
                    {
                        //when you get the hand off the button stop playing the sound
                		mp1.stop();
                        try {
							mp1.prepare();
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        mp1.seekTo(0);
                    }
                    return true;
                }

                return false;
            }
        });
        
        Button btnLeft = (Button) findViewById(R.id.btnLeft);
        btnLeft.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                	Log.d("touch3", "Touch works3");
                	int rotate = mMarsThread.getmRotating();
                	mMarsThread.setmRotating(rotate-1);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP ) {
                	Log.d("touch3", "Touch works3");
                	int rotate = mMarsThread.getmRotating();
                	mMarsThread.setmRotating(0);
                    return true;
                }

                return false;
            }
        });
        
        Button btnRight = (Button) findViewById(R.id.btnRight);
        btnRight.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                	int rotate = mMarsThread.getmRotating();
                	mMarsThread.setmRotating(rotate+1);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP ) {
                	int rotate = mMarsThread.getmRotating();
                	mMarsThread.setmRotating(0);
                    return true;
                }

                return false;
            }
        });
    }

   
    @Override
    protected void onPause() {
        super.onPause();
        mMarsView.getThread().pause(); // pause game when Activity pauses
    }

   
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // just have the View's thread save its state into our Bundle
        super.onSaveInstanceState(outState);
        mMarsThread.saveState(outState);
        
    }
    
    protected void onDestroy() {
    	// release the media on destory
        mp1.release();
        super.onDestroy();
    }
}
