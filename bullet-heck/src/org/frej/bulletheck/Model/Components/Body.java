package org.frej.bulletheck.Model.Components;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Body {
	private Vector2 position;
	private float width;
	private float height;
	private Rectangle bounds;
	
	

	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param bounds
	 */
	public Body(Vector2 position, float width, float height) {
		this.position = position;
		this.width = width;
		this.height = height;
		bounds= new Rectangle(position.x,position.y,width,height);
	}

	/**
	 * @return wartość position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * @param position wartość position do ustawienia
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/**
	 * @return wartość width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width wartość width do ustawienia
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return wartość height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height wartość height do ustawienia
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * @return wartość bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * @param bounds wartość bounds do ustawienia
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	

}
