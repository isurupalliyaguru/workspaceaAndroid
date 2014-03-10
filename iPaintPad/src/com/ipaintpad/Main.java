package com.ipaintpad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ipaintpad.R;
import com.ipaintpad.drawings.DrawingActivity;

public class Main extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // evoking the drawing activity
        Intent drawIntent = new Intent(this, DrawingActivity.class);
        startActivity( drawIntent);
    }
    
}
