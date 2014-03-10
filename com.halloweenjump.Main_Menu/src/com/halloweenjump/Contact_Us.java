package com.halloweenjump;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Contact_Us setup
 * @author Luke Wang
 */

public class Contact_Us extends Activity implements  OnClickListener
{
	private Button exit;
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contact_us);
        
        exit = (Button) findViewById(R.id.contact_us_exit); //assign id for Exit button
        exit.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_contact_us, menu);
        return true;
    }

	public void onClick(View v) 
	{
		if (v.getId() == R.id.contact_us_exit)   //setup onClick event
		{
			finish();      
			Intent i = new Intent(this, Main_Menu.class);
			startActivity(i);            
		}
		
	}
}
