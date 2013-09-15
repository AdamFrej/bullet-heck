package org.frej.bulletheck.Model;

import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Decay;
import org.frej.bulletheck.Model.Components.Health;
import org.frej.bulletheck.Model.Components.Physics;
import org.frej.bulletheck.Model.Components.Weapon;

import com.badlogic.gdx.utils.Array;

public abstract class Entity implements IEntity {

	private Physics physics;
	private Body body;
	private Decay decay;
	private Health health;
	private Weapon weapon;
	private Array<Entity> targets;
	private Array<Entity> group;

	private boolean destroyed = false;

	public Physics getPhysics() {
		return physics;
	}

	public void setPhysics(Physics physics) {
		this.physics = physics;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Decay getDecay() {
		return decay;
	}

	public void setDecay(Decay decay) {
		this.decay = decay;
	}

	public Health getHealth() {
		return health;
	}

	public void setHealth(Health health) {
		this.health = health;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public abstract void update();

	public boolean isDestroyed() {
		return destroyed;
	}

	public void destroy() {
		destroyed = true;
	}

	public Array<Entity> getTargets() {
		return targets;
	}

	public void setTargets(Array<Entity> targets) {
		this.targets = targets;
	}

	public Array<Entity> getGroup() {
		return group;
	}

	public void setGroup(Array<Entity> group) {
		this.group = group;
	}
	
	

}
