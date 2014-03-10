package com.halloweenjump;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class JumpLoop extends Thread
{
	private SurfaceHolder mSurfaceHolder;
	private JumpPanel jumpPanel;
	private volatile boolean mRunning;

	/**
	 * Constructor
	 * 
	 * @param surfaceHolder
	 *            Surface holder for hold drawing objects
	 * @param gamePanel
	 *            Game panel to update the game
	 */
	public JumpLoop(SurfaceHolder surfaceHolder, JumpPanel jmpPanel)
	{
		super();
		mSurfaceHolder = surfaceHolder;
		jumpPanel = jmpPanel;
	}

	@Override
	public void run()
	{
		Canvas canvas;

		// run the game
		while (mRunning)
		{
			canvas = null;

			// try to update the game status
			try
			{
				canvas = mSurfaceHolder.lockCanvas();

				synchronized (mSurfaceHolder)
				{
					if(canvas != null)
					{
						jumpPanel.update();
						jumpPanel.render(canvas);
					}
				}
			}
			finally
			{
				if (canvas != null)
				{
					mSurfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	/**
	 * Method to end the game
	 * 
	 * @param running
	 *            Defines if game should run or not
	 */
	public void setRunning(boolean running)
	{
		mRunning = running;
	}
}
