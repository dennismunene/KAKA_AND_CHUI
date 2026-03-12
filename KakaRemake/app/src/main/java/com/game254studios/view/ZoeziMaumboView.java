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

public class ZoeziMaumboView extends MyView {

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;

	// TEXTURES
	private BitmapTextureAtlas bgTA,  duaraTA, duaradufuTA,
			mrabaTA, mstatiliTA, nyotaTA, backTA, pembetatuTA, happyTA, sadTA,
			greenTickTA, redXTA;
	private ITextureRegion bgTR,  duaraTR, duaradufuTR, mrabaTR,
			mstatiliTR, nyotaTR, backTR, pembetatuTR, greenTickTR, redXTR;

	private TiledTextureRegion happyTR, sadTR;
	private Sound applause, sadSound,clapping;

	public ZoeziMaumboView(BaseGameActivity act, Engine eng, Camera cam) {

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
		mstatiliTA.load();

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

		array[0] = bgTA;
		array[1] = happyTA;
		array[2] = sadTA;
		array[3] = duaradufuTA;
		array[4] = duaraTA;
		array[5] = mrabaTA;
		array[6] = mstatiliTA;
		array[7] = pembetatuTA;
		array[8] = nyotaTA;

		array[10] = greenTickTA;
		array[9] = redXTA;
		array [11] = backTA;

		return array;

	}

	public Sound[] getZoeziSounds() {

		Sound[] zoeziSounds = new Sound[6];
		SoundFactory.setAssetBasePath("mfx/Maumbo/Maumbo Zoezi/");
		try {
			Sound soundDuara = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Duara.aac");
			Sound soundDuaradufu = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Duaradufu.aac");
			Sound soundMraba = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Mraba.aac");
			Sound soundMstatili = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Mstatili.aac");
			Sound soundNyota = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Chagua Nyota.aac");
			Sound soundPembetatu = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Pembe Tatu.aac");
			
			SoundFactory.setAssetBasePath("mfx/");
			clapping = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity,
					"clapping.mp3");

			zoeziSounds[0] = soundDuara;
			zoeziSounds[1] = soundDuaradufu;
			zoeziSounds[2] = soundMraba;
			zoeziSounds[3] = soundMstatili;
		
			zoeziSounds[4] = soundNyota;
			zoeziSounds[5] = soundPembetatu;

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
		ButtonSprite duara = new ButtonSprite(0, 0, duaraTR,
				activity.getVertexBufferObjectManager());
		duara.setPosition((this.mCamera.getWidth() - duara.getWidth()) / 2,
				(this.mCamera.getHeight() - duara.getHeight()) / 2);
		duara.setScale(.7f);

		// vowel 'e'
		ButtonSprite duaradufu = new ButtonSprite(0, 0, duaradufuTR,
				activity.getVertexBufferObjectManager());
		duaradufu.setPosition(
				(this.mCamera.getWidth() - duaradufu.getWidth()) / 2,
				(this.mCamera.getHeight() - duaradufu.getHeight()) / 2);
		duaradufu.setScale(.7f);

		// vowel 'a'
		ButtonSprite mraba = new ButtonSprite(0, 0, mrabaTR,
				activity.getVertexBufferObjectManager());
		mraba.setPosition(
				(this.mCamera.getWidth() - mraba.getWidth()) / 2,
				(this.mCamera.getHeight() - mraba.getHeight()) / 2);
		mraba.setScale(.7f);

		// vowel 'a'
		ButtonSprite mstatili = new ButtonSprite(0, 0, mstatiliTR,
				activity.getVertexBufferObjectManager());
		mstatili.setPosition((this.mCamera.getWidth() - mstatili.getWidth()) / 2,
				(this.mCamera.getHeight() - mstatili.getHeight()) / 2);
		mstatili.setScale(.7f);

		// vowel 'a'
		ButtonSprite nyota = new ButtonSprite(0, 0, nyotaTR,
				activity.getVertexBufferObjectManager());
		nyota.setPosition((this.mCamera.getWidth() - nyota.getWidth()) / 2,
				(this.mCamera.getHeight() - nyota.getHeight()) / 2);
		nyota.setScale(.7f);

		// vowel 'a'
		ButtonSprite pembetatu = new ButtonSprite(0, 0, pembetatuTR,
				activity.getVertexBufferObjectManager());
		pembetatu.setPosition((this.mCamera.getWidth() - pembetatu.getWidth()) / 2,
				(this.mCamera.getHeight() - pembetatu.getHeight()) / 2);
		pembetatu.setScale(.7f);

		array[0] = duara;
		array[1] = duaradufu;
		array[2] = mraba;
		array[3] = mstatili;
		array[4] = nyota;
		array[5] = pembetatu;

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
		SceneManager.getInstance().unLoadZoeziMaumboresources();
		SceneManager.getInstance().setCurrentScene(AllScenes.SOMAMAUMBO);

	}
	
	public Sound getClappingSound(){return clapping;}

}
