package org.frej.bulletheck.Model.Components;

import org.frej.bulletheck.Model.Entity;

public class Health {

	private int hitPoints;
	private Entity entity;
	
	public Health(Entity entity,int hitPoints) {
		this.hitPoints = hitPoints;
		this.entity=entity;
	}

	public void update(){
		if(hitPoints<=0)entity.destroy();
	}

	public void damage(int damageValue) {
		hitPoints-=damageValue;
		this.update();
	}
}
