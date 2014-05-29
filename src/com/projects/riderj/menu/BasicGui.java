package com.projects.riderj.menu;

import com.projects.riderj.gfx.Screen;

public abstract class BasicGui {
	protected int x,y,w, h;
	protected String message;
	protected Screen screen;
	
	public BasicGui(Screen screen, int x, int y,int w,int h, String message){
		this.screen = screen;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.message = message;
	}
	
	public abstract void tick();
	public abstract  void render(Screen screen);
}
