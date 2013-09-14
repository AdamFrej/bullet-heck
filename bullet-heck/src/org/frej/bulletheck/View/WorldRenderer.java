package org.frej.bulletheck.View;

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
	private World world;
	private Entity player;
	private Entity enemy;

	private Texture playerTexture;
	private Texture enemyTexture;
	private SpriteBatch batch;
	private Texture bulletTexture;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera mapCam;
	private float unitScale;

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

	/**
	 * @param world
	 */
	public WorldRenderer(World world) {

		this.world = world;
		player = world.getPlayer();
		enemy = world.getEnemy();

		playerTexture = new Texture("data/test.png");// rycerzp.png");
		enemyTexture = new Texture("data/rycerzp.png");
		bulletTexture = new Texture("data/pocisk.png");

		map = new TmxMapLoader().load("data/mapka.tmx");

		mapCam = new OrthographicCamera();
		mapCam.setToOrtho(false, 40, 22);
		mapCam.update();

		unitScale = 1 / 32f;
		mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
		mapRenderer.setView(mapCam);

		batch = new SpriteBatch();

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

	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		mapCam.position.set(player.getBody().getX() * unitScale, player
				.getBody().getY() * unitScale, 0);
		mapCam.update();

		mapRenderer.setView(mapCam);
		mapRenderer.render();

		stateTime += Gdx.graphics.getDeltaTime();
		switch (player.getBody().getFace()) {
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

		batch.begin();
		batch.draw(currentFrame, (Gdx.graphics.getWidth() - player.getBody()
				.getWidth()) / 2, (Gdx.graphics.getHeight() - player.getBody()
				.getHeight()) / 2);
		for (Entity bullet : world.getBullets())
			batch.draw(bulletTexture, bullet.getBody().getX(), bullet.getBody()
					.getY());
		batch.draw(enemyTexture, enemyX(), enenmyY());
		batch.end();

	}

	private float enenmyY() {
		return enemy.getBody().getY()
				+ (Gdx.graphics.getHeight() - enemy.getBody().getHeight()) / 2
				- mapCam.position.y / unitScale;
	}

	private float enemyX() {
		return enemy.getBody().getX()
				+ (Gdx.graphics.getWidth() - enemy.getBody().getWidth()) / 2
				- mapCam.position.x / unitScale;
	}

	public void dispose() {
		playerTexture.dispose();
		bulletTexture.dispose();
		batch.dispose();
		map.dispose();
		mapRenderer.dispose();

	}

}
