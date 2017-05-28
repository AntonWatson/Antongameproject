package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector3;

public class PlayerCharacter extends BaseCharacter{
	BaseScene baseScene;	
	float Xposition;
	float Yposition;
	float XSpeed;
	float YSpeed;
	float targetXpos;
	float targetYpos;
	 PlayerCharacter (BaseScene aBaseScene){
		
		baseScene = aBaseScene;
		
		
		
	}
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.A) {
			targetXpos -= baseScene.tilePixelWidth;

		}
		if(keycode == Input.Keys.D) {
			targetXpos += baseScene.tilePixelWidth;

		}
		if(keycode == Input.Keys.S) {
			targetYpos -= baseScene.tilePixelHeight;

		}
		if(keycode == Input.Keys.W) {
			targetYpos += baseScene.tilePixelHeight;
		}


		return false;
	}
	
	public boolean keyUp(int keycode) {

		return false;
	}

	
	public boolean keyTyped(char character) {

		return false;
	}
	public boolean collision(int posX, int posY){
		
		int tileX = posX/baseScene.tilePixelWidth;
		int tileY = posY/baseScene.tilePixelHeight;
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer)baseScene.tiledMap.getLayers().get("Colisiones");
		Cell tileID = collisionLayer.getCell(tileX,tileY);
		if(tileID == null){
			return false;
			
		}else{
		return true;
		}
	}
		public boolean updateSpriteLocation(float elapsedTime){
		float speed = 96;
		TextureMapObject character = (TextureMapObject)baseScene.tiledMap.getLayers().get("objects").getObjects().get(0);
		int characterWidth = character.getTextureRegion().getRegionWidth();
		int characterHeight = character.getTextureRegion().getRegionHeight();
		
		float oldX = Xposition;
		float oldY = Yposition;

		if(Math.abs(Xposition - targetXpos)< 3) {
			Xposition = targetXpos;		
		} else if(Xposition > targetXpos){
			oldX = Xposition;
			Xposition -= speed * elapsedTime;
		} else if(Xposition < targetXpos){
			oldX = Xposition;
			Xposition += speed * elapsedTime;;
		}

		if(Math.abs(Yposition - targetYpos)< 3) {
			Yposition = targetYpos;
		} else  if(Yposition > targetYpos) {
			oldY = Yposition;
			Yposition -= speed * elapsedTime;
		}
		else if(Yposition < targetYpos){
			oldY = Yposition;
			Yposition += speed * elapsedTime;
		}

		if(Xposition < 0){
			Xposition = 0;
		} else if((Xposition + characterWidth) > baseScene.mapPixelWidth){
			Xposition = baseScene.mapPixelWidth-characterWidth;
		}    
 	
		if(Yposition < 0){
			Yposition = 0;
		} else if((Yposition + characterHeight) > baseScene.mapPixelHeight){
			Yposition = baseScene.mapPixelHeight-characterHeight;
		}	
		
		if ((collision((int)Xposition,(int)Yposition)) ||
			(collision((int)Xposition+characterWidth-2,(int)Yposition)) ||
			(collision((int)Xposition,(int)Yposition+characterHeight-2)) ||
			(collision((int)Xposition+characterWidth-2,(int)Yposition+characterHeight-2)))
		{
			
			Xposition = (int)oldX;
			Yposition = (int)oldY;
			targetXpos = Xposition;
			targetYpos = Yposition;
		}
		Vector3 position = new Vector3(Xposition,Yposition,0);
		character.setX((float)position.x);
		character.setY((float)position.y);
		
		return true;
	}
}
