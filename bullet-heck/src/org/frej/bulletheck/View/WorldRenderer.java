package org.frej.bulletheck.View;

import org.frej.bulletheck.Model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {
	private World world;
	private Entity player;

	private Texture playerTexture;
	private SpriteBatch batch;
	private Texture bulletTexture;

	/**
	 * @param world
	 */
	public WorldRenderer(World world) {
		this.world = world;
		player = this.world.getPlayer();

		playerTexture = new Texture("data/rycerzp.png");
		bulletTexture = new Texture("data/pocisk.png");
		batch = new SpriteBatch();
	}
	
	public void render(){
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(playerTexture,player.getBody().getPosition().x,player.getBody().getPosition().y);
		for(Entity bullet:world.getBullets()){
			batch.draw(bulletTexture,bullet.getBody().getPosition().x,bullet.getBody().getPosition().y);
		}
		//batch.draw(bulletTexture,bullet.getBody().getPosition().x,bullet.getBody().getPosition().y);
		batch.end();
		
	}

	public void dispose(){
		playerTexture.dispose();
		batch.dispose();
		
	}

}
