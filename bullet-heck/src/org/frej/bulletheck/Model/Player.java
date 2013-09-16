package org.frej.bulletheck.Model;

import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Health;
import org.frej.bulletheck.Model.Components.Physics;
import org.frej.bulletheck.Model.Components.Weapon;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player extends Entity{

	private static final float SPEED = 150f;
	private static final float WIDTH = 64;
	private static final float HEIGHT = 64;
	private static final int HIT_POINTS = 100;
	public static final float BULLETS_TIME = 1/10f;

	public Player(Vector2 position,TiledMapTileLayer groundColisions) {
		setBody(new Body(position,WIDTH,HEIGHT ));
		setPhysics(new Physics(this,SPEED,groundColisions));
		setHealth(new Health(this,HIT_POINTS));
		setWeapon(new Weapon(this));
	}

	@Override
	public void update(Array<Entity> entities) {
		getPhysics().update(entities);
		getWeapon().update(entities);
		getHealth().update();
		
	}
}
