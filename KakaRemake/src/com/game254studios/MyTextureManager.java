package com.game254studios;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;

/**
 * @author Kawawa Dennis***/
public class MyTextureManager {

	
	ITexture [] array;
	TextureManager manager;
	public MyTextureManager(ITexture[] textureAtlases) {
		manager =new TextureManager();
		array=textureAtlases;
	}
	
	
	public void loadTextures(){
		for(ITexture texture : array)
			texture.load();
		
	}
	
	public void unLoadTextures(){
		for(ITexture texture : array)
			texture.unload();
	}
}
