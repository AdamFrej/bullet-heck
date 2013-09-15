package org.frej.bulletheck.View;

import org.frej.bulletheck.Model.Bullet;
import org.frej.bulletheck.Model.EvilKnight;
import org.frej.bulletheck.Model.Entity;
import org.frej.bulletheck.Model.Components.Body;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class WorldRenderer {
	private final World world;
	private final Entity mainPlayer;

	private final SpriteBatch batch;
	private final Texture bulletTexture;
	private final Texture enemyTexture;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera mapCam;
	private final float unitScale = 1 / 32f;

	private static final int FRAME_COLS = 9;
	private static final int FRAME_ROWS = 4;

	private Animation leftAnimation;
	private Animation rightAnimation;
	private Animation upAnimation;
	private Animation downAnimation;

	private Texture walkSheet;

	private TextureRegion[] leftFrames;
	private TextureRegion[] rightFrames;
	private TextureRegion[] upFrames;
	private TextureRegion[] downFrames;

	private TextureRegion currentFrame;
	private float stateTime;

	public WorldRenderer(World world) {

		this.world = world;
		mainPlayer = world.getMainPlayer();
		setupMainPlayerAnimation();

		setupMap();
		bulletTexture = new Texture("data/pocisk.png");
		enemyTexture = new Texture("data/rycerzp.png");
		batch = new SpriteBatch();
	}

	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		renderMap();

		setMainPlayerFace();

		batch.begin();
		batch.draw(
				currentFrame,
				(Gdx.graphics.getWidth() - mainPlayer.getBody().getWidth()) / 2,
				(Gdx.graphics.getHeight() - mainPlayer.getBody().getHeight()) / 2);
		for (Entity bullet : mainPlayer.getWeapon().getBullets())
			batch.draw(getTexture(bullet), translateX(bullet),
					translateY(bullet));
		for (Entity entity : world.getEntities())
			batch.draw(getTexture(entity), translateX(entity),
					translateY(entity));
		batch.end();

	}

	private Texture getTexture(Entity entity) {
		if (entity instanceof Bullet)
			return bulletTexture;
		if (entity instanceof EvilKnight)
			return enemyTexture;
		return null;
	}

	private void setupMap() {
		map = new TmxMapLoader().load("data/mapka.tmx");

		mapCam = new OrthographicCamera();
		mapCam.setToOrtho(false, 40, 22);
		mapCam.update();

		mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
		mapRenderer.setView(mapCam);
	}

	private void setupMainPlayerAnimation() {
		walkSheet = new Texture("data/orcAnimation.png");
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS);
		upFrames = new TextureRegion[FRAME_COLS];
		downFrames = new TextureRegion[FRAME_COLS];
		leftFrames = new TextureRegion[FRAME_COLS];
		rightFrames = new TextureRegion[FRAME_COLS];

		for (int j = 0; j < FRAME_COLS; j++) {
			upFrames[j] = tmp[0][j];
		}
		for (int j = 0; j < FRAME_COLS; j++) {
			leftFrames[j] = tmp[1][j];
		}
		for (int j = 0; j < FRAME_COLS; j++) {
			downFrames[j] = tmp[2][j];
		}
		for (int j = 0; j < FRAME_COLS; j++) {
			rightFrames[j] = tmp[3][j];
		}
		upAnimation = new Animation(0.1f, upFrames);
		leftAnimation = new Animation(0.1f, leftFrames);
		rightAnimation = new Animation(0.1f, rightFrames);
		downAnimation = new Animation(0.1f, downFrames);

		stateTime = 0f;
	}

	private void renderMap() {
		mapCam.position.set(mainPlayer.getBody().getX() * unitScale, mainPlayer
				.getBody().getY() * unitScale, 0);
		mapCam.update();

		mapRenderer.setView(mapCam);
		mapRenderer.render();
	}

	private void setMainPlayerFace() {
		stateTime += Gdx.graphics.getDeltaTime();
		switch (mainPlayer.getBody().getFace()) {
		case Body.DOWN:
			currentFrame = downAnimation.getKeyFrame(stateTime, true);
			break;
		case Body.UP:
			currentFrame = upAnimation.getKeyFrame(stateTime, true);
			break;
		case Body.LEFT:
			currentFrame = leftAnimation.getKeyFrame(stateTime, true);
			break;
		case Body.RIGHT:
			currentFrame = rightAnimation.getKeyFrame(stateTime, true);
			break;
		case Body.STOP:
			currentFrame = downAnimation.getKeyFrame(0);
			break;
		default:
			currentFrame = downAnimation.getKeyFrame(0);
			break;
		}
	}

	private float translateY(Entity entity) {
		return entity.getBody().getY()
				+ (Gdx.graphics.getHeight() - entity.getBody().getHeight()) / 2
				- mainPlayer.getBody().getY();
	}

	private float translateX(Entity entity) {
		return entity.getBody().getX()
				+ (Gdx.graphics.getWidth() - entity.getBody().getWidth()) / 2
				- mainPlayer.getBody().getX();
	}

	public void dispose() {
		bulletTexture.dispose();
		batch.dispose();
		map.dispose();
		mapRenderer.dispose();

	}

}
