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

public class SomaVokaliView extends MyView {

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;

	// TEXTURES
	private BitmapTextureAtlas bgTA, nextTA, prevTA, aTA, eTA, iTA, oTA, uTA,
			backTA;
	private ITextureRegion bgTR, nextTR, prevTR, aTR, eTR, iTR, oTR, uTR,
			backTR;

	private ButtonSprite next, prev, btnA, btnE, btnI, btnO, btnU;

	// sounds

	private Sound soundA, soundE, soundI, soundO, soundU;
	private Sound buttonSound;

	public SomaVokaliView(BaseGameActivity act, Engine eng, Camera cam) {

		this.activity = act;
		this.engine = eng;
		this.mCamera = cam;
	}

	@Override
	public ITexture[] getTextureAtlases() {
		ITexture[] array = new ITexture[9];

		loadSounds();

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
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("gfx/somavokali/");
		aTA = new BitmapTextureAtlas(activity.getTextureManager(), 341, 361,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		aTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aTA,
				activity, "a.png", 0, 0);

		// vowel 'e'
		eTA = new BitmapTextureAtlas(activity.getTextureManager(), 341, 361,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		eTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(eTA,
				activity, "e.png", 0, 0);

		// vowel 'i'
		iTA = new BitmapTextureAtlas(activity.getTextureManager(), 341, 361,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		iTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(iTA,
				activity, "i.png", 0, 0);

		// vowel 'o'
		oTA = new BitmapTextureAtlas(activity.getTextureManager(), 341, 361,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		oTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(oTA,
				activity, "o.png", 0, 0);

		// vowel 'u'
		uTA = new BitmapTextureAtlas(activity.getTextureManager(), 341, 361,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		uTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(uTA,
				activity, "u.png", 0, 0);

		loadSounds();
		array[0] = bgTA;
		array[1] = nextTA;
		array[2] = prevTA;
		array[3] = aTA;
		array[4] = eTA;
		array[5] = iTA;
		array[6] = uTA;
		array[7] = oTA;
		array[8] = backTA;

		return array;
	}

	private void loadSounds() {

		try {
			SoundFactory.setAssetBasePath("mfx/");
			buttonSound = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "two_tone_nav.mp3");

			SoundFactory.setAssetBasePath("mfx/Vokali/Vokali Soma/");

			soundA = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "a.aac");
			soundE = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "e.aac");
			soundI = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "i.aac");
			soundO = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "o.aac");
			soundU = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "u.aac");

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public ButtonSprite[] getSomaItems() {

		ButtonSprite[] array = new ButtonSprite[5];

		// vowel 'a'
		btnA = new ButtonSprite(0, 0, aTR,
				activity.getVertexBufferObjectManager());
		btnA.setPosition((this.mCamera.getWidth() - btnA.getWidth()) / 2,
				(this.mCamera.getHeight() - btnA.getHeight()) / 2);
		btnA.setScale(.7f);

		// vowel 'e'
		btnE = new ButtonSprite(0, 0, eTR,
				activity.getVertexBufferObjectManager());
		btnE.setPosition((this.mCamera.getWidth() - btnE.getWidth()) / 2,
				(this.mCamera.getHeight() - btnE.getHeight()) / 2);
		btnE.setScale(.7f);

		// vowel 'a'
		btnI = new ButtonSprite(0, 0, iTR,
				activity.getVertexBufferObjectManager());
		btnI.setPosition((this.mCamera.getWidth() - btnI.getWidth()) / 2,
				(this.mCamera.getHeight() - btnI.getHeight()) / 2);
		btnI.setScale(.7f);

		// vowel 'a'
		btnO = new ButtonSprite(0, 0, oTR,
				activity.getVertexBufferObjectManager());
		btnO.setPosition((this.mCamera.getWidth() - btnO.getWidth()) / 2,
				(this.mCamera.getHeight() - btnO.getHeight()) / 2);
		btnO.setScale(.7f);

		// vowel 'a'
		btnU = new ButtonSprite(0, 0, uTR,
				activity.getVertexBufferObjectManager());
		btnU.setPosition((this.mCamera.getWidth() - btnU.getWidth()) / 2,
				(this.mCamera.getHeight() - btnU.getHeight()) / 2);
		btnU.setScale(.7f);

		array[0] = btnA;
		array[1] = btnE;
		array[2] = btnI;
		array[3] = btnO;
		array[4] = btnU;

		return array;

	}

	public Sound[] getSounds() {
		Sound[] array = new Sound[5];
		array[0] = soundA;
		array[1] = soundE;
		array[2] = soundI;
		array[3] = soundO;
		array[4] = soundU;
	

		return array;
	}

	public ButtonSprite nextButton() {
		next = new ButtonSprite(0, 0, nextTR, nextTR,
				activity.getVertexBufferObjectManager());

		next.setPosition(mCamera.getWidth() - next.getWidth()-10,
				mCamera.getHeight() / 2 - next.getHeight() / 2);
		next.setScale(1.2f);
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
		

	/*	engine.registerUpdateHandler(new TimerHandler(0.01f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						engine.unregisterUpdateHandler(pTimerHandler);

						//SceneManager.getInstance().unLoadSomaVokaliResources();
					
						SceneManager.getInstance().setCurrentScene(AllScenes.MAINMENU);

					}
				}));
*/
	}

}
