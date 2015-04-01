package com.game254studios.view;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import com.game254studios.SceneManager;
import com.game254studios.SceneManager.AllScenes;

public class SomaRangiView extends MyView {

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;

	// TEXTURES
	private BitmapTextureAtlas bgTA, nextTA, prevTA, kijaniTA, manjanoTA,
			nyekunduTA, nyeupeTA, nyeusiTA, backTA, samawatiTA;
	private ITextureRegion bgTR, nextTR, prevTR, kijaniTR, manjanoTR,
			nyekunduTR, nyeupeTR, nyeusiTR, backTR, samawatiTR;

	private ButtonSprite next, prev, kijani, manjano, nyekundu, nyeupe, nyeusi,
			samawati;

	// sounds

	private Sound soundKijani, soundManjano, soundNyekundu, soundNyeupe,
			soundNyeusi, soundSamawati, buttonSound;

	public SomaRangiView(BaseGameActivity act, Engine eng, Camera cam) {

		this.activity = act;
		this.engine = eng;
		this.mCamera = cam;
	}

	@Override
	public ITexture[] getTextureAtlases() {
		ITexture[] array = new ITexture[10];

		// background
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		bgTA = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		bgTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bgTA,
				activity, "background.png", 0, 0);

		// arrow next
		nextTA = new BitmapTextureAtlas(activity.getTextureManager(), 124, 91,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		nextTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(nextTA,
				activity, "green_arrow.png", 0, 0);

		// arrow prev
		prevTA = new BitmapTextureAtlas(activity.getTextureManager(), 124, 91,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		prevTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(prevTA,
				activity, "green_arrow.png", 0, 0);

		// back
		backTA = new BitmapTextureAtlas(activity.getTextureManager(), 124, 91,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		backTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backTA,
				activity, "backbutton.png", 0, 0);
		// TODO

		// vowel 'a'
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/rangi/");
		kijaniTA = new BitmapTextureAtlas(activity.getTextureManager(), 470,
				486, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		kijaniTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				kijaniTA, activity, "kijani.png", 0, 0);
		

		// vowel 'e'
		manjanoTA = new BitmapTextureAtlas(activity.getTextureManager(), 470,
				486, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		manjanoTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				manjanoTA, activity, "manjano.png", 0, 0);
		manjanoTA.load();

		// vowel 'i'
		nyekunduTA = new BitmapTextureAtlas(activity.getTextureManager(), 470,
				486, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		nyekunduTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				nyekunduTA, activity, "nyekundu.png", 0, 0);
		

		// vowel 'o'
		nyeupeTA = new BitmapTextureAtlas(activity.getTextureManager(), 470,
				486, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		nyeupeTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				nyeupeTA, activity, "nyeupe.png", 0, 0);
		

		// vowel 'u'
		nyeusiTA = new BitmapTextureAtlas(activity.getTextureManager(), 470,
				486, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		nyeusiTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				nyeusiTA, activity, "nyeusi.png", 0, 0);
		

		samawatiTA = new BitmapTextureAtlas(activity.getTextureManager(), 470,
				486, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		samawatiTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				samawatiTA, activity, "samawati.png", 0, 0);

		loadSounds();
		array[0] = bgTA;
		array[1] = nextTA;
		array[2] = prevTA;
		array[3] = kijaniTA;
		array[4] = manjanoTA;
		array[5] = nyeupeTA;
		array[6] = nyeusiTA;
		array[7] = samawatiTA;
		array[8] = nyekunduTA;
		array [9] = backTA;

		return array;
	}

	private void loadSounds() {

		SoundFactory.setAssetBasePath("mfx/Rangi/Rangi Soma/");
		try {
			soundKijani = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Kijani.aac");
			soundManjano = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Manjano.aac");
			soundNyekundu = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Nyekundu.aac");
			soundNyeupe = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Nyeupe.aac");
			soundNyeusi = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Nyeusi.aac");

			soundSamawati = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Samawati.aac");

			SoundFactory.setAssetBasePath("mfx/");
			buttonSound = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "two_tone_nav.mp3");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public ButtonSprite[] getSomaItems() {

		ButtonSprite[] array = new ButtonSprite[6];

		// vowel 'a'
		kijani = new ButtonSprite(0, 0, kijaniTR,
				activity.getVertexBufferObjectManager());
		kijani.setPosition((this.mCamera.getWidth() - kijani.getWidth()) / 2,
				(this.mCamera.getHeight() - kijani.getHeight()) / 2);
		kijani.setScale(.7f);

		// vowel 'e'
		samawati = new ButtonSprite(0, 0, samawatiTR,
				activity.getVertexBufferObjectManager());
		samawati.setPosition(
				(this.mCamera.getWidth() - samawati.getWidth()) / 2,
				(this.mCamera.getHeight() - samawati.getHeight()) / 2);
		samawati.setScale(.7f);

		// vowel 'a'
		nyekundu = new ButtonSprite(0, 0, nyekunduTR,
				activity.getVertexBufferObjectManager());
		nyekundu.setPosition(
				(this.mCamera.getWidth() - nyekundu.getWidth()) / 2,
				(this.mCamera.getHeight() - nyekundu.getHeight()) / 2);
		nyekundu.setScale(.7f);

		// vowel 'a'
		nyeusi = new ButtonSprite(0, 0, nyeusiTR,
				activity.getVertexBufferObjectManager());
		nyeusi.setPosition((this.mCamera.getWidth() - nyeusi.getWidth()) / 2,
				(this.mCamera.getHeight() - nyeusi.getHeight()) / 2);
		nyeusi.setScale(.7f);

		// vowel 'a'
		nyeupe = new ButtonSprite(0, 0, nyeupeTR,
				activity.getVertexBufferObjectManager());
		nyeupe.setPosition((this.mCamera.getWidth() - nyeupe.getWidth()) / 2,
				(this.mCamera.getHeight() - nyeupe.getHeight()) / 2);
		nyeupe.setScale(.7f);

		// vowel 'a'
		manjano = new ButtonSprite(0, 0, manjanoTR,
				activity.getVertexBufferObjectManager());
		manjano.setPosition((this.mCamera.getWidth() - manjano.getWidth()) / 2,
				(this.mCamera.getHeight() - manjano.getHeight()) / 2);
		manjano.setScale(.7f);

		array[0] = kijani;
		array[1] = manjano;
		array[2] = nyeupe;
		array[3] = nyeusi;
		array[4] = samawati;
		array[5] = nyekundu;

		return array;

	}

	public Sound[] getSounds() {
		Sound[] array = new Sound[6];
		array[0] = soundKijani;
		array[1] = soundManjano;
		array[2] =  soundNyeupe;
		array[3] = soundNyeusi;
		array[4] = soundSamawati;
		array[5] = soundNyekundu;
		

		return array;
	}

	public ButtonSprite nextButton() {
		next = new ButtonSprite(0, 0, nextTR, nextTR,
				activity.getVertexBufferObjectManager());
		 next.setScale(1.2f);
		next.setPosition(mCamera.getWidth() - next.getWidth()-10,
				mCamera.getHeight() / 2 - next.getHeight() / 2);

		return next;
	}

	public ButtonSprite prevButton() {
		prev = new ButtonSprite(0, 0, prevTR,
				activity.getVertexBufferObjectManager());
		 prev.setScale(1.2f);
		prev.setPosition(10, mCamera.getHeight() / 2 - prev.getHeight() / 2);
		prev.setRotation(180f);

		return prev;
	}

	public SpriteBackground background() {
		final float centerX = (mCamera.getWidth() - bgTR.getWidth()) / 2;
		final float centerY = (mCamera.getHeight() - bgTR.getHeight()) / 2;
		SpriteBackground bg = new SpriteBackground(new Sprite(centerX, centerY,
				bgTR, engine.getVertexBufferObjectManager()));

		return bg;
	}

	public Sound getButtonSound() {
		return buttonSound;
	}
	
	public ButtonSprite backButton() {
		ButtonSprite btn = new ButtonSprite(0, 0, backTR,
				activity.getVertexBufferObjectManager(),
				new ButtonSprite.OnClickListener() {

					@Override
					public void onClick(ButtonSprite pButtonSprite,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						pButtonSprite.registerEntityModifier(new ScaleModifier(
								0.3f, 0.5f, 0.7f));

						getButtonSound().play();
						
						gotoMainMenu();
						

					}
				});
		btn.setScale(0.7f);
		return btn;
	}

	private void gotoMainMenu() {
		SceneManager.getInstance().setCurrentScene(AllScenes.MAINMENU);

	}


}
