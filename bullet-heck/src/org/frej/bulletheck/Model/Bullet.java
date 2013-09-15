package org.frej.bulletheck.Model;

import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Decay;
import org.frej.bulletheck.Model.Components.Physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Bullet extends Entity {

	private static final float WIDTH = 8;
	private static final float HEIGHT = 8;
	private static final float SPEED = 250f;
	private static final float MAX_DISTANCE = 150f;
	public static final int DAMAGE_VALUE = 1;

	public Bullet(Vector2 position, Vector2 velocity,Array<Entity> targets) {
		setBody(new Body(position, WIDTH, HEIGHT));
		setPhysics(new Physics(this, velocity, SPEED));
		setDecay(new Decay(this, getBody().getPosition(), MAX_DISTANCE));
		setTargets(targets);
	}

	@Override
	public void update() {
		getPhysics().update();
		getDecay().update();
		for (Entity target : getTargets()) {
			if (getBody().getBounds().overlaps(target.getBody().getBounds())) {
				target.getHealth().damage(DAMAGE_VALUE);
				destroy();
			}
		}
	}

}
