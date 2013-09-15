package org.frej.bulletheck.Model.Components;

import org.frej.bulletheck.Model.Entity;

import com.badlogic.gdx.utils.Array;

public class Weapon {
	Entity entity;
	Array<Entity> bullets;

	public Weapon(Entity entity) {
		this.entity = entity;
		this.bullets = new Array<Entity>();
	}

	public void update() {
		for (Entity bullet : bullets) {
			bullet.update();
			if (bullet.isDestroyed())
				bullets.removeValue(bullet, false);
		}
	}

	public Array<Entity> getBullets() {
		return bullets;
	}

	public void setBullets(Array<Entity> bullets) {
		this.bullets = bullets;
	}

	public void add(Entity entity) {
		bullets.add(entity);
	}

	public void removeValue(Entity entity, boolean identity) {
		bullets.removeValue(entity, identity);
	}

}
