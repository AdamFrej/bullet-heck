package org.frej.bulletheck.Model.Components;

import org.frej.bulletheck.Model.Bullet;
import org.frej.bulletheck.Model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Physics {

	private Vector2 velocity;
	private float speed;
	private Entity entity;
	private TiledMapTileLayer groundColisions;
	private final float unitScale = 1 / 32f;
	private final boolean destroyedOnColison;

	public Physics(Entity entity, float speed, TiledMapTileLayer groundColisions) {
		this.entity = entity;
		this.speed = speed;
		this.velocity = new Vector2();
		this.groundColisions = groundColisions;
		destroyedOnColison = false;
	}

	public Physics(Entity entity, Vector2 velocity, float speed,
			TiledMapTileLayer groundColisions) {
		this.entity = entity;
		this.velocity = velocity;
		this.speed = speed;
		this.groundColisions = groundColisions;
		destroyedOnColison = false;
	}

	public Physics(Entity entity, Vector2 velocity, float speed,
			TiledMapTileLayer groundColisions, boolean destroyedOnColison) {
		this.entity = entity;
		this.velocity = velocity;
		this.speed = speed;
		this.groundColisions = groundColisions;
		this.destroyedOnColison = destroyedOnColison;
	}

	public void update(Array<Entity> entities) {
		if (isValidPosition(entities))
			entity.getBody().setPosition(nextPosition());
		else if (destroyedOnColison)
			entity.destroy();

		if (velocity.y > 0)
			entity.getBody().setFace(Body.UP);
		else
			entity.getBody().setFace(Body.DOWN);

		if (velocity.x > 0)
			entity.getBody().setFace(Body.RIGHT);
		else if (velocity.x < 0)
			entity.getBody().setFace(Body.LEFT);

		if (velocity.x == 0 && velocity.y == 0)
			entity.getBody().setFace(Body.STOP);

	}

	public Vector2 nextPosition() {
		return entity.getBody().getPosition()
				.add(velocity.cpy().scl(speed * Gdx.graphics.getDeltaTime()));
	}

	public void setVelocityY(float y) {
		velocity.y = y;

	}

	public void setVelocityX(float x) {
		velocity.x = x;

	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	private boolean isValidPosition(Array<Entity> entities) {
		boolean isBlocked = groundColisions
				.getCell((int) (nextPosition().x * unitScale),
						(int) (nextPosition().y * unitScale)).getTile()
				.getProperties().containsKey("blocked");
		boolean colidesWithOtherEntity = false;
		for (Entity entity : entities)
			if (!(this.entity instanceof Bullet) && !entity.equals(this.entity)
					&& entity.getBody().getBounds().overlaps(nextBounds()))
				colidesWithOtherEntity = true;

		return !isBlocked && !colidesWithOtherEntity;
	}

	public Rectangle nextBounds() {
		return new Rectangle(nextPosition().x, nextPosition().y, this.entity
				.getBody().getWidth(), this.entity.getBody().getHeight());
	}

	public Vector2 getVelocity() {
		return velocity.cpy();
	}
}
