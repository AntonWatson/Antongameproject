package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraController {
	OrthographicCamera camera;
	BaseScene baseScene;
	EntityController entityController;
	
	
	 CameraController(int w, int h,EntityController aEntityController,BaseScene aBaseScene){
		entityController = aEntityController;
		baseScene = aBaseScene;
		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		
	}
	public void render(){
		updateCamera();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		baseScene.tiledMapRenderer.setView(camera);
		baseScene.tiledMapRenderer.render();
		
	}
	public void updateCamera(){
		float camPosX =Math.max(Gdx.graphics.getWidth()/2,entityController.playerCharacter.Xposition);
		float camPosY = Math.max(Gdx.graphics.getHeight()/2,entityController.playerCharacter.Yposition);
		camera.position.set(camPosX,camPosY,0);
		camera.update();
	}


}
