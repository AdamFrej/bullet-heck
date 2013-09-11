package org.frej.bulletheck.Model;

import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Physics;

public interface IEntity {
	
	
	public Body getBody();
	public void setBody(Body body);
	public Physics getPhysics();
	public void setPhysics(Physics physics);
	

}
