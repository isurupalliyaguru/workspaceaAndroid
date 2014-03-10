package com.halloweenjump;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Select Avatar setup
 * @author Luke Wang
 */

public class Select_Avatar extends Activity implements OnClickListener 
{
	public static ImageView iv;
	private MediaPlayer sa;
	
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_select_avatar);
        
        iv = (ImageView) findViewById(R.id.ImageObject);    //assign ID for IV from ImageObject that selected by users. 
        
        ImageView Avatar1   =  (ImageView) findViewById(R.id.Avatar1);     //assign IDs for each images in the HorizontalScrollView
        ImageView Avatar2   =  (ImageView) findViewById(R.id.Avatar2);
        ImageView Avatar3   =  (ImageView) findViewById(R.id.Avatar3);
        ImageView Avatar4   =  (ImageView) findViewById(R.id.Avatar4);
        ImageView Avatar5   =  (ImageView) findViewById(R.id.Avatar5);
        ImageView Avatar6   =  (ImageView) findViewById(R.id.Avatar6);
        ImageView Avatar7   =  (ImageView) findViewById(R.id.Avatar7);
        ImageView Avatar8   =  (ImageView) findViewById(R.id.Avatar8);
        ImageView Avatar9   =  (ImageView) findViewById(R.id.Avatar9);
        ImageView Avatar10  =  (ImageView) findViewById(R.id.Avatar10);
        ImageView Avatar11  =  (ImageView) findViewById(R.id.Avatar11);
        ImageView Avatar12  =  (ImageView) findViewById(R.id.Avatar12);
        Button btstartgame   =  (Button) findViewById(R.id.BTstartgame);
        Button btexit       =  (Button) findViewById(R.id.BTExit); //assign ID for exit button
        
        Avatar1.setOnClickListener(this);                   //Setup OnClickListener for make them click-able. 
        Avatar2.setOnClickListener(this);
        Avatar3.setOnClickListener(this);
        Avatar4.setOnClickListener(this);
        Avatar5.setOnClickListener(this);
        Avatar6.setOnClickListener(this);
        Avatar7.setOnClickListener(this);                  
        Avatar8.setOnClickListener(this);
        Avatar9.setOnClickListener(this);
        Avatar10.setOnClickListener(this);
        Avatar11.setOnClickListener(this);
        Avatar12.setOnClickListener(this);
        
        btstartgame.setOnClickListener(this);
        btstartgame.getBackground().setAlpha(100);
        
        btexit.setOnClickListener(this);
        btexit.getBackground().setAlpha(100);
        
        sa = MediaPlayer.create(this, R.raw.scary_haunting );
        sa.start();
        
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_select_avatar, menu);
        return true;
    }

	public void onClick(View v) 
	{
		switch(v.getId())                   
		{
		case R.id.Avatar1:                      //setup each possibility for changeable value of IV. 
			iv.setImageResource(R.drawable.avatar1);
			break;
			
		case R.id.Avatar2:
			iv.setImageResource(R.drawable.avatar2);
			break;
			
		case R.id.Avatar3:
			iv.setImageResource(R.drawable.avatar3);
			break;
			
		case R.id.Avatar4:
			iv.setImageResource(R.drawable.avatar4);
			break;
		case R.id.Avatar5:
			iv.setImageResource(R.drawable.avatar5);
			break;
			
		case R.id.Avatar6:
			iv.setImageResource(R.drawable.avatar6);
			break;
			
		case R.id.Avatar7:
			iv.setImageResource(R.drawable.avatar7);
			break;
		
		case R.id.Avatar8:
			iv.setImageResource(R.drawable.avatar8);
			break;
		case R.id.Avatar9:
			iv.setImageResource(R.drawable.avatar9);
			break;
			
		case R.id.Avatar10:
			iv.setImageResource(R.drawable.avatar10);
			break;
			
		case R.id.Avatar11:
			iv.setImageResource(R.drawable.avatar11);
			break;
		
		case R.id.Avatar12:
			iv.setImageResource(R.drawable.avatar12);
			break;
			
		case R.id.BTstartgame:                    
		    iv.destroyDrawingCache();                  
			Intent i = new Intent(this,JumpActivity.class);   
		    startActivity(i);
		    sa.stop();
			break;
			
		case R.id.BTExit:
			sa.stop();
			finish();
			Intent i2 = new Intent(this, Main_Menu.class);
			startActivity(i2);
			break;
		}
		
	}
	
    @Override
	protected void onPause() {
		sa.stop();
		finish();
		sa.stop();
		super.onPause();
	}
}
