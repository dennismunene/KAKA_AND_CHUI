package com.game254studios.view;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
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
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import com.game254studios.R;
import com.game254studios.SceneManager;
import com.game254studios.SceneManager.AllScenes;






import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.NetworkInfo.DetailedState;
import android.net.nsd.NsdManager.RegistrationListener;
import android.view.View;
import android.widget.Toast;

public class MainMenuView extends MyView {

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;

	// Texture Regions and BitmapAtlas
	private BitmapTextureAtlas bgTA, titleTA, kakaTA, vokaliTA, tarakimuTA,
			rangiTA, aboutTA, animTA, maumboTA, settingsTA, settings_iconTA,
			btn_bgMusicTA, menubgTA, btnExitTA, padlockTA;
	private ITextureRegion bgTR, titleTR, kakaTR, vokaliTR, tarakimuTR,
			maumboTR, rangiTR, aboutTR, settingsTR, settings_iconTR,
			btn_bgMusicTR, menubgTR, btnExitTR, padlockTR;

	private Font font, white_font;

	private TiledTextureRegion animTR;

	private Sound vokaliSound, tarakimuSound, maumboSound, rangiSound;

	private Music bgMusic;

	final float sizeX, sizeY;
	
	private  boolean hasPaid;

	public MainMenuView(BaseGameActivity act, Engine eng, Camera cam) {

		this.activity = act;
		this.engine = eng;
		this.mCamera = cam;

		sizeX = mCamera.getWidth() / 5;
		sizeY = (float) (mCamera.getHeight() / 2.8);
		 hasPaid = activity.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getBoolean("hasPaid", false);
	}

	@Override
	public ITexture[] getTextureAtlases() {
		ITexture[] array = new ITexture[12];

		// background
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		bgTA = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		bgTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bgTA,
				activity, "background.png", 0, 0);

		settingsTA = new BitmapTextureAtlas(activity.getTextureManager(), 596,
				680, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		settingsTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				settingsTA, activity, "setting_bg.png", 0, 0);

		settings_iconTA = new BitmapTextureAtlas(activity.getTextureManager(),
				128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		settings_iconTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(settings_iconTA, activity,
						"settings_icon.png", 0, 0);

		btn_bgMusicTA = new BitmapTextureAtlas(activity.getTextureManager(),
				491, 99, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		btn_bgMusicTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				btn_bgMusicTA, activity, "btn_bgmusic.png", 0, 0);
		padlockTA = new BitmapTextureAtlas(activity.getTextureManager(), 142,
				139, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		padlockTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				padlockTA, activity, "padlock.png", 0, 0);

		// menu buttons
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("gfx/mainmenu/");
		menubgTA = new BitmapTextureAtlas(activity.getTextureManager(), 596,
				680, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		menubgTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				menubgTA, activity, "menubg.png", 0, 0);

		btnExitTA = new BitmapTextureAtlas(activity.getTextureManager(), 491,
				99, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		btnExitTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				btnExitTA, activity, "btn_exit.png", 0, 0);

		vokaliTA = new BitmapTextureAtlas(activity.getTextureManager(), 302,
				302, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		vokaliTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				vokaliTA, activity, "vokali.png", 0, 0);

		// tarakimu button
		tarakimuTA = new BitmapTextureAtlas(activity.getTextureManager(), 302,
				302, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		tarakimuTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				tarakimuTA, activity, "tarakimu.png", 0, 0);

		// maumbo btn
		maumboTA = new BitmapTextureAtlas(activity.getTextureManager(), 302,
				302, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		maumboTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				maumboTA, activity, "maumbo.png", 0, 0);

		// rangi
		rangiTA = new BitmapTextureAtlas(activity.getTextureManager(), 302,
				302, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		rangiTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				rangiTA, activity, "rangi.png", 0, 0);

		// load animation
		animTA = new BitmapTextureAtlas(activity.getTextureManager(), 2304,
				2880, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		animTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				animTA, activity.getAssets(), "kakanchuiwave.png", 0, 0, 4, 5);

		// load font
		FontFactory.setAssetBasePath("font/");
		final ITexture fontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font = FontFactory.createFromAsset(activity.getFontManager(),
				fontTexture, activity.getAssets(), "agbookrounded-bold.otf",
				40, true, Color.parseColor("#FFFFFF"));
		font.load();

		final ITexture fontTexture1 = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		white_font = FontFactory.createFromAsset(activity.getFontManager(),
				fontTexture1, activity.getAssets(), "agbookrounded-bold.otf",
				40, true, Color.parseColor("#FFFFFF"));
		white_font.load();

		// load sounds
		loadSounds();

		// load bg music
		loadMusic();

		// add TA to array
		array[0] = bgTA;
		array[1] = vokaliTA;
		array[2] = tarakimuTA;
		array[3] = maumboTA;
		array[4] = rangiTA;
		array[5] = animTA;
		array[6] = settingsTA;
		array[7] = settings_iconTA;
		array[8] = btn_bgMusicTA;
		array[9] = menubgTA;
		array[10] = btnExitTA;
		array[11] = padlockTA;

		return array;
	}

	public SpriteBackground Background() {

		final float centerX = (mCamera.getWidth() - bgTR.getWidth()) / 2;
		final float centerY = (mCamera.getHeight() - bgTR.getHeight()) / 2;
		SpriteBackground bg = new SpriteBackground(new Sprite(centerX, centerY,
				bgTR, engine.getVertexBufferObjectManager()));

		return bg;
	}

	public AnimatedSprite kakaAnim() {
		AnimatedSprite kakaAnim = new AnimatedSprite(0, 0, animTR,
				engine.getVertexBufferObjectManager());
		// kakaAnim.setPosition(-110, mCamera.getHeight() - kakaAnim.getHeight()
		// + 95);
		kakaAnim.setPosition(-110, mCamera.getHeight() - kakaAnim.getHeight()
				+ 135);
		kakaAnim.setScale(0.6f);

		return kakaAnim;
	}

	public ButtonSprite vokaliButton() {
		ButtonSprite vokali = new ButtonSprite(0, 0, vokaliTR,
				activity.getVertexBufferObjectManager());
		final float posX = (mCamera.getWidth() - vokali.getWidth() + tarakimuTR
				.getWidth()) / 2;// - PADDING;
		final float posY = (mCamera.getHeight() - vokali.getHeight()) / 2 - 30;

		vokali.setSize(sizeX, sizeY);
		vokali.setPosition(posX, posY);

		vokali.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				vokaliSound.play();
				pButtonSprite.registerEntityModifier(new ScaleModifier(0.3f,
						0.8f, 1.0f));

				
				SceneManager.getInstance()
						.setCurrentScene(AllScenes.SOMAVOKALI);

			}
		});

		
		return vokali;
	}

	public Text vokaliLable() {
		// vokali lable
		Text vokaliLable = new Text(0, 0, font,
				"Dennoh Games \n Nodem studios",
				engine.getVertexBufferObjectManager());
		vokaliLable.setText("Vokali");
		vokaliLable.setPosition(vokaliButton().getX() + 30, vokaliButton()
				.getY() + vokaliButton().getHeight() - 10);

		return vokaliLable;
	}

	public ButtonSprite tarakimuButton() {
		// Tarakimu
		ButtonSprite tarakimu = new ButtonSprite(0, 0, tarakimuTR,
				activity.getVertexBufferObjectManager());
		tarakimu.setSize(sizeX, sizeY);
		tarakimu.setPosition(vokaliButton().getX() + tarakimu.getWidth() + 40,
				vokaliButton().getY());

		tarakimu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				pButtonSprite.registerEntityModifier(new ScaleModifier(0.3f,
						0.8f, 1.0f));

				tarakimuSound.play();

				SceneManager.getInstance().setCurrentScene(
						AllScenes.SOMATARAKIMU);
			}
		});
		return tarakimu;
	}

	public Text tarakimuLable() {
		// Tarakimu Label
		Text tarakimuLable = new Text(0, 0, font,
				"Dennoh Games \n Nodem studios",
				engine.getVertexBufferObjectManager());
		tarakimuLable.setText("Tarakimu");
		tarakimuLable.setPosition(vokaliButton().getX()
				+ tarakimuButton().getWidth() + 40, vokaliButton().getY()
				+ tarakimuButton().getHeight() - 10);
		return tarakimuLable;
	}

	public ButtonSprite maumboButton() {
		// maumbo
		ButtonSprite maumbo = new ButtonSprite(0, 0, maumboTR,
				activity.getVertexBufferObjectManager());
		maumbo.setSize(sizeX, sizeY);
		maumbo.setPosition(vokaliButton().getX(), vokaliButton().getY()
				+ maumbo.getHeight() + 30);

		maumbo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				pButtonSprite.registerEntityModifier(new ScaleModifier(0.3f,
						0.8f, 1.0f));
				maumboSound.play();

				if(activity.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getBoolean("hasPaid", false))
				SceneManager.getInstance()
						.setCurrentScene(AllScenes.SOMAMAUMBO);
				else
					showTrialVersionDialog();
			}
		});
		if(!activity.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getBoolean("hasPaid", false))
		maumbo.attachChild(getPadlock());
		return maumbo;
	}

	public Text maumboLable() {
		// Maumbo Label
		Text maumboLable = new Text(0, 0, font,
				"Dennoh Games \n Nodem studios",
				engine.getVertexBufferObjectManager());
		maumboLable.setText("Maumbo");
		maumboLable.setPosition(vokaliButton().getX() + 10, vokaliButton()
				.getY()
				+ maumboButton().getHeight()
				+ vokaliButton().getHeight() + 10);

		return maumboLable;
	}

	public ButtonSprite rangiButton() {
		// rangi
		final ButtonSprite rangi = new ButtonSprite(0, 0, rangiTR,
				activity.getVertexBufferObjectManager());
		rangi.setSize(sizeX, sizeY);
		rangi.setPosition(vokaliButton().getX() + rangi.getWidth() + 50,
				vokaliButton().getY() + rangi.getHeight() + 30);

		rangi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				rangiSound.play();
				pButtonSprite.registerEntityModifier(new ScaleModifier(0.3f,
						0.8f, 1.0f));

				rangi.registerEntityModifier(new ScaleModifier(0.3f, 1.0f, 1.2f));

				if(activity.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getBoolean("hasPaid", false))
				SceneManager.getInstance().setCurrentScene(AllScenes.SOMARANGI);
				else
					showTrialVersionDialog();
			}
		});
		if(!activity.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getBoolean("hasPaid", false))
			rangi.attachChild(getPadlock());
		return rangi;
	}

	protected void showTrialVersionDialog() {
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				View view = View.inflate(activity, R.layout.payment, null);
				AlertDialog.Builder bd = new AlertDialog.Builder(activity);
				bd.setTitle("Trial version Alert");
				bd.setView(view);
				bd.setCancelable(false);
				bd.setPositiveButton("Go premium now", new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						showPaymentDialog();

					}
				});
				bd.setNegativeButton("No Thanks!", new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
					}
				});
				bd.show();
			}
		});
		

		
	}
	
	protected void showPaymentDialog() {
		AlertDialog.Builder bd = new AlertDialog.Builder(activity);
		bd.setTitle("Choose  Payment Method");
		bd.setItems(new String[] { "Mpesa", "Credit Card", "Paypal" },
				new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0:
							showMpesaPaymentOption();
							break;

						default:
							break;
						}
					}

					private void showMpesaPaymentOption() {
						AlertDialog.Builder bd = new AlertDialog.Builder(activity);
						bd.setTitle("Lipa na Mpesa");
						bd.setCancelable(false);
						bd.setMessage(activity.getResources().getString(R.string.mpesa));
						bd.setPositiveButton("Go to Mpesa", new Dialog.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
								activity.startActivity(activity.getPackageManager().getLaunchIntentForPackage("com.android.stk"));
							}
						});
						bd.setNegativeButton("Cancel",new Dialog.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
						bd.show();
						
					}
				});

		bd.setCancelable(false);
	
		bd.setNeutralButton("Cancel", new Dialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		bd.show();

	}


	public Text rangiLable() {
		// rangi label
		Text rangiLable = new Text(0, 0, font, "Dennoh Games \n Nodem studios",
				engine.getVertexBufferObjectManager());
		rangiLable.setText("Rangi");
		rangiLable.setPosition(vokaliButton().getX() + rangiButton().getWidth()
				+ 80, vokaliButton().getY() + rangiButton().getHeight()
				+ tarakimuButton().getHeight() + 10);
		return rangiLable;
	}

	private void loadSounds() {
		SoundFactory.setAssetBasePath("mfx/Home/");
		try {
			vokaliSound = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Tusome Vokali.aac");
			tarakimuSound = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Tusome takrimu.aac");
			maumboSound = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Tusome Maumbo.aac");
			rangiSound = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "Tusome Rangi.aac");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadMusic() {
		MusicFactory.setAssetBasePath("mfx/");
		try {
			bgMusic = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "POL-snowy-hill-short.wav");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Music getBackgroundMusic() {
		return this.bgMusic;
	}

	public ITextureRegion getSettingsBGTA() {

		return settingsTR;
	}

	public ButtonSprite getSettingsButton() {
		ButtonSprite btn = new ButtonSprite(0, 10, settings_iconTR,
				activity.getVertexBufferObjectManager());
		btn.setScale(0.6f);

		return btn;
	}

	ButtonSprite btn;

	public ButtonSprite getBtnMusicBg() {

		btn = new ButtonSprite(50, 140, btn_bgMusicTR,
				activity.getVertexBufferObjectManager());
		btn.attachChild(bgMusicState());
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				pButtonSprite.registerEntityModifier(new ScaleModifier(0.3f,
						0.8f, 1.0f));

				if (bgMusic != null)
					if (bgMusic.isPlaying()) {
						bgMusic.pause();

						activity.getSharedPreferences("PREFS",
								Context.MODE_PRIVATE).edit()
								.putBoolean("music", false).commit();
					} else {
						if (activity.getSharedPreferences("PREFS",
								Context.MODE_PRIVATE).getBoolean("music", true) == false)
							bgMusic.resume();
						else
							bgMusic.play();

						activity.getSharedPreferences("PREFS",
								Context.MODE_PRIVATE).edit()
								.putBoolean("music", true).commit();
					}

				btn.detachChildren();
				btn.attachChild(bgMusicState());

			}
		});
		return btn;
	}

	public Text bgMusicState() {
		// rangi label

		Text text = new Text(100, 25, white_font,
				"Dennoh Games \n Nodem studios",
				engine.getVertexBufferObjectManager());
		text.setText("MUSIC "
				+ (activity.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
						.getBoolean("music", true) ? " ON" : " OFF"));
		return text;
	}

	public Sprite getBlueMenuBackground() {
		Sprite sp = new Sprite(290, -100, menubgTR,
				activity.getVertexBufferObjectManager());
		sp.setScale(0.7f);
		return sp;
	}

	public ButtonSprite getExitButton() {
		ButtonSprite exit = new ButtonSprite(50, 540, btnExitTR,
				activity.getVertexBufferObjectManager());

		return exit;
	}

	public Sprite getPadlock() {
		Sprite padlock = new Sprite(10, 10, padlockTR,
				activity.getVertexBufferObjectManager());
		padlock.setScale(0.4f);
		return padlock;
	}

}
