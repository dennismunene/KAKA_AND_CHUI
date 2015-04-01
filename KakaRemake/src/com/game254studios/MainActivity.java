package com.game254studios;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.BaseGameActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.game254studios.SceneManager.AllScenes;
import com.game254studios.view.PauseMenu;

/***
 * @author Kawawa Dennis
 * ****/

public class MainActivity extends BaseGameActivity {

	private Camera mCamera;
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	private SceneManager sceneManager;

	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		EngineOptions engOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
				mCamera);
		engOptions.getAudioOptions().setNeedsMusic(true);
		engOptions.getAudioOptions().setNeedsSound(true);

		return engOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		sceneManager = new SceneManager(this, this.mEngine, this.mCamera);

		sceneManager.loadResourses();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {

		sceneManager.setCurrentScene(AllScenes.SPLASH);
		pOnCreateSceneCallback.onCreateSceneFinished(sceneManager
				.getSplashScene());
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		
		mEngine.registerUpdateHandler(new TimerHandler(0.01f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);

						sceneManager.unloadSplashResources();
						sceneManager.loadGameResources();
						sceneManager.loadScenes();
						sceneManager.setCurrentScene(AllScenes.MAINMENU);
						sceneManager.setInstance(sceneManager);
					

					}
				}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}

	@Override
	public void onBackPressed() {
		if (sceneManager.getCurrentScene() == AllScenes.MAINMENU) {
			AlertDialog.Builder bd = new AlertDialog.Builder(this);
			bd.setTitle("Exit app?");
			bd.setMessage("Do you want to exit?");
			bd.setPositiveButton("Yes", new Dialog.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					finish();
				}
			});
			bd.setNegativeButton("No", new Dialog.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					//FIXME 
					getSharedPreferences("PREFS", MODE_PRIVATE).edit().putBoolean("hasPaid", true).commit();
				}
			});
			bd.show();
		} else if (sceneManager.getCurrentScene() == AllScenes.SOMAVOKALI) {
			SceneManager.getInstance().setCurrentScene(AllScenes.MAINMENU);
		} else if (sceneManager.getCurrentScene() == AllScenes.ZOEZIVOKALI) {
			SceneManager.getInstance().unLoadZoeziVokaliResources();
			SceneManager.getInstance().setCurrentScene(AllScenes.SOMAVOKALI);
		} else if (sceneManager.getCurrentScene() == AllScenes.SOMATARAKIMU) {
			SceneManager.getInstance().setCurrentScene(AllScenes.MAINMENU);
		} else if (sceneManager.getCurrentScene() == AllScenes.ZOEZITARAKIMU) {
			SceneManager.getInstance().unLoadZoeziTarakimuResources();
			SceneManager.getInstance().setCurrentScene(AllScenes.SOMATARAKIMU);
		} else if (sceneManager.getCurrentScene() == AllScenes.SOMAMAUMBO) {
			SceneManager.getInstance().setCurrentScene(AllScenes.MAINMENU);
		} else if (sceneManager.getCurrentScene() == AllScenes.ZOEIMAUMBO) {
			SceneManager.getInstance().unLoadZoeziMaumboresources();
			SceneManager.getInstance().setCurrentScene(AllScenes.SOMAMAUMBO);
		} else if (sceneManager.getCurrentScene() == AllScenes.SOMARANGI) {
			SceneManager.getInstance().setCurrentScene(AllScenes.MAINMENU);
		} else if (sceneManager.getCurrentScene() == AllScenes.ZOEZIRANGI) {
			SceneManager.getInstance().unLoadZoeziRangiResources();
			SceneManager.getInstance().setCurrentScene(AllScenes.SOMARANGI);
		}
	}
	
	@Override
	protected void onPause() {
		
		//sceneManager.getMainMenuScene().pauseBackgroundMusic();
		super.onPause();
	}
	
	@Override
	public synchronized void onPauseGame() {
		sceneManager.getMainMenuScene().pauseBackgroundMusic();
		super.onPauseGame();
	}
	
	@Override
	public synchronized void onResumeGame() {
		sceneManager.getMainMenuScene().resumeBackgroundMusic();
		super.onResumeGame();
	}

	// use this for ads
	/*
	 * @Override protected void onSetContentView() {
	 * 
	 * final RelativeLayout relativeLayout = new RelativeLayout(this); final
	 * FrameLayout.LayoutParams relativeLayoutLayoutParams = new
	 * FrameLayout.LayoutParams( RelativeLayout.LayoutParams.FILL_PARENT,
	 * RelativeLayout.LayoutParams.FILL_PARENT);
	 * 
	 * this.mRenderSurfaceView = new RenderSurfaceView(this);
	 * this.mRenderSurfaceView.setRenderer(this.mEngine, this);
	 * 
	 * 
	 * final android.widget.RelativeLayout.LayoutParams surfaceViewLayoutParams
	 * = new
	 * RelativeLayout.LayoutParams(BaseGameActivity.createSurfaceViewLayoutParams
	 * ()); surfaceViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
	 * 
	 * relativeLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
	 * 
	 * 
	 * FrameLayout frameLayout = new FrameLayout(this);
	 * 
	 * 
	 * 
	 * frameLayout.addView(txt); relativeLayout.addView(frameLayout);
	 * 
	 * this.setContentView(relativeLayout, relativeLayoutLayoutParams);
	 * 
	 * 
	 * 
	 * }
	 */

}
