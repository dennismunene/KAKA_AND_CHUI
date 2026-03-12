package com.game254studios.scenes;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.ui.activity.BaseGameActivity;

import android.os.Handler;
import android.util.Log;

import com.game254studios.MyTextureManager;
import com.game254studios.SceneManager;
import com.game254studios.SceneManager.AllScenes;
import com.game254studios.Utils;
import com.game254studios.models.SpriteSoundPair;
import com.game254studios.models.ZoeziVokaliModel;

public class ZoeziVokaliScene extends ParentScene implements OnClickListener {

	private ZoeziVokaliModel model;
	private BaseGameActivity activity;
	private MyTextureManager textureManager;
	private Engine engine;
	private Camera mCamera;
	private String TAG = ZoeziVokaliScene.class.getSimpleName();
	private Sprite greenTick, redx;

	public ZoeziVokaliScene(BaseGameActivity act, Engine eng, Camera cam) {
		this.activity = act;
		this.engine = eng;
		this.mCamera = cam;

		model = new ZoeziVokaliModel(activity, engine, mCamera);
		textureManager = new MyTextureManager(model.getResources());
	}

	@Override
	public void loadResources() {

		textureManager.loadTextures();
	}

	@Override
	public Scene createScene() {
		setBackground(model.getBackground());

		for (SpriteSoundPair obj : model.getZoeziItems())
			qaList.add(obj);

		setZoeziItems();

		ButtonSprite back = model.getBackButton();
		registerTouchArea(back);
		attachChild(back);

		// AnimatedSprite anim = model.getAnim(true);

		// attachChild(anim);
		// anim.animate(90);

		return this;
	}

	@Override
	public void unloadResources() {
		// TODO Auto-generated method stub
		textureManager.unLoadTextures();
	}

	private ArrayList<SpriteSoundPair> qaList = new ArrayList<SpriteSoundPair>();
	private ArrayList<SpriteSoundPair> cache = new ArrayList<SpriteSoundPair>();
	private int position = 0;
	private boolean isFirst = true;
	private ArrayList<Sound> recent = new ArrayList<Sound>();
	private ButtonSprite sprite1, sprite2, sprite3, sprite4;
	private AnimatedSprite anim;

	private void setZoeziItems() {
		
		randomiseqaList();

		// play sound for first zoezi item
		if (isFirst) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(1000);
						Log.i(TAG, "cache size " + cache.size()
								+ " getting index " + position);
						cache.get(position).getSound().play();

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		} else {
			Log.i(TAG, "cache size " + cache.size() + " getting index "
					+ position);
			cache.get(position).getSound().play();
		}

		// init sprites

		sprite1 = cache.get(0).getButtonSprite();
		sprite2 = cache.get(1).getButtonSprite();
		sprite3 = cache.get(2).getButtonSprite();
		sprite4 = cache.get(3).getButtonSprite();

		// set scale
		sprite1.setScale(.5f);
		sprite2.setScale(.5f);
		sprite3.setScale(.5f);
		sprite4.setScale(.5f);

		// set position on the screen
		sprite1.setPosition(10, -80);
		sprite2.setPosition(10, mCamera.getHeight() - sprite2.getHeight() + 80);
		sprite3.setPosition(mCamera.getWidth() - sprite3.getWidth() + 80, -80);
		sprite4.setPosition(mCamera.getWidth() - sprite4.getWidth() + 80,
				mCamera.getHeight() - sprite4.getHeight() + 70);

		// register listener
		sprite1.setOnClickListener(this);
		sprite2.setOnClickListener(this);
		sprite3.setOnClickListener(this);
		sprite4.setOnClickListener(this);

		// register touch area
		registerTouchArea(sprite1);
		registerTouchArea(sprite2);
		registerTouchArea(sprite3);
		registerTouchArea(sprite4);

		

				attachChild(sprite1);
				attachChild(sprite2);
				attachChild(sprite3);
				attachChild(sprite4);
		

		sprite1.registerEntityModifier(new FadeInModifier(1.0f));
		sprite2.registerEntityModifier(new FadeInModifier(1.0f));
		sprite3.registerEntityModifier(new FadeInModifier(1.0f));
		sprite4.registerEntityModifier(new FadeInModifier(1.0f));

	}

	private void randomiseqaList() {
		Random r = new Random();
		position = r.nextInt(4);// randomise the position of the answer

		cache.clear();
		while (true) {
			if (cache.size() == qaList.size())
				break;

			int i = r.nextInt(qaList.size());
			Log.i(TAG, "qaList size " + qaList.size() + " getting index " + i);
			cache.add(qaList.get(i));// randomise
										// qaList

			// remove any duplicate entries
			Set<SpriteSoundPair> set = new LinkedHashSet<SpriteSoundPair>(cache);
			cache.clear();
			cache.addAll(set);

		}

		// handle stupid repeatitions
		if (!recent.isEmpty()) {
			while (true) {
				Log.i(TAG, "cache size " + cache.size() + " getting index "
						+ position);
				if (!recent.contains(cache.get(position).getSound()))
					break;
				else if (recent.size() == qaList.size()) {
					// end of stage
					break;
				} else
					position = r.nextInt(4);
			}
		}
		Log.i(TAG, "cache size " + cache.size() + " getting index " + position);
		recent.add(cache.get(position).getSound());

	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {

		// disable clicks on these first\
		if (sprite1 != null)
			unregisterTouchArea(sprite1);
		if (sprite2 != null)
			unregisterTouchArea(sprite2);
		if (sprite3 != null)
			unregisterTouchArea(sprite3);
		if (sprite4 != null)
			unregisterTouchArea(sprite4);

		if (pButtonSprite == cache.get(position).getButtonSprite()) {
			isFirst = false;
			greenTick = model.getGreenTick();
			greenTick.setPosition(pButtonSprite.getX() - 40,
					pButtonSprite.getY() - 40);
			activity.runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					attachChild(greenTick);
				}
			});

			greenTick.registerEntityModifier(new FadeOutModifier(2.0f));

			if (anim == null) {
				anim = model.getAnim(true);

				activity.runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(anim != null)
						attachChild(anim);
					}
				});

				anim.animate(90);
				Sound applause = model.getApplauseSound();
				applause.play();
				model.getClappingSound().play();

				gotoNext();
			}
			// play happy anim

		} else {

			redx = model.getRedX();
			redx.setPosition(pButtonSprite.getX() - 40,
					pButtonSprite.getY() - 40);
			activity.runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					attachChild(redx);
				}
			});

			redx.registerEntityModifier(new FadeOutModifier(2.0f));

			// play sad anim
			if (anim == null) {

				anim = model.getAnim(false);
				activity.runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(anim != null)
						attachChild(anim);
					}
				});
				anim.animate(90);
				Sound sadSound = model.getSadSound();
				sadSound.play();

				repeatQuestion();
			}

		}

	}

	private void repeatQuestion() {
		
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3000);
					detachAnim();
					cache.get(position).getSound().play();
					
					// re - enable click events
					if(sprite1 != null)
						registerTouchArea(sprite1);
					if(sprite2 != null)
						registerTouchArea(sprite2);
					if(sprite3 != null)
						registerTouchArea(sprite3);
					if(sprite4 != null)
						registerTouchArea(sprite4);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void detachAnim() {
				activity.runOnUpdateThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						detachChild(anim);
						detachChild(greenTick);	
						detachChild(redx);	anim = null;
					}
				});
			}
		}).start();
		
		
		

	

	}

	private void gotoNext() {

		if (recent.size() != (qaList.size()-1)) {

			// set current items and remove anim
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(3000);
						
						
						fadeOutandDetachSprites();
						
						
						
						

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				private void fadeOutandDetachSprites() {
					
					
					/*FadeOutModifier prFadeOutModifier = new FadeOutModifier(1.0f,EaseLinear.getInstance());
					sprite1.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
					sprite1.registerEntityModifier(prFadeOutModifier);*/
					
					activity.runOnUpdateThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							detachChild(anim);
							anim = null;	
							detachChild(greenTick);	
							detachChild(redx);
							detachChild(sprite1);
							detachChild(sprite2);
							detachChild(sprite3);
							detachChild(sprite4);
							
							// set next items
							setZoeziItems();
						}
					});
					
					
					
				}
			}).start();

		} else {

			activity.runOnUpdateThread(new  Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					detachChild(anim);
				}
			});
			
			anim = null;
			
			gotoMainMenu();
		}

	}



	private void gotoMainMenu() {

		SceneManager.getInstance().setCurrentScene(AllScenes.LOADING);
		engine.registerUpdateHandler(new TimerHandler(3.0f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						engine.unregisterUpdateHandler(pTimerHandler);

						SceneManager.getInstance().unLoadZoeziVokaliResources();
						SceneManager.getInstance().setCurrentScene(
								AllScenes.MAINMENU);

					}
				}));

	}

}
