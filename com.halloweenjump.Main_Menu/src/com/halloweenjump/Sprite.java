package com.halloweenjump;

import android.graphics.Canvas;

/**
 * Interface to define necessary methods for the game objects.
 * 
 * @author Richard
 * 
 */
public interface Sprite
{
	/**
	 * Method to load content of the object (sound, graphics, etc.)
	 */
	public void load();

	/**
	 * Method to move the game object (best place for AI implementation)
	 */
	public void move();

	/**
	 * Method to update the sprite sheet
	 */
	public void update();

	/**
	 * Method to draw the object to the canvas
	 * 
	 * @param canvas
	 *            Canvas to draw objects
	 */
	public void draw(Canvas canvas);

	/**
	 * Method to set status of moving
	 * 
	 * @param move
	 *            Status of moving
	 */
	public void setMove(boolean move);
}
