package com.game254studios.view;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

public class SplashView extends MyView{

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;
	
	private BitmapTextureAtlas splashTA;
	private ITextureRegion splashTR;

	
	public SplashView(BaseGameActivity act,Engine eng,Camera cam) {

		this.activity=act;
		this.engine =eng;
		this.mCamera = cam;
		
	}
	
	public ITexture[] getTextureAtlases(){
		ITexture[]  array= new ITexture[1];
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTA = new BitmapTextureAtlas(activity.getTextureManager(), 785,
				495);
		splashTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				splashTA, activity, "splash.png", 0, 0);
		
		
		//also load pause menu resources
		
		
		//add texture to array
		array[0] = splashTA;
		
		return array;
	
	}
	
	
	public Sprite getSplashImage(){
			
		Sprite logoImage=new Sprite(0, 0, splashTR, engine.getVertexBufferObjectManager());
		logoImage.setPosition((this.mCamera.getWidth() - logoImage.getWidth()) / 2,
				(this.mCamera.getHeight() - logoImage.getHeight()) / 2);
		
		
		return logoImage;
		
	}
	
	
	
	
	
	
	
}
