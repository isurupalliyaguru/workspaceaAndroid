package com.almondmendoza;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.almondmendoza.drawings.DrawingActivity;

public class Main extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent drawIntent = new Intent(this, DrawingActivity.class);
        startActivity( drawIntent);
    }
    /*
    public void onClick(View view){
        switch (view.getId()){
            case R.id.drawBtn:
                Intent drawIntent = new Intent(this, DrawingActivity.class);
                startActivity( drawIntent);
                break;
        }
    }
    */
    
   
}
