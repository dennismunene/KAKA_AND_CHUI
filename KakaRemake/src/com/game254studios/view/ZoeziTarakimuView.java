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

public class ZoeziTarakimuView extends MyView {

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;

	// TEXTURES
	private BitmapTextureAtlas bgTA, t1TA, t2TA, t3TA, t4TA, t5TA, t6TA, t7TA,
			t8TA, t9TA, t10TA, happyTA, sadTA, greenTickTA, redXTA, backTA;
	private ITextureRegion bgTR, t1tR, t2tR, t3TR, t4TR, t5TR, t6TR, t7TR,
			t8TR, t9TR, t10TR, greenTickTR, redXTR, backTR;

	private TiledTextureRegion happyTR, sadTR;
	private Sound applause, sadSound,clapping;
	// sprites
	private ButtonSprite t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;

	public ZoeziTarakimuView(BaseGameActivity act, Engine eng, Camera cam) {

		this.activity = act;
		this.engine = eng;
		this.mCamera = cam;

	}

	@Override
	public ITexture[] getTextureAtlases() {

		ITexture[] array = new ITexture[16];

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

		array[1] = t1TA;
		array[2] = t2TA;
		array[3] = t3TA;
		array[4] = t4TA;
		array[5] = t5TA;
		array[6] = t6TA;
		array[7] = t7TA;
		array[8] = t8TA;
		array[9] = t9TA;
		array[10] = t10TA;
		array[11] = happyTA;
		array[12] = sadTA;

		array[13] = greenTickTA;
		array[14] = redXTA;
		array[15] = backTA;

		return array;

	}

	public Sound[] getZoeziSounds() {
		SoundFactory.setAssetBasePath("mfx/Tarakimu/Tarakimu  Zoezi/");
		Sound[] zoeziSounds = new Sound[10];

		try {
			Sound soundMoja = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Moja .aac");
			Sound soundMbili = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Mbili.aac");
			Sound soundTatu = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Tatu.aac");
			Sound soundNne = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua  Nne.aac");
			Sound soundTano = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Tano.aac");
			Sound soundSita = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua  Sita.aac");

			Sound soundSaba = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Saba.aac");
			Sound soundNane = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Nane.aac");
			Sound soundTisa = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Tisa.aac");
			Sound soundKumi = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Kumi.aac");

			SoundFactory.setAssetBasePath("mfx/");
			applause = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "correct_answer.mp3");
			sadSound = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity,
					"sadtrumpet_funny.mp3");
			
			clapping = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity,
					"clapping.mp3");

			zoeziSounds[0] = soundMoja;
			zoeziSounds[1] = soundMbili;
			zoeziSounds[2] = soundTatu;
			zoeziSounds[3] = soundNne;
			zoeziSounds[4] = soundTano;
			zoeziSounds[5] = soundSita;
			zoeziSounds[6] = soundSaba;
			zoeziSounds[7] = soundNane;
			zoeziSounds[8] = soundTisa;
			zoeziSounds[9] = soundKumi;

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

	public AnimatedSprite getAnim(boolean isHappy) {
		AnimatedSprite mSprite;

		if (isHappy) {
			mSprite = new AnimatedSprite(0, 0, happyTR,
					activity.getVertexBufferObjectManager());
			mSprite.setPosition(
					(mCamera.getWidth() - mSprite.getWidth()) / 2 + 50,
					(mCamera.getHeight() - 20) / 2);

		} else {
			mSprite = new AnimatedSprite(0, 0, sadTR,
					activity.getVertexBufferObjectManager());
			mSprite.setPosition(
					(mCamera.getWidth() - mSprite.getWidth()) / 2 + 50,
					(mCamera.getHeight() - mSprite.getHeight() - 40));

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
		SceneManager.getInstance().unLoadZoeziTarakimuResources();
		SceneManager.getInstance().setCurrentScene(AllScenes.SOMATARAKIMU);

	}
	
	public Sound getClappingSound(){
		return clapping;
	}

}
