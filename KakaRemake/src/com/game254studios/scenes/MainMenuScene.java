package com.game254studios.scenes;

import org.andengine.audio.music.Music;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.ui.activity.BaseGameActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.game254studios.MyTextureManager;
import com.game254studios.R;
import com.game254studios.models.MainMenuModel;

public class MainMenuScene extends ParentScene {

	private MainMenuModel model;
	private BaseGameActivity activity;
	private MyTextureManager textureManager;
	private Engine engine;
	private Camera mCamera;
	private Music bgMusic = null;
	MainMenuModel.Settings settings;

	public MainMenuScene(BaseGameActivity act, Engine eng, Camera cam) {
		this.activity = act;
		this.engine = eng;
		this.mCamera = cam;

		model = new MainMenuModel(this, act, eng, cam);

		textureManager = new MyTextureManager(model.getResources());
	}

	@Override
	public void loadResources() {
		textureManager.loadTextures();

	}

	private Sprite bluebg;
	private ButtonSprite vokaliBtn, maumboBtn, tarakimuBtn, rangiBtn,
			settingsBtn;

	public void setBlueBgVisible() {
		bluebg.setVisible(true);
		registerTouchArea(vokaliBtn);
		registerTouchArea(maumboBtn);
		registerTouchArea(tarakimuBtn);
		registerTouchArea(rangiBtn);
		attachChild(settingsBtn);
		settings.registerEntityModifier(new FadeOutModifier(1.0f));

		detachChild(settings);

	}

	public Sprite getSettingsDialog() {
		return settings;
	}

	@Override
	public Scene createScene() {
		setBackground(model.getSpriteBackground());
		bluebg = model.getBlueMenuBackground();
		attachChild(bluebg);

		playBackgroundMusic();

		AnimatedSprite kaka = model.getKakaAnim();
		attachChild(kaka);
		kaka.animate(90);// animte kaka 30FPS

		// get Buttons
		vokaliBtn = model.getVokaliButton();
		tarakimuBtn = model.getTarakimuButton();
		maumboBtn = model.getMaumboButton();
		rangiBtn = model.getRangiButton();

		// register buttons for clicks
		registerTouchArea(vokaliBtn);
		registerTouchArea(maumboBtn);
		registerTouchArea(tarakimuBtn);
		registerTouchArea(rangiBtn);

		// settings button

		settingsBtn = model.getSettingsButton();
		registerTouchArea(settingsBtn);
		settingsBtn.setOnClickListener(new ButtonSprite.OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				pButtonSprite.registerEntityModifier(new ScaleModifier(0.3f,
						0.4f, 0.6f));
				// unregister buttons for clicks
				// detachChild(bluebg);//remove blue bg temporarily

				bluebg.setVisible(false);
				unregisterTouchArea(vokaliBtn);
				unregisterTouchArea(maumboBtn);
				unregisterTouchArea(tarakimuBtn);
				unregisterTouchArea(rangiBtn);
				detachChild(pButtonSprite);

				settings = model.new Settings();
				attachChild(settings);
				settings.registerEntityModifier(new FadeInModifier(1.0f));
			}
		});

		attachChild(settingsBtn);

		// attach buttons
		attachChild(vokaliBtn);
		attachChild(tarakimuBtn);
		attachChild(rangiBtn);
		attachChild(maumboBtn);

		// attach labels
		attachChild(model.getVokaliLabel());
		attachChild(model.getTarakimuLabel());
		attachChild(model.getMaumboLabel());
		attachChild(model.getRangiLabel());

		return this;
	}

	public void showWelcomeDialog() {
		AlertDialog.Builder bd = new AlertDialog.Builder(activity);
		bd.setTitle("Welcome");
		bd.setMessage(activity.getResources().getString(R.string.welcome));
		bd.setCancelable(false);
		bd.setPositiveButton("Try it out", new Dialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		bd.setNegativeButton("Go premium now", new Dialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showPaymentDialog();
			}
		});
		bd.show();

	}

	protected void showPaymentDialog() {
		AlertDialog.Builder bd = new AlertDialog.Builder(activity);
		bd.setTitle("Choose Payment Method");
		bd.setItems(new String[] { "Mpesa", "Credit Card", "Paypal" },
				new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		bd.setCancelable(false);
		bd.setPositiveButton("Try it out", new Dialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		bd.setNegativeButton("Go premium now", new Dialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showPaymentDialog();
			}
		});
		bd.show();

	}

	private void playBackgroundMusic() {
		bgMusic = model.getBacjkgroundMusic();
		if (activity.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
				.getBoolean("music", true)) {

			bgMusic.play();
			bgMusic.setLooping(true);
		}

	}

	public void pauseBackgroundMusic() {
		if (bgMusic != null)
			bgMusic.play();
	}

	public void resumeBackgroundMusic() {
		if (bgMusic != null)
			bgMusic.resume();
	}
	
	public Music getBackGroundMusic(){ return bgMusic;}

	@Override
	public void unloadResources() {
		textureManager.unLoadTextures();
		if (bgMusic != null) {
			bgMusic.stop();

			bgMusic = null;
		}
	}

}
