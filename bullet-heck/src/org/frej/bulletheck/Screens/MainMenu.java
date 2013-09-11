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
		Gdx.gl.glClearColor(1, 1, 1, 1);
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
		play.setWidth(2*96);
		play.setHeight(2*32);
		play.setX(Gdx.graphics.getWidth()/8);
		play.setY(Gdx.graphics.getHeight()-4*32);
		
		TextButton options = new TextButton("Options", optionsStyle);
		options.setWidth(2*96);
		options.setHeight(2*32);
		options.setX(Gdx.graphics.getWidth()/8);
		options.setY(Gdx.graphics.getHeight()-6*32);
		
		TextButton quit = new TextButton("Quit", quitStyle);
		quit.setWidth(2*96);
		quit.setHeight(2*32);
		quit.setX(Gdx.graphics.getWidth()/8);
		quit.setY(Gdx.graphics.getHeight()-8*32);
		
		play.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				game.setScreen(new GameScreen());
			}
		});
		
		LabelStyle ls = new LabelStyle(font,Color.BLACK);
		Label gameName = new Label("bullet heck",ls);
		gameName.setX(0);
		gameName.setY(Gdx.graphics.getHeight()/2+100);
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
