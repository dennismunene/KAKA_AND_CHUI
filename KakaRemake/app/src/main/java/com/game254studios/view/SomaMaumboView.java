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

public class SomaMaumboView extends MyView {

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;

	// TEXTURES
	private BitmapTextureAtlas bgTA, nextTA, prevTA, duaraTA, duaradufuTA,
			mrabaTA, mstatiliTA, nyotaTA, backTA, pembetatuTA;
	private ITextureRegion bgTR, nextTR, prevTR, duaraTR, duaradufuTR, mrabaTR,
			mstatiliTR, nyotaTR, backTR, pembetatuTR;

	private ButtonSprite next, prev, duara, duaradufu, mraba, mstatili, nyota,
			pembetatu;

	// sounds

	private Sound soundDuara, soundDuaradufu, soundMraba, soundMstatili,
			soundNyota, soundPembetatu;

	private Sound buttonSound;

	public SomaMaumboView(BaseGameActivity act, Engine eng, Camera cam) {

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
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/maumbo/");
		duaraTA = new BitmapTextureAtlas(activity.getTextureManager(), 520,
				520, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		duaraTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				duaraTA, activity, "duara.png", 0, 0);

		// vowel 'e'
		duaradufuTA = new BitmapTextureAtlas(activity.getTextureManager(), 520,
				520, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		duaradufuTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				duaradufuTA, activity, "duaradufu.png", 0, 0);

		// vowel 'i'
		mrabaTA = new BitmapTextureAtlas(activity.getTextureManager(), 520,
				520, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mrabaTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mrabaTA, activity, "mraba.png", 0, 0);

		// vowel 'o'
		mstatiliTA = new BitmapTextureAtlas(activity.getTextureManager(), 520,
				520, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mstatiliTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mstatiliTA, activity, "mstatili.png", 0, 0);

		// vowel 'u'
		nyotaTA = new BitmapTextureAtlas(activity.getTextureManager(), 520,
				520, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		nyotaTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				nyotaTA, activity, "nyota.png", 0, 0);

		// vowel 'u'
		pembetatuTA = new BitmapTextureAtlas(activity.getTextureManager(), 520,
				520, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		pembetatuTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				pembetatuTA, activity, "pembetatu.png", 0, 0);

		loadSounds();
		array[0] = bgTA;
		array[1] = nextTA;
		array[2] = prevTA;
		array[3] = duaraTA;
		array[4] = duaradufuTA;
		array[5] = mrabaTA;
		array[6] = mstatiliTA;
		array[7] = nyotaTA;
		array[8] = pembetatuTA;
		array[9] =backTA;

		return array;
	}

	private void loadSounds() {

		SoundFactory.setAssetBasePath("mfx/Maumbo/Maumbo Soma/");
		try {
			soundDuara = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Duara.aac");
			soundDuaradufu = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Duara Dufu.aac");
			soundMraba = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Mraba.aac");
			soundMstatili = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Mstatili.aac");
			soundNyota = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Nyota.aac");
			soundPembetatu = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Pembe Tatu.aac");

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
		duara = new ButtonSprite(0, 0, duaraTR,
				activity.getVertexBufferObjectManager());
		duara.setPosition((this.mCamera.getWidth() - duara.getWidth()) / 2,
				(this.mCamera.getHeight() - duara.getHeight()) / 2);
		duara.setScale(.7f);

		// vowel 'e'
		duaradufu = new ButtonSprite(0, 0, duaradufuTR,
				activity.getVertexBufferObjectManager());
		duaradufu.setPosition(
				(this.mCamera.getWidth() - duaradufu.getWidth()) / 2,
				(this.mCamera.getHeight() - duaradufu.getHeight()) / 2);
		duaradufu.setScale(.7f);

		// vowel 'a'
		mstatili = new ButtonSprite(0, 0, mstatiliTR,
				activity.getVertexBufferObjectManager());
		mstatili.setPosition(
				(this.mCamera.getWidth() - mstatili.getWidth()) / 2,
				(this.mCamera.getHeight() - mstatili.getHeight()) / 2);
		mstatili.setScale(.7f);

		// vowel 'a'
		nyota = new ButtonSprite(0, 0, nyotaTR,
				activity.getVertexBufferObjectManager());
		nyota.setPosition((this.mCamera.getWidth() - nyota.getWidth()) / 2,
				(this.mCamera.getHeight() - nyota.getHeight()) / 2);
		nyota.setScale(.7f);

		// vowel 'a'
		pembetatu = new ButtonSprite(0, 0, pembetatuTR,
				activity.getVertexBufferObjectManager());
		pembetatu.setPosition(
				(this.mCamera.getWidth() - pembetatu.getWidth()) / 2,
				(this.mCamera.getHeight() - pembetatu.getHeight()) / 2);
		pembetatu.setScale(.7f);

		// vowel 'a'
		mraba = new ButtonSprite(0, 0, mrabaTR,
				activity.getVertexBufferObjectManager());
		mraba.setPosition((this.mCamera.getWidth() - mraba.getWidth()) / 2,
				(this.mCamera.getHeight() - mraba.getHeight()) / 2);
		mraba.setScale(.7f);

		array[0] = duara;
		array[1] = duaradufu;
		array[2] = mstatili;
		array[3] = nyota;
		array[4] = pembetatu;
		array[5] = mraba;

		return array;

	}

	public Sound[] getSounds() {
		Sound[] array = new Sound[6];
		array[0] = soundDuara;
		array[1] = soundDuaradufu;
		array[2] = soundMstatili;
		array[3] = soundNyota;
		array[4] = soundPembetatu;
		array[5] = soundMraba;

		return array;
	}

	public ButtonSprite nextButton() {
		next = new ButtonSprite(0, 0, nextTR, nextTR,
				activity.getVertexBufferObjectManager());
		 next.setScale(1.2f);
		next.setPosition(mCamera.getWidth() - next.getWidth() - 10,
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
		SceneManager.getInstance().setCurrentScene(AllScenes.MAINMENU);

	}

}
