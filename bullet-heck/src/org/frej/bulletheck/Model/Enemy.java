package org.frej.bulletheck.Model;

import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Health;
import org.frej.bulletheck.Model.Components.Physics;

import com.badlogic.gdx.math.Vector2;


public class Enemy extends Entity {

	private static final float WIDTH = 64;
	private static final float HEIGHT = 64;
	private static final float SPEED = 100f;
	private static final int HIT_POINTS = 10;

	public Enemy(Vector2 position) {
		setBody(new Body(position,WIDTH,HEIGHT));
		setPhysics(new Physics(this,SPEED));
		setHealth(new Health(this,HIT_POINTS));
	}
	
	@Override
	public void update() {
		getPhysics().update();
		getHealth().update();
	}
}
