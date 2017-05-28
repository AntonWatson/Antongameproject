package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	
	BaseScene baseScene;
	EntityController entityController;
	CameraController cameraController;
	
	
	@Override public void create () {
		
		Gdx.input.setInputProcessor(this);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		baseScene = new BaseScene();
		entityController = new EntityController(baseScene);
		cameraController = new CameraController((int)w,(int)h,entityController,baseScene);
	}	

	@Override
	public void render () {
		double elapsedTime = Gdx.graphics.getDeltaTime();
		entityController.movement(elapsedTime);
		cameraController.render();
	}
	@Override
	public boolean keyDown(int keycode){
	return	entityController.playerCharacter.keyDown(keycode);
		
	}
	@Override
	public boolean keyUp(int keycode){
	return	entityController.playerCharacter.keyUp(keycode);
	}
	@Override
	public boolean keyTyped(char character){
		
	return	entityController.playerCharacter.keyTyped(character);
		
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}