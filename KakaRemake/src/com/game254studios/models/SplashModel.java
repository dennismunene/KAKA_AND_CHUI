package com.game254studios.models;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.ui.activity.BaseGameActivity;

import com.game254studios.view.SplashView;

public class SplashModel extends Model {

	
	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;
	private SplashView  view;
	
	public SplashModel(BaseGameActivity act,Engine eng,Camera cam) {
		this.activity=act;
		this.engine =eng;
		this.mCamera = cam;
		view = new SplashView(activity,engine,mCamera);
	}
	
	public ITexture[] getResources(){
		
		
		
		return view.getTextureAtlases();
	}
	
	
	public Sprite getSplashImage(){
		
		return view.getSplashImage();
	}
	
}
