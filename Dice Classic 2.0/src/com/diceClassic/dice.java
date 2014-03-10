package com.diceClassic;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class dice extends Activity {
	private TextView diceNumber;
	private Button goButton;
	private ImageView diceImage;
	private int faceupside = 0;
	private int min = 1;
	private int max = 6;
	
	//for the sensor
	//Ref: http://stackoverflow.com/questions/2317428/android-i-want-to-shake-it
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	
	private final SensorEventListener mSensorListener = new SensorEventListener() {

	    public void onSensorChanged(SensorEvent se) {
	      float x = se.values[0];
	      float y = se.values[1];
	      float z = se.values[2];
	      mAccelLast = mAccelCurrent;
	      mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
	      float delta = mAccelCurrent - mAccelLast;
	      mAccel = mAccel * 0.9f + delta; // perform low-cut filter
	      if(mAccel>2.0){
	    	  rollDice();
	      }
	    }

	    public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    }
	  };
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initControls();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }
    @Override
    protected void onResume() {
      super.onResume();
      mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
      mSensorManager.unregisterListener(mSensorListener);
      super.onStop();
    }
    /** Called when the activity is first created. */
    
    private void initControls() {
    	diceNumber = (TextView)findViewById(R.id.diceNumber);
    	goButton = (Button)findViewById(R.id.goButton);
    	goButton.setOnClickListener(new Button.OnClickListener(){public void onClick(View v){ rollDice();}});
    	diceImage = (ImageView)findViewById(R.id.diceImage);
    	
    }
    private void rollDice() {
    	Random rn = new Random();
    	faceupside = rn.nextInt(max - min + 1) + min;
    	diceNumber.setText(Integer.toString(faceupside));
    	switch(faceupside){
    		case 1:
    			diceImage.setImageResource(R.drawable.d1);
    			break;
    		case 2:
    			diceImage.setImageResource(R.drawable.d2);
    			break;
    		case 3:
    			diceImage.setImageResource(R.drawable.d3);
    			break;
    		case 4:
    			diceImage.setImageResource(R.drawable.d4);
    			break;
    		case 5:
    			diceImage.setImageResource(R.drawable.d5);
    			break;
    		case 6:
    			diceImage.setImageResource(R.drawable.d6);
    			break;
    	}
    }
}