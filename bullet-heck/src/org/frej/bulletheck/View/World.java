package org.frej.bulletheck.View;

import org.frej.bulletheck.Model.Entity;
import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Decay;
import org.frej.bulletheck.Model.Components.Physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {

	private static final float PALYER_SPEED = 150f;
	private static final float BULLET_SPEED = 250f;
	private static final float BULLET_MAX_DISTANCE = 150f;//(Gdx.graphics.getHeight()+Gdx.graphics.getWidth())/2;
	
	private Entity player;
	private Array<Entity> bullets;

	/**
	 * @param player
	 */
	public World() {
		player = new Entity();
		player.setBody(new Body(new Vector2(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2), 64, 64));
		player.setPhysics(new Physics(player, new Vector2(0, 0), PALYER_SPEED));

		bullets = new Array<Entity>();
	}

	/**
	 * @return wartość player
	 */
	public Entity getPlayer() {
		return player;
	}

	/**
	 * @return wartość bullets
	 */
	public Array<Entity> getBullets() {
		return bullets;
	}

	public void update() {
		movement();
		player.getPhysics().update();
		for (Entity bullet : bullets){
			bullet.getPhysics().update();
			if (bullet.getDecay().isDecayed())
				bullets.removeValue(bullet, false);
		}
		
	}

	private void movement() {
		if (Gdx.input.isKeyPressed(Keys.W))
			player.getPhysics().getVelocity().y = 1;

		if (Gdx.input.isKeyPressed(Keys.S))
			player.getPhysics().getVelocity().y = -1;

		if (Gdx.input.isKeyPressed(Keys.A))
			player.getPhysics().getVelocity().x = -1;

		if (Gdx.input.isKeyPressed(Keys.D))
			player.getPhysics().getVelocity().x = 1;

		if (!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S)
				&& !Gdx.input.isKeyPressed(Keys.A)
				&& !Gdx.input.isKeyPressed(Keys.D))
			player.getPhysics().setVelocity(new Vector2(0, 0));

		if (Gdx.input.isTouched()) {
			Vector2 touchPosition = new Vector2(Gdx.input.getX(),
					Gdx.graphics.getHeight() - Gdx.input.getY());
			addBullet(touchPosition.sub(player.getBody().getPosition().cpy())
					.nor());
		}

	}

	private void addBullet(Vector2 velocity) {

		Entity bullet = new Entity();
		bullet.setBody(new Body(player.getBody().getPosition().cpy(), 8, 8));
		bullet.setPhysics(new Physics(bullet, velocity, BULLET_SPEED));
		bullet.setDecay(new Decay(bullet,player.getBody().getPosition().cpy(),BULLET_MAX_DISTANCE));
		bullets.add(bullet);

	}
}
