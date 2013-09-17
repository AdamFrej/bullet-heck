package org.frej.bulletheck.View;

import org.frej.bulletheck.BulletHeck;
import org.frej.bulletheck.Model.Bullet;
import org.frej.bulletheck.Model.Entity;
import org.frej.bulletheck.Model.EvilKnight;
import org.frej.bulletheck.Model.Player;
import org.frej.bulletheck.Multiplayer.Multiplayer;

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
	private final TiledMapTileLayer groundColisions;

	private Multiplayer mp;
	private Array<Entity> enemyTargets;

	public World(BulletHeck game) {

		if (game.args.length == 3)
			mp = new Multiplayer(game.args[0], game.args[1], game.args[2]);
		else
			mp = new Multiplayer(game.args[0], game.args[1], "localhost");

		Vector2 mainPlayerStartingPosition = new Vector2(
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		onScreenPosition = new Vector2((Gdx.graphics.getWidth()) / 2,
				(Gdx.graphics.getHeight()) / 2);

		TiledMap map = new TmxMapLoader().load("data/mapka.tmx");
		map.dispose();
		groundColisions = (TiledMapTileLayer) map.getLayers().get(0);

		entities = new Array<Entity>();
		mainPlayer = new Player(mainPlayerStartingPosition, groundColisions);

		Vector2 enemyStartingPosition = new Vector2(1000, 600);
		Entity enemy = new EvilKnight(enemyStartingPosition, groundColisions);
		enemyTargets = new Array<Entity>();
		enemyTargets.add(mainPlayer);
		enemy.setTargets(enemyTargets);
		//entities.add(enemy);

		Vector2 otherPlayerStartingPosition = new Vector2(1000, 1600);
		Entity otherPlayer = new Player(otherPlayerStartingPosition,
				groundColisions);
		otherPlayer.setTargets(enemyTargets);
		entities.add(otherPlayer);
	}

	public Entity getMainPlayer() {
		return mainPlayer;
	}

	public Array<Entity> getEntities() {
		return entities;
	}

	public void update() {
		mp.handleMessage();
		movement();
		mainPlayer.update(entities);
		if (mainPlayer.isDestroyed())
			System.out.println("You are now dead!!!");
		mainPlayer.setTargets(entities);
		mp.move(mainPlayer.getBody().getPosition());

		for (Entity entity : entities) {
			entity.update(mainPlayerAndEntities());
			if (entity instanceof Player) {
				entity.getBody().setPosition(mp.getPosition());
				Vector2 bulletVelocity = mp.getBulletVelocity();
				if (bulletVelocity.x != 0 || bulletVelocity.y != 0)
					entity.getWeapon().add(
							new Bullet(entity.getBody().getPosition(),
									bulletVelocity, enemyTargets,
									groundColisions));
			}
			if (entity.isDestroyed())
				entities.removeValue(entity, false);
		}
	}

	private Array<Entity> mainPlayerAndEntities() {
		Array<Entity> ret = new Array<Entity>();
		ret.add(mainPlayer);
		ret.addAll(entities);
		return ret;
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
						.add(new Bullet(
								mainPlayer.getBody().getPosition(),
								touchPosition.cpy().sub(onScreenPosition).nor(),
								mainPlayer.getTargets(), groundColisions));
				mp.shot(touchPosition.cpy().sub(onScreenPosition).nor());
				bulletTime = 0;
			}
		}

	}
}
