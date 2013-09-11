package org.frej.bulletheck.Model;

import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Decay;
import org.frej.bulletheck.Model.Components.Physics;

public class Entity implements IEntity {

	private Physics physics;
	private Body body;
	private Decay decay;
	
	@Override
	public Physics getPhysics() {
		return physics;
	}

	@Override
	public void setPhysics(Physics physics) {
		this.physics=physics;
	}

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public void setBody(Body body) {
		this.body=body;
	}

	/**
	 * @return wartość decay
	 */
	public Decay getDecay() {
		return decay;
	}

	/**
	 * @param decay wartość decay do ustawienia
	 */
	public void setDecay(Decay decay) {
		this.decay = decay;
	}

}
