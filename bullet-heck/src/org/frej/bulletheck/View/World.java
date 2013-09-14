package org.frej.bulletheck.View;

import org.frej.bulletheck.BulletHeck;
import org.frej.bulletheck.Model.Entity;
import org.frej.bulletheck.Model.Components.Body;
import org.frej.bulletheck.Model.Components.Bullets;
import org.frej.bulletheck.Model.Components.Decay;
import org.frej.bulletheck.Model.Components.Physics;
import org.frej.bulletheck.Multiplayer.Multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {

	public static final float PALYER_SPEED = 150f;
	public static final float BULLET_SPEED = 250f;
	public static final float BULLET_MAX_DISTANCE = 150f;// (Gdx.graphics.getHeight()+Gdx.graphics.getWidth())/2;

	private Entity player;
	private Array<Entity> bullets;
	private Entity enemy;
	private Bullets bullets2;
	private Bullets enemyBullets;
	
	private boolean aWasPressedFirst;
	private boolean aWasPressed;
	private boolean wWasPressed;
	private boolean wWasPressedFirst;

	private TiledMap map;
	private float unitScale;
	private TiledMapTileLayer layer;
	private Multiplayer mp;

	/**
	 * @param player
	 */
	public World(BulletHeck game) {
		player = new Entity();
		player.setBody(new Body(new Vector2(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()), 64, 64));
		player.setPhysics(new Physics(player, new Vector2(1280, 720), PALYER_SPEED));

		bullets = new Array<Entity>();
		bullets2 = new Bullets();
		enemyBullets = new Bullets();
		
		enemy = new Entity();		
		enemy.setBody(new Body(new Vector2(0,0),64,64));
		
		map = new TmxMapLoader().load("data/mapka.tmx");
		layer = (TiledMapTileLayer) map.getLayers().get(0);
		unitScale = 1 / 32f;
		mp = new Multiplayer(game.args[0],game.args[1]);
	}

	/**
	 * @return wartość player
	 */
	public Entity getPlayer() {
		return player;
	}

	/**
	 * @return wartość bullets
	 */
	public Array<Entity> getBullets() {
		Array<Entity> ret = new Array<Entity>();
		ret.addAll(bullets2.getBullets());
		ret.addAll(enemyBullets.getBullets());
		
		return ret;
	}

	public void update() {
		mp.handleMessage();
		movement();
		if (isValidPosition(player.getPhysics().nextPosition())){
			player.getPhysics().update();
			mp.move(player.getPhysics().nextPosition());
		}/*
		for (Entity bullet : bullets) {
			bullet.getPhysics().update();
			if (bullet.getDecay().isDecayed())
				bullets.removeValue(bullet, false);
		}*/
		bullets2.setBulletOrigin(onScreenPosition());
		bullets2.update();
		enemy.getBody().setPosition(mp.getPosition());
		enemyBullets.setBulletOrigin(onScreenPosition(enemy));
		enemyBullets.addBullet(mp.getBulletVelocity());
		enemyBullets.update();

	}

	private boolean isValidPosition(Vector2 nextPosition) {
		boolean isBlocked=layer
				.getCell((int) (nextPosition.x * unitScale),
						(int) (nextPosition.y * unitScale)).getTile()
				.getProperties().containsKey("blocked");	
		return !isBlocked;
	}

	private void movement() {
		if (Gdx.input.isKeyPressed(Keys.W)) {
			player.getPhysics().setVelocityY(1);
			wWasPressed = true;
			if (!Gdx.input.isKeyPressed(Keys.S))
				wWasPressedFirst = true;
		}

		if (Gdx.input.isKeyPressed(Keys.S)) {
			if (!Gdx.input.isKeyPressed(Keys.W)) {
				wWasPressedFirst = false;
				wWasPressed = false;
			}
			if (wWasPressedFirst || !wWasPressed)
				player.getPhysics().setVelocityY(-1);
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			player.getPhysics().setVelocityX(-1);
			aWasPressed = true;
			if (!Gdx.input.isKeyPressed(Keys.D))
				aWasPressedFirst = true;
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			if (!Gdx.input.isKeyPressed(Keys.A)) {
				aWasPressedFirst = false;
				aWasPressed = false;
			}
			if (aWasPressedFirst || !aWasPressed)
				player.getPhysics().setVelocityX(1);
		}

		if (!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S))
			player.getPhysics().setVelocityY(0);
		if (!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D))
			player.getPhysics().setVelocityX(0);

		if (Gdx.input.isTouched()) {
			Vector2 touchPosition = new Vector2(Gdx.input.getX(),
					Gdx.graphics.getHeight() - Gdx.input.getY());
			bullets2.addBullet(touchPosition.sub(onScreenPosition()).nor());
			mp.shot(touchPosition);
		}

	}

	private void addBullet(Vector2 velocity) {

		Entity bullet = new Entity();
		bullet.setBody(new Body(onScreenPosition(), 8, 8));
		bullet.setPhysics(new Physics(bullet, velocity, BULLET_SPEED));
		bullet.setDecay(new Decay(bullet, onScreenPosition(),
				BULLET_MAX_DISTANCE));
		bullets.add(bullet);

	}

	private Vector2 onScreenPosition() {
		return new Vector2((Gdx.graphics.getWidth()) / 2,
				(Gdx.graphics.getHeight()) / 2);
	}
	
	
	private Vector2 onScreenPosition(Entity entity) {
		float translatedX = entity.getBody().getX() + (Gdx.graphics.getWidth() - entity.getBody().getWidth()) / 2
				- player.getBody().getX();
		float translatedY = entity.getBody().getY() + (Gdx.graphics.getHeight() - entity.getBody().getHeight()) / 2
				- player.getBody().getY();
		return new Vector2(translatedX,translatedY);		
	}

	public Entity getEnemy() {
		return enemy;
	}
	
	
}
