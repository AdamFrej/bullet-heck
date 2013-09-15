package org.frej.bulletheck.View;

import org.frej.bulletheck.BulletHeck;
import org.frej.bulletheck.Model.Bullet;
import org.frej.bulletheck.Model.Enemy;
import org.frej.bulletheck.Model.Entity;
import org.frej.bulletheck.Model.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {
	private Array<Entity> entities;
	private Entity mainPlayer;

	private boolean aWasPressedFirst;
	private boolean aWasPressed;
	private boolean wWasPressed;
	private boolean wWasPressedFirst;
	private float bulletTime;
	private final Vector2 onScreenPosition;

	private TiledMap map;
	private final float unitScale = 1 / 32f;
	private TiledMapTileLayer layer;

	public World(BulletHeck game) {

		Vector2 mainPlayerStartingPosition = new Vector2(
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		onScreenPosition = new Vector2((Gdx.graphics.getWidth()) / 2,
				(Gdx.graphics.getHeight()) / 2);

		entities = new Array<Entity>();
		mainPlayer = new Player(mainPlayerStartingPosition);

		Vector2 enemyStartingPosition = new Vector2(1200, 500);
		entities.add(new Enemy(enemyStartingPosition));

		map = new TmxMapLoader().load("data/mapka.tmx");
		layer = (TiledMapTileLayer) map.getLayers().get(0);

	}

	public Entity getMainPlayer() {
		return mainPlayer;
	}

	public Array<Entity> getEntities() {
		return entities;
	}

	public void update() {
		movement();
		if (isValidPosition(mainPlayer.getPhysics().nextPosition()))
			mainPlayer.update();

		for (Entity bullet : mainPlayer.getWeapon().getBullets()) {
			for (Entity entity : entities)
				if (bullet.getBody().getBounds()
						.overlaps(entity.getBody().getBounds())) {
					entity.getHealth().damage(Bullet.DAMAGE_VALUE);
					bullet.destroy();
				}

			if (bullet.isDestroyed())
				mainPlayer.getWeapon().removeValue(bullet, false);

		}

		for (Entity entity : entities) {
			entity.update();
			if (entity.isDestroyed())
				entities.removeValue(entity, false);
		}
	}

	private boolean isValidPosition(Vector2 nextPosition) {
		boolean isBlocked = layer
				.getCell((int) (nextPosition.x * unitScale),
						(int) (nextPosition.y * unitScale)).getTile()
				.getProperties().containsKey("blocked");
		return !isBlocked;
	}

	private void movement() {
		if (Gdx.input.isKeyPressed(Keys.W)) {
			mainPlayer.getPhysics().setVelocityY(1);
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
				mainPlayer.getPhysics().setVelocityY(-1);
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			mainPlayer.getPhysics().setVelocityX(-1);
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
				mainPlayer.getPhysics().setVelocityX(1);
		}

		if (!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S))
			mainPlayer.getPhysics().setVelocityY(0);
		if (!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D))
			mainPlayer.getPhysics().setVelocityX(0);

		if (Gdx.input.isTouched()) {
			Vector2 touchPosition = new Vector2(Gdx.input.getX(),
					Gdx.graphics.getHeight() - Gdx.input.getY());
			bulletTime += Gdx.graphics.getDeltaTime();
			if (bulletTime >= Player.BULLETS_TIME) {
				mainPlayer
						.getWeapon()
						.add(new Bullet(mainPlayer.getBody().getPosition(),
								touchPosition.cpy().sub(onScreenPosition).nor()));
				bulletTime = 0;
			}
		}

	}
}
