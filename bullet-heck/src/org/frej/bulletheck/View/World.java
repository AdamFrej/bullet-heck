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
	private static final float BULLET_MAX_DISTANCE = 150f;// (Gdx.graphics.getHeight()+Gdx.graphics.getWidth())/2;

	private Entity player;
	private Array<Entity> bullets;
	private boolean aWasPressedFirst;
	private boolean aWasPressed;
	private boolean wWasPressed;
	private boolean wWasPressedFirst;

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
		for (Entity bullet : bullets) {
			bullet.getPhysics().update();
			if (bullet.getDecay().isDecayed())
				bullets.removeValue(bullet, false);
		}

	}

	private void movement() {
		if (Gdx.input.isKeyPressed(Keys.W)) {
			player.getPhysics().setVelocityY(1);
			wWasPressed = true;
			if (!Gdx.input.isKeyPressed(Keys.S))
				wWasPressedFirst = true;
		}

		if (Gdx.input.isKeyPressed(Keys.S)) {
			if (!Gdx.input.isKeyPressed(Keys.W)) {
				wWasPressedFirst = false;
				wWasPressed = false;
			}
			if (wWasPressedFirst || !wWasPressed)
				player.getPhysics().setVelocityY(-1);
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			player.getPhysics().setVelocityX(-1);
			aWasPressed = true;
			if (!Gdx.input.isKeyPressed(Keys.D))
				aWasPressedFirst = true;
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			if (!Gdx.input.isKeyPressed(Keys.A)) {
				aWasPressedFirst = false;
				aWasPressed = false;
			}
			if (aWasPressedFirst || !aWasPressed)
				player.getPhysics().setVelocityX(1);
		}

		if (!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S))
			player.getPhysics().setVelocityY(0);
		if (!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D))
			player.getPhysics().setVelocityX(0);

		if (Gdx.input.isTouched()) {
			Vector2 touchPosition = new Vector2(Gdx.input.getX(),
					Gdx.graphics.getHeight() - Gdx.input.getY());
			addBullet(touchPosition.sub(onScreenPosition()).nor());
		}

	}

	private void addBullet(Vector2 velocity) {

		Entity bullet = new Entity();
		bullet.setBody(new Body(onScreenPosition(), 8, 8));
		bullet.setPhysics(new Physics(bullet, velocity, BULLET_SPEED));
		bullet.setDecay(new Decay(bullet, onScreenPosition(),
				BULLET_MAX_DISTANCE));
		bullets.add(bullet);

	}

	private Vector2 onScreenPosition() {
		return new Vector2((Gdx.graphics.getWidth()) / 2,
				(Gdx.graphics.getHeight()) / 2);
	}
}
