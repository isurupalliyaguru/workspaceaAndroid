package com.jcasey;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Week4Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main_activity);
        
        Button btnNext = (Button)findViewById(R.id.btnNext);
        
        btnNext.setOnClickListener(new OnClickListener(){

            //@Override
            public void onClick(View v) {
                
                Intent message = new Intent();
                
//                message.setAction(Intent.ACTION_VIEW);
//                message.setDataAndType(Uri.parse("http://www.google.co.nz/"), "text/html");
//                
//                startActivity(message);
                
//                message.setAction("com.jcasey.OPEN_SIMPLE_ACTIVITY");
//                startActivity(message);
                
                message.setClass(Week4Activity.this, SimpleActivity.class);
                
                // add some key-value pairs to our message
                message.putExtra("clientId", "jcasey");
                message.putExtra("firstName","John");
                message.putExtra("lastName","Casey");
                
                startActivityForResult(message, 0);
            }});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent message) {
        
        // also check the requestCode ...
        
        if(resultCode == Activity.RESULT_OK)
        {
            Bundle extras = message.getExtras();
            String result = extras.getString("RESULT");
            
        }
    }
}