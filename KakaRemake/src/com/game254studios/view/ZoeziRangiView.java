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
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import com.game254studios.SceneManager;
import com.game254studios.SceneManager.AllScenes;

import android.widget.Toast;

public class ZoeziRangiView extends MyView {

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;

	// TEXTURES
	private BitmapTextureAtlas bgTA, nextTA, prevTA, kijaniTA, manjanoTA,
			nyekunduTA, nyeupeTA, nyeusiTA, backTA, samawatiTA, happyTA, sadTA,
			greenTickTA, redXTA;
	private ITextureRegion bgTR, nextTR, prevTR, kijaniTR, manjanoTR,
			nyekunduTR, nyeupeTR, nyeusiTR, backTR, samawatiTR, greenTickTR,
			redXTR;

	private TiledTextureRegion happyTR, sadTR;
	private Sound applause, sadSound,clapping;

	public ZoeziRangiView(BaseGameActivity act, Engine eng, Camera cam) {

		this.activity = act;
		this.engine = eng;
		this.mCamera = cam;

	}

	@Override
	public ITexture[] getTextureAtlases() {

		ITexture[] array = new ITexture[12];

		SoundFactory.setAssetBasePath("mfx/");
		try {
			applause = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "correct_answer.mp3");
			sadSound = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity,
					"sadtrumpet_funny.mp3");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// background
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		bgTA = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		bgTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bgTA,
				activity, "background.png", 0, 0);

		// happy anim
		happyTA = new BitmapTextureAtlas(activity.getTextureManager(), 691,
				922, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		happyTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				happyTA, activity, "happyanim.png", 0, 0, 3, 4);
		// happyTA.load();

		// sad anim
		sadTA = new BitmapTextureAtlas(activity.getTextureManager(), 691, 864,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		sadTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				sadTA, activity, "sadanim.png", 0, 0, 4, 5);
		// sadTA.load();
		// back
		backTA = new BitmapTextureAtlas(activity.getTextureManager(), 124, 91,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		backTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backTA,
				activity, "backbutton.png", 0, 0);

		// TODO
		// tick
		greenTickTA = new BitmapTextureAtlas(activity.getTextureManager(), 480,
				480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		greenTickTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				greenTickTA, activity, "green_tick.png", 0, 0);

		// x
		redXTA = new BitmapTextureAtlas(activity.getTextureManager(), 478, 480,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		redXTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(redXTA,
				activity, "red_x.png", 0, 0);

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

		array[0] = bgTA;
		array[1] = happyTA;
		array[2] = sadTA;
		array[3] = kijaniTA;
		array[4] = manjanoTA;
		array[5] = nyeupeTA;
		array[6] = nyeusiTA;
		array[7] = samawatiTA;
		array[8] = nyekunduTA;

		array[10] = greenTickTA;
		array[9] = redXTA;
		array[11] = backTA;

		return array;

	}

	public Sound[] getZoeziSounds() {

		Sound[] zoeziSounds = new Sound[6];
		SoundFactory.setAssetBasePath("mfx/Rangi/Rangi Zoezi/");
		try {
			Sound soundKijani = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Kijani.aac");
			Sound soundManjano = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Manjano.aac");
			Sound soundNyekundu = SoundFactory
					.createSoundFromAsset(activity.getSoundManager(), activity,
							"Chagua Nyekundu.aac");
			Sound soundNyeupe = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Nyeupe.aac");
			Sound soundNyeusi = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Nyeusi.aac");

			Sound soundSamawati = SoundFactory
					.createSoundFromAsset(activity.getSoundManager(), activity,
							"Chagua Samawati.aac");
			SoundFactory.setAssetBasePath("mfx/");
			clapping = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity,
					"clapping.mp3");

			zoeziSounds[0] = soundKijani;
			zoeziSounds[1] = soundManjano;
			zoeziSounds[2] = soundNyeupe;
			zoeziSounds[3] = soundNyeusi;
			zoeziSounds[4] = soundSamawati;
			zoeziSounds[5] = soundNyekundu;

			// zoeziSounds[6] = applause;
			// zoeziSounds[7] = sadSound;

			return zoeziSounds;

		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

	}

	public Sound getApplauseSound() {

		return applause;

	}

	public Sound getSadSound() {
		return sadSound;
	}

	public ButtonSprite[] getSomaItems() {

		ButtonSprite[] array = new ButtonSprite[6];

		// vowel 'a'
		ButtonSprite kijani = new ButtonSprite(0, 0, kijaniTR,
				activity.getVertexBufferObjectManager());
		kijani.setPosition((this.mCamera.getWidth() - kijani.getWidth()) / 2,
				(this.mCamera.getHeight() - kijani.getHeight()) / 2);
		kijani.setScale(.7f);

		// vowel 'e'
		ButtonSprite samawati = new ButtonSprite(0, 0, samawatiTR,
				activity.getVertexBufferObjectManager());
		samawati.setPosition(
				(this.mCamera.getWidth() - samawati.getWidth()) / 2,
				(this.mCamera.getHeight() - samawati.getHeight()) / 2);
		samawati.setScale(.7f);

		// vowel 'a'
		ButtonSprite nyekundu = new ButtonSprite(0, 0, nyekunduTR,
				activity.getVertexBufferObjectManager());
		nyekundu.setPosition(
				(this.mCamera.getWidth() - nyekundu.getWidth()) / 2,
				(this.mCamera.getHeight() - nyekundu.getHeight()) / 2);
		nyekundu.setScale(.7f);

		// vowel 'a'
		ButtonSprite nyeusi = new ButtonSprite(0, 0, nyeusiTR,
				activity.getVertexBufferObjectManager());
		nyeusi.setPosition((this.mCamera.getWidth() - nyeusi.getWidth()) / 2,
				(this.mCamera.getHeight() - nyeusi.getHeight()) / 2);
		nyeusi.setScale(.7f);

		// vowel 'a'
		ButtonSprite nyeupe = new ButtonSprite(0, 0, nyeupeTR,
				activity.getVertexBufferObjectManager());
		nyeupe.setPosition((this.mCamera.getWidth() - nyeupe.getWidth()) / 2,
				(this.mCamera.getHeight() - nyeupe.getHeight()) / 2);
		nyeupe.setScale(.7f);

		// vowel 'a'
		ButtonSprite manjano = new ButtonSprite(0, 0, manjanoTR,
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

	public AnimatedSprite getAnim(boolean isHappy) {
		AnimatedSprite mSprite;


		if (isHappy){
			mSprite = new AnimatedSprite(0, 0, happyTR,
					activity.getVertexBufferObjectManager());
		mSprite.setPosition((mCamera.getWidth() - mSprite.getWidth()) / 2,
				(mCamera.getHeight() - 20) / 2);

		}else{
			mSprite = new AnimatedSprite(0, 0, sadTR,
					activity.getVertexBufferObjectManager());
			mSprite.setPosition((mCamera.getWidth() - mSprite.getWidth()) / 2,
					(mCamera.getHeight() - mSprite.getHeight()-40));

		}
		mSprite.setScale(1.5f);
		
		return mSprite;

	}

	public Sprite getGreenTick() {
		Sprite tick = new Sprite(0, 0, greenTickTR,
				activity.getVertexBufferObjectManager());

		tick.setScale(.4f);

		return tick;
	}

	public Sprite getRedX() {
		Sprite x = new Sprite(0, 0, redXTR,
				activity.getVertexBufferObjectManager());
		x.setScale(.4f);
		return x;
	}

	public SpriteBackground background() {
		final float centerX = (mCamera.getWidth() - bgTR.getWidth()) / 2;
		final float centerY = (mCamera.getHeight() - bgTR.getHeight()) / 2;
		SpriteBackground bg = new SpriteBackground(new Sprite(centerX, centerY,
				bgTR, engine.getVertexBufferObjectManager()));

		return bg;
	}

	public ButtonSprite backButton() {
		ButtonSprite btn = new ButtonSprite(-10, -10, backTR,
				activity.getVertexBufferObjectManager(),
				new ButtonSprite.OnClickListener() {

					@Override
					public void onClick(ButtonSprite pButtonSprite,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						pButtonSprite.registerEntityModifier(new ScaleModifier(
								0.3f, 0.5f, 0.7f));

						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								gotoMainMenu();
							}
						}).start();

					}
				});
		btn.setScale(0.7f);
		return btn;
	}

	private void gotoMainMenu() {
		SceneManager.getInstance().unLoadZoeziRangiResources();
		SceneManager.getInstance().setCurrentScene(AllScenes.SOMARANGI);
	}
	
	public Sound getClappingSound(){return clapping;}

}
