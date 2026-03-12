package com.game254studios.view;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.AlphaModifier;
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
import com.game254studios.models.SpriteSoundPair;

public class SomaTarakimuView extends MyView {

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;

	// TEXTURES
	private BitmapTextureAtlas bgTA, nextTA, backTA, prevTA, t1TA, t2TA, t3TA,
			t4TA, t5TA, t6TA, t7TA, t8TA, t9TA, t10TA, pauseBgTA;
	private ITextureRegion bgTR, prevTR, t1tR, t2tR, t3TR, t4TR, t5TR, t6TR,
			t7TR, t8TR, t9TR, t10TR, nextTR, backTR, pauseBgTR;

	private TiledTextureRegion happyTR, sadTR;
	// sprites
	private ButtonSprite t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
	private ButtonSprite next, prev;

	private Sound soundMoja, soundMbili, soundTatu, soundNne, soundTano,
			soundSita, soundSaba, soundNane, soundTisa, soundKumi;

	// sounds
	private String[] sounds = { "a.aac", "e.aac", "i.aac", "u.aac" };
	private Sound soundA, soundE, soundI, soundO, soundU;
	private Sound tarakimuSound, buttonSound;
	PauseMenu pauseMenu;

	public SomaTarakimuView(BaseGameActivity act, Engine eng, Camera cam) {

		this.activity = act;
		this.engine = eng;
		this.mCamera = cam;

	}

	@Override
	public ITexture[] getTextureAtlases() {
		ITexture[] array = new ITexture[15];

		loadSounds();
		loadPauseMenu();

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

		// number 1
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("gfx/somatarakimu/");
		t1TA = new BitmapTextureAtlas(activity.getTextureManager(), 520, 520,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		t1tR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(t1TA,
				activity, "t1.png", 0, 0);

		// number 2
		t2TA = new BitmapTextureAtlas(activity.getTextureManager(), 520, 520,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		t2tR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(t2TA,
				activity, "t2.png", 0, 0);

		// num 3
		t3TA = new BitmapTextureAtlas(activity.getTextureManager(), 520, 520,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		t3TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(t3TA,
				activity, "t3.png", 0, 0);

		// num 4
		t4TA = new BitmapTextureAtlas(activity.getTextureManager(), 520, 520,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		t4TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(t4TA,
				activity, "t4.png", 0, 0);

		// num 5
		t5TA = new BitmapTextureAtlas(activity.getTextureManager(), 520, 520,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		t5TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(t5TA,
				activity, "t5.png", 0, 0);

		// num 6
		t6TA = new BitmapTextureAtlas(activity.getTextureManager(), 520, 520,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		t6TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(t6TA,
				activity, "t6.png", 0, 0);

		// num 7
		t7TA = new BitmapTextureAtlas(activity.getTextureManager(), 520, 520,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		t7TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(t7TA,
				activity, "t7.png", 0, 0);

		// num 8
		t8TA = new BitmapTextureAtlas(activity.getTextureManager(), 520, 520,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		t8TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(t8TA,
				activity, "t8.png", 0, 0);

		// num 9
		t9TA = new BitmapTextureAtlas(activity.getTextureManager(), 520, 520,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		t9TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(t9TA,
				activity, "t9.png", 0, 0);

		// num 10
		t10TA = new BitmapTextureAtlas(activity.getTextureManager(), 520, 520,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		t10TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(t10TA,
				activity, "t10.png", 0, 0);

		array[0] = bgTA;
		array[1] = nextTA;
		array[2] = prevTA;
		array[3] = t1TA;
		array[4] = t2TA;
		array[5] = t3TA;
		array[6] = t4TA;
		array[7] = t5TA;
		array[8] = t6TA;
		array[9] = t7TA;
		array[10] = t8TA;
		array[11] = t9TA;
		array[12] = t10TA;
		array[13] = pauseBgTA;
		array[14] = backTA;

		// array[13] = happyTA;
		// array[14] = sadTA;
		// TODO
		// array[15] = greenTickTA;
		// array[16] = redXTA;

		return array;
	}

	private void loadPauseMenu() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		pauseBgTA = new BitmapTextureAtlas(activity.getTextureManager(), 488,
				686, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		pauseBgTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				pauseBgTA, activity, "pause_bg.png", 0, 0);

	}

	public Sprite getPauseMenu() {
		Sprite pauseMenu = new Sprite(150, -70, pauseBgTR,
				activity.getVertexBufferObjectManager());
		pauseMenu.setScale(0.7f);
		return pauseMenu;

	}

	private void loadSounds() {

		try {
			SoundFactory.setAssetBasePath("mfx/");
			buttonSound = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "two_tone_nav.mp3");

			SoundFactory.setAssetBasePath("mfx/Home/");
			tarakimuSound = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Tusome takrimu.aac");

			SoundFactory.setAssetBasePath("mfx/Tarakimu/Tarakimu Soma/");
			soundMoja = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Moja.aac");
			soundMbili = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Mbili.aac");
			soundTatu = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Tatu.aac");
			soundNne = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Nne.aac");
			soundTano = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Tano.aac");
			soundSita = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Sita.aac");

			soundSaba = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Saba.aac");
			soundNane = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Nane.aac");
			soundTisa = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Tisa.aac");
			soundKumi = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Kumi.aac");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public ButtonSprite[] getSomaItems() {

		ButtonSprite[] array = new ButtonSprite[10];

		// vowel 'a'
		t1 = new ButtonSprite(0, 0, t1tR,
				activity.getVertexBufferObjectManager());
		t1.setPosition((this.mCamera.getWidth() - t1.getWidth()) / 2,
				(this.mCamera.getHeight() - t1.getHeight()) / 2);
		t1.setScale(.7f);

		// vowel 'e'
		t2 = new ButtonSprite(0, 0, t2tR,
				activity.getVertexBufferObjectManager());

		t2.setPosition((mCamera.getWidth() - t2.getWidth()) / 2,
				(mCamera.getHeight() - t2.getHeight()) / 2);
		t2.setScale(0.7f);

		// vowel 'i'

		t3 = new ButtonSprite(0, 0, t3TR,
				activity.getVertexBufferObjectManager());

		t3.setPosition((mCamera.getWidth() - t3.getWidth()) / 2,
				(mCamera.getHeight() - t3.getHeight()) / 2);
		t3.setScale(0.7f);

		// vowel 'o'

		t4 = new ButtonSprite(0, 0, t4TR,
				activity.getVertexBufferObjectManager());

		t4.setPosition((mCamera.getWidth() - t4.getWidth()) / 2,
				(mCamera.getHeight() - t4.getHeight()) / 2);
		t4.setScale(.7f);

		// vowel 'u'

		t5 = new ButtonSprite(0, 0, t5TR,
				activity.getVertexBufferObjectManager());

		t5.setPosition((mCamera.getWidth() - t5.getWidth()) / 2,
				(mCamera.getHeight() - t5.getHeight()) / 2);
		t5.setScale(.7f);

		t6 = new ButtonSprite(0, 0, t6TR,
				activity.getVertexBufferObjectManager());

		t6.setPosition((mCamera.getWidth() - t6.getWidth()) / 2,
				(mCamera.getHeight() - t6.getHeight()) / 2);
		t6.setScale(.7f);

		t7 = new ButtonSprite(0, 0, t7TR,
				activity.getVertexBufferObjectManager());

		t7.setPosition((mCamera.getWidth() - t7.getWidth()) / 2,
				(mCamera.getHeight() - t7.getHeight()) / 2);
		t7.setScale(0.7f);

		t8 = new ButtonSprite(0, 0, t8TR,
				activity.getVertexBufferObjectManager());

		t8.setPosition((mCamera.getWidth() - t8.getWidth()) / 2,
				(mCamera.getHeight() - t8.getHeight()) / 2);
		t8.setScale(.7f);

		t9 = new ButtonSprite(0, 0, t9TR,
				activity.getVertexBufferObjectManager());

		t9.setPosition((mCamera.getWidth() - t9.getWidth()) / 2,
				(mCamera.getHeight() - t9.getHeight()) / 2);
		t9.setScale(.7f);

		t10 = new ButtonSprite(0, 0, t10TR,
				activity.getVertexBufferObjectManager());

		t10.setPosition((mCamera.getWidth() - t10.getWidth()) / 2,
				(mCamera.getHeight() - t10.getHeight()) / 2);
		t10.setScale(.7f);

		array[0] = t1;
		array[1] = t2;
		array[2] = t3;
		array[3] = t4;
		array[4] = t5;
		array[5] = t6;
		array[6] = t7;
		array[7] = t8;
		array[8] = t9;
		array[9] = t10;

		return array;

	}

	public Sound[] getSounds() {
		Sound[] array = new Sound[10];
		array[0] = soundMoja;
		array[1] = soundMbili;
		array[2] = soundTatu;
		array[3] = soundNne;
		array[4] = soundTano;
		array[5] = soundSita;
		array[6] = soundSaba;
		array[7] = soundNane;
		array[8] = soundTisa;
		array[9] = soundKumi;

		return array;
	}

	public Sound tusomeSound() {
		return tarakimuSound;
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

	private void gotoMainMenu() {
		SceneManager.getInstance().setCurrentScene(AllScenes.MAINMENU);

	}

}
