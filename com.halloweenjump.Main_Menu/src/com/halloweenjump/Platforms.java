package com.halloweenjump;

import com.halloweenjump.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

public class Platforms implements Sprite
{
	private Resources mResources;

	private Bitmap platform;
	private Bitmap clouds;
	
	private int mSpeed;
	private int genNum;
	private int pPosX;
	private int pPosY;
	private int pPosX2;
	private int pPosY2;
	private int pPosX3;
	private int pPosY3;
	private int pPosX4;
	private int pPosY4;
	private int pSpeed;
	
	private Point mPos;

	private boolean mMove;
	private boolean start;

	/**
	 * Constructor
	 * 
	 * @param resources
	 *            Resource to load content from files
	 * @param speed
	 *            Speed of the object
	 * @param pos
	 *            Start position of the object
	 */
	public Platforms(Resources resources, int speed, Point pos)
	{
		mMove = false;
		
		mResources = resources;
		mSpeed = speed;

		start = true;
		
		pSpeed = 10;
		
		mPos = pos;
		
		platform = makeScaled (BitmapFactory.decodeResource(resources, R.drawable.platform), 150);

		
		load();
	}
	
	private Bitmap makeScaled(Bitmap base, float width) {
		float scale = width / base.getWidth() / 2;
		return Bitmap.createScaledBitmap(base, (int)(base.getWidth()*scale), (int)(base.getHeight()*scale), false);


	}

	public void load()
	{
		// load different kind of objects (quick and dirty solution)
		if (mSpeed == 5)
			clouds = BitmapFactory.decodeResource(mResources,
					R.drawable.cloud2);
		else
			clouds = BitmapFactory.decodeResource(mResources,
					R.drawable.cloud1);
		
		
	}
	
	public void move()
	{

	}

	public void update()
	{
		// update position of object
		if (mMove)
		{
			mPos.y += mSpeed;
			pPosY += pSpeed;
			pPosY2 += pSpeed;
			pPosY3 += pSpeed;
			pPosY4 += pSpeed;
		}
	}

	public void draw(Canvas canvas)
	{
				
		if (start == true)
		{
			mPos.x = (int)(Math.random()*(canvas.getWidth() - 25) - 0);
			mPos.y = (int)(Math.random()*canvas.getHeight() - 0);
			
			pPosX = (int)(Math.random()*(canvas.getWidth() - 25) - 0);
			pPosY = (canvas.getHeight() * 1/4) - 0;
			pPosX2 = (int)(Math.random()*(canvas.getWidth() - 25) - 0);
			pPosY2 = (canvas.getHeight() * 2/4) - 0;
			pPosX3 = (int)(Math.random()*(canvas.getWidth() - 25) - 0);
			pPosY3 =  (canvas.getHeight() * 3/4) - 0;
			pPosX4 = (canvas.getWidth() /2 - 25);
			pPosY4 =  canvas.getHeight() - 0;
			
			start = false;
		}
		// draw object to canvas
		canvas.drawBitmap(clouds, mPos.x, mPos.y, null);
		canvas.drawBitmap(platform, pPosX, pPosY, null);
		canvas.drawBitmap(platform, pPosX2, pPosY2, null);
		canvas.drawBitmap(platform, pPosX3, pPosY3, null);
		canvas.drawBitmap(platform, pPosX4, pPosY4, null);

		// reset position if it has already passed the screen
		if (mPos.y > canvas.getHeight() + mSpeed)
		{
			mPos.y = -80 - mSpeed;
			genNum = (int)(Math.random()*canvas.getWidth() - 0);
			mPos.x = genNum;
		}
		 if  (pPosY > canvas.getHeight() + mSpeed) 
		 {
				pPosY = -50 - mSpeed;
				pPosX = genNum;
		 }
		 if (pPosY2 > canvas.getHeight() + mSpeed)
		 {
				pPosY2 = -50 - mSpeed;
				genNum = (int)(Math.random()*canvas.getWidth() - 0);
				pPosX2 = genNum;
		 }
		 if (pPosY3 > canvas.getHeight() + mSpeed)
		 {
				pPosY3 = -50 - mSpeed;
				genNum = (int)(Math.random()*canvas.getWidth() - 0);
				pPosX3 = genNum;
		 }
		 if (pPosY4 > canvas.getHeight() + mSpeed)
		 {
				pPosY4 = -50 - mSpeed;
				genNum = (int)(Math.random()*canvas.getWidth() - 0);
				pPosX4 = genNum;
		 }
		
	}

	public void setMove(boolean move)
	{
		mMove = move;
	}

}
