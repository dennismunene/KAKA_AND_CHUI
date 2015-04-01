package com.game254studios.models;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.ui.activity.BaseGameActivity;

import com.game254studios.view.LoadingSceneView;
import com.game254studios.view.SplashView;

public class LoadingSceneModel extends Model{

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;
	private LoadingSceneView  view;
	
	public LoadingSceneModel(BaseGameActivity act,Engine eng,Camera cam) {
		this.activity=act;
		this.engine =eng;
		this.mCamera = cam;
		view = new LoadingSceneView(activity,engine,mCamera);
	}

	@Override
	public ITexture[] getResources() {
		// TODO Auto-generated method stub
		return view.getTextureAtlases();
	}
	
	public AnimatedSprite getLoaingImage(){
		return view.getLoadingImage();
	}

}
