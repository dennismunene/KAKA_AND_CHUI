package com.game254studios.view;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class LoadingSceneView extends MyView {

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;

	private BitmapTextureAtlas LoadingTA;
	private ITiledTextureRegion LoadingTR;

	public LoadingSceneView(BaseGameActivity act, Engine eng, Camera cam) {

		this.activity = act;
		this.engine = eng;
		this.mCamera = cam;

	}

	@Override
	public ITexture[] getTextureAtlases() {
		ITexture[] array = new ITexture[1];

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		LoadingTA = new BitmapTextureAtlas(activity.getTextureManager(), 2355,
				990);
		LoadingTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				LoadingTA, activity.getAssets(), "loading_anim.png", 0, 0, 3, 2);

		// LoadingTA.load();

		// add texture to array
		array[0] = LoadingTA;

		return array;
	}
	
	
	public AnimatedSprite getLoadingImage(){
			
		AnimatedSprite logoImage=new AnimatedSprite(0, 0, LoadingTR, engine.getVertexBufferObjectManager());
		logoImage.setPosition((this.mCamera.getWidth() - logoImage.getWidth()) / 2,
				(this.mCamera.getHeight() - logoImage.getHeight()) / 2);
		
		
		return logoImage;
		
	}

}
