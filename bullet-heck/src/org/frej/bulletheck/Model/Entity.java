package org.frej.bulletheck.Model;

import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Decay;
import org.frej.bulletheck.Model.Components.Physics;

public abstract class Entity implements IEntity {

	private Physics physics;
	private Body body;
	private Decay decay;
	private boolean destroyed = false;
	
	public Physics getPhysics() {
		return physics;
	}
	public void setPhysics(Physics physics) {
		this.physics=physics;
	}
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body=body;
	}
	public Decay getDecay() {
		return decay;
	}
	public void setDecay(Decay decay) {
		this.decay = decay;
	}
	public abstract void update();
	public boolean isDestroyed() {
		return destroyed;
	}
	public void destroy() {
		destroyed = true;
	}
	

}
