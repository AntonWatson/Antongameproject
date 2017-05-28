package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class BaseScene{
	Texture img;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	SpriteBatch sb;
	Texture texture;
	Sprite sprite;
	MapLayer objectLayer;
	TextureRegion textureRegion;
	Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
	Texture walkSheet;
	SpriteBatch spriteBatch;
	
	float mapPixelWidth;
	float mapPixelHeight;
	int tilePixelWidth; 
	int tilePixelHeight; 
	 BaseScene() {
		
		tiledMap = new TmxMapLoader().load("mapita.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
		
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
}
