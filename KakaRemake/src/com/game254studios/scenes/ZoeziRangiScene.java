package com.game254studios.scenes;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.modifier.ease.EaseLinear;

import android.opengl.GLES20;
import android.widget.Toast;

import com.game254studios.MyTextureManager;
import com.game254studios.SceneManager;
import com.game254studios.Utils;
import com.game254studios.SceneManager.AllScenes;
import com.game254studios.models.SpriteSoundPair;
import com.game254studios.models.ZoeziRangiModel;
import com.game254studios.models.ZoeziTarakimuModel;
import com.game254studios.models.ZoeziVokaliModel;

public class ZoeziRangiScene extends ParentScene implements OnClickListener {

	private ZoeziRangiModel model;
	private BaseGameActivity activity;
	private MyTextureManager textureManager;
	private Engine engine;
	private Camera mCamera;
	private Sprite redx,greenTick;

	public ZoeziRangiScene(BaseGameActivity act, Engine eng, Camera cam) {
		this.activity = act;
		this.engine = eng;
		this.mCamera = cam;
		
		model = new ZoeziRangiModel(activity, engine, mCamera);
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
						cache.get(position).getSound().play();

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		} else {
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
		sprite1.setPosition(-50, -110);
		sprite2.setPosition(-50, mCamera.getHeight() - sprite2.getHeight()
				+ 110);
		sprite3.setPosition(mCamera.getWidth() - sprite3.getWidth() + 110, -110);
		sprite4.setPosition(mCamera.getWidth() - sprite4.getWidth() + 110,
				mCamera.getHeight() - sprite4.getHeight() + 100);

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

		// attach items
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

			cache.add(qaList.get(r.nextInt(qaList.size())));// randomise
															// qaList

			// remove any duplicate entries
			Set<SpriteSoundPair> set = new LinkedHashSet<SpriteSoundPair>(cache);
			cache.clear();
			cache.addAll(set);

		}

		// handle stupid repeatitions
		if (!recent.isEmpty()) {
			while (true) {
				if (!recent.contains(cache.get(position).getSound()))
					break;
				else if (recent.size() == qaList.size()) {
					// end of stage
					break;
				} else
					position = r.nextInt(4);
			}
		}
		recent.add(cache.get(position).getSound());

	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		
		//disable clicks on these first
		if(sprite1 != null)
			unregisterTouchArea(sprite1);
		if(sprite2 != null)
			unregisterTouchArea(sprite2);
		if(sprite3 != null)
			unregisterTouchArea(sprite3);
		if(sprite4 != null)
			unregisterTouchArea(sprite4);
		
		if (pButtonSprite == cache.get(position).getButtonSprite()) {
			isFirst = false;
			 greenTick = model.getGreenTick();
			greenTick.setPosition(pButtonSprite);
			attachChild(greenTick);
			greenTick.registerEntityModifier(new FadeOutModifier(2.0f));
			
			
			
			
			// got to next
			
				if (anim == null) {
					anim = model.getAnim(true);
					attachChild(anim);
					anim.animate(90);
					Sound applause =model.getApplauseSound();
					applause.play();
					model.getClappingSound().play();

					gotoNext();
				}
				// play happy anim
			
				

			

			
		}else{
			
			 redx = model.getRedX();
			redx.setPosition(pButtonSprite);
			attachChild(redx);
			
			redx.registerEntityModifier(new FadeOutModifier(2.0f));
			
			// play sad anim
			if (anim == null) {
				
				anim = model.getAnim(false);
				attachChild(anim);
				anim.animate(90);
				Sound sadSound =model.getSadSound();
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
					detachChild(anim);

					anim = null;

					cache.get(position).getSound().play();
					
					detachChild(greenTick);	
					detachChild(redx);
					//disable clicks on these first
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
		}).start();

	}

	private void gotoNext() {

		if (recent.size() != qaList.size()) {

			// set current items and remove anim
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(3000);
						detachChild(anim);
						anim = null;
						
						
						fadeOutandDetachSprites();
						
						
						
						// set next items
						setZoeziItems();

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				private void fadeOutandDetachSprites() {
					
					
					/*FadeOutModifier prFadeOutModifier = new FadeOutModifier(1.0f,EaseLinear.getInstance());
					sprite1.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
					sprite1.registerEntityModifier(prFadeOutModifier);*/
					
				
					detachChild(greenTick);	
					detachChild(redx);
					detachChild(sprite1);
					detachChild(sprite2);
					detachChild(sprite3);
					detachChild(sprite4);
					
					
				}
			}).start();

		} else {

			detachChild(anim);
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

						
						SceneManager.getInstance().unLoadZoeziRangiResources();
						SceneManager.getInstance().setCurrentScene(AllScenes.MAINMENU);

					}
				}));
		
	}

	
}

