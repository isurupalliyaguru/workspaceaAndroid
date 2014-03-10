package com.halloweenjump;

/**
 * About setup
 * @author Luke Wang
 */

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class About extends Activity implements  OnClickListener  
{


	private Button exit;
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
        
        exit = (Button) findViewById(R.id.about_exit); //assign id for Exit button
        exit.setOnClickListener(this);  
    }


	public void onClick(View v) 
	{ 
		if (v.getId() == R.id.about_exit)   //setup onClick event
		{
			finish();
			Intent i = new Intent(this, Main_Menu.class);
			startActivity(i);
		}
		// TODO Auto-generated method stub
	}
}
