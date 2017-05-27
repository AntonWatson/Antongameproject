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
	Texture img;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	SpriteBatch sb;
	Texture texture;
	Sprite sprite;
	MapLayer objectLayer;
	TextureRegion textureRegion;
	Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
	Texture walkSheet;
	SpriteBatch spriteBatch;
	
	float Xposition;
	float Yposition;
	float XSpeed;
	float YSpeed;
	float mapPixelWidth;
	float mapPixelHeight;
	float targetXpos;
	float targetYpos;
	int tilePixelWidth; 
	int tilePixelHeight; 
	@Override public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("mapita.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
		Gdx.input.setInputProcessor(this);
		texture = new Texture(Gdx.files.internal("sprait.png"));

		objectLayer = tiledMap.getLayers().get("objects");
		textureRegion = new TextureRegion(texture,64,64);
     
		//createWalkAnimation();
		
		TextureMapObject tmo = new TextureMapObject(textureRegion);
		tmo.setX(0);
		tmo.setY(0);
		MapProperties prop = tiledMap.getProperties();
		objectLayer.getObjects().add(tmo);
		int mapWidth = prop.get("width", Integer.class);
		int mapHeight = prop.get("height", Integer.class);
		tilePixelWidth = prop.get("tilewidth", Integer.class);
		tilePixelHeight = prop.get("tileheight", Integer.class);
		mapPixelWidth = mapWidth * tilePixelWidth;
		mapPixelHeight = mapHeight * tilePixelHeight;
	}
	
/*	
	public void createWalkAnimation() {
		walkSheet = new Texture(Gdx.files.internal("spraitsheet.png"));
        int FRAME_COLS = 7;
        int FRAME_ROWS = 7;
        
		// Use the split utility method to create a 2D array of TextureRegions. This is 
		// possible because this sprite sheet contains frames of equal size and they are 
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, 
				walkSheet.getWidth() / FRAME_COLS,
				walkSheet.getHeight() / FRAME_ROWS);

		// Place the regions into a 1D array in the correct order, starting from the top 
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);

	}*/

	@Override
	public void render () {
		updateSpriteLocation(Gdx.graphics.getDeltaTime());
		updateCamera();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.A) {
			targetXpos -= tilePixelWidth;

		}
		if(keycode == Input.Keys.D) {
			targetXpos += tilePixelWidth;

		}
		if(keycode == Input.Keys.S) {
			targetYpos -= tilePixelHeight;

		}
		if(keycode == Input.Keys.W) {
			targetYpos += tilePixelHeight;
		}


		return false;
	}
	@Override
	public boolean keyUp(int keycode) {

		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}
	public boolean collision(int posX, int posY){
		
		int tileX = posX/tilePixelWidth;
		int tileY = posY/tilePixelHeight;
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer)tiledMap.getLayers().get("Colisiones");
		Cell tileID = collisionLayer.getCell(tileX,tileY);
		if(tileID == null){
			return false;
			
		}else{
		return true;
		}
	}
	public void updateCamera(){
		float camPosX =Math.max(Gdx.graphics.getWidth()/2,Xposition);
		float camPosY = Math.max(Gdx.graphics.getHeight()/2,Yposition);
		camera.position.set(camPosX,camPosY,0);
		camera.update();
	}
	public boolean updateSpriteLocation(float elapsedTime){
		float speed = 96;
		TextureMapObject character = (TextureMapObject)tiledMap.getLayers().get("objects").getObjects().get(0);
		int characterWidth = character.getTextureRegion().getRegionWidth();
		int characterHeight = character.getTextureRegion().getRegionHeight();
		float oldX = 0;
		float oldY = 0;

		if(Math.abs(Xposition - targetXpos)< 3) {
			oldX = Xposition;
			Xposition = targetXpos;		
		} else if(Xposition > targetXpos){
			oldX = Xposition;
			Xposition -= speed * elapsedTime;
		} else if(Xposition < targetXpos){
			oldX = Xposition;
			Xposition += speed * elapsedTime;;
		}

		if(Math.abs(Yposition - targetYpos)< 3) {
			oldY = Yposition;
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
		} else if((Xposition + characterWidth) > mapPixelWidth){
			Xposition = mapPixelWidth-characterWidth;
		}    
 	
		if(Yposition < 0){
			Yposition = 0;
		} else if((Yposition + characterHeight) > mapPixelHeight){
			Yposition = mapPixelHeight-characterHeight;
		}	
		if (collision((int)Xposition,(int)Yposition)){
			
			Xposition = (int)oldX;
			Yposition = (int)oldY;
			
		}
		Vector3 position = new Vector3(Xposition,Yposition,0);
		character.setX((float)position.x);
		character.setY((float)position.y);
		
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
		Vector3 position = camera.unproject(clickCoordinates);
		TextureMapObject character = (TextureMapObject)tiledMap.getLayers().get("objects").getObjects().get(0);
		character.setX((float)position.x);
		character.setY((float)position.y);
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