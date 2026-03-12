package com.game254studios.models;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;

public class SpriteSoundPair {

	private ButtonSprite mButtonSprite;
	private Sound mSound;
	private Sprite sprite;

	public SpriteSoundPair(ButtonSprite mButtonSprite, Sound mSound) {
		this.mButtonSprite = mButtonSprite;
		this.mSound = mSound;
	}
	
	public SpriteSoundPair(Sprite mSprite, Sound mSound) {
		this.sprite = mSprite;
		this.mSound = mSound;
	}

	public ButtonSprite getButtonSprite() {
		return mButtonSprite;
	}
	
	public Sprite getSprite(){
		return sprite;
	}

	public Sound getSound() {
		return mSound;
	}

}
