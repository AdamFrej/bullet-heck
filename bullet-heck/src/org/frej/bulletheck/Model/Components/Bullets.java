package org.frej.bulletheck.Model.Components;

import org.frej.bulletheck.Model.Entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Bullets {

	public static final float BULLET_SPEED = 250f;
	public static final float BULLET_MAX_DISTANCE = 150f;

	private Array<Entity> bullets;
	private Vector2 bulletOrigin;

	public Bullets() {
		bullets = new Array<Entity>();
	}

	public void setBulletOrigin(Vector2 bulletOrigin) {
		this.bulletOrigin = bulletOrigin;
	}

	public void addBullet(Vector2 velocity) {

		if (velocity.x != 0 || velocity.y != 0) {
			Entity bullet = new Entity();
			bullet.setBody(new Body(bulletOrigin, 8, 8));
			bullet.setPhysics(new Physics(bullet, velocity, BULLET_SPEED));
			bullet.setDecay(new Decay(bullet, bulletOrigin, BULLET_MAX_DISTANCE));
			bullets.add(bullet);
		}

	}

	public void update() {
		for (Entity bullet : bullets) {
			bullet.getPhysics().update();
			if (bullet.getDecay().isDecayed())
				bullets.removeValue(bullet, false);
		}

	}

	public Array<Entity> getBullets() {
		return bullets;
	}

}
