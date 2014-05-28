package com.projects.riderj.level;

import java.util.ArrayList;

import com.projects.riderj.entity.Entity;
import com.projects.riderj.gfx.Screen;
import com.projects.riderj.level.tiles.Tile;

public class Level {
	private byte[] tiles;
	public int width;
	public int height;
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public Level(int width, int height){
		tiles = new byte[width*height];
		this.width = width;
		this.height = height;
		this.generateLevel();
	}
	
	public void tick(){
		for(Entity e : entities){
			e.tick();
		}
	}

	public void generateLevel() {
		for(int y = 0; y < height; y ++){
			for(int x = 0; x < width; x ++){
				if(x*y % 10 < 2){
					tiles[x+y*width] = Tile.GRASS.getID();			
				}else{
					tiles[x+y*width] = Tile.STONE.getID();			
				}
			}
		}
	}
	
	public void renderTiles(Screen screen, int xOffset, int yOffset){
		
		/*
		 * The checks below will prevent us from going past the edge of the screen, and will prevent the screen from moving if the sprite is not at the edge of the screen.
		 */
		if(xOffset < 0) xOffset = 0;
		if(xOffset > ((width << 3) - screen.width)) xOffset = ((width << 3) - screen.width);
		if(yOffset < 0) yOffset = 0;
		if(yOffset > ((height << 3) - screen.height)) yOffset = ((height << 3) - screen.height);
		
		screen.setOffset(xOffset,yOffset);
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				getTile(x,y).render(screen, this, x<<3, y<<3); //We shift left by 3 because each sprite is 8bitsx8bits
			}
		}
	}
	
	public void renderEntities(Screen screen){
		for(Entity e : entities){
			e.render(screen);
		}
	}

	private Tile getTile(int x, int y) {
		if(x<0 || x > width || y<0 || y> height) return Tile.VOID;
		return Tile.tiles[tiles[x+y*width]];
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}
}
