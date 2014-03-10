package ac.unitec;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Week3Activity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        
        Button addDetails = (Button) findViewById(R.id.btnAddDetail);
        
        if(addDetails != null)
        {
        	addDetails.setOnClickListener(new OnClickListener()
        	{
				@Override
				public void onClick(View v)
				{
					Log.e("addDetails","button was clicked");
					//todo handle button click event
					
					// bind to our pre-defined linear layout
					LinearLayout dynamicPanel = (LinearLayout) findViewById(R.id.dynamicPanel);
					
				
					
					
					// construct a TextView object
					TextView childTextView = new TextView(getApplicationContext());
					childTextView.setText("Test"); // set some basic properties
					
					// add the new widget to our pre-defined LinearLayout
					dynamicPanel.addView(childTextView);
					
				}
			});
        }
    }

	@Override
	public void onClick(View v) {
		Button temp = (Button)v;
		
		if(temp.getId() == R.id.button1)
		{
			Log.e("button1","clicked");
		}
		else if(temp.getId() == R.id.button2)
		{
			Log.e("button2","clicked");
		}
		else if(temp.getId() == R.id.button3)
		{
			Log.e("button3","clicked");
		}
		
	}
}