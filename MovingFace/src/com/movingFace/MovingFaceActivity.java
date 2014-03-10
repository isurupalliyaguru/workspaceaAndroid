package com.movingFace;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MovingFaceActivity extends Activity {

	private String selectedImagePath;
	InputStream imageStream;
	Bitmap mySelectedImage;
	private int min = 1;
	private int max = 9;

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
			mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
			float delta = mAccelCurrent - mAccelLast;
			mAccel = mAccel * 0.9f + delta; // perform low-cut filter

			if (mAccel > 1.5) {
				changeImage();
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	private static final int SELECT_PHOTO = 100;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.text:
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(
					Intent.createChooser(intent, "Select Picture"),
					SELECT_PHOTO);
			break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				Uri selectedImage = imageReturnedIntent.getData();

				try {
					imageStream = getContentResolver().openInputStream(
							selectedImage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				mySelectedImage = BitmapFactory.decodeStream(imageStream);
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener,
		mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		mAccel = 0.00f;
		mAccelCurrent = SensorManager.GRAVITY_EARTH;
		mAccelLast = SensorManager.GRAVITY_EARTH;

		class DrawView extends View implements OnTouchListener {

			float xcordi;
			float ycordi;

			public DrawView(Context context) {
				super(context);
				setOnTouchListener(this);
			}

			@Override
			protected void onDraw(Canvas canvas) {
				Bitmap img;
				if (mySelectedImage == null) { // In the first go there is no
												// selected image. We are
												// loading a default one.
					img = BitmapFactory.decodeResource(getResources(),
							R.drawable.p2);
				} else {
					img = mySelectedImage;
				}
				int height = img.getHeight();
				int width = img.getWidth();
				// brining the center of the image to the click position
				canvas.drawBitmap(img, xcordi - width / 2, ycordi - height / 2,
						null);
				super.onDraw(canvas);
				long now = System.currentTimeMillis();
				String tempNow = Long.toString(now);

				ycordi++;
				invalidate();
			}

			public boolean onTouch(View v, MotionEvent event) {

				xcordi = event.getX();
				ycordi = event.getY();
				invalidate();
				return true;
			}

		}

		DrawView dv = new DrawView(this);
		dv.setBackgroundResource(R.drawable.background);
		setContentView(dv);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {
		mSensorManager.unregisterListener(mSensorListener);
		super.onStop();
	}

	// changing the image when the phone is shaked. See above "onSensorChanged()" method
	private void changeImage() {
		Random rn = new Random();
		int num = 0;
		// Generating a random number
		num = rn.nextInt(max - min + 1) + min;
		switch (num) {
		case 1:
			mySelectedImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.p1);
			break;
		case 2:
			mySelectedImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.p2);
			break;
		case 3:
			mySelectedImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.p3);
			break;
		case 4:
			mySelectedImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.p4);
			break;
		case 5:
			mySelectedImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.p5);
			break;
		case 6:
			mySelectedImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.p6);
			break;
		case 7:
			mySelectedImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.p7);
			break;
		case 8:
			mySelectedImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.p8);
			break;
		case 9:
			mySelectedImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.p9);
			break;
		}
	}

}