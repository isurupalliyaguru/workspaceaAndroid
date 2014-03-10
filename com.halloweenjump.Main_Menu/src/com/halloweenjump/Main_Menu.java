package com.halloweenjump;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Main_Menu setup
 * @author Luke Wang
 */

public class Main_Menu extends Activity implements OnClickListener {
	
	MediaPlayer os;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_main);
        
        View about = findViewById(R.id.about); //setup button(view) in the SrollView, assign id for each view and ready for click
        about.setOnClickListener(this);
        about.getBackground().setAlpha(100);
        
        View startgame = findViewById(R.id.start_game);
        startgame.setOnClickListener(this);
        startgame.getBackground().setAlpha(100);
        
        View contactus = findViewById(R.id.contact_us);
        contactus.setOnClickListener(this);
        contactus.getBackground().setAlpha(100);
        
        View scorelist = findViewById(R.id.score_list);
        scorelist.setOnClickListener(this);
        scorelist.getBackground().setAlpha(100);
        
        View quit = findViewById(R.id.quit);
        quit.setOnClickListener(this);
        quit.getBackground().setAlpha(100);
        
        os  = MediaPlayer.create(this, R.raw.scary );
        
//        MediaPlayer MP = MediaPlayer.create(this, R.raw.girls_mockingbir );
//        MP.start();
        
//        FC = MediaPlayer.create(this, R.raw.fisher_cat );
//        FC.start(); 
//        FC.setLooping(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_main_main, menu);
        return true;
    }
    
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId())
		{
		  //connect each button(view) in the ScrollView to different class.  
		case R.id.about:
			Intent i1 = new Intent(this, About.class);
			startActivity(i1);

	        os.start();
			
			break;
		
		case R.id.start_game:
			Intent i3 = new Intent (this,Select_Avatar.class);
			startActivity(i3);

	        os.start();
			break;
			
		case R.id.contact_us:
			Intent i4 = new Intent (this, Contact_Us.class);
			startActivity(i4);
	        os.start();
			break;	
			
		case R.id.score_list:
			Intent i5 = new Intent(this, Score_List.class);
			startActivity(i5);
			
			break;	
			
		case R.id.quit:
	        os.stop();
			finish();
			break;
			
		}
	}
	
    @Override
	protected void onPause() {
        os.stop();
		finish();
		super.onPause();
	}
}
