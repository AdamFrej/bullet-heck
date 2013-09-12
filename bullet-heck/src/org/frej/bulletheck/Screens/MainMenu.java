package org.frej.bulletheck.Screens;

import org.frej.bulletheck.BulletHeck;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class MainMenu implements Screen{

	private Stage stage;
	private SpriteBatch batch;
	private TextureAtlas atlas;
	private Skin skin;
	private BitmapFont font;
	private BulletHeck game;

	/**
	 * @param game
	 */
	public MainMenu(BulletHeck game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.188f, 0.204f, 0.427f,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		batch.begin();
		stage.draw();
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();

		Gdx.input.setInputProcessor(stage);

		TextButtonStyle playStyle = new TextButtonStyle();
		playStyle.up = skin.getDrawable("play");
		playStyle.down = skin.getDrawable("play");
		playStyle.font = font;
		
		TextButtonStyle optionsStyle = new TextButtonStyle();
		optionsStyle.up = skin.getDrawable("options");
		optionsStyle.down = skin.getDrawable("options");
		optionsStyle.font = font;
		
		TextButtonStyle quitStyle = new TextButtonStyle();
		quitStyle.up = skin.getDrawable("quit");
		quitStyle.down = skin.getDrawable("quit");
		quitStyle.font = font;

		TextButton play = new TextButton("Play", playStyle);
		play.setWidth(4*96);
		play.setHeight(4*32);
		play.setX(Gdx.graphics.getWidth()/2-2*96);
		play.setY(Gdx.graphics.getHeight()-10*32);
		
		TextButton options = new TextButton("  options", optionsStyle);
		options.setWidth(4*96);
		options.setHeight(4*32);
		options.setX(Gdx.graphics.getWidth()/2-2*96);
		options.setY(Gdx.graphics.getHeight()-14*32);
		
		TextButton quit = new TextButton("Quit ", quitStyle);
		quit.setWidth(4*96);
		quit.setHeight(4*32);
		quit.setX(Gdx.graphics.getWidth()/2-2*96);
		quit.setY(Gdx.graphics.getHeight()-18*32);
		
		play.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				game.setScreen(new GameScreen());
			}
		});
		quit.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				Gdx.app.exit();
			}
		});
		
		
		
		LabelStyle ls = new LabelStyle(font,Color.WHITE);
		Label gameName = new Label("bullet heck",ls);
		gameName.setX(0);
		gameName.setY(Gdx.graphics.getHeight()*0.85f);
		gameName.setWidth(width);
		gameName.setAlignment(Align.center);
		
		stage.addActor(play);
		stage.addActor(options);
		stage.addActor(quit);
		stage.addActor(gameName);

	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		atlas = new TextureAtlas("data/buttons.pack");
		skin = new Skin();
		skin.addRegions(atlas);
		font = new BitmapFont(Gdx.files.internal("data/visitor.fnt"),
				false);
		//font.setScale(0.7f);
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
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		font.dispose();
		stage.dispose();
		
	}

}
