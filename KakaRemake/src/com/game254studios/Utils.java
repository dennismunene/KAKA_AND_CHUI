package com.game254studios;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.modifier.ease.EaseLinear;

public class Utils {

	
	public static void fadeOut(Sprite mSprite){
		FadeOutModifier prFadeOutModifier = new FadeOutModifier(2.0f,EaseLinear.getInstance());
		mSprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mSprite.registerEntityModifier(prFadeOutModifier);
	}
}
