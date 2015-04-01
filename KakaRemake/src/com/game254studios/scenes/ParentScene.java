package com.game254studios.scenes;

import org.andengine.entity.scene.Scene;

/**
 * @author Kawawa Dennis
 **/
public abstract class ParentScene extends Scene {

	public abstract void loadResources();

	public abstract Scene createScene();

	public  abstract void unloadResources();
	
	

}
