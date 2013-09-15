package org.frej.bulletheck.Model;

import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Physics;

import com.badlogic.gdx.math.Vector2;

public class Player extends Entity{

	private static final float SPEED = 150f;
	private static final float WIDTH = 64;
	private static final float HEIGHT = 64;

	public Player(Vector2 position) {
		setBody(new Body(position,WIDTH,HEIGHT ));
		setPhysics(new Physics(this,SPEED));
	}

	@Override
	public void update() {
		getPhysics().update();
		
	}
}
