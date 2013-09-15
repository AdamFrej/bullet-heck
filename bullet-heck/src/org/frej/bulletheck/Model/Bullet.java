package org.frej.bulletheck.Model;

import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Decay;
import org.frej.bulletheck.Model.Components.Physics;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity {
	
	private static final float WIDTH = 8;
	private static final float HEIGHT = 8;
	private static final float SPEED = 250f;
	private static final float MAX_DISTANCE = 150f;

	public Bullet(Vector2 position,Vector2 velocity) {
		setBody(new Body(position,WIDTH,HEIGHT ));
		setPhysics(new Physics(this,velocity,SPEED));
		setDecay(new Decay(this,getBody().getPosition(),MAX_DISTANCE));
	}

	@Override
	public void update() {
			getPhysics().update();
			getDecay().update();
	}

}
