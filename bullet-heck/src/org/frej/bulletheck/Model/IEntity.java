package org.frej.bulletheck.Model;

import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Decay;
import org.frej.bulletheck.Model.Components.Health;
import org.frej.bulletheck.Model.Components.Physics;
import org.frej.bulletheck.Model.Components.Weapon;

import com.badlogic.gdx.utils.Array;

public interface IEntity {
	
	public Body getBody();
	public void setBody(Body body);
	public Physics getPhysics();
	public void setPhysics(Physics physics);
	public Decay getDecay();
	public void setDecay(Decay decay);
	public Health getHealth();
	public void setHealth(Health health);
	public Weapon getWeapon();
	public void setWeapon(Weapon weapon);
	
	
	public void destroy();
	public boolean isDestroyed();
	
	public void update(Array<Entity> entities);
	
	public Array<Entity> getTargets();
	public void setTargets(Array<Entity> targets);
	
	public Array<Entity> getGroup();
	public void setGroup(Array<Entity> group);

}
