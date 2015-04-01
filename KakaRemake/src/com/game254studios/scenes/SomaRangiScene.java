package com.game254studios.scenes;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.game254studios.MyTextureManager;
import com.game254studios.SceneManager;
import com.game254studios.VideoActivity;
import com.game254studios.SceneManager.AllScenes;
import com.game254studios.models.MainMenuModel;
import com.game254studios.models.SomaRangiModel;
import com.game254studios.models.SomaTarakimuModel;
import com.game254studios.models.SomaVokaliModel;

public class SomaRangiScene extends ParentScene implements OnClickListener {

	private SomaRangiModel model;

	private BaseGameActivity activity;
	private MyTextureManager textureManager;
	private Engine engine;
	private Camera mCamera;

	private ButtonSprite prevBtn, nextBtn, back;

	private int cursor = 0;
	private ButtonSprite[] somaItems;
	private Sound[] somaSounds;

	public SomaRangiScene(BaseGameActivity act, Engine eng, Camera cam) {
		this.activity = act;
		this.engine = eng;
		this.mCamera = cam;

		model = new SomaRangiModel(act, eng, cam);

		textureManager = new MyTextureManager(model.getResources());
	}

	@Override
	public void loadResources() {
		textureManager.loadTextures();

	}

	@Override
	public Scene createScene() {
		setBackground(model.getBackground());
		somaItems = model.getSomaItems();
		somaSounds = model.getSomaSounds();
		nextBtn = model.getNextButton();
		prevBtn = model.getPrevButton();
		back = model.getBackButton();

		nextBtn.setOnClickListener(this);
		prevBtn.setOnClickListener(this);

		attachChild(nextBtn);
		attachChild(back);
		registerTouchArea(nextBtn);
		registerTouchArea(prevBtn);
		registerTouchArea(back);

		setTouchAreaBindingOnActionDownEnabled(true);

		attachChild(prevBtn);
		attachChild(somaItems[0]);

		// welcome sound
		// if(this.isVisible())
		// model.getTusomeSound().play();

		return this;
	}

	public Sound getSceneInitSound() {
		return somaSounds[0];
	}

	@Override
	public void unloadResources() {
		textureManager.unLoadTextures();
	}

	@Override
	public void onClick(final ButtonSprite pButtonSprite,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {

		pButtonSprite
				.registerEntityModifier(new ScaleModifier(0.3f, 1.0f, 1.2f));
		model.getButtonSound().play();

		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (pButtonSprite == nextBtn) {
					next();

				} else if (pButtonSprite == prevBtn) {
					prev();
				}
			}
		});

	}

	private void prev() {

		activity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				if (cursor > 0) {
					detachChild(somaItems[cursor]);
					if (cursor != 0) {
						attachChild(somaItems[--cursor]);
						somaSounds[cursor].play();
					}

				}

			}
		});
	}

	private void next() {

		activity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				if (cursor < somaItems.length - 1) {

					detachChild(somaItems[cursor]);
					attachChild(somaItems[++cursor]);
					somaSounds[cursor].play();

				} else {

					SceneManager.getInstance().setCurrentScene(
							AllScenes.LOADING);
					// move to zoezi tarakimu
					engine.registerUpdateHandler(new TimerHandler(3.0f,
							new ITimerCallback() {

								@Override
								public void onTimePassed(
										TimerHandler pTimerHandler) {
									SceneManager.getInstance()
											.loadZoeziRangiResources();
									SceneManager.getInstance().setCurrentScene(
											AllScenes.ZOEZIRANGI);
								}
							}));

				}
			}
		});

	}
}
