package com.halloweenjump;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Score_List extends Activity implements  OnClickListener  
{
    private Button exit;
    private DBAdapter db;
	private ListView lv;
	
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.layout_scorelist);
        
        exit = (Button) findViewById(R.id.scorelist_exit);
        exit.setText("Exit");
        exit.setOnClickListener(this);  
        exit.bringToFront();

        db = new DBAdapter(this);
        
        lv = (ListView)findViewById(R.id.list_scores);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, addAllScores());
        lv.setAdapter(arrayAdapter); 
    }


	public void onClick(View v)
	{ 
		if (v.getId() == R.id.scorelist_exit)   //setup onClick event
		{
			finish();  
			Intent i = new Intent(this, Main_Menu.class);
			startActivity(i);                
		}
	}
	private ArrayList<String> addAllScores(){

        db.open();
        ArrayList<String> score = new ArrayList<String>();
        try{
        	Cursor c = db.getAllScores();
            if (c.moveToLast())
            {
                do {
                	score.add(displayScores(c));
                } while (c.moveToPrevious());
            }
        }catch(Exception e){
        	System.out.println(e);
        }
        db.close();
        return score;
    }
	
	 public String displayScores(Cursor c)
    {
		String s = "Name: " + c.getString(1) + " | Score: " + c.getString(2);
		return  s ;
    }
}

