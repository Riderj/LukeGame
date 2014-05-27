package com.projects.riderj.level.tiles;

import com.projects.riderj.gfx.Screen;
import com.projects.riderj.level.Level;

public class BasicTile extends Tile{
	
	protected int tileID;
	protected int tileColor;

	public BasicTile(int id,int x, int y, int tileColor) {
		super(id, false,false);
		this.tileID = x+y;
		this.tileColor = tileColor;
	}

	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileID, tileColor);
	}
}
