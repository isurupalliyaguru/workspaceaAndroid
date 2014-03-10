package com.halloweenjump;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Display;
import android.view.WindowManager;

public class JumpActivity extends Activity
{
	private JumpPanel mGame;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// get display properties
		Display display = getWindowManager().getDefaultDisplay();
		
		// create new GamePanel
		mGame = new JumpPanel(this, display);

		setContentView(mGame);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGame.onPause();
	}
	
	public void saveScore(int score)
	{
		Intent i = new Intent();
        i.setClass(this, SaveNameDialog.class);
        i.putExtra("score", score);
        startActivity(i);
	}
}
