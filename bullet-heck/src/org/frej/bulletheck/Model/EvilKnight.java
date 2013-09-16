package org.frej.bulletheck.Model;

import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Health;
import org.frej.bulletheck.Model.Components.Physics;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class EvilKnight extends Entity {

	private static final float WIDTH = 64;
	private static final float HEIGHT = 64;
	private static final float SPEED = 100f;
	private static final int HIT_POINTS = 10;
	private static final float AGRO_RANGE = 300;
	private static final int DAMAGE_VALUE = 1;

	public EvilKnight(Vector2 position, TiledMapTileLayer groundColisions) {
		setBody(new Body(position,WIDTH,HEIGHT));
		setPhysics(new Physics(this,SPEED,groundColisions));
		setHealth(new Health(this,HIT_POINTS));
	}
	
	@Override
	public void update(Array<Entity> entities) {
		getPhysics().update(entities);
		getHealth().update();
		for(Entity target : getTargets()){
			boolean targetIsInRange = target.getBody().getPosition().dst(getBody().getPosition())<AGRO_RANGE;
			if (targetIsInRange){
				getPhysics().setVelocity(target.getBody().getPosition().sub(getBody().getPosition()).nor());	
			}
			else
				getPhysics().setVelocity(new Vector2(0,0));
			boolean targetIsTouching = target.getBody().getBounds().overlaps(getPhysics().nextBounds());
			if(targetIsTouching)target.getHealth().damage(DAMAGE_VALUE);
		}
	}
}
