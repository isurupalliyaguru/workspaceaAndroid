package com.halloweenjump;

 import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SaveNameDialog extends Activity implements  OnClickListener  
{
    private Button save;
    private Button cancel;
    private EditText name;
    private DBAdapter db;
    private Bundle extras;
    private TextView score;
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_save_name);
        
        save = (Button) findViewById(R.id.btn_save);
        cancel = (Button) findViewById(R.id.btn_cancel); 
        name = (EditText) findViewById(R.id.tb_name); 
        score = (TextView) findViewById(R.id.tvScore); 
        
        
        save.setText("Save");
        cancel.setText("Cancel");
        
        save.setOnClickListener(this); 
        cancel.setOnClickListener(this);
        
        db = new DBAdapter(this);
        
        extras = getIntent().getExtras();
        int sc = extras.getInt("score");
        score.setText("Your score is :"+ Integer.toString(sc));
    }


	public void onClick(View v) 
	{ 
		if (v.getId() == R.id.btn_cancel)   //setup onClick event
		{
			finish();                  
		}
		if (v.getId() == R.id.btn_save)   //setup onClick event
		{
			int score = extras.getInt("score");
			
	        db.open();
			db.insertScores(name.getText().toString(),score);
			db.close();
			finish();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Intent i = new Intent();
        i.setClass(this, Main_Menu.class);
        startActivity(i);
	}
}
