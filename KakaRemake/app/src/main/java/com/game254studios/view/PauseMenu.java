package com.game254studios.view;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class PauseMenu {

	private BaseGameActivity activity;
	
	private BitmapTextureAtlas bgTA;
	private ITextureRegion bgTR;

	public PauseMenu(BaseGameActivity act) {

		this.activity = act;
		loadResources();
	}

	public void loadResources() {
		// background
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		bgTA = new BitmapTextureAtlas(activity.getTextureManager(), 488, 686,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		bgTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bgTA,
				activity, "pause_bg.png", 0, 0);
		bgTA.load();
	}
	
	public Sprite getPauseMenu(){
		Sprite pauseMenu = new Sprite(150, -70, bgTR, activity.getVertexBufferObjectManager());
		pauseMenu.setScale(0.7f);
		return pauseMenu;
		
	}
}
