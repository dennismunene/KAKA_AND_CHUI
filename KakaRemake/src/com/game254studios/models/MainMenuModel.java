package com.game254studios.models;

import org.andengine.audio.music.Music;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import com.game254studios.scenes.MainMenuScene;
import com.game254studios.view.MainMenuView;

public class MainMenuModel extends Model{

	private BaseGameActivity activity;
	private Engine engine;
	private Camera mCamera;
	private MainMenuView  view;
	private MainMenuScene scene;
	
	public MainMenuModel(MainMenuScene scene,BaseGameActivity act,Engine eng,Camera cam) {
		this.scene = scene;
		this.activity=act;
		this.engine =eng;
		this.mCamera = cam;
		view = new MainMenuView(activity,engine,mCamera);
	}
	
	public ITexture[] getResources(){
		
		return view.getTextureAtlases();
	}
	
	public SpriteBackground getSpriteBackground(){
		return view.Background();
	}
	
	public AnimatedSprite getKakaAnim(){
		return view.kakaAnim();
	}
	
	
	public ButtonSprite getVokaliButton(){
		return view.vokaliButton();
	}
	
	
	public Text getVokaliLabel(){
		return view.vokaliLable();
	}
	
	
	public ButtonSprite getTarakimuButton(){
		return view.tarakimuButton();
	}
	
	public Text getTarakimuLabel(){
		return view.tarakimuLable();
	}
	
	
	public ButtonSprite getMaumboButton(){
		return view.maumboButton();
	}
	
	public Text getMaumboLabel(){
		return view.maumboLable();
	}
	
	
	public ButtonSprite getRangiButton(){
		return view.rangiButton();
	}
	
	public Text getRangiLabel(){
		return view.rangiLable();
	}
	

	public Music getBacjkgroundMusic(){
		return view.getBackgroundMusic();
	}
	
	public ITextureRegion getSettingsBgTA(){
		return view.getSettingsBGTA();
	}
	
	public ButtonSprite getSettingsButton(){
		return view.getSettingsButton();
	}
	
	public Sprite getBlueMenuBackground()
	{
		return view.getBlueMenuBackground();
	}
	
	public class Settings extends Sprite{
		public Settings() {
			super(280,-100, view.getSettingsBGTA(), activity.getVertexBufferObjectManager());
			setScale(0.7f);
			
			
		final	ButtonSprite music = view.getBtnMusicBg();
			scene.registerTouchArea(music);
		
			attachChild(music);
			
		final	ButtonSprite exit = view.getExitButton();
			scene.registerTouchArea(exit);
			exit.setOnClickListener(new ButtonSprite.OnClickListener() {
				
				@Override
				public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
						float pTouchAreaLocalY) {
					exit.registerEntityModifier(new ScaleModifier(0.3f,
							0.8f, 1.0f));
					
					scene.setBlueBgVisible();
					scene.unregisterTouchArea(music);
					scene.unregisterTouchArea(exit);
				}
			});
			attachChild(exit);
			
		}
	}
	
}
