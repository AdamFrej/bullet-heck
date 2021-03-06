package org.frej.bulletheck.Screens;

import org.frej.bulletheck.BulletHeck;
import org.frej.bulletheck.View.World;
import org.frej.bulletheck.View.WorldRenderer;

import com.badlogic.gdx.Screen;


public class GameScreen implements Screen {

	private World world;
	private WorldRenderer wr;
	
	public GameScreen(BulletHeck game) {
		this.world = new World(game);
		this.wr = new WorldRenderer(world);
	}

	@Override
	public void render(float delta) {
		world.update();
		wr.render();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Automatycznie generowany szkielet metody

	}

	@Override
	public void show() {
		// TODO Automatycznie generowany szkielet metody

	}
	
	@Override
	public void hide() {
		// TODO Automatycznie generowany szkielet metody

	}
	
	@Override
	public void pause() {
		// TODO Automatycznie generowany szkielet metody

	}
	
	@Override
	public void resume() {
		// TODO Automatycznie generowany szkielet metody

	}

	@Override
	public void dispose() {
		wr.dispose();
	}

}
