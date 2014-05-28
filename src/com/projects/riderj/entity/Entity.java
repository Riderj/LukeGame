package com.projects.riderj.entity;

import com.projects.riderj.gfx.Screen;
import com.projects.riderj.level.Level;

public abstract class Entity {
	public int x;
	public int y;
	protected Level level;
	
	public Entity(Level level){
		init(level);
	}
	
	public void init(Level level){
		this.level = level;
	}
	public abstract void tick();
	
	public abstract void render(Screen screen);
}
