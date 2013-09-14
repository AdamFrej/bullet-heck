package org.frej.bulletheck.Model.Components;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Body {
	public static final int DOWN = 0;
	public static final int UP = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int STOP = 4;
	private Vector2 position;
	private float width;
	private float height;
	private int face;
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
		this.face=STOP;
	}

	/**
	 * @return wartość position
	 */
	Vector2 getPosition() {
		return position.cpy();
	}
	
	
	public float getX(){
		return position.x;
	}
	/**
	 * @param position wartość position do ustawienia
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
		bounds= new Rectangle(position.x,position.y,width,height);
		//System.out.println(position);
	}

	public float getY(){
		return position.y;
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
		bounds= new Rectangle(position.x,position.y,width,height);
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
		bounds= new Rectangle(position.x,position.y,width,height);
	}

	/**
	 * @return wartość bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * @return wartość face
	 */
	public int getFace() {
		return face;
	}

	/**
	 * @param face wartość face do ustawienia
	 */
	public void setFace(int face) {
		this.face = face;
	}
	
	

}
