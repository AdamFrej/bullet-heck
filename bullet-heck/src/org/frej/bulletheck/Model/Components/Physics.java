package org.frej.bulletheck.Model.Components;

import org.frej.bulletheck.Model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Physics {

	private Vector2 velocity;
	private float speed;
	private Entity entity;
	
	public Physics(Entity entity,float speed){
		this.entity = entity;
		this.speed=speed;
		this.velocity = new Vector2();
	}
	public Physics(Entity entity,Vector2 velocity, float speed) {
		this.entity = entity;
		this.velocity=velocity;
		this.speed=speed;
	}
	/*
	 * @return wartość velocity
	 
	public Vector2 getVelocity() {
		return velocity;
	}
	
	 * @param velocity wartość velocity do ustawienia
	 
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
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
		entity.getBody().setPosition(nextPosition());
		
		if(velocity.y>0)entity.getBody().setFace(Body.UP);
		else entity.getBody().setFace(Body.DOWN);
		
		if(velocity.x>0)entity.getBody().setFace(Body.RIGHT);
		else if(velocity.x<0) entity.getBody().setFace(Body.LEFT);
		
		if(velocity.x==0&&velocity.y==0)entity.getBody().setFace(Body.STOP);
		
	}
	public Vector2 nextPosition() {
		return entity.getBody().getPosition().add(velocity.cpy().scl(speed*Gdx.graphics.getDeltaTime()));
	}
	
	public void setVelocityY(float y) {
		velocity.y=y;
		
	}
	public void setVelocityX(float x) {
		velocity.x=x;
		
	}
	public void setVelocity(Vector2 velocity) {
		this.velocity=velocity;		
	}
	
}
