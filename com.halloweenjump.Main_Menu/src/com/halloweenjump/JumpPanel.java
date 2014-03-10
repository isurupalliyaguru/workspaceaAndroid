package com.halloweenjump;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.halloweenjump.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff.Mode;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class JumpPanel  extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener
{
	public Paint clearPaint;

	public Bitmap background;
	private Bitmap main;
	private Bitmap buffer;
	private Bitmap flame;
	private Bitmap flames;

	private JumpLoop jumpLoop;

	private boolean mMove;
	private boolean moveLeft;
	private boolean moveRight;
	private boolean falling;
	private boolean leftColliding;
	private boolean rightColliding;
	private boolean flaming;
	private boolean hitBottom;

	private Canvas jCanvas;

	private int color;
	private int color2;
	private int leftX;
	private int leftY;
	private int rightX;
	private int rightY;
	private int score;

	private Timer bounceTimer;

	private MediaPlayer mp; 

	private float countY;
	private float shapeX;
	private float shapeY;


	private SensorManager mSensorManager;
	private Sensor mAccelerometer;


	private Vector<Sprite> mSprites;

	/**
	 * Constructor
	 * 
	 * @param context
	 *            Context of the application
	 * @param display
	 *            Display properties of the application
	 */
	public JumpPanel(Context context, Display display)
	{
		super(context);

		this.setZOrderOnTop(true);
		this.getHolder().setFormat(PixelFormat.TRANSPARENT);

		clearPaint = new Paint();
		clearPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));	

		setBackgroundResource(R.drawable.sky);


		// create new game object vector
		mSprites = new Vector<Sprite>();

		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		@SuppressWarnings("deprecation")
		int width = display.getWidth();
		@SuppressWarnings("deprecation")
		int height = display.getHeight();

		// add objects to the object vector
		//this.setBackground(background = BitmapFactory.decodeResource(platform.mResources,R.drawable.sky));

		mSprites.add(new Platforms(getResources(), 3, new Point(width,
				height / 5)));
		mSprites.add(new Platforms(getResources(), 5, new Point(width,
				height / 4)));
		mSprites.add(new Platforms(getResources(), 10, new Point(width,
				height * 3 / 8)));

		// create the game loop thread
		jumpLoop = new JumpLoop(getHolder(), this);


		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	public void surfaceCreated(SurfaceHolder holder)
	{
		// sound for each bounce
		mp = MediaPlayer.create(getContext(), R.raw.boundced);

		//boolean values
		leftColliding = false;
		rightColliding = false;
		jumpLoop.setRunning(true);
		falling = true;
		flaming = true;
		mMove = false;

		//Bitmap values
		Select_Avatar.iv.setDrawingCacheEnabled(true);   //enable drawing cache for ImageView object from selection
		Select_Avatar.iv.buildDrawingCache();            //create drawing cache (space) for the ImageView object (selected photo).
		Bitmap scale = Select_Avatar.iv.getDrawingCache();
		main = Bitmap.createScaledBitmap(scale,(int)Math.floor(scale.getWidth()*0.45),(int)Math.floor(scale.getHeight()*0.45), false);
		flame = BitmapFactory.decodeResource(getResources(), R.drawable.flame);
		flames = BitmapFactory.decodeResource(getResources(), R.drawable.flames);

		//colour and initial int values for objects
		color = Color.rgb(3, 120, 0);
		color2 = Color.rgb(48, 41, 18);
		countY = 0;
		shapeX = (this.getWidth() / 2) - (main.getWidth() / 2);
		shapeY = (this.getHeight() / 2) - (main.getHeight() / 2);
		score = 0;

		//Sensors for accelerometer
		mSensorManager = (SensorManager) (((JumpActivity) getContext()).getSystemService(Context.SENSOR_SERVICE));
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);

		if(buffer == null)
		{
			buffer = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
			jCanvas = new Canvas(buffer);
		}	

		//timer object
		bounceTimer = new Timer();
		bounceTimer.scheduleAtFixedRate(new bounceTimerTask(),100,100);
		
		jumpLoop.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder)
	{
		onPause();
	}

	public void onPause()
	{
		jumpLoop.setRunning(false);
		while(jumpLoop.isAlive())
		{
			try
			{
				bounceTimer.cancel();
				jumpLoop.join();
				Log.e("", "yes");
			}
			catch(InterruptedException e)
			{
			}
		}
		mSensorManager.unregisterListener(this, mAccelerometer);

	}

	/**
	 * Method to check accelerometer movements
	 */
	public void onSensorChanged(SensorEvent event) {
		float xAxis = event.values[0];

		if(xAxis < -1)
		{
			moveRight = true;
			moveLeft = false;
		}
		else if(xAxis > 1)
		{
			moveLeft = true;
			moveRight = false;
		}
		else
		{
			moveRight = false;
			moveLeft = false;

		}
	}

	/**
	 * Method to render the game view
	 * 
	 * @param canvas
	 *            Canvas where the objects get drawn
	 */
	public void render(Canvas canvas)
	{
		// draw transparent background
		clearPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));	
		canvas.drawPaint(clearPaint);
		jCanvas.drawPaint(clearPaint);

		// for loop to draw all objects
		for (Sprite sprite : mSprites)
		{
			sprite.draw(canvas);
			sprite.draw(jCanvas);
		}

		//paint object for score
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);

		//boolean defined by accelerometer
		if(moveLeft)
		{
			if(shapeX > 0)
			{
				shapeX -= 4;
			}
		}

		//boolean defined by accelerometer
		if(moveRight)
		{
			if((shapeX + main.getWidth()) < this.getWidth())
			{
				shapeX += 4;
			}
		}

		//while object is moving up score is increased
		if(countY >= -8 && countY < 0)
		{
			score = score + 7;
		}
		//place the score on screen
		paint.setTextSize(18);
		canvas.drawText("Score: " + score, 10, 25, paint);

		//adds speed to the object
		shapeY += countY;
		//draws the object into bitmap and off screen bitmap for checking
		canvas.drawBitmap(main, shapeX, shapeY, null);
		jCanvas.drawBitmap(main, shapeX, shapeY, null);

		//draws flames
		if (flaming)
		{
			canvas.drawBitmap(flames, 0, this.getHeight() - 50, null);
			jCanvas.drawBitmap(flames, 0, this.getHeight() - 50, null);
			flaming = false;
		}
		else
		{
			canvas.drawBitmap(flame, 0, this.getHeight() - 50, null);
			jCanvas.drawBitmap(flame, 0, this.getHeight() - 50, null);
			flaming = true;
		}

		//creates points just at the bottom of the object
		leftX = (int)shapeX - 2;
		leftY = (int)shapeY + main.getHeight() +2;
		rightX = (int)shapeX + main.getWidth() + 2;
		rightY = (int)shapeY + main.getHeight() + 2;

		//stops object from going past the left side
		if(leftX <= 0)
		{
			leftX = 0;
		}

		//stops object from going past the right side
		if(rightX >= this.getWidth())
		{
			rightX = this.getWidth() - 2;
		}

		//stops the objects from going through the top of the screen
		if(shapeY <= (this.getHeight() / 6))
		{
			countY = 0; 
		}

		//checks if objects hits bottom
		if(leftY >= this.getHeight() || rightY >= this.getHeight())
		{
			jumpLoop.setRunning(false);
			hitBottom = true;
			((JumpActivity) getContext()).saveScore(score);
		}

		//checks collision of object and platforms
		if(falling && !hitBottom)
		{
			if (leftX > 0 && leftX < this.getWidth() && leftY > 0)
			{
				if ((buffer.getPixel(leftX, leftY)) == color ||
						(buffer.getPixel(leftX, leftY)) == color2)
				{
					leftColliding = true;
				}
			}

			if( rightX > 0 && rightX < this.getHeight() && rightY > 0)
			{
				if ((buffer.getPixel(rightX, rightY)) == color ||
						(buffer.getPixel(rightX, rightY)) == color2)
				{
					leftColliding = true;
				}
			}

		}

		if(leftColliding || rightColliding)
		{
			mp.start();
			countY = -6;
			mMove = true;
			falling = false;
			leftColliding = false;
			rightColliding = false;
		}

	}

	/**
	 * Method to update the game objects
	 */
	public void update()
	{
		// for loop to move the objects and update the sprite sheet
		for (Sprite sprite : mSprites)
		{
			sprite.setMove(mMove);
			sprite.update();
		}
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height)
	{
		// TODO Auto-generated method stub
	}

	class bounceTimerTask extends TimerTask
	{
		@Override
		public void run() 
		{
			countY ++;
			if(countY >= 0)
			{
				falling = true;
				mMove = false;
			}
		}
	}
}