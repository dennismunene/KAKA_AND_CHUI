package com.game254studios.models;


import org.andengine.audio.sound.Sound;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.ui.activity.BaseGameActivity;

import android.widget.Toast;

import com.game254studios.view.ZoeziMaumboView;



public class ZoeziMaumboModel extends Model {

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;
	private ZoeziMaumboView view;

	public ZoeziMaumboModel(BaseGameActivity act, Engine eng, Camera cam) {
		this.activity = act;
		this.engine = eng;
		this.mCamera = cam;

		view = new ZoeziMaumboView(act, eng, cam);
	}

	@Override
	public ITexture[] getResources() {
	
		
		return view.getTextureAtlases();
	}

	private ButtonSprite getSomaItem(int index) {
		return view.getSomaItems()[index];
	}

	private Sound getZoeziSounds(int index) {
		return view.getZoeziSounds()[index];
	}

	public SpriteBackground getBackground() {
		return view.background();
	}

	public SpriteSoundPair[] getZoeziItems() {

		SpriteSoundPair items[] = new SpriteSoundPair[view.getSomaItems().length];

		for (int i = 0; i < view.getSomaItems().length; i++)
			items[i] = new SpriteSoundPair(getSomaItem(i), getZoeziSounds(i));

		
		return items;

	}

	public AnimatedSprite getAnim(boolean isHappy) {
		return view.getAnim(isHappy);
	}

	public Sprite getGreenTick() {
		return view.getGreenTick();
	}

	public Sprite getRedX() {
		return view.getRedX();
	}

	public Sound getApplauseSound() {

		return view.getApplauseSound();

	}

	public Sound getSadSound() {

		return view.getSadSound();

	}
	
	public ButtonSprite getBackButton(){
		return view.backButton();
	}
	public Sound getClappingSound(){return view.getClappingSound();}
	
	public void showToast(final String text) {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(activity, text, Toast.LENGTH_LONG).show();

			}
		});
	}

}

