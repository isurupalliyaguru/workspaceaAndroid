package com.jcasey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SimpleActivity extends Activity {
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        if(item.getItemId() == R.id.mnuConnectivity)
        {
            Log.e("mnuConnectivity","...");
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        
        Bundle extras = getIntent().getExtras();
        String clientId = extras.getString("clientId");
        String firstName = extras.getString("firstName");
        String lastName = extras.getString("lastName");

        Log.e("clientId", clientId);
        Log.e("firstName", firstName);
        Log.e("lastName", lastName);
        
        setContentView(R.layout.simple_activity);
        
        
        Button btnPrevious = (Button)findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new OnClickListener(){

           // @Override
            public void onClick(View v) {
                Intent intent = new Intent(); 
                intent.putExtra("RESULT", "Return message"); 
                setResult(Activity.RESULT_OK, intent); 
                
                finish();                
            }});
    }    
}
