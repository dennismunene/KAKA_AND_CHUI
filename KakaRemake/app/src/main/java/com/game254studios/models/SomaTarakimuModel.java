package com.game254studios.models;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.ui.activity.BaseGameActivity;

import com.game254studios.view.PauseMenu;
import com.game254studios.view.SomaTarakimuView;

public class SomaTarakimuModel extends Model{
	
	
	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;
	private SomaTarakimuView  view;
	
	public SomaTarakimuModel(BaseGameActivity act,Engine eng,Camera cam) {
		this.activity=act;
		this.engine =eng;
		this.mCamera = cam;
		
		view = new SomaTarakimuView(act, eng, cam);
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
	
	
	public Sound getTusomeSound(){
		return view.tusomeSound();
	}

	public SpriteBackground getBackground(){
		return view.background();
	}
	
	
	public Sound getButtonSound(){
		return view.getButtonSound();
	}
	
	public Sprite getPauseMenu(){
		return view.getPauseMenu();
	}
	
	public ButtonSprite getBackButton(){
		return view.backButton();
	}
	
}
