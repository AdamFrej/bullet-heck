package org.frej.bulletheck.Model.Components;

import org.frej.bulletheck.Model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Physics {

	private Vector2 velocity;
	private float speed;
	private Entity entity;
	
	
	
	/**
	 * @param entity
	 */
	public Physics(Entity entity,Vector2 velocity, float speed) {
		this.entity = entity;
		this.velocity=velocity;
		this.speed=speed;
	}
	/**
	 * @return wartość velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}
	/**
	 * @param velocity wartość velocity do ustawienia
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	/**
	 * @return wartość speed
	 */
	public float getSpeed() {
		return speed;
	}
	/**
	 * @param speed wartość speed do ustawienia
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public void update(){
		entity.getBody().getPosition().add(velocity.cpy().scl(speed*Gdx.graphics.getDeltaTime()));
		entity.getBody().getBounds().x = entity.getBody().getPosition().x; 
		entity.getBody().getBounds().y = entity.getBody().getPosition().y;
		
	}
	public void moveTo(Vector2 newPosition){
		entity.getBody().getPosition().lerp(newPosition, 0.1f);
		this.update();
	}
	
}
