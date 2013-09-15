package org.frej.bulletheck.Model.Components;

import org.frej.bulletheck.Model.Entity;

import com.badlogic.gdx.math.Vector2;

public class Decay {
	Entity entity;
	private Vector2 origin;
	private float maxDistance;

	public Decay(Entity entity, Vector2 origin, float maxDistance) {
		this.entity = entity;
		this.origin = origin;
		this.maxDistance = maxDistance;
	}
	public void update() {
		if (entity.getBody().getPosition().dst(origin)>maxDistance)
			entity.destroy();
	}

}
