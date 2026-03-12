package com.game254studios;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Context;
import android.widget.Toast;

import com.game254studios.scenes.LoadingScene;
import com.game254studios.scenes.MainMenuScene;
import com.game254studios.scenes.SomaMaumboScene;
import com.game254studios.scenes.SomaRangiScene;
import com.game254studios.scenes.SomaTarakimuScene;
import com.game254studios.scenes.SomaVokaliScene;
import com.game254studios.scenes.SplashScene;
import com.game254studios.scenes.ZoeziMaumboScene;
import com.game254studios.scenes.ZoeziRangiScene;
import com.game254studios.scenes.ZoeziTarakimuScene;
import com.game254studios.scenes.ZoeziVokaliScene;
import com.game254studios.view.PauseMenu;

public class SceneManager {

	private BaseGameActivity activity;
	private Engine engine;
	private Camera cam;

	public enum AllScenes {
		SPLASH, LOADING, MAINMENU, SOMATARAKIMU, ZOEZITARAKIMU, SOMAVOKALI, ZOEZIVOKALI, SOMAMAUMBO, ZOEIMAUMBO, SOMARANGI, ZOEZIRANGI
	}

	private static SceneManager instance;

	public void setInstance(SceneManager manager) {
		SceneManager.instance = manager;
	}

	public static SceneManager getInstance() {
		return SceneManager.instance;
	}

	private AllScenes currentScene;
	private SplashScene splashScene;
	private MainMenuScene mainMenuScene;
	private SomaTarakimuScene somaTarakimuScene;
	private ZoeziTarakimuScene zoeziTarakimuScene;
	private LoadingScene loadingScene;
	private SomaVokaliScene somaVokaliScene;
	private ZoeziVokaliScene zoeziVokaliScene;
	private SomaRangiScene somaRangiScene;
	private ZoeziRangiScene zoeziRangiScene;
	private SomaMaumboScene somaMaumboScene;
	private ZoeziMaumboScene zoeziMaumboScene;
	
	public static boolean VOKALI = false;

	public SceneManager(BaseGameActivity act, Engine eng, Camera cam) {
		this.activity = act;
		this.engine = eng;
		this.cam = cam;

		splashScene = new SplashScene(act, eng, cam);
		mainMenuScene = new MainMenuScene(act, eng, cam);
		somaTarakimuScene = new SomaTarakimuScene(act, eng, cam);
		zoeziTarakimuScene = new ZoeziTarakimuScene(act, eng, cam);
		loadingScene = new LoadingScene(act, eng, cam);
		somaVokaliScene = new SomaVokaliScene(act, eng, cam);
		zoeziVokaliScene = new ZoeziVokaliScene(act, eng, cam);
		somaRangiScene = new SomaRangiScene(act, eng, cam);
		zoeziRangiScene = new ZoeziRangiScene(act, eng, cam);
		somaMaumboScene = new SomaMaumboScene(act, eng, cam);
		zoeziMaumboScene = new ZoeziMaumboScene(act, eng, cam);

	}

	public void setCurrentScene(AllScenes scene) {
		this.currentScene = scene;

		switch (scene) {
		case SPLASH:
			engine.setScene(splashScene.createScene());
			break;
		case MAINMENU:
			engine.setScene(mainMenuScene);
	
			break;
		case SOMATARAKIMU:
			engine.setScene(somaTarakimuScene);
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(1500);
						somaTarakimuScene.getSceneInitSound().play();
						
					} catch (Exception e) {

					}

				}
			}).start();
			break;
		case ZOEZITARAKIMU:

			engine.setScene(zoeziTarakimuScene.createScene());
			break;

		case SOMAVOKALI:
			engine.setScene(somaVokaliScene);
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(1500);
						
						
						if(VOKALI){
							somaVokaliScene.getLastItemsSound().play();
							VOKALI = false;
						}else{
							somaVokaliScene.getSceneInitSound().play();
							
						}
							
					} catch (Exception e) {

					}

				}
			}).start();
			break;

		case ZOEZIVOKALI:
			
			engine.setScene(zoeziVokaliScene.createScene());
			VOKALI = true ;
			break;
		case SOMARANGI:

			engine.setScene(somaRangiScene);
			
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(1500);
						somaRangiScene.getSceneInitSound().play();
						
					} catch (Exception e) {

					}

				}
			}).start();
			break;

		case ZOEZIRANGI:

			engine.setScene(zoeziRangiScene.createScene());
			break;

		case SOMAMAUMBO:
			engine.setScene(somaMaumboScene);
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(1500);
						somaMaumboScene.getSceneInitSound().play();
						
					} catch (Exception e) {

					}

				}
			}).start();
			break;

		case ZOEIMAUMBO:
			engine.setScene(zoeziMaumboScene.createScene());
			break;

		case LOADING:
			
			engine.setScene(loadingScene.createScene());
			
			break;

		default:
			break;
		}

	}
	
	public Scene getSplashScene(){ return splashScene;}
	
	public MainMenuScene getMainMenuScene(){return mainMenuScene;}

	public AllScenes getCurrentScene() {
		/*if (currentScene == AllScenes.SPLASH)
			return splashScene;
		else if (currentScene == AllScenes.MAINMENU) {

			return mainMenuScene;
		} else if (currentScene == AllScenes.SOMATARAKIMU) {

			return somaTarakimuScene;
		} else if (currentScene == AllScenes.ZOEZITARAKIMU)
			return zoeziTarakimuScene;
		else if (currentScene == AllScenes.SOMAVOKALI)
			return somaVokaliScene;
		else if (currentScene == AllScenes.ZOEZIVOKALI)
			return zoeziVokaliScene;
		else if (currentScene == AllScenes.SOMAMAUMBO)
			return somaMaumboScene;
		else if (currentScene == AllScenes.ZOEIMAUMBO)
			return zoeziMaumboScene;
		else if (currentScene == AllScenes.SOMARANGI)
			return somaRangiScene;
		else if (currentScene == AllScenes.ZOEZIRANGI)
			return zoeziRangiScene;
		else
			return null;*/
		return currentScene;
	}

	public void showPauseMenu() {

		showToast("show pausedMenu");
	}

	public void loadResourses() {

		splashScene.loadResources();

		// mainMenuScene.loadResources();

	}

	public void loadScenes() {
		somaVokaliScene.createScene();
		mainMenuScene.createScene();
		somaTarakimuScene.createScene();
		somaMaumboScene.createScene();
		somaRangiScene.createScene();
		
		//zoeziVokaliScene.createScene();
		//zoeziTarakimuScene.createScene();

		//zoeziMaumboScene.createScene();

		//zoeziRangiScene.createScene();

	}

	public void loadGameResources() {
		loadLoadingPageResources();
		loadMenuResources();
		loadSomaVokaliResources();
		//loadZoeziVokaliResources();
		loadSomaTarakimuResources();
		//loadZoeziTarakimuResources();
		loadSomaMaumboResources();
	//	loadZoeziMaumboresources();
		loadSomaRangiResources();
		//loadZoeziRangiResources();
	}

	public void loadLoadingPageResources() {
		loadingScene.loadResources();
	}

	public void unloadLoadingSceneResources() {
		loadingScene.unloadResources();
		loadingScene.dispose();
	}

	public void loadMenuResources() {
		mainMenuScene.loadResources();
	}

	public void loadSomaTarakimuResources() {
		somaTarakimuScene.loadResources();

	}

	public void loadZoeziTarakimuResources() {
		zoeziTarakimuScene.loadResources();
		
	}

	public void unLoadZoeziTarakimuResources() {
		zoeziTarakimuScene.unloadResources();
		zoeziTarakimuScene.detachChildren();
		zoeziTarakimuScene.dispose();
			
		zoeziTarakimuScene = new ZoeziTarakimuScene(activity, engine, cam);
	}

	public void unloadSplashResources() {

		splashScene.unloadResources();
		splashScene.dispose();

	}

	public void unloadMenuResources() {
		mainMenuScene.unloadResources();
		mainMenuScene.dispose();
	}

	public void unloadSomaTarakimuResources() {
		somaTarakimuScene.unloadResources();
		somaTarakimuScene.dispose();
	}

	public void loadSomaVokaliResources() {
		somaVokaliScene.loadResources();

	}

	public void unLoadSomaVokaliResources() {
		somaVokaliScene.unloadResources();

	}

	public void loadZoeziVokaliResources() {
		zoeziVokaliScene.loadResources();
		//if(!zoeziVokaliScene.isDisposed())
		//	zoeziVokaliScene.createScene();
	}

	public void unLoadZoeziVokaliResources() {
		zoeziVokaliScene.unloadResources();
		zoeziVokaliScene.detachSelf();
		zoeziVokaliScene.dispose();
		
		zoeziVokaliScene = new ZoeziVokaliScene(activity, engine, cam);
		//if(!zoeziVokaliScene.isDisposed())
			//zoeziVokaliScene.dispose();
	}

	public void loadSomaRangiResources() {
		somaRangiScene.loadResources();
	}

	public void unLoadSomaRangiResources() {
		somaRangiScene.unloadResources();
		somaRangiScene.dispose();
	}

	public void loadZoeziRangiResources() {
		zoeziRangiScene.loadResources();
	
	}

	public void unLoadZoeziRangiResources() {
		// zoeziRangiScene.registerEntityModifier(new FadeOutModifier(1.0f));
		zoeziRangiScene.unloadResources();
		zoeziRangiScene.detachChildren();
		zoeziRangiScene.dispose();
		
		zoeziRangiScene = new ZoeziRangiScene(activity, engine, cam);

	}

	public void loadSomaMaumboResources() {
		somaMaumboScene.loadResources();
		
	}

	public void unLoadSomaMaumboResources() {
		somaMaumboScene.unloadResources();
		
		somaMaumboScene.dispose();
	}

	public void loadZoeziMaumboresources() {
		zoeziMaumboScene.loadResources();
		
	}

	public void unLoadZoeziMaumboresources() {
		zoeziMaumboScene.unloadResources();
		zoeziMaumboScene.detachChildren();
		zoeziMaumboScene.dispose();

		zoeziMaumboScene = new ZoeziMaumboScene(activity, engine, cam);
	}

	public void showToast(final String text) {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(activity, text, Toast.LENGTH_LONG).show();

			}
		});
	}

}
