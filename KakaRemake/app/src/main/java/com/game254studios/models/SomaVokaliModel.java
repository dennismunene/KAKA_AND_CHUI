package com.game254studios.models;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.ui.activity.BaseGameActivity;

import com.game254studios.view.SomaVokaliView;

public class SomaVokaliModel extends Model{

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;
	private SomaVokaliView  view;
	
	public SomaVokaliModel(BaseGameActivity act,Engine eng,Camera cam) {
		this.activity=act;
		this.engine =eng;
		this.mCamera = cam;
		
		view = new SomaVokaliView(act, eng, cam);
	}

	@Override
	public ITexture[] getResources() {
		
		return view.getTextureAtlases();
	}
	
	
	
	public ButtonSprite getNextButton(){
		return view.nextButton();
	}
	
	public ButtonSprite getPrevButton(){
		return view.prevButton();
	}
	
	public ButtonSprite[] getSomaItems(){
		return view.getSomaItems();
	}
	
	
	public Sound[] getSomaSounds(){
		return view.getSounds();
	}
	


	public SpriteBackground getBackground(){
		return view.background();
	}
	
	
	public Sound getButtonSound(){
		return view.getButtonSound();
	}
	
	public ButtonSprite getBackButton(){
		return view.backButton();
	}
	
	private ButtonSprite getSomaItem(int index) {
		return view.getSomaItems()[index];
	}



	
}
