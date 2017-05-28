package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class EntityController {

	PlayerCharacter playerCharacter;
	BaseScene baseScene;
 EntityController(BaseScene aBaseScene){
	
	baseScene = aBaseScene;
	playerCharacter = new PlayerCharacter(aBaseScene);
	}

public void movement(double elapsedTime){
	
	playerCharacter.updateSpriteLocation(Gdx.graphics.getDeltaTime());
	
	
	
	
	
}
}
