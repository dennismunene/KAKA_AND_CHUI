package com.game254studios.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import com.game254studios.MyTextureManager;
import com.game254studios.models.LoadingSceneModel;


public class LoadingScene extends ParentScene{

	private LoadingSceneModel model;
	private BaseGameActivity activity;
	private MyTextureManager textureManager;
	private Engine engine;
	private Camera mCamera;

	public LoadingScene(BaseGameActivity act,Engine eng,Camera cam) {
		this.activity = act;
		this.engine =eng;
		this.mCamera = cam;
		
		model = new LoadingSceneModel(act,eng,cam);
		textureManager = new MyTextureManager(model.getResources());
	}

	
	@Override
	public void loadResources() {
		textureManager.loadTextures();
		
	}

	@Override
	public Scene createScene() {
		setBackground(new Background(Color.BLACK));
		
		AnimatedSprite anim = model.getLoaingImage();
		attachChild(anim);
		anim.animate(200);
		
		return this;
	}

	@Override
	public void unloadResources() {
		textureManager.unLoadTextures();
		
	}

}
