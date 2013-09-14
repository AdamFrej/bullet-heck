package org.frej.bulletheck;

import org.frej.bulletheck.Screens.MainMenu;

import com.badlogic.gdx.Game;


public class BulletHeck extends Game{

	public static final String VERSION = "Pre-alpha";
	public String[] args;
	

	/**
	 * @param args
	 */
	public BulletHeck(String[] args) {
		super();
		this.args = args;
	}


	@Override
	public void create() {
		setScreen(new MainMenu(this));
		
	}
}
